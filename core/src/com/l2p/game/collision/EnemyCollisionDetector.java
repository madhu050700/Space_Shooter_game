package com.l2p.game.collision;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.projectile.abstractProducts.Projectile;

import java.util.LinkedList;
import java.util.ListIterator;


public class EnemyCollisionDetector extends CollisionDetector {

    public static SpriteBatch batch;


    public static int detectCollision(LinkedList<Projectile> projectileList, LinkedList<Actor> actorList, int score,float deltaTime) {
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
                    iterator.remove();
                    if (health == 0) {
                       // LinkedList<PowerUps> powerUps = new LinkedList<>();
                        //Texture powerup = new Texture("ufoYellow.png");
                        //PowerUps p = new PowerUps(176,160,4*16,4*16,70,powerup);
                        //powerUps.add(p);
                        //ListIterator<PowerUps> iterator1 = powerUps.listIterator();
                        //while (iterator1.hasNext()) {
                          //  PowerUps powerups = iterator1.next();
                            //powerups.draw(batch);
                            //if (powerups.move(deltaTime) + powerups.getBoundingBox().height < 0) {
                              //  iterator1.remove();
                            //}
                        //}
                        enemyListIterator1.remove();
                    }
                }
            }
        }
        return score;
    }


}
