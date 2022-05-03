package com.l2p.game.actor.controllers;

import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.actor.factories.ActorFactory;
import com.l2p.game.actor.factories.BossFactory;
import com.l2p.game.actor.factories.EnemyFactory;

import java.util.HashMap;
import java.util.LinkedList;

public class SpawnController {


    ActorFactory enemyFactory;
    ActorFactory bossFactory;
    int WORLD_WIDTH;
    int WORLD_HEIGHT;
    Boolean spawnMidBoss;
    Boolean spawnBoss;
    HashMap<String, Float> enemySpawnTimers;

    public SpawnController(int width, int height) {
        enemyFactory = new EnemyFactory();
        bossFactory = new BossFactory();
        WORLD_WIDTH = width;
        WORLD_HEIGHT = height;

        enemySpawnTimers = new HashMap();

        spawnMidBoss = true;
        spawnBoss = true;
    }

    public SpawnState spawnBoss(String agent, float deltaTime, float stateTime, float timetoStartBoss, LinkedList<Actor> bossList,
                                String type, int movementSpeed, int health, int width, int height, float center_x, float center_y, float timeBetweenShots, float projectileWidth, float projectileHeight,
                                float projectileSpeed, String actorTexture, String projectileTexture, float projectile_x1, float projectile_x2, float projectile_y, String movementType) {


        float spawnTimer;
        if (enemySpawnTimers.containsKey(agent))
            spawnTimer = enemySpawnTimers.get(agent);
        else {
            enemySpawnTimers.put(agent, stateTime);
            spawnTimer = stateTime;
        }

        spawnTimer += deltaTime;


        if ((spawnTimer > timetoStartBoss && bossList.size() < 1 && agent.equals("midBoss") && spawnMidBoss) ||
                (spawnTimer > timetoStartBoss && bossList.size() < 1 && agent.equals("finalBoss") && spawnBoss)

        ) {
            bossList.add(bossFactory.createActor(type, movementSpeed, health, width, height, center_x, center_y,
                    timeBetweenShots, projectileWidth, projectileHeight, projectileSpeed, actorTexture, projectileTexture, projectile_x1, projectile_x2, projectile_y, movementType));
            spawnTimer -= timetoStartBoss;

            if (agent.equals("midBoss"))
                spawnMidBoss = false;
            if (agent.equals("finalBoss"))
                spawnBoss = false;

        }
        enemySpawnTimers.put(agent, spawnTimer);


        return new SpawnState(stateTime, bossList);
    }


    public LinkedList<Actor> spawnEnemyShips(String agent, float deltaTime, float enemySpawnTimer, float timeBetweenEnemySpawns, LinkedList<Actor> enemyList, int number_enemy_1,
                                             String type, int movementSpeed, int health, int width, int height, float center_x, float center_y, float timeBetweenShots, float projectileWidth, float projectileHeight,
                                             float projectileSpeed, String actorTexture, String projectileTexture, float projectile_x1, float projectile_x2, float projectile_y, String movementType) {

        float spawnTimer;
        if (enemySpawnTimers.containsKey(agent))
            spawnTimer = enemySpawnTimers.get(agent);
        else {
            enemySpawnTimers.put(agent, enemySpawnTimer);
            spawnTimer = enemySpawnTimer;
        }

        spawnTimer += deltaTime;
        if (spawnTimer > timeBetweenEnemySpawns) {
            if (enemyList.size() < number_enemy_1)
                enemyList.add(enemyFactory.createActor(type, movementSpeed, health, width, height, center_x, center_y,
                        timeBetweenShots, projectileWidth, projectileHeight, projectileSpeed, actorTexture, projectileTexture, projectile_x1, projectile_x2, projectile_y, movementType));
            spawnTimer -= timeBetweenEnemySpawns;

        }
        enemySpawnTimers.put(agent, spawnTimer);
        return enemyList;
    }

}
