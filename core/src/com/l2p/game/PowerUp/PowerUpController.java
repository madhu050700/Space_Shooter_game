package com.l2p.game.PowerUp;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.l2p.game.actor.abstractProducts.Actor;

import java.util.LinkedList;
import java.util.ListIterator;

public class PowerUpController {


    private LinkedList<PowerUp> powerUps;
    private LinkedList<PowerUp> absorbedPowerUps;
    private Texture powerUpTexture;
    public PowerUpController(){
        powerUps = new LinkedList<>();
        powerUpTexture = new Texture("coin.png");
        absorbedPowerUps = new LinkedList<>();
    }

    public void addPowerUp(Actor e){
        powerUps.add(new PowerUp(e.getBoundingBox(),20,powerUpTexture));
    }
    public void drawPowerUp(float deltaTime, SpriteBatch batch){
        ListIterator<PowerUp> iterator1 = powerUps.listIterator();
        while(iterator1.hasNext()) {
            PowerUp powerups = iterator1.next();
            powerups.update(deltaTime);
            powerups.draw(batch);
            if (powerups.move(deltaTime) + powerups.getBoundingBox().height < 0) {
                iterator1.remove();
            }
        }
    }

    public LinkedList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(LinkedList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public void absorbedPowerUp(PowerUp p){
        absorbedPowerUps.add(p);
    }

    public LinkedList<PowerUp> getAbsorbedPowerUps() {
        return absorbedPowerUps;
    }

    public float triggerPowerUp(){
        float duration = absorbedPowerUps.size();
        this.absorbedPowerUps = new LinkedList<>();
        return duration * 1;
       // ListIterator<PowerUps> absorbedListIterator = this.absorbedPowerUps.listIterator();

    }
}
