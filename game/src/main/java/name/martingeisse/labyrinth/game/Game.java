/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.SoundEffects;

/**
 *
 */
public class Game {

	private Room room;

	public Game() {
		Block.checkTexturesLoaded();
		SoundEffects.checkSoundsLoaded();
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
