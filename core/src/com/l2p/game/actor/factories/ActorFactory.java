package com.l2p.game.actor.factories;

import com.badlogic.gdx.graphics.Texture;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.movement.factories.MovementFactory;

public abstract class ActorFactory {

protected Texture actorTexture;
protected Texture projectileTexture;


    public abstract Actor createActor(String type, float movementSpeed, int health, float width, float height, float center_x, float center_y, float timeBetweenShots, float projectileWidth, float projectileHeight, float projectileSpeed, String actorTexture, String projectileTexture,
                                  float projectile_x1, float projectile_x2, float projectile_y, String movementType);


}
