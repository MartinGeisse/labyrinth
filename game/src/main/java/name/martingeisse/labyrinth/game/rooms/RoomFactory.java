/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game.rooms;

import name.martingeisse.labyrinth.game.*;

/**
 *
 */
public interface RoomFactory {

	Room buildRoom(int enteringDoor);

}
