package com.l2p.game.collision.services;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.l2p.game.PowerUp.PowerUpController;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.collision.EnemyCollisionDetector;
import com.l2p.game.collision.ExplosionController;
import com.l2p.game.collision.PlayerCollisionDetector;
import com.l2p.game.collision.State.CollisionDetectionState;
import com.l2p.game.projectile.abstractProducts.Projectile;

import java.util.ArrayList;
import java.util.LinkedList;

public class CollisionDetectionService {
private CollisionDetectionState currentState;
private PowerUpController currentPowerUpController;

    public CollisionDetectionState run(int score, float deltatime, SpriteBatch batch, Actor playerCharacter, LinkedList<Projectile> playerProjectileList, LinkedList<Actor> enemyList1,
                                       LinkedList<Projectile> enemyProjectileList1,
                                       LinkedList<Actor> enemyList2, LinkedList<Projectile> enemyProjectileList2,
                                       LinkedList<Actor> midBoss, LinkedList<Projectile> midBossProjectileList,
                                       LinkedList<Actor> finalBoss, LinkedList<Projectile> finalBossProjectileList, PowerUpController powerUpController, CollisionDetectionState collisionDetectionState,
                                       boolean cheating, ExplosionController explosionController) {

        currentState= EnemyCollisionDetector.detectCollision(playerProjectileList, enemyList1, score,deltatime,batch,powerUpController,collisionDetectionState, explosionController);
        currentState= EnemyCollisionDetector.detectCollision(playerProjectileList, enemyList2, score,deltatime,batch,powerUpController,collisionDetectionState,explosionController);
        currentState = EnemyCollisionDetector.detectCollision(playerProjectileList, midBoss, score,deltatime,batch,powerUpController,collisionDetectionState, explosionController);
        currentState = EnemyCollisionDetector.detectCollision(playerProjectileList, finalBoss, score,deltatime,batch,powerUpController,collisionDetectionState, explosionController);


        ArrayList<LinkedList<Projectile>> projectileListArray = new ArrayList<LinkedList<Projectile>>();
        projectileListArray.add(enemyProjectileList1);
        projectileListArray.add(enemyProjectileList2);
        projectileListArray.add(midBossProjectileList);
        projectileListArray.add(finalBossProjectileList);

       currentPowerUpController= PlayerCollisionDetector.detectCollision(projectileListArray, playerCharacter,powerUpController, cheating);
       currentState.setPowerUpController(currentPowerUpController);
        return currentState;
    }

}
