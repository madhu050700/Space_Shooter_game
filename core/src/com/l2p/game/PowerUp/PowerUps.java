package com.l2p.game.PowerUp;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class PowerUps {
    private Texture powerUpTexture;
    private  float movementSpeed;
    private  Rectangle boundingBox;

    public PowerUps(float xCenter, float yCenter, float width, float height, float speed, Texture texture) {
        this.boundingBox = new Rectangle(xCenter - width / 2, yCenter, width, height);
        this.movementSpeed = speed;
        this.powerUpTexture = texture;
    }
    public void draw(Batch batch) {
        batch.draw(powerUpTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
    public Rectangle getBoundingBox() {
        return boundingBox;
    }


    public float move(float deltaTime){
        this.boundingBox.y = this.boundingBox.y - movementSpeed * deltaTime;
        return this.boundingBox.y;

    }
}
