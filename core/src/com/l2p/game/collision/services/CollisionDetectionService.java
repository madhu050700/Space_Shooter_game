package com.l2p.game.collision.services;

import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.collision.EnemyCollisionDetector;
import com.l2p.game.collision.PlayerCollisionDetector;
import com.l2p.game.projectile.abstractProducts.Projectile;

import java.util.ArrayList;
import java.util.LinkedList;

public class CollisionDetectionService {


    public int run(int score, Actor playerCharacter, LinkedList<Projectile>playerProjectileList, LinkedList<Actor> enemyList1,
                   LinkedList<Projectile>enemyProjectileList1,
                   LinkedList<Actor> enemyList2,LinkedList<Projectile>enemyProjectileList2,
                   LinkedList<Actor> midBoss, LinkedList<Projectile>midBossProjectileList,
                   LinkedList<Actor> finalBoss,LinkedList<Projectile> finalBossProjectileList ){

        score = EnemyCollisionDetector.detectCollision(playerProjectileList,enemyList1,score);
        score = EnemyCollisionDetector.detectCollision(playerProjectileList,enemyList2,score);
        score = EnemyCollisionDetector.detectCollision(playerProjectileList,midBoss,score);
        score = EnemyCollisionDetector.detectCollision(playerProjectileList, finalBoss,score);



        ArrayList<LinkedList<Projectile>> projectileListArray =  new ArrayList<LinkedList<Projectile>>();
        projectileListArray.add(enemyProjectileList1);
        projectileListArray.add(enemyProjectileList2);
        projectileListArray.add(midBossProjectileList);
        projectileListArray.add(finalBossProjectileList);

        PlayerCollisionDetector.detectCollision(projectileListArray,playerCharacter);
        return score;
    }

}
