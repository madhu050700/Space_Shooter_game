package com.l2p.game.projectile.factories;

import com.badlogic.gdx.graphics.Texture;
import com.l2p.game.projectile.abstractProducts.Projectile;
import com.l2p.game.projectile.concreteProducts.FinalBossProjectile;

public class FinalBossProjectileFactory extends ProjectileFactory {
    @Override
    public Projectile createProjectile(float xCenter, float yCenter, float width, float height, float speed, Texture texture, String movementType) {
        FinalBossProjectile finalBossProjectile = new FinalBossProjectile(xCenter, yCenter, width, height, speed, texture, movementType);

        return finalBossProjectile;
    }
}
