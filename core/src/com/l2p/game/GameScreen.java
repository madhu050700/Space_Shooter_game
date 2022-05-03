package com.l2p.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.l2p.game.PowerUp.PowerUpController;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.actor.controllers.SpawnController;
import com.l2p.game.actor.controllers.SpawnState;
import com.l2p.game.actor.factories.ActorFactory;
import com.l2p.game.actor.factories.PlayerFactory;
import com.l2p.game.collision.ExplosionController;
import com.l2p.game.collision.State.CollisionDetectionState;
import com.l2p.game.collision.services.CollisionDetectionService;
import com.l2p.game.engine.JSONEngine;
import com.l2p.game.movement.controllers.MovementController;
import com.l2p.game.projectile.abstractProducts.Projectile;
import com.l2p.game.projectile.controllers.LinearProjectileController;
import com.l2p.game.world.abstractProducts.World;
import com.l2p.game.world.factories.LevelFactory;
import com.l2p.game.world.factories.WorldFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;

public class GameScreen implements Screen {


    //Texture Paths
    String texturePathEnemy1;
    String texturePathEnemy2;
    String texturePathProjectileEnemy1;


    //timing


    //TODO Only declare variables here, set values from hasmap that is returned by readJSON
    String texturePathProjectileEnemy2;
    String texturePathMidBoss;
    String texturePathFinalBoss;
    String texturePathProjectileMidBoss;
    String texturePathProjectileFinalBoss;
    String texturePathPlayer;
    String texturePathProjectilePlayer;
    String[] texturePathBackgrounds;
    //isplay setting
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth, bottomY,bottom2Y;
    //Factories
    WorldFactory levelFactory;
    ActorFactory playerFactory;
    SpawnController spawnController;
    SpawnState spawnState;
    LinearProjectileController linearProjectileController;
    MovementController movementController;
    CollisionDetectionService collisionDetectionService;
    PowerUpController powerUpController;
    ExplosionController explosionController;
    float duration = -1;
    Boolean trigger = false;
    //screen
    private Camera camera;
    private Viewport viewport;
    //graphics
    private SpriteBatch batch;
    private float timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer = 0;
    private float stateTime, stateTime1, playTime = 0;
    private float timetoStartMidBoss = 20f;
    private float timetoStartFinalBoss = 40f;
    //world parameters
    private int WORLD_WIDTH;
    private int WORLD_HEIGHT;
    private int number_enemy_1;
    private int number_enemy_2;
    private float enemy1LifeSpan;
    private float enemy2LifeSpan;
    private float midBossLifeSpan;
    private float bossLifeSpan;
    //game Objects
    private World level1;
    private Actor playerCharacter;
    private LinkedList<Projectile> playerProjectileList;
    private LinkedList<Projectile> enemyProjectileList, enemyProjectileList1;
    private LinkedList<Projectile> midBossProjectileList, finalBossProjectileList;
    private LinkedList<Actor> enemyList, enemyList1;
    private LinkedList<Actor> midBoss, finalBoss;
    private int score = 0;
    private HashMap<String, HashMap<String, String>> gameData;
    private CollisionDetectionState collisionDetectionState;
    private boolean cheating=false;
    private JSONEngine engine;
    private Music eightBitSurf;

