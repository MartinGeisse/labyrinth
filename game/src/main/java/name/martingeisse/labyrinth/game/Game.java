/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import java.io.Serializable;

import name.martingeisse.labyrinth.game.rooms.RoomFactories;
import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.SoundEffects;

/**
 *
 */
public class Game implements Serializable {

    private Room room;

    public Game() {
        Block.checkTexturesLoaded();
        SoundEffects.checkSoundsLoaded();
        BackgroundSoundSelector.checkSoundsLoaded();
        setRoom(RoomFactories.startRoom.buildRoom(RoomFactories.startRoomInitialDoor));
    }

    public void step() {
        if (room != null) {
            room.step();
        }
    }

    public void draw() {
        Renderer.Holder.INSTANCE.beginFrame();
        if (room != null) {
            room.draw();
        }
        Renderer.Holder.INSTANCE.endFrame();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
        room.bindToGame(this);
    }

}
