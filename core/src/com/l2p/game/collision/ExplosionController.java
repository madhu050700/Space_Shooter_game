package com.l2p.game.collision;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.l2p.game.actor.abstractProducts.Actor;

import java.util.LinkedList;
import java.util.ListIterator;

public class ExplosionController {
    private LinkedList<Explosion> ExplosionList;
    private Texture ExplosionTexture;
    public ExplosionController(){
        ExplosionList = new LinkedList<>();
        ExplosionTexture = new Texture("explosion.png");
    }

    public void addExplosion(Actor e){
        ExplosionList.add(new Explosion(ExplosionTexture,e.getBoundingBox(),0.7f));
    }
    public void drawExplosion(float deltaTime, Batch batch){
        ListIterator<Explosion> explosionListIterator = ExplosionList.listIterator();
        while (explosionListIterator.hasNext()) {
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if(explosion.isFinished()){
                explosionListIterator.remove();
            } else {
                explosion.draw(batch);
            }
        }
    }

//    public LinkedList<PowerUps> getExplosions() {
//        return Explosions;
//    }



}
