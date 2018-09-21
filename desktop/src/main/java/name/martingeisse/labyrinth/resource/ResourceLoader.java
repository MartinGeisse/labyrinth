/**
 * Copyright (c) 2013 Martin Geisse
 * <p>
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.labyrinth.resource;

import name.martingeisse.labyrinth.system.lwjgl.LwjglTexture;
import org.newdawn.slick.openal.Audio;

import java.io.IOException;

/**
 * Loads resource files from disk.
 */
public interface ResourceLoader {

	/**
	 * Loads a texture.
	 *
	 * @param filename the filename of the texture image, relative to the texture folder
	 * @return the texture
	 * @throws IOException on I/O errors
	 */
	LwjglTexture loadTexture(final String filename) throws IOException;

	/**
	 * Loads a sound.
	 *
	 * @param filename the filename of the sound file, relative to the sound folder
	 * @return the sound
	 * @throws IOException on I/O errors
	 */
	Audio loadSound(final String filename) throws IOException;

}
