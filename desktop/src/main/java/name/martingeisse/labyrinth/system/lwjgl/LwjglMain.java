/**
 * Copyright (c) 2010 Martin Geisse
 * <p>
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.labyrinth.system.lwjgl;

import name.martingeisse.labyrinth.game.*;
import name.martingeisse.labyrinth.game.rooms.RoomFactories;
import name.martingeisse.labyrinth.system.*;
import name.martingeisse.labyrinth.system.lwjgl.resource.DefaultResouceLoader;
import name.martingeisse.labyrinth.system.lwjgl.resource.DefaultResourceManager;
import name.martingeisse.labyrinth.system.lwjgl.resource.Resources;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * The main class.
 */
public class LwjglMain {

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

		// initialize game
		Game game = new Game();
		game.setRoom(RoomFactories.startRoom.buildRoom(RoomFactories.startRoomInitialDoor));

		// main loop
		FrameTimer frameTimer = new FrameTimer(30);
		while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {

			// synchronize with OS
			Display.processMessages();
			Mouse.poll();
			Keyboard.poll();

			// handle game logic
			game.step();

			// draw next frame
			game.draw();

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
