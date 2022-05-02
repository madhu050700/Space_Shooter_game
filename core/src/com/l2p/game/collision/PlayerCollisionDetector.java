package com.l2p.game.collision;

import com.l2p.game.PowerUp.PowerUpController;
import com.l2p.game.PowerUp.PowerUps;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.projectile.abstractProducts.Projectile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class PlayerCollisionDetector extends CollisionDetector {


    public static PowerUpController detectCollision(ArrayList<LinkedList<Projectile>> projectileListArray, Actor playerCharacter, PowerUpController powerUpController) {
        for (LinkedList<Projectile> projectileList : projectileListArray) {
            ListIterator<Projectile> iterator = projectileList.listIterator();
            while (iterator.hasNext()) {
                Projectile projectile = iterator.next();
                if (playerCharacter.intersects(projectile.getBoundingBox())) {
                    int health = playerCharacter.hit(projectile);
                    System.out.println("Player got hit");
                    playerCharacter.setRespawn();
                    iterator.remove();
                    if (health == 0) {
                        System.out.println("You dead mate");
                    }
                }
            }

        }
        LinkedList<PowerUps> powerUpArray;
        powerUpArray = powerUpController.getPowerUps();
        ListIterator<PowerUps> powerUpsListIterator = powerUpArray.listIterator();
        while(powerUpsListIterator.hasNext()){
            PowerUps powerUps = powerUpsListIterator.next();
            if(playerCharacter.intersects(powerUps.getBoundingBox())){
                System.out.println("Powerup absorbed");

                powerUpsListIterator.remove();
            }
        }
        powerUpController.setPowerUps(powerUpArray);
        return powerUpController;
    }


}
