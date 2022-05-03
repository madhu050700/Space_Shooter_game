package com.l2p.game.PowerUp;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PowerUp {
    protected Texture powerUpTexture;
    protected float movementSpeed;
    protected  Rectangle boundingBox;
    protected float powerUpTimer;
    Vector2 directionVector;


    public PowerUp(Rectangle boundingBox, float speed, Texture texture) {
        this.boundingBox = boundingBox;
        this.movementSpeed = speed;
        this.powerUpTexture = texture;

        powerUpTimer =0;
    }
    public void draw(Batch batch) {
        batch.draw(powerUpTexture, boundingBox.x, boundingBox.y, boundingBox.width/2, boundingBox.height/2);
    }
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void update(float deltaTime){
        powerUpTimer += deltaTime;
    }

    public float move(float deltaTime){
        this.boundingBox.y = this.boundingBox.y - movementSpeed*deltaTime;
        return this.boundingBox.y;

    }
}
