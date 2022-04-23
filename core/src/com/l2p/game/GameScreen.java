
package com.l2p.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.actor.factories.ActorFactory;
import com.l2p.game.actor.factories.PlayerFactory;
import com.l2p.game.actor.controllers.SpawnController;
import com.l2p.game.actor.controllers.SpawnState;
import com.l2p.game.collision.services.CollisionDetectionService;
import com.l2p.game.movement.controllers.MovementController;
import com.l2p.game.projectile.abstractProducts.Projectile;
import com.l2p.game.projectile.controllers.LinearProjectileController;
import com.l2p.game.world.abstractProducts.World;
import com.l2p.game.world.factories.LevelFactory;
import com.l2p.game.world.factories.WorldFactory;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.LinkedList;
import java.util.Locale;

public class GameScreen implements Screen {


    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;


    //timing


    //TODO Only declare variables here, set values from hasmap that is returned by readJSON

    private float timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer = 0;
    private float stateTime, stateTime1, playTime = 0;
    private float timetoStartMidBoss = 20f;
    private float timetoStartFinalBoss = 40f;


    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128;
    private int number_enemy_1 = 2;
    private int number_enemy_2 = 3;
    private float enemy1LifeSpan = 10f;
    private float enemy2LifeSpan = 10f;
    private float midBossLifeSpan = 100000f;
    private float bossLifeSpan = 100000f;

    //Texture Paths
    String texturePathEnemy1;
    String texturePathEnemy2;
    String texturePathProjectileEnemy1;
    String texturePathProjectileEnemy2;
    String texturePathMidBoss;
    String texturePathFinalBoss;
    String texturePathProjectileMidBoss;
    String texturePathProjectileFinalBoss;
    String texturePathPlayer;
    String texturePathProjectilePlayer;

    String[] texturePathBackgrounds;


    //game Objects
    private World level1;
    private Actor playerCharacter;
    private LinkedList<Projectile> playerProjectileList;
    private LinkedList<Projectile> enemyProjectileList, enemyProjectileList1;
    private LinkedList<Projectile> midBossProjectileList, finalBossProjectileList;
    private LinkedList<Actor> enemyList, enemyList1;
    private LinkedList<Actor> midBoss, finalBoss;

    //Head-Up display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;
    private int score = 0;
    //Factories
    WorldFactory levelFactory;

    ActorFactory playerFactory;


    SpawnController spawnController;
    SpawnState spawnState;

    LinearProjectileController linearProjectileController;

    MovementController movementController;


    CollisionDetectionService collisionDetectionService;

    GameScreen() {

        //TODO load jsondata: create new hashmap
        readJSON();

        batch = new SpriteBatch();


        texturePathEnemy1 = "enemy1.png";
        texturePathEnemy2 = "enemy2.png";
        texturePathProjectileEnemy1 = "laserRed10.png";
        texturePathProjectileEnemy2 = "laserRed10.png";

        texturePathMidBoss = "midboss1.png";
        texturePathFinalBoss = "boss1.png";
        texturePathProjectileMidBoss = "midBossProjectile1.png";
        texturePathProjectileFinalBoss = "bossProjectile1.png";


        playerFactory = new PlayerFactory();
        texturePathPlayer = "playerShip1.png";
        texturePathProjectilePlayer = "playerProjectile2.png";
        playerCharacter = playerFactory.createActor("player", 9, 5, 10, 10, (float) WORLD_WIDTH / 2, (float) WORLD_HEIGHT / 4, 0.5f, 1f, 5, 45,
                texturePathPlayer, texturePathProjectilePlayer, 0f, 0f, 0f, "player");


        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);


        texturePathBackgrounds = new String[]{"space1.jpg", "parallex1.png", "parallex2.png", "parallex3.png"};
        levelFactory = new LevelFactory();
        level1 = levelFactory.createWorld("level1", texturePathBackgrounds, WORLD_WIDTH, WORLD_HEIGHT);


        playerProjectileList = new LinkedList<>();
        enemyProjectileList = new LinkedList<>();
        enemyProjectileList1 = new LinkedList<>();
        midBossProjectileList = new LinkedList<>();
        finalBossProjectileList = new LinkedList<>();


        enemyList = new LinkedList<>();
        enemyList1 = new LinkedList<>();
        midBoss = new LinkedList<>();
        finalBoss = new LinkedList<>();


        spawnController = new SpawnController(WORLD_WIDTH, WORLD_HEIGHT);
        linearProjectileController = new LinearProjectileController();
        movementController = new MovementController();

        collisionDetectionService = new CollisionDetectionService();

