/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.editor;

import name.martingeisse.labyrinth.game.*;
import name.martingeisse.labyrinth.game.rooms.RoomFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class EditorData {

	public static final int EDIT_WIDTH = 100;
	public static final int EDIT_HEIGHT = 100;

	private final byte[] blockMatrix = new byte[EDIT_WIDTH * EDIT_HEIGHT];
	private int screenX, screenY;
	private final List<Trigger> triggers = new ArrayList<>();
	private BackgroundSoundSelector backgroundSoundSelector;

	private Block drawingBlock;
	private final Block[] blockPalette = new Block[10];

	public byte[] getBlockMatrix() {
		return blockMatrix;
	}

	public int getScreenX() {
		return screenX;
	}

	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	public int getScreenY() {
		return screenY;
	}

	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	public List<Trigger> getTriggers() {
		return triggers;
	}

	public BackgroundSoundSelector getBackgroundSoundSelector() {
		return backgroundSoundSelector;
	}

	public void setBackgroundSoundSelector(BackgroundSoundSelector backgroundSoundSelector) {
		this.backgroundSoundSelector = backgroundSoundSelector;
	}

	public boolean isValidPosition(int x, int y) {
		return x >= 0 && y >= 0 && x < EDIT_WIDTH && y < EDIT_HEIGHT;
	}

	public int getBlockIndex(int x, int y) {
		if (!isValidPosition(x, y)) {
			throw new IllegalArgumentException("block coordinates out of bounds: (" + x + ", " + y +
				") for room size (" + EDIT_WIDTH + " x " + EDIT_HEIGHT + ")");
		}
		return y * EDIT_WIDTH + x;
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

	public void load(RoomFactory factory) {
		Arrays.fill(blockMatrix, (byte)0);
		Room room = factory.buildRoom(0);
		int offsetX = (EDIT_WIDTH - room.getWidth()) / 2;
		int offsetY = (EDIT_HEIGHT - room.getHeight()) / 2;
		for (int x = 0; x < room.getWidth(); x++) {
			for (int y = 0; y < room.getHeight(); y++) {
				setBlock(offsetX + x, offsetY + y, room.getBlock(x, y));
			}
		}
		screenX = (offsetX - 1) * 32;
		screenY = (offsetY - 1) * 32;
	}

	public Block getDrawingBlock() {
		return drawingBlock;
	}

	public void setDrawingBlock(Block drawingBlock) {
		this.drawingBlock = drawingBlock;
	}

	public Block[] getBlockPalette() {
		return blockPalette;
	}

}
