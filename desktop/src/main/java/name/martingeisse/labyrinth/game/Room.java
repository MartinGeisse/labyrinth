/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public class Room {

	private final int width;
	private final int height;
	private final byte[] blockMatrix;
	private final PlayerSprite playerSprite;
	private int screenX, screenY;

	public Room(int width, int height) {
		this.width = width;
		this.height = height;
		this.blockMatrix = new byte[width * height];
		this.playerSprite = new PlayerSprite(this);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public byte[] getBlockMatrix() {
		return blockMatrix;
	}

	public PlayerSprite getPlayerSprite() {
		return playerSprite;
	}

	public boolean isValidPosition(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}

	public int getBlockIndex(int x, int y) {
		if (!isValidPosition(x, y)) {
			throw new IllegalArgumentException("block coordinates out of bounds: (" + x + ", " + y +
				") for room size (" + width + " x " + height + ")");
		}
		return y * width + x;
	}

	public static byte convertBlockNumberToBlockByte(int blockNumber) {
		if (blockNumber != (blockNumber & 0xff)) {
			throw new IllegalArgumentException("invalid block number: " + blockNumber);
		}
		return (byte) blockNumber;
	}

	public static int convertBlockByteToBlockNumber(byte blockByte) {
		return blockByte & 0xff;
	}

	public void setBlockNumber(int x, int y, int blockNumber) {
		blockMatrix[getBlockIndex(x, y)] = convertBlockNumberToBlockByte(blockNumber);
	}

	public int getBlockNumber(int x, int y) {
		return convertBlockByteToBlockNumber(blockMatrix[getBlockIndex(x, y)]);
	}

	public Block getBlock(int x, int y) {
		return Block.get(getBlockNumber(x, y));
	}

	public void step() {
		playerSprite.step();

		// make the screen follow the player (TODO assumes fixed size of 640x480, but that's easy to change)
		if (width <= 20) {
			screenX = -((Display.getWidth() - (width << 5)) >> 1);
		} else {
			screenX = (playerSprite.getPlayerX() << 5) + playerSprite.getPlayerFractionX() + 16 - (Display.getWidth() >> 1);
			if (screenX < 0) {
				screenX = 0;
			} else if (screenX + Display.getWidth() > (width << 5)) {
				screenX = (width << 5) - Display.getWidth();
			}
		}
		if (height <= 15) {
			screenY = -((Display.getHeight() - (height << 5)) >> 1);
		} else {
			screenY = (playerSprite.getPlayerY() << 5) + playerSprite.getPlayerFractionY() + 16 - (Display.getHeight() >> 1);
			if (screenY < 0) {
				screenY = 0;
			} else if (screenY + Display.getHeight() > (height << 5)) {
				screenY = (height << 5) - Display.getHeight();
			}
		}
	}

	public void draw() {
		GL11.glTranslatef(-screenX, -screenY, 0.0f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				getBlock(x, y).getTexture().glBindTexture();
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
