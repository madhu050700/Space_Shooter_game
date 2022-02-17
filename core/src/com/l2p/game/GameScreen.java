
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

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
    private Texture enemyProjectileTexture;

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
    private float stateTime,stateTime1=0;
    private float timetoStartMidBoss = 24f;
    private float timetoStartFinalBoss = 30f;



    //world parameters
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT= 128;


    //game Objects
    private PlayerCharacter playerCharacter;
    private LinkedList<Projectile> playerProjectileList;
    private LinkedList<Projectile> enemyProjectileList,enemyProjectileList1;
    private LinkedList<Projectile> midBossProjectileList,finalBossProjectileList;
    private LinkedList<Enemy> enemyList,enemyList1;
    private LinkedList<Bosses> midBoss,finalBoss;





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
        playerInput();


        //enemy1
//        enemyType1.draw(batch);
        spawnEnemyShips(deltaTime);

        ListIterator<Enemy> enemyListIterator = enemyList.listIterator();
        while(enemyListIterator.hasNext()){
            Enemy enemy = enemyListIterator.next();
            moveEnemy(enemy,deltaTime);
            enemy.update(deltaTime);
            enemy.draw(batch);
        }
        enemyListIterator = enemyList1.listIterator();

        while(enemyListIterator.hasNext()){
            Enemy enemy1 = enemyListIterator.next();
            moveEnemy1(enemy1,deltaTime);
            enemy1.update(deltaTime);
            enemy1.draw(batch);
        }
        midBossStart(deltaTime);
        ListIterator<Bosses> midBossIterator = midBoss.listIterator();
        while(midBossIterator.hasNext()){
            Bosses midBoss= midBossIterator.next();
            moveMidBoss(midBoss,deltaTime);
            midBoss.update(deltaTime);
            midBoss.draw(batch);
        }
        finalBossStart(deltaTime);
        ListIterator<Bosses> finalBossIterator = finalBoss.listIterator();
        while(finalBossIterator.hasNext()){
            Bosses finalBoss= finalBossIterator.next();
            moveFinalBoss(finalBoss,deltaTime);
            finalBoss.update(deltaTime);
            finalBoss.draw(batch);
        }

        // Projectile.
        renderProjectile(deltaTime);

        batch.end();

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
            projectile.boundingBox.y += projectile.movementSpeed * deltaTime;
            if (projectile.boundingBox.y > WORLD_HEIGHT)
                iterator.remove();
        }


        iterator = enemyProjectileList.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            projectile.boundingBox.y -= projectile.movementSpeed*deltaTime;
            if(projectile.boundingBox.y + projectile.boundingBox.height < 0){
                iterator.remove();
            }
        }
        iterator = enemyProjectileList1.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            projectile.boundingBox.y -= projectile.movementSpeed*deltaTime;
            if(projectile.boundingBox.y+ projectile.boundingBox.height < 0){
                iterator.remove();
            }
        }
        iterator = midBossProjectileList.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            projectile.boundingBox.y -= projectile.movementSpeed*deltaTime;
            if(projectile.boundingBox.y+ projectile.boundingBox.height < 0){
                iterator.remove();
            }
        }
        iterator = finalBossProjectileList.listIterator();
        while(iterator.hasNext()){
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            projectile.boundingBox.y -= projectile.movementSpeed*deltaTime;
            if(projectile.boundingBox.y+ projectile.boundingBox.height < 0){
                iterator.remove();
            }
        }

    }

    private void midBossStart(float deltaTime){
        stateTime += deltaTime;
        if(stateTime > timetoStartMidBoss && midBoss.size() < 1){
            midBoss.add( new Bosses(60,5,15,15,SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 15) + 7.5f, WORLD_HEIGHT - 7.5f,0.5f,
                    0.7f, 5, 50,midBossTexture,enemyProjectileTexture,0.125f,0.819f,0.05f ));

            stateTime -= timetoStartMidBoss;

        }
    }

    private void finalBossStart(float deltaTime){
        stateTime1 += deltaTime;
        if(stateTime1 > timetoStartFinalBoss  && finalBoss.size() < 1 ){
            finalBoss.add( new Bosses(40,5,20,20,SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 20) + 10, WORLD_HEIGHT - 10,0.5f,
                    0.3f, 5, 50,finalBossTexture,enemyProjectileTexture,0.125f,0.819f,0.05f ));

            stateTime1 -= timetoStartFinalBoss;

        }
    }



    private void spawnEnemyShips(float deltaTime){
        enemySpawnTimer += deltaTime;
        if(enemySpawnTimer > timeBetweenEnemySpawns) {
            enemyList.add(new Enemy(48,1,10,10,Math.min(SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_WIDTH -1), WORLD_HEIGHT - 5,0.8f,
                    0.3f, 5, 50,enemyType1Texture,enemyProjectileTexture,0.125f,0.819f,0.05f ));
            enemyList1.add(new Enemy(48,1,10,10,SpaceShooter.random.nextFloat() * (WORLD_WIDTH - 10) + 10, WORLD_HEIGHT - 10,0.8f,
                    0.3f, 5, 50,enemyType2Texture,enemyProjectileTexture,0.138f,0.847f,0.037f ));
            enemySpawnTimer -= timeBetweenEnemySpawns;
        }
    }

    private void moveEnemy(Enemy enemy,float deltaTime){
        float downLimit;
        downLimit = (float)WORLD_HEIGHT/2 - enemy.boundingBox.y;

        float xMove = enemy.getDirectionVector().x*enemy.movementSpeed * deltaTime;
        float yMove = enemy.getDirectionVector().y*enemy.movementSpeed * deltaTime;


        if(xMove >0){
            xMove = xMove;
        }
        if(yMove > 0){
            yMove = yMove;
        }
        else{
            yMove = Math.max(yMove,downLimit);
        }
        enemy.translate(xMove,yMove);
    }
    private void moveEnemy1(Enemy enemy1,float deltaTime){
        float downLimit;
        downLimit = (float)WORLD_HEIGHT/2 - enemy1.boundingBox.y;
        float xMove1 =enemy1.getDirectionVector().x *enemy1.movementSpeed * deltaTime;
        float yMove1 = enemy1.getDirectionVector().y*enemy1.movementSpeed * deltaTime;
        if(xMove1 > 0){
            xMove1 = xMove1;
        }
        if(yMove1 > 0){
            yMove1 = yMove1;
        }
        else{
            yMove1 = Math.max(yMove1,downLimit);

        }

        enemy1.translate(xMove1,yMove1);

    }
    private void moveMidBoss(Bosses midBoss,float deltaTime){
        float leftLimit = 0;
        float rightLimit = (float)WORLD_WIDTH - midBoss.boundingBox.x;
        float downLimit;
        downLimit = (float)WORLD_HEIGHT/2 - midBoss.boundingBox.y;
        float xMove2 =midBoss.getDirectionVector1().x *midBoss.movementSpeed * deltaTime;
        float yMove2 = midBoss.getDirectionVector1().y*midBoss.movementSpeed * deltaTime;
        if(xMove2 >0){
            xMove2 = xMove2;
        }
        if(yMove2 > 0){
            yMove2 = yMove2;
        }
        else{
            yMove2 = Math.max(yMove2,downLimit);
            xMove2 = xMove2>0?xMove2:0;
            xMove2 = xMove2>WORLD_WIDTH?rightLimit:xMove2;
        }
        midBoss.translate(xMove2,yMove2);

    }
    private void moveFinalBoss(Bosses finalBoss,float deltaTime){
        float downLimit;
        downLimit = (float)WORLD_HEIGHT/2 - finalBoss.boundingBox.y;
        float xMove3 =finalBoss.getDirectionVector1().x *finalBoss.movementSpeed * deltaTime;
        float yMove3 = finalBoss.getDirectionVector1().y*finalBoss.movementSpeed * deltaTime;
        if(xMove3 >0){
            xMove3 = xMove3;
        }
        if(yMove3 > 0){
            yMove3 = yMove3;
        }
        else{
            yMove3 = Math.max(yMove3,downLimit);
        }
        finalBoss.translate(xMove3,yMove3);

    }


    private void playerInput()
    {
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
