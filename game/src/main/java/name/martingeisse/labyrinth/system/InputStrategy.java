package name.martingeisse.labyrinth.system;

import name.martingeisse.labyrinth.game.Direction;

/**
 *
 */
public interface InputStrategy {

	// the direction currently input, or null if none
	Direction getInputDirection();

	class Holder {
		public static InputStrategy INSTANCE;
	}
}
