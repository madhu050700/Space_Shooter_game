package com.l2p.game.actor.concreteProducts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.movement.abstractProducts.Movement;
import com.l2p.game.projectile.abstractProducts.Projectile;
import com.l2p.game.projectile.factories.PlayerProjectileFactory;
import com.l2p.game.projectile.factories.ProjectileFactory;

import java.util.LinkedList;

public class PlayerCharacter extends Actor {

    Movement playerMovement;
    private Boolean toggleSpeed = false;
    private Boolean powerUpToggle = false;

    public PlayerCharacter(float movementSpeed, int health, float width, float height, float center_x, float center_y, float timeBetweenShots, float projectileWidth, float projectileHeight
            , float projectileSpeed, Texture actorTexture, Texture projectileTexture, String movementType) {
        super(movementSpeed, health, width, height, center_x, center_y, timeBetweenShots, projectileWidth, projectileHeight, projectileSpeed, actorTexture, projectileTexture, movementType);

        playerMovement = movementFactory.createMovement(this.boundingBox);
    }

    public Boolean getToggleSpeed() {
        return toggleSpeed;
    }

    public void setToggleSpeed(Boolean toggleSpeed) {
        this.toggleSpeed = toggleSpeed;
    }


    @Override
    public Boolean moveActor(float deltaTime, int WORLD_WIDTH, int WORLD_HEIGHT, float lifeSpan) {
        if (respawn) {
            float coord[] = playerMovement.setPlayerMovement(this.toggleSpeed, WORLD_WIDTH, WORLD_HEIGHT, this.respawn);
            this.boundingBox.setPosition(coord[0], coord[1]);
            respawn = false;
            return true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.CAPS_LOCK)) {
            this.setToggleSpeed(!this.getToggleSpeed());
        }

        float coord[] = playerMovement.setPlayerMovement(this.toggleSpeed, WORLD_WIDTH, WORLD_HEIGHT, this.respawn);
        this.boundingBox.setPosition(coord[0], coord[1]);
        return true;
    }

    @Override
    public void setRespawn() {
        this.respawn = true;
    }

    @Override
    public LinkedList<Projectile> fire() {
        LinkedList<Projectile> projectiles;
        projectiles = new LinkedList<>();
        ProjectileFactory projectile = new PlayerProjectileFactory();
        projectiles.add(projectile.createProjectile(boundingBox.x + boundingBox.width * 0.387f, boundingBox.y + boundingBox.height * 0.925f, projectileWidth,
                projectileHeight, projectileSpeed, projectileTexture, "linearProjectile"));
        projectiles.add(projectile.createProjectile(boundingBox.x + boundingBox.width * 0.575f, boundingBox.y + boundingBox.height * 0.925f, projectileWidth,
                projectileHeight, projectileSpeed, projectileTexture, "linearProjectile"));

        timeSinceLastShot = 0;
        return projectiles;
    }



}
