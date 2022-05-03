package com.l2p.game.collision;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Explosion {
    private Animation<TextureRegion> explosionAnimation;
    private float explosionTimer;
    private Texture explosionTexture = new Texture("explosion.png");

    private Rectangle boundingBox;

    public Explosion(Texture texture, Rectangle boundingBox, float totalAnimationTime) {
        this.boundingBox = boundingBox;

        //split texture
        TextureRegion[][] textureRegion2D = TextureRegion.split(texture, 64,64);

        //convert to 1D array
        TextureRegion[] textureRegion1D = new TextureRegion[16];
        int index = 0;
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4;j++){
                textureRegion1D[index] = textureRegion2D[i][j];
                index+=1;
            }
        }
        //animation object
        explosionAnimation = new Animation<TextureRegion>(totalAnimationTime/16, textureRegion1D);
        explosionTimer = 0;

    }

    public void update(float deltaTime){
        explosionTimer += deltaTime;
    }

    public void draw(Batch batch){
        batch.draw(explosionAnimation.getKeyFrame(explosionTimer),
                boundingBox.x,
                boundingBox.y,
                boundingBox.width,
                boundingBox.height);
    }



    public boolean isFinished() {
        return explosionAnimation.isAnimationFinished(explosionTimer);
    }
}
