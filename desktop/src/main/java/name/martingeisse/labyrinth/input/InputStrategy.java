package name.martingeisse.labyrinth.input;

import name.martingeisse.labyrinth.game.Direction;

/**
 *
 */
public interface InputStrategy {

	// the direction currently input, or null if none
	Direction getInputDirection();

	// whether the user wants to leave the game
	boolean isQuitPressed();

}
