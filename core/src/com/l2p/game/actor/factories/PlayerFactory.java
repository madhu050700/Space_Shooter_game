package com.l2p.game.actor.factories;

import com.badlogic.gdx.graphics.Texture;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.actor.concreteProducts.PlayerCharacter;


public class PlayerFactory extends ActorFactory {


    @Override
    public Actor createActor(String type, float movementSpeed, int health, float width, float height, float center_x,float center_y, float timeBetweenShots, float projectileWidth, float projectileHeight,
                             float projectileSpeed, String actorTexture, String projectileTexture, float projectile_x1, float projectile_x2, float projectile_y, String movementType) {
        this.actorTexture = new Texture(actorTexture);
        this.projectileTexture =  new Texture(projectileTexture);

        PlayerCharacter player = new PlayerCharacter(movementSpeed,health,width,height,center_x,center_y,timeBetweenShots,
                projectileWidth, projectileHeight, projectileSpeed,this.actorTexture,this.projectileTexture, movementType);

        if (type.equals("player"))
            return player;
        else
            return null;
    }
}
