/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.system;

/**
 *
 */
public interface TouchInputSource {

	boolean isTouching();
	int getX();
	int getY();

}
