package com.l2p.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Bosses extends Actor{
    float projectile_x1,projectile_x2, projectile_y;
    Vector2 directionVector1;
    float timeSinceLastChange =0;
    float directionChangeFrequency = 0.75f;

    float sizeMultiplier = 2;

    public Bosses(float movementSpeed, int health, float width, float height, float center_x, float center_y, float timeBetweenShots, float projectileWidth, float projectileHeight, float projectileSpeed, Texture actorTexture, Texture projectileTexture,
                 float projectile_x1, float projectile_x2, float projectile_y ) {
        super(movementSpeed, health, width, height, center_x, center_y, timeBetweenShots, projectileWidth, projectileHeight, projectileSpeed, actorTexture, projectileTexture);


        directionVector1 = new Vector2(0,-1);

        this.projectile_x1 = projectile_x1;
        this.projectile_x2 =projectile_x2;
        this.projectile_y = projectile_y;
    }

    public Vector2 getDirectionVector1() {
        return directionVector1;
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastChange += deltaTime;
        if(timeSinceLastChange > directionChangeFrequency){
            randomisedDirectionVector();
            timeSinceLastChange -= directionChangeFrequency;
        }
    }

    private void randomisedDirectionVector(){
        double bearing = SpaceShooter.random.nextDouble()*2*Math.PI;
        directionVector1.x = (float)Math.sin(bearing);
        directionVector1.y = (float)Math.cos(bearing);
    }


    @Override
    public Projectile[] fire() {
        Projectile[] projectile = new Projectile[2];
        projectile[0] = new Projectile(boundingBox.x +boundingBox.width*projectile_x1,boundingBox.y-projectileHeight, projectileWidth, projectileHeight, projectileSpeed, projectileTexture);
        projectile[1] = new Projectile(boundingBox.x+boundingBox.width*projectile_x2,boundingBox.y-projectileHeight, projectileWidth, projectileHeight, projectileSpeed, projectileTexture);
        timeSinceLastShot = 0;
        return projectile;
    }


    @Override
    public void draw(Batch batch) {
        batch.draw(actorTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

}
