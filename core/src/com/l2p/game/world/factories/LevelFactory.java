package com.l2p.game.world.factories;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.l2p.game.world.abstractProducts.World;
import com.l2p.game.world.concreteProducts.Level1;

public class LevelFactory extends WorldFactory {


    public World createWorld(String type, String[] paths,  int WORLD_WIDTH, int WORLD_HEIGHT){

        if(type.equals("level1")){
            return new Level1(paths,WORLD_WIDTH,WORLD_HEIGHT);
        }
        else
            return null;
    }


}
