package com.l2p.game.actor.factories;

import com.badlogic.gdx.graphics.Texture;
import com.l2p.game.SpaceShooter;
import com.l2p.game.actor.abstractProduct.Actor;
import com.l2p.game.actor.concreteProducts.Enemy;

public class EnemyFactory extends ActorFactory {


    @Override
    public Actor createActor(String type, float movementSpeed, int health, float width, float height, int WORLD_WIDTH, int WORLD_HEIGHT, float timeBetweenShots, float projectileWidth, float projectileHeight,
                             float projectileSpeed, String actorTexture, String projectileTexture, float projectile_x1, float projectile_x2, float projectile_y) {
        this.actorTexture = new Texture(actorTexture);
        this.projectileTexture =  new Texture(projectileTexture);

        Enemy enemy = new Enemy(movementSpeed,health,width,height, Math.min(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_WIDTH -1), WORLD_HEIGHT - 5,timeBetweenShots,
                projectileWidth, projectileHeight, projectileSpeed,this.actorTexture,this.projectileTexture,projectile_x1,projectile_x2,projectile_y);

        if (type.equals("enemy"))
            return enemy;
        else
            return null;
    }
}
