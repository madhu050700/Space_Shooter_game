package com.l2p.game.projectile.concreteProducts;

import com.badlogic.gdx.graphics.Texture;
import com.l2p.game.projectile.abstractProducts.Projectile;


public class EnemyProjectile extends Projectile {


    public EnemyProjectile(float xCenter, float yCenter, float width, float height, float speed, Texture texture, String movementType) {
        super(xCenter, yCenter, width, height, speed, texture, movementType);
        this.projectileMovement = movementFactory.createMovement(this.boundingBox);
    }


}
