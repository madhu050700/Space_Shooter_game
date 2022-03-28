package com.l2p.game.actor.controllers;

import com.l2p.game.actor.abstractProducts.Actor;

import java.util.LinkedList;

public class SpawnState {

    float stateTime;
    LinkedList<Actor> actorList;

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public LinkedList<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(LinkedList<Actor> actorList) {
        this.actorList = actorList;
    }

    public SpawnState(float time, LinkedList<Actor> list) {
        stateTime = time;
        actorList = list;
    }


}
