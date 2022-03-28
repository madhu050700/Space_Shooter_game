package com.l2p.game.movement.concreteProducts;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.l2p.game.movement.abstractProducts.Movement;

public class CircularMovement extends Movement {

    float angle = (float) (Math.PI / 2);
    float sign = -1;

    @Override
    public float[] setMovement(Vector2 directionVector, float movementSpeed, float deltaTime, float enemyLifeSpan, Rectangle boundingBox, int WORLD_WIDTH, int WORLD_HEIGHT) {

        float downLimit;
        downLimit = (float) WORLD_HEIGHT / 2 - boundingBox.y;

        Vector2 newPos = directionVector.rotateDeg(10);

        float xMove = (float) (newPos.x * movementSpeed * deltaTime);
        float yMove = (float) (newPos.y * movementSpeed * deltaTime);


        if (xMove > 0) {
            xMove = xMove;
        }
        if (yMove > 0) {
            yMove = yMove;
        } else {
            yMove = Math.max(yMove, downLimit);
        }
        float coord[] = {xMove, yMove};

        return coord;

    }

    public float[] setPlayerMovement(Boolean ToggleSpeed, float WORLD_WIDTH, float WORLD_HEIGHT, Boolean respawn) {
        return null;
    }

    public float setProjectileMovement(float deltaTime, float y, float movementSpeed, float WORLD_WIDTH, float WORLD_HEIGHT, String direction) {
        return 0f;
    }


}
