package com.l2p.game.movement.factories;

import com.badlogic.gdx.math.Shape2D;
import com.l2p.game.movement.abstractProducts.Movement;
import com.l2p.game.movement.concreteProducts.BossMovement;

public class BossMovementFactory extends MovementFactory {

    public Movement createMovement(Shape2D boundingBox) {
        return new BossMovement();
    }
}
