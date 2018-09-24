/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 */
public abstract class Trigger implements Serializable {

    private int x;
    private int y;

    public Trigger(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void check(Room room) {
        PlayerSprite playerSprite = room.getPlayerSprite();
        if (playerSprite.getPlayerX() == x && playerSprite.getPlayerY() == y) {
            onTouch(room);
        }
    }

    protected abstract void onTouch(Room room);

}
