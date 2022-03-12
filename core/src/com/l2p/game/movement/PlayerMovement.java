package com.l2p.game.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerMovement extends Movement {

    float x_coord;
    float y_coord;
    float playerCharWidth;
    float playerCharHeight;


    public PlayerMovement(float x_coord, float y_coord, float playerCharWidth, float playerCharHeight){
        this.x_coord = x_coord;
        this.y_coord =  y_coord;
        this.playerCharHeight = playerCharHeight;
        this.playerCharWidth = playerCharWidth;
    }

    @Override
    public float[] setPlayerMovement(Boolean ToggleSpeed, float WORLD_WIDTH, float WORLD_HEIGHT){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (x_coord - 1) >= 0)
            x_coord = (ToggleSpeed)?x_coord-1: x_coord-2;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (x_coord + playerCharWidth + 1) <= WORLD_WIDTH)
            x_coord = (ToggleSpeed)?x_coord+1:x_coord+2;

        if (Gdx.input.isKeyPressed(Input.Keys.UP) && (y_coord + playerCharHeight + 1) <= WORLD_HEIGHT)
            y_coord = (ToggleSpeed)?y_coord+1:y_coord+2;

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && (y_coord - 1) >= 0)
            y_coord = (ToggleSpeed)?y_coord-1:y_coord-2;

        // Diagonal movement.
        // Top-Left
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)
                && (x_coord - 1) >= 0 && (y_coord + playerCharHeight + 1) <= WORLD_HEIGHT)
        {
            x_coord = (ToggleSpeed)?x_coord-1:x_coord-2;
            y_coord = (ToggleSpeed)?y_coord+1:y_coord+2;
        }

        // Top-Right.
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)
                && (x_coord + playerCharWidth + 1) <= WORLD_WIDTH && (y_coord + playerCharHeight + 1) <= WORLD_HEIGHT)
        {
            x_coord = (ToggleSpeed)?x_coord+1:x_coord+2;
            y_coord = (ToggleSpeed)?y_coord+1:y_coord+2;
        }

        // Down-Left.
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)
                && (x_coord - 1) >= 0 && (y_coord - 1) >= 0)
        {
            x_coord = (ToggleSpeed)?x_coord-1:x_coord-2;
            y_coord = (ToggleSpeed)?y_coord-1:y_coord-2;
        }

        // Down-Right.
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)
                && (x_coord + playerCharWidth + 1) <= WORLD_WIDTH && (y_coord - 1) >= 0)
        {
            x_coord = (ToggleSpeed)?x_coord+1:x_coord+2;
            y_coord = (ToggleSpeed)?y_coord-1:y_coord-2;
        }

        float[] coord = {x_coord,y_coord};
        return coord;

    };

}
