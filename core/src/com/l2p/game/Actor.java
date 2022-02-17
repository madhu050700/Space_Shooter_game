package com.l2p.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Actor {



    //actor characteristics
    float movementSpeed; //per second
    int health;


    //position and dimensions
    Rectangle boundingBox;

    //graphics
    //TODO Projectile texture
    Texture actorTexture;
    Texture projectileTexture;

    //projectile information
    float projectileWidth, projectileHeight;
    float projectileSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

    Boolean justSpawned = false;


    Actor(float movementSpeed, int health, float width, float height, float center_x, float center_y,  float timeBetweenShots, float projectileWidth,
          float projectileHeight,float projectileSpeed, Texture actorTexture, Texture projectileTexture){

        this.movementSpeed = movementSpeed;
        this.health =  health;

        this.timeBetweenShots = timeBetweenShots;
        this.projectileHeight = projectileHeight;
        this.projectileWidth = projectileWidth;
        this.projectileSpeed= projectileSpeed;

        this.boundingBox = new Rectangle(center_x - width/2, center_y - width/2,width,height);
        this.actorTexture = actorTexture;
        this.projectileTexture = projectileTexture;

        this.justSpawned = true;

    }

    public void draw(Batch batch){

        batch.draw(actorTexture,boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
    }

    public void update(float deltaTime)
    {timeSinceLastShot += deltaTime;}

    public boolean canFireProjectile()
    {return (timeSinceLastShot - timeBetweenShots >= 0);}

    public abstract Projectile[] fire();


    public void translate(float xChange,float yChange){

        if(this.justSpawned)

        {
            boundingBox.setPosition(boundingBox.x+xChange,boundingBox.y+yChange);
            this.justSpawned = false;

        }

        else{
            if(boundingBox.x+xChange>0 && (boundingBox.x + boundingBox.width  + xChange)< 71 && boundingBox.y + boundingBox.height + yChange < 127)
                boundingBox.setPosition(boundingBox.x+xChange,boundingBox.y+yChange);

        }
    }


    public boolean intersects(Rectangle otherRect)
    {
        return boundingBox.overlaps(otherRect);
    }
}
