/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import name.martingeisse.labyrinth.resource.Resources;
import name.martingeisse.labyrinth.system.Texture;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public class Room {

	private final int width;
	private final int height;
	private final byte[] blocks;
	private final PlayerSprite playerSprite;

	public Room(int width, int height) {
		this.width = width;
		this.height = height;
		this.blocks = new byte[width * height];
		this.playerSprite = new PlayerSprite(this);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public byte[] getBlocks() {
		return blocks;
	}

	public PlayerSprite getPlayerSprite() {
		return playerSprite;
	}

	public int getBlockIndex(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			throw new IllegalArgumentException("block coordinates out of bounds: (" + x + ", " + y +
				") for room size (" + width + " x " + height + ")");
		}
		return y * width + x;
	}

	public static byte convertToBlockByte(int block) {
		if (block != (block & 0xff)) {
			throw new IllegalArgumentException("invalid block byte: " + block);
		}
		return (byte) block;
	}

	public static int convertFromBlockByte(byte block) {
		return block & 0xff;
	}

	public void setBlock(int x, int y, int block) {
		blocks[getBlockIndex(x, y)] = convertToBlockByte(block);
	}

	public int getBlock(int x, int y) {
		return convertFromBlockByte(blocks[getBlockIndex(x, y)]);
	}

	public void step() {
		playerSprite.step();
	}

	public void draw() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int block = convertFromBlockByte(blocks[y * width + x]);
				Texture texture = Resources.getTexture(block == 0 ? "tile1.png" : "wall1.png");
				if (texture == null) {
					continue;
				}
				texture.glBindTexture();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0.0f, 1.0f);
				GL11.glVertex2i(x << 5, y << 5);
				GL11.glTexCoord2f(1.0f, 1.0f);
				GL11.glVertex2i((x + 1) << 5, y << 5);
				GL11.glTexCoord2f(1.0f, 0.0f);
				GL11.glVertex2i((x + 1) << 5, (y + 1) << 5);
				GL11.glTexCoord2f(0.0f, 0.0f);
				GL11.glVertex2i(x << 5, (y + 1) << 5);
				GL11.glEnd();
			}
		}
		playerSprite.draw();
	}

}
