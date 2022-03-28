package com.l2p.game.composite;

import com.l2p.game.projectile.abstractProducts.Projectile;

import java.util.LinkedList;

public abstract class ActorComponent {

    protected String type;

    public abstract LinkedList<Projectile> fire();

}