    GameScreen() {

        //TODO load jsondata: create new hashmap
        engine = new JSONEngine();
        gameData = engine.readJSON();


        WORLD_WIDTH = Integer.parseInt(gameData.get("WORLD").get("WORLD_WIDTH"));
        System.out.println(WORLD_WIDTH);
        WORLD_HEIGHT = Integer.parseInt(gameData.get("WORLD").get("WORLD_HEIGHT"));


        timeBetweenEnemySpawns = Float.parseFloat(gameData.get("WORLD").get("timeBetweenEnemySpawns"));
        enemySpawnTimer = Float.parseFloat(gameData.get("WORLD").get("enemySpawnTimer"));
        timetoStartMidBoss = Float.parseFloat(gameData.get("WORLD").get("timeToStartMidBoss"));
        timetoStartFinalBoss = Float.parseFloat(gameData.get("WORLD").get("timeToStartFinalBoss"));
        //world parameters

        number_enemy_1 = Integer.parseInt(gameData.get("enemy1").get("max_num"));
        number_enemy_2 = Integer.parseInt(gameData.get("enemy2").get("max_num"));
        enemy1LifeSpan = Float.parseFloat(gameData.get("enemy1").get("lifespan"));
        enemy2LifeSpan = Float.parseFloat(gameData.get("enemy2").get("lifespan"));
        midBossLifeSpan = Float.parseFloat(gameData.get("midBoss").get("lifespan"));
        bossLifeSpan = Float.parseFloat(gameData.get("boss").get("lifespan"));

        texturePathEnemy1 = gameData.get("enemy1").get("texturePath");
        texturePathEnemy2 = gameData.get("enemy2").get("texturePath");
        texturePathProjectileEnemy1 = gameData.get("enemy1").get("texturePathProjectile");
        texturePathProjectileEnemy2 = gameData.get("enemy2").get("texturePathProjectile");

        texturePathMidBoss = gameData.get("midBoss").get("texturePath");
        ;
        texturePathFinalBoss = gameData.get("boss").get("texturePath");
        ;
        texturePathProjectileMidBoss = gameData.get("midBoss").get("texturePathProjectile");
        texturePathProjectileFinalBoss = gameData.get("boss").get("texturePathProjectile");


        batch = new SpriteBatch();


        playerFactory = new PlayerFactory();
        texturePathPlayer = "playerShip1.png";
        texturePathProjectilePlayer = "playerProjectile2.png";

        HashMap<String, String> playerData = gameData.get("player");
        playerCharacter = playerFactory.createActor(
                playerData.get("type"),
                Integer.parseInt(playerData.get("movementSpeed")),
                Integer.parseInt(playerData.get("health")),
                Float.parseFloat(playerData.get("width")),
                Float.parseFloat(playerData.get("height")),
                (float) WORLD_WIDTH / 2,
                (float) WORLD_HEIGHT / 4,
                Float.parseFloat(playerData.get("timeBetweenShots")),
                Float.parseFloat(playerData.get("projectileWidth")),
                Float.parseFloat(playerData.get("projectileHeight")),
                Float.parseFloat(playerData.get("projectileSpeed")),
                playerData.get("texturePath"),
                playerData.get("texturePathProjectile"),
                Float.parseFloat(playerData.get("projectile_x1")),
                Float.parseFloat(playerData.get("projectile_x2")),
                Float.parseFloat(playerData.get("projectile_y")),
                playerData.get("movementType"));


        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);


        texturePathBackgrounds = new String[]{
                gameData.get("WORLD").get("background1"),
                gameData.get("WORLD").get("background2"),
                gameData.get("WORLD").get("background3"),
                gameData.get("WORLD").get("background4")};
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
        powerUpController = new PowerUpController();

        explosionController = new ExplosionController();

        collisionDetectionService = new CollisionDetectionService();
        collisionDetectionState = new CollisionDetectionState(score, powerUpController);

