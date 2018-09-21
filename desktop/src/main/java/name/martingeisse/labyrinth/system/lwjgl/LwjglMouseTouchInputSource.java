/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.system.lwjgl;

import name.martingeisse.labyrinth.system.TouchInputSource;
import org.lwjgl.input.Mouse;

/**
 *
 */
public class LwjglMouseTouchInputSource implements TouchInputSource {

	@Override
	public boolean isTouching() {
		return Mouse.isButtonDown(0);
	}

	@Override
	public int getX() {
		return Mouse.getX();
	}

	@Override
	public int getY() {
		return Mouse.getY();
	}

}
