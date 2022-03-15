package com.l2p.game.world.concreteProducts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.l2p.game.world.abstractProducts.World;

public class Level1 extends World {



    public Level1(String[] backgrounds,int WORLD_WIDTH, int WORLD_HEIGHT){
        this.texturePaths = backgrounds;
        this.WORLD_WIDTH = WORLD_WIDTH;
        this.WORLD_HEIGHT = WORLD_HEIGHT;


        this.backgrounds = new Texture[backgrounds.length];
        this.backgroundOffsets = new float[backgrounds.length];

        for(int i=0; i<this.texturePaths.length; i++){
            this.backgrounds[i] = new Texture(this.texturePaths[i]);
            this.backgroundOffsets[i] = 0;
        }

        this.backgroundMaxScrollingSpeed = (float)WORLD_HEIGHT/4;
    }

    @Override
    public void renderBackground(float deltaTime, SpriteBatch batch) {
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;


        for (int layer=0; layer<backgroundOffsets.length; layer++){
            if(backgroundOffsets[layer]>WORLD_HEIGHT){
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer],0,-backgroundOffsets[layer],WORLD_WIDTH,WORLD_HEIGHT);
            batch.draw(backgrounds[layer],0,-backgroundOffsets[layer]+WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT);
        }

    }


}
