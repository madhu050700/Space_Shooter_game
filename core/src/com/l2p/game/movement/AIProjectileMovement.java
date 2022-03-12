package com.l2p.game.movement;

public class AIProjectileMovement extends Movement {

   @Override
   public float setProjectileMovement(float deltaTime,float y, float movementSpeed, float WORLD_WIDTH, float WORLD_HEIGHT) {

       return y - movementSpeed*deltaTime;
   }


}
