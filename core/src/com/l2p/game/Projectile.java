package com.l2p.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Projectile {

    Rectangle boundingBox;
    float movementSpeed;
    Texture projectileTexture;

    public Projectile(float xCenter, float yCenter, float width, float height, float speed, Texture texture)
    {
        this.boundingBox = new Rectangle(xCenter - width/2, yCenter, width, height);
        this.movementSpeed = speed;
        this.projectileTexture = texture;
    }

    public void draw(Batch batch)
    {
        batch.draw(projectileTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
