/**
 * Copyright (c) 2010 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.labyrinth.system;

import org.lwjgl.opengl.GL11;

/**
 * Represents a texture and provides OpenGL texture handling functions.
 */
public final class Texture {

	/**
	 * the slickTexture
	 */
	private final org.newdawn.slick.opengl.Texture slickTexture;
	
	/**
	 * Constructor.
	 * 
	 * Note that this constructor changes the currently active texture unit
	 * to GL_TEXTURE_2D to set the texture parameters.
	 * 
	 * @param slickTexture the wrapped Slick texture
	 */
	public Texture(final org.newdawn.slick.opengl.Texture slickTexture) {
		this.slickTexture = slickTexture;
		glBindTexture();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
	}

	/**
	 * Calls glBindTexture() on this texture.
	 */
	public void glBindTexture() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, slickTexture.getTextureID());
	}
	
	/**
	 * Getter method for the width.
	 * @return the width
	 */
	public int getWidth() {
		return slickTexture.getImageWidth();
	}
	
	/**
	 * Getter method for the height.
	 * @return the height
	 */
	public int getHeight() {
		return slickTexture.getImageHeight();
	}
	
}
