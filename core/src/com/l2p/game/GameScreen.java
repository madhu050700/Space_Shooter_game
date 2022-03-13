
package com.l2p.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.l2p.game.projectile.Projectile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

public class GameScreen implements Screen {


    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
//    private Texture background;
    private Texture[] backgrounds;

    //player
    private Texture playerTexture;
    private Texture playerProjectileTexture;

    //enemyType1
    private Texture enemyType1Texture;
    private Texture enemyProjectileTexture, midBossProjectileTexture ,finalBossProjectileTexture;

    //enemyType2
    private Texture enemyType2Texture;

    //bosses
    private Texture midBossTexture,finalBossTexture;

    //timing
//    private int backgroundOffset;
    private float[] backgroundOffsets = {0,0,0,0};
    private  float backgroundMaxScrollingSpeed;
    private float timeBetweenEnemySpawns = 3f;
    private float enemySpawnTimer = 0;
    private float stateTime,stateTime1,playTime=0;
    private float timetoStartMidBoss = 24f;
    private float timetoStartFinalBoss = 30f;



    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT= 128;
    private int number_enemy_1 = 2;
    private int number_enemy_2 = 2;
    private float enemy1LifeSpan = 10f;
    private float enemy2LifeSpan = 10f;
    private float midBossLifeSpan = 100000f;
    private  float bossLifeSpan = 100000f;


    //game Objects
    private PlayerCharacter playerCharacter;
    private LinkedList<Projectile> playerProjectileList;
    private LinkedList<Projectile> enemyProjectileList,enemyProjectileList1;
    private LinkedList<Projectile> midBossProjectileList,finalBossProjectileList;
    private LinkedList<Enemy> enemyList,enemyList1;
    private LinkedList<Bosses> midBoss,finalBoss;

    //Head-Up display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;





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
        playerProjectileTexture = new Texture("playerProjectile2.png");
        playerCharacter = new PlayerCharacter(9,5,10, 10, WORLD_WIDTH/2, WORLD_HEIGHT/4,0.5f,1f,5,45,playerTexture,playerProjectileTexture);


        enemyType1Texture = new Texture("enemy1.png");
        enemyType2Texture = new Texture("enemy2.png");

        midBossTexture = new Texture("midboss1.png");


        finalBossTexture = new Texture("boss1.png");

        enemyProjectileTexture= new Texture("laserRed10.png");
        midBossProjectileTexture = new Texture("midBossProjectile1.png");
        finalBossProjectileTexture= new Texture("bossProjectile1.png");

//        enemyType1 = new Enemy(2,5,10,10,WORLD_WIDTH/2, WORLD_HEIGHT*3/4,0.5f, 0.7f, 5, 50,enemyType1Texture,null,0,0,0 );

        playerProjectileList = new LinkedList<>();
        enemyProjectileList = new LinkedList<>();
        enemyProjectileList1 = new LinkedList<>();
        midBossProjectileList = new LinkedList<>();
        finalBossProjectileList = new LinkedList<>();



        enemyList = new LinkedList<>();
        enemyList1 = new LinkedList<>();
        midBoss = new LinkedList<>();
        finalBoss = new LinkedList<>();


        batch = new SpriteBatch();

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
        renderBackground(deltaTime);

        //player
        playerCharacter.draw(batch);

        // Update playerCharacter position based on user input.
        playerCharacter.moveActor(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,.0f);


        //enemy1
//        enemyType1.draw(batch);
        spawnEnemyShips(deltaTime);

        ListIterator<Enemy> enemyListIterator = enemyList.listIterator();
        while(enemyListIterator.hasNext()){
            Enemy enemy = enemyListIterator.next();

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
            Enemy enemy1 = enemyListIterator.next();
            if(enemy1.moveActor(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,enemy2LifeSpan)){
               enemyListIterator.remove();
               System.out.println("Enemy 2 left");
            }
            else{
                enemy1.update(deltaTime);
                enemy1.draw(batch);
            }
        }
        midBossStart(deltaTime);
        ListIterator<Bosses> midBossIterator = midBoss.listIterator();
        while(midBossIterator.hasNext()){
            Bosses midBoss= midBossIterator.next();
            midBoss.moveActor(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,midBossLifeSpan);
//            moveMidBoss(midBoss,deltaTime);
            midBoss.update(deltaTime);
            midBoss.draw(batch);
        }
        finalBossStart(deltaTime);
        ListIterator<Bosses> finalBossIterator = finalBoss.listIterator();
        while(finalBossIterator.hasNext()){
            Bosses finalBoss= finalBossIterator.next();
            finalBoss.moveActor(deltaTime,WORLD_WIDTH,WORLD_HEIGHT,bossLifeSpan);
//            moveFinalBoss(finalBoss,deltaTime);
            finalBoss.update(deltaTime);
            finalBoss.draw(batch);
        }

        // Projectile.
        renderProjectile(deltaTime);

        //hud rendering
        updateAndRenderHUD(deltaTime);

