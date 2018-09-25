/**
 * Copyright (c) 2010 Martin Geisse
 * <p>
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.labyrinth.editor;

import name.martingeisse.labyrinth.system.FrameTimer;
import name.martingeisse.labyrinth.system.InputStrategy;
import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.lwjgl.Launcher;
import name.martingeisse.labyrinth.system.lwjgl.LgjglResourceInitializer;
import name.martingeisse.labyrinth.system.lwjgl.LwjglKeyboardInputStrategy;
import name.martingeisse.labyrinth.system.lwjgl.LwjglRenderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * The main class.
 */
public class EditorMain {

	/**
	 * The main method.
	 *
	 * @param args command-line arguments
	 * @throws Exception on errors
	 */
	public static void main(String[] args) throws Exception {

		// initialize system
		Launcher launcher = new Launcher(640, 480, false);
		launcher.launch();
		Renderer.Holder.INSTANCE = new LwjglRenderer();
		InputStrategy.Holder.INSTANCE = new LwjglKeyboardInputStrategy();

		// initialize resources
		LgjglResourceInitializer.initializeResources();

		// initialize editor
		EditorBackbone backbone = new EditorBackbone();

		// main loop
		FrameTimer frameTimer = new FrameTimer(30);
		while (!Keyboard.isKeyDown(Keyboard.KEY_F12)) {

			// synchronize with OS
			Display.processMessages();
			Mouse.poll();
			Keyboard.poll();

			// handle game logic
			backbone.frame();

			// wait for frame timer
			while (!frameTimer.test()) {
				synchronized (frameTimer) {
					frameTimer.wait();
				}
			}

			// update display
			Display.update();

		}

		// shut down
		launcher.shutdown();
		System.exit(0);

	}

}
