package com.l2p.game.projectile.factories;

import com.badlogic.gdx.graphics.Texture;
import com.l2p.game.movement.factories.MovementFactory;
import com.l2p.game.projectile.abstractProducts.Projectile;
import com.l2p.game.projectile.concreteProducts.EnemyProjectile;

public class EnemyProjectileFactory extends ProjectileFactory{

    @Override
    public Projectile createProjectile(float xCenter, float yCenter, float width, float height, float speed, Texture texture) {
        Projectile enemyProjectile = new EnemyProjectile(xCenter,yCenter,width,height,speed,texture);
        return enemyProjectile;

    }
}