        prepareHUD();

    }

    private void prepareHUD() {
        //create a bitmapfont from font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

        font = fontGenerator.generateFont(fontParameter);

        //scale the font to fit world
        font.getData().setScale(0.08f);

        //calculate hud margins
        hudVerticalMargin = font.getCapHeight() / 2;
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {

        batch.begin();
        // update time.
        playerCharacter.update(deltaTime);
        //scrolling background
        level1.renderBackground(deltaTime, batch);

        //player
        playerCharacter.draw(batch);

        // Update playerCharacter position based on user input.
        playerCharacter.moveActor(deltaTime, WORLD_WIDTH, WORLD_HEIGHT, .0f);



        //TODO: set variables

        //enemy1
//        spawnEnemyShips(deltaTime);
        enemyList = spawnController.spawnEnemyShips("type1", deltaTime, enemySpawnTimer, timeBetweenEnemySpawns, enemyList, number_enemy_1,
                "enemy", 48, 1, 10, 10, Math.min(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_WIDTH - 1), WORLD_HEIGHT - 5, 0.8f,
                0.3f, 5, 50, texturePathEnemy1, texturePathProjectileEnemy1, 0.125f, 0.819f, 0.05f, "regular");

        enemyList1 = spawnController.spawnEnemyShips("type2", deltaTime, enemySpawnTimer, timeBetweenEnemySpawns, enemyList1, number_enemy_2,
                "enemy", 48, 1, 10, 10, Math.min(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_WIDTH - 1), WORLD_HEIGHT - 5, 0.8f,
                0.3f, 5, 50, texturePathEnemy2, texturePathProjectileEnemy2, 0.138f, 0.847f, 0.037f, "circular");


        enemyList = movementController.moveAI(batch, deltaTime, WORLD_WIDTH, WORLD_HEIGHT, enemyList, enemy1LifeSpan);
        enemyList1 = movementController.moveAI(batch, deltaTime, WORLD_WIDTH, WORLD_HEIGHT, enemyList1, enemy2LifeSpan);


        spawnState = spawnController.spawnBoss("midBoss", deltaTime, stateTime, timetoStartMidBoss, midBoss, "boss", 60, 5, 15, 15, SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 15) + 7.5f, WORLD_HEIGHT - 7.5f, 0.5f,
                1f, 7, 50, texturePathMidBoss, texturePathProjectileMidBoss, 0.125f, 0.819f, 0.05f, "boss");
        midBoss = spawnState.getActorList();
        stateTime = spawnState.getStateTime();


        movementController.moveAI(batch, deltaTime, WORLD_WIDTH, WORLD_HEIGHT, midBoss, midBossLifeSpan);


        spawnState = spawnController.spawnBoss("finalBoss", deltaTime, stateTime, timetoStartFinalBoss, finalBoss, "boss", 40, 5, 20, 20, SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 20) + 10, WORLD_HEIGHT - 10, 0.3f,
                2f, 10, 50, texturePathFinalBoss, texturePathProjectileFinalBoss, 0.125f, 0.819f, 0.05f, "boss");
        finalBoss = spawnState.getActorList();
        stateTime = spawnState.getStateTime();

        movementController.moveAI(batch, deltaTime, WORLD_WIDTH, WORLD_HEIGHT, finalBoss, bossLifeSpan);


        // Projectile.

        playerProjectileList = linearProjectileController.renderPlayerProjectiles(batch, WORLD_WIDTH, WORLD_HEIGHT, deltaTime,
                playerCharacter, playerProjectileList);

        enemyProjectileList = linearProjectileController.renderAIProjectiles(batch, WORLD_WIDTH, WORLD_HEIGHT, deltaTime,
                enemyList, enemyProjectileList);

        enemyProjectileList1 = linearProjectileController.renderAIProjectiles(batch, WORLD_WIDTH, WORLD_HEIGHT, deltaTime,
                enemyList1, enemyProjectileList1);

        midBossProjectileList = linearProjectileController.renderAIProjectiles(batch, WORLD_WIDTH, WORLD_HEIGHT, deltaTime,
                midBoss, midBossProjectileList);

        finalBossProjectileList = linearProjectileController.renderAIProjectiles(batch, WORLD_WIDTH, WORLD_HEIGHT, deltaTime,
                finalBoss, finalBossProjectileList);


        //detect collision
        collisionDetectionService.run(score, playerCharacter, playerProjectileList, enemyList, enemyProjectileList,
                enemyList1, enemyProjectileList1, midBoss, midBossProjectileList, finalBoss, finalBossProjectileList);


        //hud rendering
        updateAndRenderHUD(deltaTime);

        batch.end();

    }

    private void updateAndRenderHUD(float deltaTime) {
        playTime += deltaTime;
        //render top row label
        //font.draw(batch,"Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Time", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, "Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Health", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //render second row values
        //font.draw(batch, String.format(Locale.getDefault(), "%.0f", stateTime), hudLeftX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%06d", score), hudLeftX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%.0f", playTime), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerCharacter.getHealth()), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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


    public HashMap<String, String> readJSON(){
        JSONParser parser = new JSONParser();
        HashMap<String, HashMap<String,String>> jsonData = new HashMap();
        try {
           JSONObject data = (JSONObject) parser.parse(new FileReader("parameters.json"));

           //TODO WORLD
           JSONObject world = (JSONObject) data.get("WORLD");
           HashMap<String,String> worldData= new HashMap<>();
           worldData.put("WORLD_WIDTH",(String) world.get("WORLD_WIDTH"));
           worldData.put("WORLD_HEIGHT",(String) world.get("WORLD_HEIGHT"));

           //TODO HUD


            //TODO ENEMY1

            //TODO ENEMY2


            //TODO MIDBOSS

            //TODO BOSS

           jsonData.put("WORLD_WIDTH",worldData);
           System.out.println(jsonData.get("WORLD_WIDTH"));


            } catch (ParseException parseException) {
            parseException.printStackTrace();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //RETURN jsonData
        return null;
    }


}
