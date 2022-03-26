package com.l2p.game.projectile.factories;

import com.badlogic.gdx.graphics.Texture;
import com.l2p.game.projectile.abstractProducts.Projectile;

public abstract class ProjectileFactory {

    public abstract Projectile createProjectile(float xCenter, float yCenter, float width, float height, float speed, Texture texture);
}
