/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.system.lwjgl;

import name.martingeisse.labyrinth.game.Direction;
import name.martingeisse.labyrinth.system.InputStrategy;
import org.lwjgl.input.Keyboard;

/**
 *
 */
public class LwjglKeyboardInputStrategy implements InputStrategy {

	@Override
	public Direction getInputDirection() {
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			return Direction.WEST;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			return Direction.EAST;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			return Direction.NORTH;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			return Direction.SOUTH;
		} else {
			return null;
		}
	}

}
