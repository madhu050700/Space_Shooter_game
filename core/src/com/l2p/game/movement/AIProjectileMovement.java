package com.l2p.game.movement;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AIProjectileMovement extends Movement {

   @Override
   public float setProjectileMovement(float deltaTime,float y, float movementSpeed, float WORLD_WIDTH, float WORLD_HEIGHT) {

       return y - movementSpeed*deltaTime;
   }



    public float[] setMovement(Vector2 directionVector, float movementSpeed, float deltaTime, float enemyLifeSpan, Rectangle boundingBox, int WORLD_WIDTH, int WORLD_HEIGHT){
        return null;
    }

    public float[] setPlayerMovement(Boolean ToggleSpeed, float WORLD_WIDTH, float WORLD_HEIGHT){
        return null;
    }


}