        prepareBGM();
        prepareHUD();

    }
    private void prepareBGM(){
        eightBitSurf = Gdx.audio.newMusic(Gdx.files.internal("8_Bit_Surf.mp3"));
        eightBitSurf.setLooping(true);
        eightBitSurf.setVolume(0.1f);
        eightBitSurf.play();
    }
    private void prepareHUD() {
        //create a bitmapfont from font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = Integer.parseInt(gameData.get("HUD").get("size"));
        fontParameter.borderWidth = Float.parseFloat(gameData.get("HUD").get("borderwidth"));
        fontParameter.color = new Color(Integer.parseInt(gameData.get("HUD").get("color_r")), Integer.parseInt(gameData.get("HUD").get("color_g")),
                Integer.parseInt(gameData.get("HUD").get("color_b")), Float.parseFloat(gameData.get("HUD").get("color_a")));

        fontParameter.borderColor = new Color(Integer.parseInt(gameData.get("HUD").get("border_r")), Integer.parseInt(gameData.get("HUD").get("border_g")),
                Integer.parseInt(gameData.get("HUD").get("border_b")), Float.parseFloat(gameData.get("HUD").get("border_a")));

        font = fontGenerator.generateFont(fontParameter);

        //scale the font to fit world
        font.getData().setScale(Float.parseFloat(gameData.get("HUD").get("scale")));

        //calculate hud margins
        hudVerticalMargin = font.getCapHeight() / 2;
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;
        bottomY=WORLD_HEIGHT/10;
        bottom2Y=WORLD_HEIGHT/5;
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
        if(playerCharacter.getHealth()>0)
            playerCharacter.moveActor(deltaTime, WORLD_WIDTH, WORLD_HEIGHT, .0f);
        else{
            font.draw(batch, "Game Over", hudCentreX, WORLD_HEIGHT/2, hudSectionWidth, Align.center, false);
            eightBitSurf.dispose();
        }




        //enemy1
        enemyList = spawnController.spawnEnemyShips(
                gameData.get("enemy1").get("agent"),
                deltaTime, enemySpawnTimer, timeBetweenEnemySpawns, enemyList, number_enemy_1,
                gameData.get("enemy1").get("type"),
                Integer.parseInt(gameData.get("enemy1").get("movementSpeed")),
                Integer.parseInt(gameData.get("enemy1").get("health")),
                Integer.parseInt(gameData.get("enemy1").get("width")),
                Integer.parseInt(gameData.get("enemy1").get("height")),
                Math.min(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_WIDTH - 1), WORLD_HEIGHT - 5,
                Float.parseFloat(gameData.get("enemy1").get("timeBetweenShots")),
                Float.parseFloat(gameData.get("enemy1").get("projectileWidth")),
                Float.parseFloat(gameData.get("enemy1").get("projectileHeight")),
                Float.parseFloat(gameData.get("enemy1").get("projectileSpeed")),
                texturePathEnemy1, texturePathProjectileEnemy1,
                Float.parseFloat(gameData.get("enemy1").get("projectile_x1")),
                Float.parseFloat(gameData.get("enemy1").get("projectile_x2")),
                Float.parseFloat(gameData.get("enemy1").get("projectile_y")),
                gameData.get("enemy1").get("movementType"));

        enemyList1 = spawnController.spawnEnemyShips(
                gameData.get("enemy2").get("agent"),
                deltaTime, enemySpawnTimer, timeBetweenEnemySpawns, enemyList1, number_enemy_2,
                gameData.get("enemy2").get("type"),
                Integer.parseInt(gameData.get("enemy2").get("movementSpeed")),
                Integer.parseInt(gameData.get("enemy2").get("health")),
                Integer.parseInt(gameData.get("enemy2").get("width")),
                Integer.parseInt(gameData.get("enemy2").get("height")),
                Math.min(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_WIDTH - 1), WORLD_HEIGHT - 5,
                Float.parseFloat(gameData.get("enemy2").get("timeBetweenShots")),
                Float.parseFloat(gameData.get("enemy2").get("projectileWidth")),
                Float.parseFloat(gameData.get("enemy2").get("projectileHeight")),
                Float.parseFloat(gameData.get("enemy2").get("projectileSpeed")),
                texturePathEnemy2, texturePathProjectileEnemy2,
                Float.parseFloat(gameData.get("enemy2").get("projectile_x1")),
                Float.parseFloat(gameData.get("enemy2").get("projectile_x2")),
                Float.parseFloat(gameData.get("enemy2").get("projectile_y")),
                gameData.get("enemy2").get("movementType"));

        enemyList = movementController.moveAI(batch, deltaTime, WORLD_WIDTH, WORLD_HEIGHT, enemyList, enemy1LifeSpan);
        enemyList1 = movementController.moveAI(batch, deltaTime, WORLD_WIDTH, WORLD_HEIGHT, enemyList1, enemy2LifeSpan);

        spawnState = spawnController.spawnBoss(
                gameData.get("midBoss").get("agent"),
                deltaTime, stateTime, timetoStartMidBoss, midBoss,
                gameData.get("midBoss").get("type"),
                Integer.parseInt(gameData.get("midBoss").get("movementSpeed")),
                Integer.parseInt(gameData.get("midBoss").get("health")),
                Integer.parseInt(gameData.get("midBoss").get("width")),
                Integer.parseInt(gameData.get("midBoss").get("height")),
                SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 15) + 7.5f, WORLD_HEIGHT - 7.5f,
                Float.parseFloat(gameData.get("midBoss").get("timeBetweenShots")),
                Float.parseFloat(gameData.get("midBoss").get("projectileWidth")),
                Float.parseFloat(gameData.get("midBoss").get("projectileHeight")),
                Float.parseFloat(gameData.get("midBoss").get("projectileSpeed")),
                texturePathMidBoss, texturePathProjectileMidBoss,
                Float.parseFloat(gameData.get("midBoss").get("projectile_x1")),
                Float.parseFloat(gameData.get("midBoss").get("projectile_x2")),
                Float.parseFloat(gameData.get("midBoss").get("projectile_y")),
                gameData.get("midBoss").get("movementType"));

        midBoss = spawnState.getActorList();
        stateTime = spawnState.getStateTime();

        movementController.moveAI(batch, deltaTime, WORLD_WIDTH, WORLD_HEIGHT, midBoss, midBossLifeSpan);

        spawnState = spawnController.spawnBoss(
                gameData.get("boss").get("agent"),
                deltaTime, stateTime, timetoStartFinalBoss, finalBoss,
                gameData.get("boss").get("type"),
                Integer.parseInt(gameData.get("boss").get("movementSpeed")),
                Integer.parseInt(gameData.get("boss").get("health")),
                Integer.parseInt(gameData.get("boss").get("width")),
                Integer.parseInt(gameData.get("boss").get("height")),
                SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 20) + 10, WORLD_HEIGHT - 10,
                Float.parseFloat(gameData.get("boss").get("timeBetweenShots")),
                Float.parseFloat(gameData.get("boss").get("projectileWidth")),
                Float.parseFloat(gameData.get("boss").get("projectileHeight")),
                Float.parseFloat(gameData.get("boss").get("projectileSpeed")),
                texturePathFinalBoss, texturePathProjectileFinalBoss,
                Float.parseFloat(gameData.get("boss").get("projectile_x1")),
                Float.parseFloat(gameData.get("boss").get("projectile_x2")),
                Float.parseFloat(gameData.get("boss").get("projectile_y")),
                gameData.get("boss").get("movementType"));

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
        collisionDetectionState = collisionDetectionService.run(score, deltaTime, batch, playerCharacter, playerProjectileList, enemyList, enemyProjectileList, enemyList1,
                enemyProjectileList1, midBoss, midBossProjectileList, finalBoss, finalBossProjectileList, powerUpController, collisionDetectionState, cheating, explosionController);
        this.score = collisionDetectionState.getScore();

        //explosion
        this.explosionController.drawExplosion(deltaTime, batch);

        //powerup
        this.powerUpController = collisionDetectionState.getPowerUpController();
        this.powerUpController.drawPowerUp(deltaTime, batch);
        if (trigger == false) {
            duration = this.powerUpController.triggerPowerUp() + playTime;
            trigger = true;
        }
        if (playTime < duration) {
            playerCharacter.setTimeBetweenShots(0f);
            font.draw(batch, "Power UP", hudCentreX, bottom2Y, hudSectionWidth, Align.center, false);
            font.draw(batch, String.format(Locale.getDefault(), "%.0f", duration), hudRightX, bottom2Y, hudSectionWidth, Align.center, false);
        } else {
            playerCharacter.setTimeBetweenShots(Float.parseFloat(gameData.get("player").get("timeBetweenShots")));
            trigger = false;
        }

        //press z to active cheating mode
        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            if (cheating==true){
                cheating=false;
            } else cheating=true;
        }
        if(cheating==true){
            font.draw(batch, "cheating mode", hudCentreX, bottomY, hudSectionWidth, Align.center, false);
        }

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

}
