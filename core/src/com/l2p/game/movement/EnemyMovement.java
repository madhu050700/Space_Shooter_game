package com.l2p.game.movement;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.l2p.game.Enemy;

public class EnemyMovement extends Movement{



    public float[] setMovement(Vector2 directionVector, float movementSpeed, float deltaTime, float enemyLifeSpan, Rectangle boundingBox, int WORLD_WIDTH, int WORLD_HEIGHT) {

        float downLimit;
        downLimit = (float)WORLD_HEIGHT/2 - boundingBox.y;

        float xMove = directionVector.x*movementSpeed * deltaTime;
        float yMove = directionVector.y*movementSpeed* deltaTime;


        if(xMove >0){
            xMove = xMove;
        }
        if(yMove > 0){
            yMove = yMove;
        }
        else{
            yMove = Math.max(yMove,downLimit);
        }
        float coord[] = {xMove,yMove};

        return coord;

    }




}
