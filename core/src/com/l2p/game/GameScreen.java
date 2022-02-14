
package com.l2p.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {


    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
//    private Texture background;
    private Texture[] backgrounds;
    private Texture playerTexture;
    private Texture enemyType1Texture;


    //timing
//    private int backgroundOffset;
    private float[] backgroundOffsets = {0,0,0,0};
    private  float backgroundMaxScrollingSpeed;


    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT= 128;


    //game Objects
    private PlayerCharacter playerCharacter;
    private Enemy enemyType1;

    //TODO enemyType2, midboss and boss



    GameScreen(){


        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);
        backgrounds =  new Texture[4];
        backgrounds[0] = new Texture("space1.jpg");
        backgrounds[1] = new Texture("parallex1.png");
        backgrounds[2] = new Texture("parallex2.png");
        backgrounds[3] = new Texture("parallex3.png");

        backgroundMaxScrollingSpeed = (float)WORLD_HEIGHT/4;


        playerTexture = new Texture("playerShip1.png");
        playerCharacter = new PlayerCharacter(2,5,10, 10, WORLD_WIDTH/2, WORLD_HEIGHT/4,0.5f,0.7f,5,45,playerTexture,null);


        enemyType1Texture = new Texture("enemy1.png");
        enemyType1 = new Enemy(2,5,10,10,WORLD_WIDTH/2, WORLD_HEIGHT*3/4,0.5f, 0.7f, 5, 50,enemyType1Texture,null );

        batch = new SpriteBatch();

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {

        batch.begin();

        //scrolling background
        renderBackground(deltaTime);

        //player
        playerCharacter.draw(batch);

        //enemy1
        enemyType1.draw(batch);

        // Update playerCharacter position based on user input.
        float x_coord = playerCharacter.boundingBox.x;
        float y_coord = playerCharacter.boundingBox.y;
        float playerCharWidth = playerCharacter.boundingBox.width;
        float playerCharHeight = playerCharacter.boundingBox.height;

        if (Gdx.input.isKeyJustPressed(Input.Keys.CAPS_LOCK)){
            playerCharacter.setToggleSpeed(!playerCharacter.getToggleSpeed());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (x_coord - 1) >= 0)
            x_coord = (playerCharacter.getToggleSpeed())?x_coord-1: x_coord-2;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (x_coord + playerCharWidth + 1) <= WORLD_WIDTH)
            x_coord = (playerCharacter.getToggleSpeed())?x_coord+1:x_coord+2;

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && (y_coord + playerCharHeight + 1) <= WORLD_HEIGHT)
            y_coord = (playerCharacter.getToggleSpeed())?y_coord+1:y_coord+2;

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && (y_coord - 1) >= 0)
            y_coord = (playerCharacter.getToggleSpeed())?y_coord-1:y_coord-2;

        // Diagonal movement.
        // Top-Left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)
            && (x_coord - 1) >= 0 && (y_coord + playerCharHeight + 1) <= WORLD_HEIGHT)
        {
            x_coord = (playerCharacter.getToggleSpeed())?x_coord-1:x_coord-2;
            y_coord = (playerCharacter.getToggleSpeed())?y_coord+1:y_coord+2;
        }

        // Top-Right.
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)
            && (x_coord + playerCharWidth + 1) <= WORLD_WIDTH && (y_coord + playerCharHeight + 1) <= WORLD_HEIGHT)
        {
            x_coord = (playerCharacter.getToggleSpeed())?x_coord+1:x_coord+2;
            y_coord = (playerCharacter.getToggleSpeed())?y_coord+1:y_coord+2;
        }

        // Down-Left.
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)
            && (x_coord - 1) >= 0 && (y_coord - 1) >= 0)
        {
            x_coord = (playerCharacter.getToggleSpeed())?x_coord-1:x_coord-2;
            y_coord = (playerCharacter.getToggleSpeed())?y_coord-1:y_coord-2;
        }

        // Down-Right.
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)
            && (x_coord + playerCharWidth + 1) <= WORLD_WIDTH && (y_coord - 1) >= 0)
        {
            x_coord = (playerCharacter.getToggleSpeed())?x_coord+1:x_coord+2;
            y_coord = (playerCharacter.getToggleSpeed())?y_coord-1:y_coord-2;
        }

        // Set updated positon of playerCharacter.
        playerCharacter.boundingBox.setPosition(x_coord, y_coord);

        batch.end();

    }


    private void renderBackground(float deltaTime) {
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

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
