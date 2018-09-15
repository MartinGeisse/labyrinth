/**
 * Copyright (c) 2010 Martin Geisse
 * <p>
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.labyrinth;

import name.martingeisse.labyrinth.game.Game;
import name.martingeisse.labyrinth.game.Room;
import name.martingeisse.labyrinth.game.RoomBuilder;
import name.martingeisse.labyrinth.resource.DefaultResouceLoader;
import name.martingeisse.labyrinth.resource.DefaultResourceManager;
import name.martingeisse.labyrinth.resource.Resources;
import name.martingeisse.labyrinth.system.FrameTimer;
import name.martingeisse.labyrinth.system.Launcher;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * The main class.
 */
public class Main {

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
		Resources.setResourceManager(new DefaultResourceManager(new DefaultResouceLoader()));

		// initialize game
		Game game = new Game();
		RoomBuilder builder = new RoomBuilder(40, 30);
		builder.fill(0);
		builder.roomOutline(1);
		game.setRoom(builder.getRoom());

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
