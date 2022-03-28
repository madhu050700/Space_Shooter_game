package com.l2p.game;


import com.badlogic.gdx.Game;

import java.util.Random;

public class SpaceShooter extends Game {

    public static Random random = new Random();
    GameScreen gameScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }


    @Override
    public void dispose() {
        gameScreen.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        gameScreen.resize(width, height);
    }
}
