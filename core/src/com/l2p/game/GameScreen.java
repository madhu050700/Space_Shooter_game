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
import com.l2p.game.actor.controllers.SpawnController;
import com.l2p.game.actor.controllers.SpawnState;
import com.l2p.game.actor.factories.ActorFactory;
import com.l2p.game.actor.factories.PlayerFactory;
import com.l2p.game.collision.services.CollisionDetectionService;
import com.l2p.game.movement.controllers.MovementController;
import com.l2p.game.projectile.abstractProducts.Projectile;
import com.l2p.game.projectile.controllers.LinearProjectileController;
import com.l2p.game.world.abstractProducts.World;
import com.l2p.game.world.factories.LevelFactory;
import com.l2p.game.world.factories.WorldFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
    //Head-Up display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;
    //Factories
    WorldFactory levelFactory;
    ActorFactory playerFactory;
    SpawnController spawnController;
    SpawnState spawnState;
    LinearProjectileController linearProjectileController;
    MovementController movementController;
    CollisionDetectionService collisionDetectionService;
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
    private int number_enemy_2 ;
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

    GameScreen() {

        //TODO load jsondata: create new hashmap

        gameData = readJSON();



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
        enemy2LifeSpan =  Float.parseFloat(gameData.get("enemy2").get("lifespan"));
        midBossLifeSpan =  Float.parseFloat(gameData.get("midBoss").get("lifespan"));
        bossLifeSpan =  Float.parseFloat(gameData.get("boss").get("lifespan"));

        texturePathEnemy1 =  gameData.get("enemy1").get("texturePath");
        texturePathEnemy2 = gameData.get("enemy2").get("texturePath");
        texturePathProjectileEnemy1 = gameData.get("enemy1").get("texturePathProjectile");
        texturePathProjectileEnemy2 = gameData.get("enemy2").get("texturePathProjectile");

        texturePathMidBoss = gameData.get("midBoss").get("texturePath");;
        texturePathFinalBoss = gameData.get("boss").get("texturePath");;
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


    public HashMap<String, HashMap<String, String>> readJSON() {
        JSONParser parser = new JSONParser();
        HashMap<String, HashMap<String, String>> jsonData = new HashMap();
        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader("parameters.json"));


            //TODO WORLD
            JSONObject world = (JSONObject) data.get("WORLD");
            HashMap<String, String> worldData = new HashMap<>();

            worldData.put("timeBetweenEnemySpawns", (String) world.get("timeBetweenEnemySpawns"));
            worldData.put("enemySpawnTimer", (String) world.get("enemySpawnTimer"));
            worldData.put("timeToStartMidBoss", (String) world.get("timeToStartMidBoss"));
            worldData.put("timeToStartFinalBoss", (String) world.get("timeToStartFinalBoss"));
            worldData.put("WORLD_WIDTH", (String) world.get("WORLD_WIDTH"));
            worldData.put("WORLD_HEIGHT", (String) world.get("WORLD_HEIGHT"));
            worldData.put("background1", (String) world.get("background1"));
            worldData.put("background2", (String) world.get("background2"));
            worldData.put("background3", (String) world.get("background3"));
            worldData.put("background4", (String) world.get("background4"));

            //TODO HUD
            JSONObject hud = (JSONObject) data.get("HUD");
            HashMap<String, String> hudData = new HashMap<>();

            hudData.put("size", (String) hud.get("size"));
            hudData.put("borderwidth", (String) hud.get("borderwidth"));
            hudData.put("color_r", (String) hud.get("color_r"));
            hudData.put("color_g", (String) hud.get("color_g"));
            hudData.put("color_b", (String) hud.get("color_b"));
            hudData.put("color_a", (String) hud.get("color_a"));
            hudData.put("border_r", (String) hud.get("border_r"));
            hudData.put("border_g", (String) hud.get("border_g"));
            hudData.put("border_b", (String) hud.get("border_b"));
            hudData.put("border_a", (String) hud.get("border_a"));
            hudData.put("scale", (String) hud.get("scale"));

            //TODO: Player.
            JSONObject player = (JSONObject) data.get("player");
            HashMap<String, String> playerData = new HashMap<>();

            playerData.put("agent", (String) player.get("agent"));
            playerData.put("texturePath", (String) player.get("texturePath"));
            playerData.put("texturePathProjectile", (String) player.get("texturePathProjectile"));
            playerData.put("type", (String) player.get("type"));
            playerData.put("movementSpeed", (String) player.get("movementSpeed"));
            playerData.put("health", (String) player.get("health"));
            playerData.put("width", (String) player.get("width"));
            playerData.put("height", (String) player.get("height"));
            playerData.put("timeBetweenShots", (String) player.get("timeBetweenShots"));
            playerData.put("projectileWidth", (String) player.get("projectileWidth"));
            playerData.put("projectileHeight", (String) player.get("projectileHeight"));
            playerData.put("projectileSpeed", (String) player.get("projectileSpeed"));
            playerData.put("projectile_x1", (String) player.get("projectile_x1"));
            playerData.put("projectile_x2", (String) player.get("projectile_x2"));
            playerData.put("projectile_y", (String) player.get("projectile_y"));
            playerData.put("movementType", (String) player.get("movementType"));

            //TODO ENEMY1
            JSONObject enemy1 = (JSONObject) data.get("enemy1");
            HashMap<String, String> enemy1Data = new HashMap<>();

            enemy1Data.put("agent", (String) enemy1.get("agent"));
            enemy1Data.put("max_num", (String) enemy1.get("max_num"));
            enemy1Data.put("lifespan", (String) enemy1.get("lifespan"));
            enemy1Data.put("texturePath", (String) enemy1.get("texturePath"));
            enemy1Data.put("texturePathProjectile", (String) enemy1.get("texturePathProjectile"));
            enemy1Data.put("movementSpeed", (String) enemy1.get("movementSpeed"));
            enemy1Data.put("health", (String) enemy1.get("health"));
            enemy1Data.put("width", (String) enemy1.get("width"));
            enemy1Data.put("height", (String) enemy1.get("height"));
            enemy1Data.put("timeBetweenShots", (String) enemy1.get("timeBetweenShots"));
            enemy1Data.put("projectileWidth", (String) enemy1.get("projectileWidth"));
            enemy1Data.put("projectileHeight", (String) enemy1.get("projectileHeight"));
            enemy1Data.put("projectileSpeed", (String) enemy1.get("projectileSpeed"));
            enemy1Data.put("projectile_x1", (String) enemy1.get("projectile_x1"));
            enemy1Data.put("projectile_x2", (String) enemy1.get("projectile_x2"));
            enemy1Data.put("projectile_y", (String) enemy1.get("projectile_y"));
            enemy1Data.put("movementType", (String) enemy1.get("movementType"));

            //TODO ENEMY2
            JSONObject enemy2 = (JSONObject) data.get("enemy2");
            HashMap<String, String> enemy2Data = new HashMap<>();

            enemy2Data.put("agent", (String) enemy2.get("agent"));
            enemy2Data.put("max_num", (String) enemy2.get("max_num"));
            enemy2Data.put("lifespan", (String) enemy2.get("lifespan"));
            enemy2Data.put("texturePath", (String) enemy2.get("texturePath"));
            enemy2Data.put("texturePathProjectile", (String) enemy2.get("texturePathProjectile"));
            enemy2Data.put("movementSpeed", (String) enemy2.get("movementSpeed"));
            enemy2Data.put("health", (String) enemy2.get("health"));
            enemy2Data.put("width", (String) enemy2.get("width"));
            enemy2Data.put("height", (String) enemy2.get("height"));
            enemy2Data.put("timeBetweenShots", (String) enemy2.get("timeBetweenShots"));
            enemy2Data.put("projectileWidth", (String) enemy2.get("projectileWidth"));
            enemy2Data.put("projectileHeight", (String) enemy2.get("projectileHeight"));
            enemy2Data.put("projectileSpeed", (String) enemy2.get("projectileSpeed"));
            enemy2Data.put("projectile_x1", (String) enemy2.get("projectile_x1"));
            enemy2Data.put("projectile_x2", (String) enemy2.get("projectile_x2"));
            enemy2Data.put("projectile_y", (String) enemy2.get("projectile_y"));
            enemy2Data.put("movementType", (String) enemy2.get("movementType"));

            //TODO MIDBOSS
            JSONObject midBoss = (JSONObject) data.get("midBoss");
            HashMap<String, String> midBossData = new HashMap<>();

            midBossData.put("agent", (String) midBoss.get("agent"));
            midBossData.put("lifespan", (String) midBoss.get("lifespan"));
            midBossData.put("texturePath", (String) midBoss.get("texturePath"));
            midBossData.put("texturePathProjectile", (String) midBoss.get("texturePathProjectile"));
            midBossData.put("type", (String) midBoss.get("type"));
            midBossData.put("movementSpeed", (String) midBoss.get("movementSpeed"));
            midBossData.put("health", (String) midBoss.get("health"));
            midBossData.put("width", (String) midBoss.get("width"));
            midBossData.put("height", (String) midBoss.get("height"));
            midBossData.put("timeBetweenShots", (String) midBoss.get("timeBetweenShots"));
            midBossData.put("projectileWidth", (String) midBoss.get("projectileWidth"));
            midBossData.put("projectileHeight", (String) midBoss.get("projectileHeight"));
            midBossData.put("projectileSpeed", (String) midBoss.get("projectileSpeed"));
            midBossData.put("projectile_x1", (String) midBoss.get("projectile_x1"));
            midBossData.put("projectile_x2", (String) midBoss.get("projectile_x2"));
            midBossData.put("projectile_y", (String) midBoss.get("projectile_y"));
            midBossData.put("movementType", (String) midBoss.get("movementType"));

            //TODO BOSS
            JSONObject boss = (JSONObject) data.get("boss");
            HashMap<String, String> bossData = new HashMap<>();

            bossData.put("agent", (String) boss.get("agent"));
            bossData.put("lifespan", (String) boss.get("lifespan"));
            bossData.put("texturePath", (String) boss.get("texturePath"));
            bossData.put("texturePathProjectile", (String) boss.get("texturePathProjectile"));
            bossData.put("type", (String) boss.get("type"));
            bossData.put("movementSpeed", (String) boss.get("movementSpeed"));
            bossData.put("health", (String) boss.get("health"));
            bossData.put("width", (String) boss.get("width"));
            bossData.put("height", (String) boss.get("height"));
            bossData.put("timeBetweenShots", (String) boss.get("timeBetweenShots"));
            bossData.put("projectileWidth", (String) boss.get("projectileWidth"));
            bossData.put("projectileHeight", (String) boss.get("projectileHeight"));
            bossData.put("projectileSpeed", (String) boss.get("projectileSpeed"));
            bossData.put("projectile_x1", (String) boss.get("projectile_x1"));
            bossData.put("projectile_x2", (String) boss.get("projectile_x2"));
            bossData.put("projectile_y", (String) boss.get("projectile_y"));
            bossData.put("movementType", (String) boss.get("movementType"));

            // Create jsonData hashmap
            jsonData.put("WORLD", worldData);
            jsonData.put("HUD", hudData);
            jsonData.put("player", playerData);
            jsonData.put("enemy1", enemy1Data);
            jsonData.put("enemy2", enemy2Data);
            jsonData.put("midBoss", midBossData);
            jsonData.put("boss", bossData);


        } catch (ParseException parseException) {
            parseException.printStackTrace();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //RETURN jsonData
        return jsonData;
    }


}
