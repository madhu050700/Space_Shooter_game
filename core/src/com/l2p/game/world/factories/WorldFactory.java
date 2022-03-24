package com.l2p.game.world.factories;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.l2p.game.world.abstractProducts.World;

public abstract class WorldFactory {


    public abstract World createWorld(String type, String[] paths, int WORLD_WIDTH, int WORLD_HEIGHT);


}
