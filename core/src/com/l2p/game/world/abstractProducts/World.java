package com.l2p.game.world.abstractProducts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class World {

    protected float[] backgroundOffsets;
    protected float backgroundMaxScrollingSpeed;
    protected Texture[] backgrounds;
    protected String[] texturePaths;
    protected int WORLD_WIDTH, WORLD_HEIGHT;


    public abstract void renderBackground(float deltaTime, SpriteBatch batch);


}
