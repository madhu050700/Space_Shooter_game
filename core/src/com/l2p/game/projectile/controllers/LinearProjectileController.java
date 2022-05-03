package com.l2p.game.projectile.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.projectile.abstractProducts.Projectile;

import java.util.LinkedList;
import java.util.ListIterator;

public class LinearProjectileController {


    public LinkedList<Projectile> renderPlayerProjectiles(SpriteBatch batch, int WORLD_WIDTH, int WORLD_HEIGHT,
                                                          float deltaTime, Actor playerCharacter,
                                                          LinkedList<Projectile> projectileList) {

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // Create projectile for playerCharacter.
            if (playerCharacter.canFireProjectile()) {
                LinkedList<Projectile> projectiles = playerCharacter.fire();
                for (Projectile proj : projectiles) {
                    projectileList.add(proj);
                }
            }
        }

        ListIterator<Projectile> iterator = projectileList.listIterator();

        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            if (projectile.move(deltaTime, WORLD_WIDTH, WORLD_HEIGHT, "up") + projectile.getBoundingBox().height > WORLD_HEIGHT)
                iterator.remove();
        }

        return projectileList;

    }

    public LinkedList<Projectile> renderAIProjectiles(SpriteBatch batch, int WORLD_WIDTH, int WORLD_HEIGHT,
                                                      float deltaTime, LinkedList<Actor> enemyList,
                                                      LinkedList<Projectile> projectileList) {


        ListIterator<Actor> enemyListIterator = enemyList.listIterator();
        while (enemyListIterator.hasNext()) {
            Actor enemy = enemyListIterator.next();
            if (enemy.canFireProjectile()) {
                LinkedList<Projectile> projectiles = enemy.fire();
                projectileList.addAll(projectiles);
            }
        }

        ListIterator<Projectile> iterator = projectileList.listIterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            projectile.draw(batch);
            if (projectile.move(deltaTime, WORLD_WIDTH, WORLD_HEIGHT, "down") + projectile.getBoundingBox().height < 0) {
                iterator.remove();
            }
        }
        return projectileList;
    }



}
