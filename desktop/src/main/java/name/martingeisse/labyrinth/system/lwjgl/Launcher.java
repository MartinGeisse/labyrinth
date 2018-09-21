/**
 * Copyright (c) 2013 Martin Geisse
 * <p>
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.labyrinth.system.lwjgl;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

/**
 * Launches the game system.
 */
public class Launcher {

	private final int screenWidth;
	private final int screenHeight;
	private final boolean fullscreen;

	public Launcher(int screenWidth, int screenHeight, boolean fullscreen) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.fullscreen = fullscreen;
	}

	public void launch() throws Exception {

		// include LWJGL native libraries
		LwjglNativeLibraryHelper.prepareNativeLibraries();

		// configure the display
		DisplayMode bestMode = null;
		int bestModeFrequency = -1;
		for (DisplayMode mode : Display.getAvailableDisplayModes()) {
			if (mode.getWidth() == screenWidth && mode.getHeight() == screenHeight && (mode.isFullscreenCapable() || !fullscreen)) {
				if (mode.getFrequency() > bestModeFrequency) {
					bestMode = mode;
					bestModeFrequency = mode.getFrequency();
				}
			}
		}
		if (bestMode == null) {
			bestMode = new DisplayMode(screenWidth, screenHeight);
		}
		Display.setDisplayMode(bestMode);
		if (fullscreen) {
			Display.setFullscreen(true);
		}
		Display.create(new PixelFormat(0, 24, 0));

		// initialize the keyboard
		Keyboard.create();
		Keyboard.poll();

		// initialize the mouse
		Mouse.create();
		Mouse.poll();

	}

	/**
	 * Shuts down the game system.
	 */
	public void shutdown() {
		Mouse.destroy();
		Keyboard.destroy();
		Display.destroy();
	}

}
