/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.editor;

import name.martingeisse.labyrinth.game.Game;
import name.martingeisse.labyrinth.game.rooms.RoomFactories;

/**
 *
 */
public class GamePane implements EditorPane {

	private final Game game;

	public GamePane() {
		game = new Game();
		game.setRoom(RoomFactories.startRoom.buildRoom(RoomFactories.startRoomInitialDoor));
	}

	public void frame() {
		game.step();
		game.draw();
	}

}
