/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import name.martingeisse.labyrinth.system.Renderer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Room {

	private Game game;
	private final int width;
	private final int height;
	private final byte[] blockMatrix;
	private final PlayerSprite playerSprite;
	private int screenX, screenY;
	private final List<Trigger> triggers = new ArrayList<>();

	public Room(int width, int height) {
		this.width = width;
		this.height = height;
		this.blockMatrix = new byte[width * height];
		this.playerSprite = new PlayerSprite(this);
	}

	public Room(InputStream saveStream) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	public void save(OutputStream saveStream) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	void bindToGame(Game game) {
		if (this.game != null && this.game != game) {
			throw new IllegalStateException("room already bound to game");
		}
		this.game = game;
	}

	public Game getGame() {
		if (game == null) {
			throw new IllegalStateException("room not bound to game yet");
		}
		return game;
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

	public void setBlock(int x, int y, Block block) {
		setBlockNumber(x, y, block.ordinal());
	}

	public Block getBlock(int x, int y) {
		return Block.get(getBlockNumber(x, y));
	}

	public void addTrigger(Trigger trigger) {
		triggers.add(trigger);
	}

	public void step() {
		playerSprite.step();
	}

	public void draw() {

		// make the screen follow the player
		int displayWidth = Renderer.Holder.INSTANCE.getWidth();
		int displayHeight = Renderer.Holder.INSTANCE.getHeight();
		int widthInPixels = width << 5;
		int heightInPixels = height << 5;
		if (widthInPixels <= displayWidth) {
			screenX = -((displayWidth - widthInPixels) >> 1);
		} else {
			screenX = (playerSprite.getPlayerX() << 5) + playerSprite.getPlayerFractionX() + 16 - (displayWidth >> 1);
			if (screenX < 0) {
				screenX = 0;
			} else if (screenX + displayWidth > widthInPixels) {
				screenX = widthInPixels - displayWidth;
			}
		}
		if (heightInPixels <= displayHeight) {
			screenY = -((displayHeight - heightInPixels) >> 1);
		} else {
			screenY = (playerSprite.getPlayerY() << 5) + playerSprite.getPlayerFractionY() + 16 - (displayHeight >> 1);
			if (screenY < 0) {
				screenY = 0;
			} else if (screenY + displayHeight > heightInPixels) {
				screenY = heightInPixels - displayHeight;
			}
		}

		// draw the room
		Renderer.Holder.INSTANCE.prepareDrawRoom(screenX, screenY);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Renderer.Holder.INSTANCE.drawBlockmapCell(x, y, getBlock(x, y).getTexture());
			}
		}
		playerSprite.draw();

	}

	void checkTriggers() {
		for (Trigger trigger : triggers) {
			trigger.check(this);
		}
	}

}
