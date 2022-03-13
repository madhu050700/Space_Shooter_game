package com.l2p.game.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.l2p.game.movement.Movement;
import com.l2p.game.movement.AIProjectileMovement;

public abstract class Projectile {

    Rectangle boundingBox;
    float movementSpeed;
    Texture projectileTexture;

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    Movement projectileMovement;

    public Projectile(float xCenter, float yCenter, float width, float height, float speed, Texture texture)
    {
        this.boundingBox = new Rectangle(xCenter - width/2, yCenter, width, height);
        this.movementSpeed = speed;
        this.projectileTexture = texture;

        projectileMovement = new AIProjectileMovement();

    }

    public void draw(Batch batch)
    {
        batch.draw(projectileTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    public float move(float deltaTime, int WORLD_WIDTH,int WORLD_HEIGHT){

        this.boundingBox.y =  projectileMovement.setProjectileMovement(deltaTime,this.boundingBox.y, this.movementSpeed, WORLD_WIDTH,WORLD_HEIGHT);
        return this.boundingBox.y;
    }
}
