package com.l2p.game.collision.State;

import com.l2p.game.PowerUp.PowerUpController;

public class CollisionDetectionState {
    int score;
    PowerUpController powerUpController;

    public CollisionDetectionState(int score,PowerUpController powerUpController){
        this.powerUpController = powerUpController;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public PowerUpController getPowerUpController() {
        return powerUpController;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPowerUpController(PowerUpController powerUpController) {
        this.powerUpController = powerUpController;
    }
}
