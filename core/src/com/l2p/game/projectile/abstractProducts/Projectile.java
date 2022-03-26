package com.l2p.game.projectile.abstractProducts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.l2p.game.movement.abstractProducts.Movement;
import com.l2p.game.movement.factories.MovementFactory;
import com.l2p.game.movement.factories.MovementFactoryBuilder;

public abstract class Projectile {

    protected Rectangle boundingBox;
    protected float movementSpeed;
    protected Texture projectileTexture;
    protected MovementFactory  movementFactory;
    protected Movement projectileMovement;


    public float getMovementSpeed() {
        return movementSpeed;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }



    public Projectile(float xCenter, float yCenter, float width, float height, float speed, Texture texture, String movementType)
    {
        this.boundingBox = new Rectangle(xCenter - width/2, yCenter, width, height);
        this.movementSpeed = speed;
        this.projectileTexture = texture;
//        projectileMovement = new AIProjectileMovement();
        movementFactory = MovementFactoryBuilder.getFactory(movementType);

    }

    public void draw(Batch batch)
    {
        batch.draw(projectileTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    public float move(float deltaTime, int WORLD_WIDTH,int WORLD_HEIGHT, String direction){

        this.boundingBox.y =  projectileMovement.setProjectileMovement(deltaTime,this.boundingBox.y, this.movementSpeed, WORLD_WIDTH,WORLD_HEIGHT,direction);
        return this.boundingBox.y;
    }


}
