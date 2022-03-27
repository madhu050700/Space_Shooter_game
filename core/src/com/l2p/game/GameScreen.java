
package com.l2p.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.l2p.game.actor.factories.BossFactory;
import com.l2p.game.actor.factories.EnemyFactory;
import com.l2p.game.actor.factories.PlayerFactory;
import com.l2p.game.collision.EnemyCollisionDetector;
import com.l2p.game.collision.PlayerCollisionDetector;
import com.l2p.game.controllers.SpawnController.SpawnController;
import com.l2p.game.controllers.SpawnController.SpawnState;
import com.l2p.game.projectile.abstractProducts.Projectile;
import com.l2p.game.projectile.factories.ProjectileFactory;
import com.l2p.game.world.abstractProducts.World;
import com.l2p.game.world.factories.LevelFactory;
import com.l2p.game.world.factories.WorldFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

public class GameScreen implements Screen {


    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;


    //timing

    private float timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer = 0;
    private float stateTime,stateTime1,playTime=0;
    private float timetoStartMidBoss = 3f;
    private float timetoStartFinalBoss = 5f;



    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT= 128;
    private int number_enemy_1 = 2;
    private int number_enemy_2 = 3;
    private float enemy1LifeSpan = 10f;
    private float enemy2LifeSpan = 10f;
    private float midBossLifeSpan = 100000f;
    private  float bossLifeSpan = 100000f;

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
    private LinkedList<Projectile> enemyProjectileList,enemyProjectileList1;
    private LinkedList<Projectile> midBossProjectileList,finalBossProjectileList;
    private LinkedList<Actor> enemyList,enemyList1;
    private LinkedList<Actor> midBoss,finalBoss;

    //Head-Up display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;

    //Factories
    WorldFactory levelFactory;

    ActorFactory playerFactory;


    SpawnController spawnController;
    SpawnState spawnState;


    GameScreen(){

        batch = new SpriteBatch();


        texturePathEnemy1 = "enemy1.png";
        texturePathEnemy2 = "enemy2.png";
        texturePathProjectileEnemy1 = "laserRed10.png";
        texturePathProjectileEnemy2 = "laserRed10.png";

        texturePathMidBoss = "midboss1.png";
        texturePathFinalBoss = "boss1.png";
        texturePathProjectileMidBoss = "midBossProjectile1.png";
        texturePathProjectileFinalBoss = "bossProjectile1.png";



        playerFactory =  new PlayerFactory();
        texturePathPlayer = "playerShip1.png" ;
        texturePathProjectilePlayer = "playerProjectile2.png";
        playerCharacter = playerFactory.createActor("player",9,5,10, 10, (float) WORLD_WIDTH/2, (float) WORLD_HEIGHT/4,0.5f,1f,5,45,
                texturePathPlayer,texturePathProjectilePlayer, 0f,0f,0f,"player");


        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);


        texturePathBackgrounds = new String[]{"space1.jpg", "parallex1.png", "parallex2.png", "parallex3.png"};
        levelFactory = new LevelFactory();
        level1 = levelFactory.createWorld("level1",texturePathBackgrounds,WORLD_WIDTH,WORLD_HEIGHT);




        playerProjectileList = new LinkedList<>();
        enemyProjectileList = new LinkedList<>();
        enemyProjectileList1 = new LinkedList<>();
        midBossProjectileList = new LinkedList<>();
        finalBossProjectileList = new LinkedList<>();




        enemyList = new LinkedList<>();
        enemyList1 = new LinkedList<>();
        midBoss = new LinkedList<>();
        finalBoss = new LinkedList<>();


        spawnController = new SpawnController(WORLD_WIDTH,WORLD_HEIGHT);

