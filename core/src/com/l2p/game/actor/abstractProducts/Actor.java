package com.l2p.game.actor.abstractProducts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.l2p.game.composite.ActorComponent;
import com.l2p.game.movement.factories.MovementFactory;
import com.l2p.game.movement.factories.MovementFactoryBuilder;
import com.l2p.game.projectile.abstractProducts.Projectile;

public abstract class Actor extends ActorComponent {


    //actor characteristics
    protected float movementSpeed; //per second
    protected int health;


    //position and dimensions
    protected Rectangle boundingBox;

    //graphics
    protected Texture actorTexture;
    protected Texture projectileTexture;

    //projectile information
    protected float projectileWidth, projectileHeight;
    protected float projectileSpeed;
    protected float timeBetweenShots;
    protected float timeSinceLastShot = 0;

    protected Boolean justSpawned = false;
    protected float timeSinceSpawn = 0;


    protected MovementFactory movementFactory;
    protected Boolean respawn = false;

    public Actor(float movementSpeed, int health, float width, float height, float center_x, float center_y, float timeBetweenShots, float projectileWidth,
                 float projectileHeight, float projectileSpeed, Texture actorTexture, Texture projectileTexture, String movementType) {

        this.movementSpeed = movementSpeed;
        this.health = health;
        this.type = "actor";
        this.timeBetweenShots = timeBetweenShots;
        this.projectileHeight = projectileHeight;
        this.projectileWidth = projectileWidth;
        this.projectileSpeed = projectileSpeed;

        this.boundingBox = new Rectangle(center_x - width / 2, center_y - width / 2, width, height);
        this.actorTexture = actorTexture;
        this.projectileTexture = projectileTexture;

        this.justSpawned = true;

        movementFactory = MovementFactoryBuilder.getFactory(movementType);

    }

    public void draw(Batch batch) {

        batch.draw(actorTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }


    public int getHealth() {
        return this.health;
    }

    public void boostHealth(){
        this.health +=1;
    }

    public void update(float deltaTime) {
        timeSinceLastShot += deltaTime;
        timeSinceSpawn += deltaTime;
    }

    public boolean canFireProjectile() {
        return (timeSinceLastShot - timeBetweenShots >= 0) && this.health>0;
    }


    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public Boolean translate(float xChange, float yChange, float WORLD_WIDTH, float WORLD_HEIGHT, float lifeSpan) {

//        System.out.println(("Time since spawn for"+this.actorTexture.getTextureData()+ " "+ this.timeSinceSpawn));

        if (this.justSpawned) {
            boundingBox.setPosition(boundingBox.x + xChange, boundingBox.y + yChange);
            this.justSpawned = false;
            return false;

        }

        if (boundingBox.x + xChange > 0 && (boundingBox.x + boundingBox.width + xChange) < WORLD_WIDTH && boundingBox.y + boundingBox.height + yChange < WORLD_HEIGHT) {
            boundingBox.setPosition(boundingBox.x + xChange, boundingBox.y + yChange);
            return false;
        }


        if (timeSinceSpawn > lifeSpan) {
            //TODO: object moves out and is destroyed
            boundingBox.setPosition(boundingBox.x, boundingBox.y + 100);
            return true;

        }

        return false;

    }

    public void setRespawn() {
    }

    public boolean intersects(Rectangle otherRect) {
        Rectangle thisRect = new Rectangle(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        return thisRect.overlaps(otherRect);
    }

    public int hit(Projectile projectile) {
        if (health > 0) {
            health--;
            return health;
        } else return 0;
    }

    public float getTimeBetweenShots() {
        return timeBetweenShots;
    }
    public void setTimeBetweenShots(float timeBetweenShots){
        this.timeBetweenShots = timeBetweenShots;
    }

    public abstract Boolean moveActor(float deltaTime, int WORLD_WIDTH, int WORLD_HEIGHT, float lifeSpan);


}



