package com.l2p.game.collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.l2p.game.PowerUp.PowerUpController;
import com.l2p.game.PowerUp.PowerUp;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.projectile.abstractProducts.Projectile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class PlayerCollisionDetector extends CollisionDetector {
    static Music bomb3,powerup;

    public static PowerUpController detectCollision(ArrayList<LinkedList<Projectile>> projectileListArray, Actor playerCharacter, PowerUpController powerUpController, boolean cheating) {
        bomb3 = Gdx.audio.newMusic(Gdx.files.internal("bomb3.mp3"));
        bomb3.setVolume(0.1f);
        powerup = Gdx.audio.newMusic(Gdx.files.internal("power-up1.mp3"));
        powerup.setVolume(0.1f);
        for (LinkedList<Projectile> projectileList : projectileListArray) {
            ListIterator<Projectile> iterator = projectileList.listIterator();
            while (iterator.hasNext()) {
                Projectile projectile = iterator.next();
                if (playerCharacter.intersects(projectile.getBoundingBox())) {
                    int health = playerCharacter.hit(projectile);
                    System.out.println("Player got hit");
                    //cheating mode
                    if (cheating==false) {
                        health = playerCharacter.hit(projectile);
                        playerCharacter.setRespawn();
                        if(playerCharacter.getHealth()>0){
                            bomb3.play();
                        }

                    }

                    iterator.remove();
                    if (health == 0) {
                        System.out.println("You dead mate");
                    }
                }
            }

        }
        LinkedList<PowerUp> powerUpArray;
        powerUpArray = powerUpController.getPowerUps();
        ListIterator<PowerUp> powerUpsListIterator = powerUpArray.listIterator();
        while(powerUpsListIterator.hasNext()){
            PowerUp powerUp = powerUpsListIterator.next();
            if(playerCharacter.intersects(powerUp.getBoundingBox())){
                System.out.println("Powerup absorbed");
                powerup.play();
                powerUpController.absorbedPowerUp(powerUp);
                playerCharacter.boostHealth();
                powerUpsListIterator.remove();
            }
        }
        powerUpController.setPowerUps(powerUpArray);
        return powerUpController;
    }


}
