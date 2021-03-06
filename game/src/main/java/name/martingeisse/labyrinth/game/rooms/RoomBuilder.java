/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game.rooms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import name.martingeisse.labyrinth.game.BackgroundSoundSelector;
import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.game.Direction;
import name.martingeisse.labyrinth.game.Room;

/**
 *
 */
public class RoomBuilder {

    private final Room room;
    private final List<Integer> doorLocations = new ArrayList<>();
    private final List<Direction> doorDirections = new ArrayList<>();

    public RoomBuilder(int width, int height) {
        this.room = new Room(width, height);
    }

    public Room getRoom() {
        return room;
    }

    //
    // mirrors for block modification methods in class Room
    //

    public int getBlockIndex(int x, int y) {
        return room.getBlockIndex(x, y);
    }

    public byte convertBlockNumberToBlockByte(int blockNumber) {
        return Room.convertBlockNumberToBlockByte(blockNumber);
    }

    public int convertBlockByteToBlockNumber(byte blockByte) {
        return Room.convertBlockByteToBlockNumber(blockByte);
    }

    public void setBlockNumber(int x, int y, int blockNumber) {
        room.setBlockNumber(x, y, blockNumber);
    }

    public int getBlockNumber(int x, int y) {
        return room.getBlockNumber(x, y);
    }

    public void setBlock(int x, int y, Block block) {
        room.setBlock(x, y, block);
    }

    public Block getBlock(int x, int y) {
        return room.getBlock(x, y);
    }

    //
    // drawing operations
    //

    public void fill(Block block) {
        Arrays.fill(room.getBlockMatrix(), (byte) block.ordinal());
    }

    public void hline(int x, int y, int length, Block block) {
        for (int i = 0; i < length; i++) {
            room.setBlock(x + i, y, block);
        }
    }

    public void vline(int x, int y, int length, Block block) {
        for (int i = 0; i < length; i++) {
            room.setBlock(x, y + i, block);
        }
    }

    public void roomOutline(Block block) {
        rectangleOutline(0, 0, room.getWidth(), room.getHeight(), block);
    }

    public void rectangleOutline(int x, int y, int width, int height, Block block) {
        for (int i = 0; i < width; i++) {
            room.setBlock(x + i, y, block);
            room.setBlock(x + i, y + height - 1, block);
        }
        for (int i = 0; i < height; i++) {
            room.setBlock(x, y + i, block);
            room.setBlock(x + width - 1, y + i, block);
        }
    }

    public void rectangleFilled(int x, int y, int width, int height, Block block) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                room.setBlock(x + i, y + j, block);
            }
        }
    }

    public void copyFromArray(byte[] matrix, Block[] lookupTable) {
        for (int x = 0; x < room.getWidth(); x++) {
            for (int y = 0; y < room.getHeight(); y++) {
                room.setBlock(x, y, lookupTable[matrix[y * room.getWidth() + x] & 0xff]);
            }
        }
    }

    //
    // quick helpers
    //

    /**
     * like setBlock, but counts the block as a door (the door order is defined by the order of the
     * calls to this method) to quickly define the player's starting position via selectDoor()
     * and optionally adds a DoorTrigger for it.
     * <p>
     * The startDirection is the initial direction when entering the room through this door -- the
     * opposite of the direction used when going through the door from within this room.
     * <p>
     * If a room factory is specified, a door trigger will be placed at the door block that leads
     * to the room produced by the factory.
     */
    public void setDoor(int x, int y, Block block, Direction startDirection, RoomFactory roomFactory, int enteringDoor) {
        setBlock(x, y, block);
        doorLocations.add(x);
        doorLocations.add(y);
        doorDirections.add(startDirection);
        if (roomFactory != null) {
            room.addTrigger(new DoorTrigger(x, y, roomFactory, enteringDoor));
        }
    }

    // see setDoor()
    public void selectDoor(int doorIndex) {
        if (doorIndex < 0 || doorIndex >= doorDirections.size()) {
            throw new IllegalArgumentException("invalid door index " + doorIndex + " for " + doorDirections.size() + " doors");
        }
        room.getPlayerSprite().setPlayerX(doorLocations.get(doorIndex << 1));
        room.getPlayerSprite().setPlayerY(doorLocations.get((doorIndex << 1) + 1));
        room.getPlayerSprite().setDirection(doorDirections.get(doorIndex));
    }

    public void setBackgroundSound(BackgroundSoundSelector selector) {
        room.setBackgroundSoundSelector(selector);
    }

}