        batch.end();

    }

    private void updateAndRenderHUD(float deltaTime){
        playTime += deltaTime;
        //render top row label
        //font.draw(batch,"Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch,"Time", hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        //font.draw(batch,"Lives", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //render second row values
        //font.draw(batch, String.format(Locale.getDefault(), "%.0f", stateTime), hudLeftX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%.0f", playTime), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        //font.draw(batch, String.format(Locale.getDefault(), "%02d", 3), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
    }

    private void renderProjectile(float deltaTime)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // Create projectile for playerCharacter.
            if (playerCharacter.canFireProjectile()) {
                Projectile[] projectiles = playerCharacter.fire();
                for (Projectile proj : projectiles) {
                    playerProjectileList.add(proj);
                }
            }
        }


        ListIterator<Enemy> enemyListIterator = enemyList.listIterator();
        while(enemyListIterator.hasNext()){
            Enemy enemy = enemyListIterator.next();
            if(enemy.canFireProjectile()){
                Projectile[] projectiles = enemy.fire();
                enemyProjectileList.addAll(Arrays.asList(projectiles));
            }
        }
        enemyListIterator = enemyList1.listIterator();
        while(enemyListIterator.hasNext()){
            Enemy enemy1 = enemyListIterator.next();
            if(enemy1.canFireProjectile()){
                Projectile[] projectiles = enemy1.fire();
                enemyProjectileList1.addAll(Arrays.asList(projectiles));
            }
        }
        ListIterator<Bosses> midBossIterator = midBoss.listIterator();
        while(midBossIterator.hasNext()){
            Bosses midBoss = midBossIterator.next();
            if(midBoss.canFireProjectile()){
                Projectile[] projectiles = midBoss.fire();
                midBossProjectileList.addAll(Arrays.asList(projectiles));
            }
        }
        ListIterator<Bosses> finalBossListIterator = finalBoss.listIterator();
        while(finalBossListIterator.hasNext()){
            Bosses finalBoss = finalBossListIterator.next();
            if(finalBoss.canFireProjectile()){
                Projectile[] projectiles = finalBoss.fire();
                finalBossProjectileList.addAll(Arrays.asList(projectiles));
            }
        }



        // Draw Projectiles.
        ListIterator<Projectile> iterator = playerProjectileList.listIterator();

        while (iterator.hasNext())
        {
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            projectile.getBoundingBox().y += projectile.getMovementSpeed() * deltaTime;
            if (projectile.getBoundingBox().y > WORLD_HEIGHT)
                iterator.remove();
        }


        iterator = enemyProjectileList.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            if(projectile.move(deltaTime,WORLD_WIDTH,WORLD_HEIGHT) + projectile.getBoundingBox().height < 0){
                iterator.remove();
            }
        }
        iterator = enemyProjectileList1.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            if(projectile.move(deltaTime,WORLD_WIDTH,WORLD_HEIGHT)+ projectile.getBoundingBox().height < 0){
                iterator.remove();
            }
        }
        iterator = midBossProjectileList.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            if(projectile.move(deltaTime,WORLD_WIDTH,WORLD_HEIGHT) + projectile.getBoundingBox().height < 0){
                iterator.remove();
            }
        }
        iterator = finalBossProjectileList.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            if(projectile.move(deltaTime,WORLD_WIDTH,WORLD_HEIGHT)+ projectile.getBoundingBox().height < 0){
                iterator.remove();
            }
        }

    }

    private void midBossStart(float deltaTime){
        stateTime += deltaTime;
        if(stateTime > timetoStartMidBoss && midBoss.size() < 1){
            midBoss.add( new Bosses(60,5,15,15,SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 15) + 7.5f, WORLD_HEIGHT - 7.5f,0.5f,
                    1f, 7, 50,midBossTexture,midBossProjectileTexture,0.125f,0.819f,0.05f ));

            stateTime -= timetoStartMidBoss;

        }
    }

    private void finalBossStart(float deltaTime){
        stateTime1 += deltaTime;
        if(stateTime1 > timetoStartFinalBoss  && finalBoss.size() < 1 ){
            finalBoss.add( new Bosses(40,5,20,20,SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 20) + 10, WORLD_HEIGHT - 10,0.3f,
                    2f, 10, 50,finalBossTexture,finalBossProjectileTexture,0.125f,0.819f,0.05f ));

            stateTime1 -= timetoStartFinalBoss;

        }
    }



    private void spawnEnemyShips(float deltaTime){
        enemySpawnTimer += deltaTime;
        if(enemySpawnTimer > timeBetweenEnemySpawns) {
            if(enemyList.size() < number_enemy_1)
                enemyList.add(new Enemy(48,1,10,10,Math.min(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_WIDTH -1), WORLD_HEIGHT - 5,0.8f,
                    0.3f, 5, 50,enemyType1Texture,enemyProjectileTexture,0.125f,0.819f,0.05f ));
            if(enemyList1.size() < number_enemy_2)
                enemyList1.add(new Enemy(48,1,10,10,Math.min(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 10,WORLD_WIDTH -1), WORLD_HEIGHT - 10,0.8f,
                    0.3f, 5, 50,enemyType2Texture,enemyProjectileTexture,0.138f,0.847f,0.037f ));
            enemySpawnTimer -= timeBetweenEnemySpawns;
        }
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
