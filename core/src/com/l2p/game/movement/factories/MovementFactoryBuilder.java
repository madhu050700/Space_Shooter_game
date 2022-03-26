package com.l2p.game.movement.factories;

public class MovementFactoryBuilder {
    private static MovementFactory factory = null;

    public static MovementFactory getFactory(String type){

        if(type.equals("regular"))
            factory =  new RegularMovementFactory();
        if(type.equals("circular"))
            factory =  new CircularMovementFactory();
        if(type.equals("player"))
            factory =  new PlayerMovementFactory();

        return factory;
    }

}
