package com.l2p.game.movement;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BossMovement extends Movement{

    @Override
    public float[] setMovement(Vector2 directionVector, float movementSpeed, float deltaTime, float enemyLifeSpan, Rectangle boundingBox, int WORLD_WIDTH, int WORLD_HEIGHT) {

        float leftLimit = 0;
        float rightLimit = (float)WORLD_WIDTH - boundingBox.x;
        float downLimit;
        downLimit = (float)WORLD_HEIGHT/2 - boundingBox.y;
        float xMove =directionVector.x *movementSpeed * deltaTime;
        float yMove = directionVector.y*movementSpeed * deltaTime;
        if(xMove >0){
            xMove = xMove;
        }
        if(yMove > 0){
            yMove = yMove;
        }
        else{
            yMove = Math.max(yMove,downLimit);
            xMove = xMove>0?xMove:0;
            xMove = xMove>WORLD_WIDTH?rightLimit:xMove;
        }

        float coord[] = {xMove,yMove};

        return coord;

    }

    public float[] setPlayerMovement(Boolean ToggleSpeed, float WORLD_WIDTH, float WORLD_HEIGHT){
        return null;
    }

    public float setProjectileMovement(float deltaTime, float y, float movementSpeed, float WORLD_WIDTH, float WORLD_HEIGHT){
        return 0f;
    }



}
