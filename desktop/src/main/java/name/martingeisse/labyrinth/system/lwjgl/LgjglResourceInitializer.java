/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.system.lwjgl;

import name.martingeisse.labyrinth.game.BackgroundSoundSelector;
import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.game.Direction;
import name.martingeisse.labyrinth.game.PlayerSprite;
import name.martingeisse.labyrinth.system.SoundEffects;
import name.martingeisse.labyrinth.system.Texture;
import name.martingeisse.labyrinth.system.lwjgl.resource.DefaultResouceLoader;
import name.martingeisse.labyrinth.system.lwjgl.resource.DefaultResourceManager;
import name.martingeisse.labyrinth.system.lwjgl.resource.Resources;

/**
 *
 */
public class LgjglResourceInitializer {

	public static void initializeResources() {

		// initialize texture resources
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

		// initialize sound resources
		SoundEffects.door = new LwjglSoundEffect(Resources.getSound("door.ogg"));
		BackgroundSoundSelector.BACKGROUND1.setSound(new LwjglBackgroundSound(Resources.getSound("background1.ogg")));
		BackgroundSoundSelector.BACKGROUND2.setSound(new LwjglBackgroundSound(Resources.getSound("background2.ogg")));
		BackgroundSoundSelector.BACKGROUND3.setSound(new LwjglBackgroundSound(Resources.getSound("background3.ogg")));

	}

}
