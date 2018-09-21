/**
 * Copyright (c) 2013 Martin Geisse
 * <p>
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.labyrinth.resource;

import name.martingeisse.labyrinth.system.lwjgl.LwjglTexture;
import org.newdawn.slick.openal.Audio;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link ResourceManager}.
 */
public final class DefaultResourceManager implements ResourceManager {

	/**
	 * the loader
	 */
	private final ResourceLoader loader;

	/**
	 * the textures
	 */
	private final Map<String, LwjglTexture> textures = new HashMap<>();

	/**
	 * the sounds
	 */
	private final Map<String, Audio> sounds = new HashMap<>();

	/**
	 * Constructor.
	 * @param loader the resource loader
	 */
	public DefaultResourceManager(ResourceLoader loader) {
		this.loader = loader;
	}

	/* (non-Javadoc)
	 * @see game.engine.resource.ResourceManager#getTexture(java.lang.String)
	 */
	@Override
	public LwjglTexture getTexture(String name) {
		LwjglTexture texture = textures.get(name);
		if (texture == null) {
			try {
				texture = loader.loadTexture(name);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			textures.put(name, texture);
		}
		return texture;
	}

	/* (non-Javadoc)
	 * @see game.engine.resource.ResourceManager#getSound(java.lang.String)
	 */
	@Override
	public Audio getSound(String name) {
		Audio sound = sounds.get(name);
		if (sound == null) {
			try {
				sound = loader.loadSound(name);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			sounds.put(name, sound);
		}
		return sound;
	}

}
