/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.editor;

import org.lwjgl.input.Keyboard;

/**
 *
 */
public class EditorInputUtil {

	public static int getBlockPaletteIndexInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
			return 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			return 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
			return 2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
			return 3;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
			return 4;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
			return 5;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
			return 6;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_7)) {
			return 7;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_8)) {
			return 8;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_9)) {
			return 9;
		}
		return -1;
	}

}