        prepareHUD();

    }

    private void prepareHUD() {
        //create a bitmapfont from font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal( "EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1,1,1,0.3f);
        fontParameter.borderColor = new Color(0,0,0,0.3f);

        font = fontGenerator.generateFont(fontParameter);

        //scale the font to fit world
        font.getData().setScale(0.08f);

        //calculate hud margins
        hudVerticalMargin = font.getCapHeight() /2;
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
        level1.renderBackground(deltaTime,batch);

        //player
        playerCharacter.draw(batch);

        // Update playerCharacter position based on user input.
        playerCharacter.moveActor(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,.0f);


        //enemy1
//        spawnEnemyShips(deltaTime);
        enemyList = spawnController.spawnEnemyShips("type1", deltaTime,enemySpawnTimer,timeBetweenEnemySpawns,enemyList,number_enemy_1,
                "enemy",48, 1, 10, 10, Math.min(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_WIDTH -1), WORLD_HEIGHT - 5, 0.8f,
                0.3f, 5, 50, texturePathEnemy1, texturePathProjectileEnemy1, 0.125f, 0.819f, 0.05f, "regular");

        enemyList1 = spawnController.spawnEnemyShips("type2",deltaTime,enemySpawnTimer,timeBetweenEnemySpawns,enemyList1,number_enemy_2,
                "enemy",48,1,10,10,Math.min(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_WIDTH -1), WORLD_HEIGHT - 5,0.8f,
                0.3f, 5, 50,texturePathEnemy2,texturePathProjectileEnemy2,0.138f,0.847f,0.037f, "circular");

        ListIterator<Actor> enemyListIterator = enemyList.listIterator();
        while(enemyListIterator.hasNext()){
            Actor enemy = enemyListIterator.next();

            if(enemy.moveActor(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,enemy1LifeSpan)){
                enemyListIterator.remove();
                System.out.println("Enemy 1 left");
            }
            else {
                enemy.update(deltaTime);
                enemy.draw(batch);
            }
        }
        enemyListIterator = enemyList1.listIterator();

        while(enemyListIterator.hasNext()){
            Actor enemy1 = enemyListIterator.next();
            if(enemy1.moveActor(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,enemy2LifeSpan)){
               enemyListIterator.remove();
               System.out.println("Enemy 2 left");
            }
            else{
                enemy1.update(deltaTime);
                enemy1.draw(batch);
            }
        }


        spawnState = spawnController.spawnBoss("midBoss",deltaTime,stateTime,timetoStartMidBoss,midBoss,"boss",60,5,15,15,SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 15) + 7.5f, WORLD_HEIGHT - 7.5f,0.5f,
                1f, 7, 50,texturePathMidBoss,texturePathProjectileMidBoss,0.125f,0.819f,0.05f, "boss");
        midBoss = spawnState.getActorList();
        stateTime = spawnState.getStateTime();

        ListIterator<Actor> midBossIterator = midBoss.listIterator();
        while(midBossIterator.hasNext()){
            Actor midBoss= midBossIterator.next();
            midBoss.moveActor(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,midBossLifeSpan);
//            moveMidBoss(midBoss,deltaTime);
            midBoss.update(deltaTime);
            midBoss.draw(batch);
        }

        spawnState = spawnController.spawnBoss("finalBoss",deltaTime,stateTime,timetoStartFinalBoss,finalBoss,"boss",40,5,20,20,SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 20) + 10, WORLD_HEIGHT - 10,0.3f,
                2f, 10, 50,texturePathFinalBoss,texturePathProjectileFinalBoss,0.125f,0.819f,0.05f, "boss");
        finalBoss = spawnState.getActorList();
        stateTime = spawnState.getStateTime();


        ListIterator<Actor> finalBossIterator = finalBoss.listIterator();
        while(finalBossIterator.hasNext()){
            Actor finalBoss= finalBossIterator.next();
            finalBoss.moveActor(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,bossLifeSpan);
//            moveFinalBoss(finalBoss,deltaTime);
            finalBoss.update(deltaTime);
            finalBoss.draw(batch);
        }

        // Projectile.
        renderProjectile(deltaTime);

        //detect collision
        detectCollision();


        //hud rendering
        updateAndRenderHUD(deltaTime);

        batch.end();

    }

    private void updateAndRenderHUD(float deltaTime){
        playTime += deltaTime;
        //render top row label
        //font.draw(batch,"Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch,"Time", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch,"Health", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //render second row values
        //font.draw(batch, String.format(Locale.getDefault(), "%.0f", stateTime), hudLeftX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%.0f", playTime), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerCharacter.getHealth()), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
    }


    private void renderProjectile(float deltaTime)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // Create projectile for playerCharacter.
            if (playerCharacter.canFireProjectile()) {
                LinkedList<Projectile> projectiles = playerCharacter.fire();
                for (Projectile proj : projectiles) {
                    playerProjectileList.add(proj);
                }
            }
        }


        ListIterator<Actor> enemyListIterator = enemyList.listIterator();
        while(enemyListIterator.hasNext()){
            Actor enemy = enemyListIterator.next();
            if(enemy.canFireProjectile()){
                LinkedList<Projectile> projectiles = enemy.fire();
                enemyProjectileList.addAll(projectiles);
            }
        }
        enemyListIterator = enemyList1.listIterator();
        while(enemyListIterator.hasNext()){
            Actor enemy1 = enemyListIterator.next();
            if(enemy1.canFireProjectile()){
                LinkedList<Projectile> projectiles = enemy1.fire();
                enemyProjectileList1.addAll(projectiles);
            }
        }
        ListIterator<Actor> midBossIterator = midBoss.listIterator();
        while(midBossIterator.hasNext()){
            Actor midBoss = midBossIterator.next();
            if(midBoss.canFireProjectile()){
                LinkedList<Projectile> projectiles= midBoss.fire();
                midBossProjectileList.addAll(projectiles);
            }
        }
        ListIterator<Actor> finalBossListIterator = finalBoss.listIterator();
        while(finalBossListIterator.hasNext()){
            Actor finalBoss = finalBossListIterator.next();
            if(finalBoss.canFireProjectile()){
                LinkedList<Projectile> projectiles = finalBoss.fire();
                finalBossProjectileList.addAll(projectiles);
            }
        }

        // Draw Projectiles.
        ListIterator<Projectile> iterator = playerProjectileList.listIterator();

        while (iterator.hasNext())
        {
            Projectile projectile = iterator.next();
            projectile.draw(batch);
//            projectile.getBoundingBox().y += projectile.getMovementSpeed() * deltaTime;
            if (projectile.move(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,"up") + projectile.getBoundingBox().height > WORLD_HEIGHT)
                iterator.remove();
        }


        iterator = enemyProjectileList.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            if(projectile.move(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,"down") + projectile.getBoundingBox().height < 0){
                iterator.remove();
            }
        }
        iterator = enemyProjectileList1.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            if(projectile.move(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,"down")+ projectile.getBoundingBox().height < 0){
                iterator.remove();
            }
        }
        iterator = midBossProjectileList.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            if(projectile.move(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,"down") + projectile.getBoundingBox().height < 0){
                iterator.remove();
            }
        }
        iterator = finalBossProjectileList.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            if(projectile.move(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,"down")+ projectile.getBoundingBox().height < 0){
                iterator.remove();
            }
        }
    }

    private void detectCollision() {
        ListIterator<Projectile> iterator = playerProjectileList.listIterator();
        EnemyCollisionDetector.detectCollision(playerProjectileList,enemyList);
        EnemyCollisionDetector.detectCollision(playerProjectileList,enemyList1);
        EnemyCollisionDetector.detectCollision(playerProjectileList,midBoss);
        EnemyCollisionDetector.detectCollision(playerProjectileList, finalBoss);


        ArrayList<LinkedList<Projectile>> projectileListArray =  new ArrayList<LinkedList<Projectile>>();
        projectileListArray.add(enemyProjectileList);
        projectileListArray.add(enemyProjectileList1);
        projectileListArray.add(midBossProjectileList);
        projectileListArray.add(finalBossProjectileList);

        PlayerCollisionDetector.detectCollision(projectileListArray,playerCharacter);

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
