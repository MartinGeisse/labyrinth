/**
 * Copyright (c) 2013 Martin Geisse
 * <p>
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.labyrinth.resource;

import name.martingeisse.labyrinth.system.lwjgl.LwjglTexture;
import org.newdawn.slick.openal.Audio;

/**
 * Keeps track of the game resources (graphics files, sounds, ...)
 */
public interface ResourceManager {

	/**
	 * Returns a texture by name.
	 *
	 * @param name the name of the texture
	 * @return the texture
	 */
	LwjglTexture getTexture(final String name);

	/**
	 * Returns a sound by name.
	 *
	 * @param name the name of the sound
	 * @return the sound
	 */
	Audio getSound(final String name);

}
