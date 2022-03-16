package com.l2p.game.collision;

import com.l2p.game.actor.abstractProducts.Actor;
import com.l2p.game.actor.concreteProducts.PlayerCharacter;
import com.l2p.game.projectile.Projectile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class PlayerCollisionDetector extends CollisionDetector{


    public static void detectCollision(ArrayList<LinkedList<Projectile>> projectileListArray, Actor playerCharacter) {
        for(LinkedList<Projectile> projectileList : projectileListArray){
            ListIterator<Projectile> iterator = projectileList.listIterator();
            while (iterator.hasNext()) {
                Projectile projectile = iterator.next();
                if (playerCharacter.intersects(projectile.getBoundingBox())){
                    int health = playerCharacter.hit(projectile);
                    System.out.println("Player got hit");
                    iterator.remove();
                    if(health == 0){
                        System.out.println("You dead mate");
                    }
                }
            }
        }
    }


}
