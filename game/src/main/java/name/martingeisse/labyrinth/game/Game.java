/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.InputStream;
import java.io.OutputStream;

import name.martingeisse.labyrinth.game.rooms.RoomFactories;
import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.SoundEffects;

/**
 *
 */
public class Game {

    private Room room;

    public Game(DataInput saveStream) {
        Block.checkTexturesLoaded();
        SoundEffects.checkSoundsLoaded();
        if (saveStream == null) {
            setRoom(RoomFactories.startRoom.buildRoom(RoomFactories.startRoomInitialDoor));
        } else {
            setRoom(new Room(saveStream));
        }
    }

    public void save(DataOutput saveStream) {
        room.save(saveStream);
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
