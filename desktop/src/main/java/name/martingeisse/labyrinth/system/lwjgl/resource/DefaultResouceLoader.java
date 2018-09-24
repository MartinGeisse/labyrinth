/**
 * Copyright (c) 2013 Martin Geisse
 * <p>
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.labyrinth.system.lwjgl.resource;

import name.martingeisse.labyrinth.system.lwjgl.LwjglTexture;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.opengl.InternalTextureLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Default implementation of {@link ResourceLoader}.
 */
public final class DefaultResouceLoader implements ResourceLoader {

	private final File assetsFolder = new File("../android/app/src/main/res");

	private File resolveFolder(String type) {
		return new File(assetsFolder, type);
	}

	private File resolveFile(String type, String name) {
		return new File(resolveFolder(type), name);
	}

	@Override
	public LwjglTexture loadTexture(String filename) throws IOException {
		try (InputStream inputStream = new FileInputStream(resolveFile("drawable", filename))) {
			return new LwjglTexture(InternalTextureLoader.get().getTexture(inputStream, filename, true, GL11.GL_LINEAR));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private BufferedImage loadImage(final File file) throws IOException {
		try (InputStream inputStream = new FileInputStream(file)) {
			return ImageIO.read(inputStream);
		}
	}

	/* (non-Javadoc)
	 * @see game.engine.resource.ResourceLoader#loadSound(java.lang.String)
	 */
	@Override
	public Audio loadSound(final String filename) throws IOException {
		int lastDotIndex = filename.lastIndexOf('.');
		if (lastDotIndex == -1) {
			throw new RuntimeException("sound file name has no extension: " + filename);
		}
		String extension = filename.substring(lastDotIndex + 1).toLowerCase();
		String format;
		switch (extension) {

			case "ogg":
				format = "OGG";
				break;

			case "wav":
				format = "WAV";
				break;

			default:
				throw new RuntimeException("unknown sound file extension: " + extension);

		}

		try (InputStream inputStream = org.newdawn.slick.util.ResourceLoader.getResourceAsStream(resolveFile("raw", filename).getPath())) {
			return AudioLoader.getAudio(format, inputStream);
		}
	}

}
