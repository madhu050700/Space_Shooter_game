package com.l2p.game.actor.factories;

import com.badlogic.gdx.graphics.Texture;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.actor.concreteProducts.Enemy;

public class EnemyFactory extends ActorFactory {


    @Override
    public Actor createActor(String type, float movementSpeed, int health, float width, float height, float center_x, float center_y, float timeBetweenShots, float projectileWidth, float projectileHeight,
                             float projectileSpeed, String actorTexture, String projectileTexture, float projectile_x1, float projectile_x2, float projectile_y, String movementType) {
        this.actorTexture = new Texture(actorTexture);
        this.projectileTexture =  new Texture(projectileTexture);

        Enemy enemy = new Enemy(movementSpeed,health,width,height, center_x,center_y,timeBetweenShots,
                projectileWidth, projectileHeight, projectileSpeed,this.actorTexture,this.projectileTexture,projectile_x1,projectile_x2,projectile_y, movementType);

        if (type.equals("enemy"))
            return enemy;
        else
            return null;
    }
}
