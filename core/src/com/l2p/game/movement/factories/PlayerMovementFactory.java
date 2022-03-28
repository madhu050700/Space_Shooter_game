package com.l2p.game.movement.factories;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.l2p.game.movement.abstractProducts.Movement;
import com.l2p.game.movement.concreteProducts.PlayerMovement;

public class PlayerMovementFactory extends MovementFactory {

    public Movement createMovement(Shape2D boundingBox) {

        Rectangle r = (Rectangle) boundingBox;
        return new PlayerMovement(r.x, r.y, r.width, r.height);
    }
}
