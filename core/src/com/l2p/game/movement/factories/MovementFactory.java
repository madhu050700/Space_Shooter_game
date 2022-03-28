package com.l2p.game.movement.factories;

import com.badlogic.gdx.math.Shape2D;
import com.l2p.game.movement.abstractProducts.Movement;

public abstract class MovementFactory {


    public abstract Movement createMovement(Shape2D boundingBox);
}
