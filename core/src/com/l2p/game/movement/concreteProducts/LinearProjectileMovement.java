package com.l2p.game.movement.concreteProducts;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.l2p.game.movement.abstractProducts.Movement;

public class LinearProjectileMovement extends Movement {

    @Override
    public float setProjectileMovement(float deltaTime, float y, float movementSpeed, float WORLD_WIDTH, float WORLD_HEIGHT, String direction) {

        if (direction.equals("down"))
            return y - movementSpeed * deltaTime;
        else
            return y + movementSpeed * deltaTime;
    }


    public float[] setMovement(Vector2 directionVector, float movementSpeed, float deltaTime, float enemyLifeSpan, Rectangle boundingBox, int WORLD_WIDTH, int WORLD_HEIGHT) {
        return null;
    }

    public float[] setPlayerMovement(Boolean ToggleSpeed, float WORLD_WIDTH, float WORLD_HEIGHT, Boolean respawn) {
        return null;
    }


}
