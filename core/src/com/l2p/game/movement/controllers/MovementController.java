package com.l2p.game.movement.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.l2p.game.actor.abstractProducts.Actor;

import java.util.LinkedList;
import java.util.ListIterator;

public class MovementController {


    public LinkedList<Actor> moveAI(SpriteBatch batch, float deltaTime, int WORLD_WIDTH, int WORLD_HEIGHT,
                                    LinkedList<Actor> enemyList, float lifeSpan) {

        ListIterator<Actor> enemyListIterator = enemyList.listIterator();
        while (enemyListIterator.hasNext()) {
            Actor enemy = enemyListIterator.next();

            if (enemy.moveActor(deltaTime, WORLD_WIDTH, WORLD_HEIGHT, lifeSpan)) {
                enemyListIterator.remove();
//                System.out.println("Enemy left");
            } else {
                enemy.update(deltaTime);
                enemy.draw(batch);
            }
        }
        return enemyList;
    }
}
