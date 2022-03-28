package com.l2p.game.actor.concreteProducts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.l2p.game.SpaceShooter;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.movement.abstractProducts.Movement;
import com.l2p.game.projectile.abstractProducts.Projectile;
import com.l2p.game.projectile.factories.EnemyProjectileFactory;
import com.l2p.game.projectile.factories.ProjectileFactory;

import java.util.LinkedList;

public class Enemy extends Actor {


    float projectile_x1, projectile_x2, projectile_y;
    Vector2 directionVector;
    float timeSinceLastChange = 0;
    float directionChangeFrequency = 0.75f;
    String movementType;


    Movement enemyMovement;


    public Enemy(float movementSpeed, int health, float width, float height, float center_x, float center_y, float timeBetweenShots, float projectileWidth, float projectileHeight, float projectileSpeed, Texture actorTexture, Texture projectileTexture,
                 float projectile_x1, float projectile_x2, float projectile_y, String movementType) {
        super(movementSpeed, health, width, height, center_x, center_y, timeBetweenShots, projectileWidth, projectileHeight, projectileSpeed, actorTexture, projectileTexture, movementType);
        directionVector = new Vector2(0, -1);

        this.projectile_x1 = projectile_x1;
        this.projectile_x2 = projectile_x2;
        this.projectile_y = projectile_y;

        enemyMovement = movementFactory.createMovement(this.boundingBox);


    }

    public Vector2 getDirectionVector() {
        return directionVector;
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastChange += deltaTime;
        if (timeSinceLastChange > directionChangeFrequency) {
            randomisedDirectionVector();
            timeSinceLastChange -= directionChangeFrequency;
        }
    }

    private void randomisedDirectionVector() {
        double bearing = SpaceShooter.random.nextDouble() * 2 * Math.PI;
        directionVector.x = (float) Math.sin(bearing);
        directionVector.y = (float) Math.cos(bearing);
    }


    @Override
    public LinkedList<Projectile> fire() {
        LinkedList<Projectile> projectiles;
        projectiles = new LinkedList<>();
        ProjectileFactory projectileFactory = new EnemyProjectileFactory();
        projectiles.add(projectileFactory.createProjectile(boundingBox.x + boundingBox.width * projectile_x1, boundingBox.y - projectileHeight, projectileWidth,
                projectileHeight, projectileSpeed, projectileTexture, "linearProjectile"));
        projectiles.add(projectileFactory.createProjectile(boundingBox.x + boundingBox.width * projectile_x2, boundingBox.y - projectileHeight, projectileWidth,
                projectileHeight, projectileSpeed, projectileTexture, "linearProjectile"));
        timeSinceLastShot = 0;
        return projectiles;
    }


    @Override
    public Boolean moveActor(float deltaTime, int WORLD_WIDTH, int WORLD_HEIGHT, float lifeSpan) {
        float coord[] =
                this.enemyMovement.setMovement(this.directionVector, this.movementSpeed, deltaTime, lifeSpan, this.boundingBox, WORLD_WIDTH, WORLD_HEIGHT);
        return this.translate(coord[0], coord[1], WORLD_WIDTH, WORLD_HEIGHT, lifeSpan);

    }

}
