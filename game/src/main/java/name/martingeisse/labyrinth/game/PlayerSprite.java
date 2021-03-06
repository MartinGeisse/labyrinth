/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import java.io.Serializable;

import name.martingeisse.labyrinth.system.InputStrategy;
import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.Texture;

/**
 *
 */
public class PlayerSprite implements Serializable {

    private static Texture[][] textures;

    private final Room room;
    private int playerX = 3;
    private int playerY = 3;
    private int playerFractionX = 0;
    private int playerFractionY = 0;
    private Direction direction = Direction.EAST;
    private boolean walking = false;

    public PlayerSprite(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getPlayerFractionX() {
        return playerFractionX;
    }

    public int getPlayerFractionY() {
        return playerFractionY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isWalking() {
        return walking;
    }

    public void step() {
        if (!walking) {
            Direction inputDirection = InputStrategy.Holder.INSTANCE.getInputDirection();
            if (inputDirection != null) {
                tryStartWalking(inputDirection);
            }
        }
        if (walking) {
            playerFractionX += 4 * direction.getDeltaX();
            playerFractionY += 4 * direction.getDeltaY();
            if (playerFractionX == 0 && playerFractionY == 0) {
                walking = false;
                room.checkTriggers();
            }
        }
    }

    private void tryStartWalking(Direction direction) {
        this.direction = direction;
        int newX = playerX + direction.getDeltaX();
        int newY = playerY + direction.getDeltaY();
        if (room.isValidPosition(newX, newY) && !room.getBlock(newX, newY).isSolid()) {
            walking = true;
            playerX = newX;
            playerFractionX = -32 * direction.getDeltaX();
            playerY = newY;
            playerFractionY = -32 * direction.getDeltaY();
        }
    }

    public void draw() {
        int anchorX = (playerX << 5) + playerFractionX;
        int anchorY = (playerY << 5) + playerFractionY;
        int frame = (walking ? ((((playerFractionX + playerFractionY) >> 2) + 100) % 3) : 0);
        Texture texture = textures[direction.ordinal()][frame];
        Renderer.Holder.INSTANCE.drawPlayerSprite(anchorX, anchorY, texture);
    }

    public static Texture[][] getTextures() {
        return textures;
    }

    public static void setTextures(Texture[][] textures) {
        PlayerSprite.textures = textures;
    }

}
