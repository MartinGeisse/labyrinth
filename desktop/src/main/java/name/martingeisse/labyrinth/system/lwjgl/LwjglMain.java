/**
 * Copyright (c) 2010 Martin Geisse
 * <p>
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.labyrinth.system.lwjgl;

import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.game.Direction;
import name.martingeisse.labyrinth.game.Game;
import name.martingeisse.labyrinth.game.PlayerSprite;
import name.martingeisse.labyrinth.game.rooms.RoomFactories;
import name.martingeisse.labyrinth.system.FrameTimer;
import name.martingeisse.labyrinth.system.InputStrategy;
import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.Texture;
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
		Resources.setResourceManager(new DefaultResourceManager(new DefaultResouceLoader()));
		for (Block block : Block.values()) {
			block.setTexture(Resources.getTexture(block.name().toLowerCase() + ".png"));
		}
		{
			Texture[][] playerTextures = new Texture[4][3];
			for (Direction direction : Direction.values()) {
				for (int i = 0; i < 3; i++) {
					String path = "player_" + direction.name().toLowerCase() + '_' + i + ".png";
					Texture texture = Resources.getTexture(path);
					playerTextures[direction.ordinal()][i] = texture;
				}
			}
			PlayerSprite.setTextures(playerTextures);
		}

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
