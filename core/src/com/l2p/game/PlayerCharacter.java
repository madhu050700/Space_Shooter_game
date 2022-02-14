package com.l2p.game;

import com.badlogic.gdx.graphics.Texture;

public class PlayerCharacter extends Actor{

    private Boolean toggleSpeed = false;

    public PlayerCharacter(float movementSpeed, int health, float width, float height, float center_x, float center_y, float timeBetweenShots, float projectileWidth, float projectileHeight, float projectileSpeed, Texture actorTexture, Texture projectileTexture) {
        super(movementSpeed, health, width, height, center_x, center_y, timeBetweenShots, projectileWidth, projectileHeight, projectileSpeed, actorTexture, projectileTexture);
    }

    public Boolean getToggleSpeed() {
        return toggleSpeed;
    }

    public void setToggleSpeed(Boolean toggleSpeed) {
        this.toggleSpeed = toggleSpeed;
    }

    public Projectile[] fire() {
        Projectile[] projectile = new Projectile[2];
        projectile[0] = new Projectile(boundingBox.x +boundingBox.width*0.387f,boundingBox.y+boundingBox.height*0.925f, projectileWidth, projectileHeight, projectileSpeed, projectileTexture);
        projectile[1] = new Projectile(boundingBox.x+boundingBox.width*0.575f,boundingBox.y+boundingBox.height*0.925f, projectileWidth, projectileHeight, projectileSpeed, projectileTexture);

        timeSinceLastShot = 0;

        return projectile;
    }
}
