package com.l2p.game.collision;

import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.projectile.abstractProducts.Projectile;

import java.util.LinkedList;
import java.util.ListIterator;

public class EnemyCollisionDetector extends CollisionDetector{


    public static int detectCollision(LinkedList<Projectile> projectileList, LinkedList<Actor> actorList, int score) {
        ListIterator<Projectile> iterator = projectileList.listIterator();
        while (iterator.hasNext() ) {
            Projectile projectile = iterator.next();
            ListIterator<Actor> enemyListIterator1 = actorList.listIterator();
            while(enemyListIterator1.hasNext()){
                Actor enemy1 = enemyListIterator1.next();
                if (enemy1.intersects(projectile.getBoundingBox())){
                    int health = enemy1.hit(projectile);
                    System.out.println("Enemy1 got hit");
                    score += 100;
                    iterator.remove();
                    if(health == 0){
                        enemyListIterator1.remove();
                    }
                }
            }
        }
        return score;
    }


}
