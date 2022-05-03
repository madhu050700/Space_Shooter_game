package com.l2p.game.engine;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;



public class JSONEngine {


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
            enemy1Data.put("type", (String) enemy1.get("type"));
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
            enemy2Data.put("type", (String) enemy2.get("type"));
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
