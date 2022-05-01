package com.l2p.game.collision;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.l2p.game.PowerUp.PowerUpController;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.collision.State.CollisionDetectionState;
import com.l2p.game.projectile.abstractProducts.Projectile;

import java.util.LinkedList;
import java.util.ListIterator;


public class EnemyCollisionDetector extends CollisionDetector {

//private static CollisionDetectionState currentState ;

    public static CollisionDetectionState detectCollision(LinkedList<Projectile> projectileList, LinkedList<Actor> actorList, int score, float deltaTime, SpriteBatch batch, PowerUpController powerUpController,CollisionDetectionState collisionDetectionState) {
        ListIterator<Projectile> iterator = projectileList.listIterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            ListIterator<Actor> enemyListIterator1 = actorList.listIterator();
            while (enemyListIterator1.hasNext()) {
                Actor enemy1 = enemyListIterator1.next();
                if (enemy1.intersects(projectile.getBoundingBox())) {
                    int health = enemy1.hit(projectile);
                    System.out.println("Enemy1 got hit");
                    score += 100;
                    collisionDetectionState.setScore(score);
                    iterator.remove();
                    if (health == 0) {
                        powerUpController.addPowerUp(enemy1);
                        collisionDetectionState.setPowerUpController(powerUpController);
                        enemyListIterator1.remove();
                    }
                }
            }
        }
        return collisionDetectionState;
    }


}
