package com.l2p.game.movement;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.l2p.game.Enemy;

public abstract class Movement {


    public abstract float[] setMovement(Vector2 directionVector, float movementSpeed, float deltaTime, float enemyLifeSpan, Rectangle boundingBox,int WORLD_WIDTH,int WORLD_HEIGHT);



}
