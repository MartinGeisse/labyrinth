/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.input;

import org.lwjgl.input.Keyboard;

/**
 *
 */
public class InputStrategyHolder {

	public static InputStrategy INPUT_STRATEGY = new LwjglKeyboardInputStrategy();

//	public static InputStrategy INPUT_STRATEGY = new TouchInputStrategy(new LwjglMouseTouchInputSource(), 1.0f) {
//		@Override
//		public boolean isQuitPressed() {
//			return Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
//		}
//	};

}
