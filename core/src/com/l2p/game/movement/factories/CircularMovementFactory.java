package com.l2p.game.movement.factories;

import com.badlogic.gdx.math.Shape2D;
import com.l2p.game.movement.abstractProducts.Movement;
import com.l2p.game.movement.concreteProducts.CircularMovement;

public class CircularMovementFactory extends MovementFactory {

    public Movement createMovement(Shape2D boundingBox) {
        return new CircularMovement();
    }


}
