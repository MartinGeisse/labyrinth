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

	public void save() {

		System.out.println();
		System.out.println("------------------------------------------------------------");
		System.out.println();

		int firstX = 0;
		while (firstX < EDIT_WIDTH && columnEmpty(firstX)) {
			firstX++;
		}
		if (firstX == EDIT_WIDTH) {
			System.out.println("EMPTY!");
			return;
		}
		int lastX = EDIT_WIDTH - 1;
		while (columnEmpty(lastX)) {
			lastX--;
		}
		int firstY = 0;
		while (rowEmpty(firstY)) {
			firstY++;
		}
		int lastY = EDIT_HEIGHT - 1;
		while (rowEmpty(lastY)) {
			lastY--;
		}
		int width = (lastX - firstX + 1);
		int height = (lastY - firstY + 1);

		System.out.println("DUMMY!!!");
		System.out.println();
		System.out.println("\tpublic static final RoomFactory xyzxyzxyz = new RoomFactory() {");
		System.out.println("\t\t@Override");
		System.out.println("\t\tpublic Room buildRoom(int enteringDoor) {");
		System.out.println("\t\t\tRoomBuilder builder = new RoomBuilder(" + width + ", " + height + ");");
		System.out.println("\t\t\tbuilder.fill(Block.WATER);");
		System.out.println("\t\t\tbuilder.roomOutline(Block.WALL3);");
		System.out.println("\t\t\tbuilder.rectangleOutline(3, 3, 7, 7, Block.TILE3);");
		System.out.println("\t\t\tbuilder.rectangleOutline(4, 4, 5, 5, Block.TILE3);");
		System.out.println("\t\t\tbuilder.rectangleFilled(5, 5, 3, 3, Block.WALL3);");
		System.out.println("\t\t\tbuilder.setDoor(6, 7, Block.DOOR1, Direction.SOUTH, initial3, 2);");
		System.out.println("\t\t\tbuilder.selectDoor(enteringDoor);");
		System.out.println("\t\t\tbuilder.setBackgroundSound(BackgroundSoundSelector.BACKGROUND3);");
		System.out.println("\t\t\treturn builder.getRoom();");
		System.out.println("\t\t}");
		System.out.println("\t};");
	}

	private boolean rowEmpty(int y) {
		for (int x = 0; x < EDIT_WIDTH; x++) {
			if (getBlockNumber(x, y) != 0) {
				return false;
			}
		}
		return true;
	}

	private boolean columnEmpty(int x) {
		for (int y = 0; y < EDIT_HEIGHT; y++) {
			if (getBlockNumber(x, y) != 0) {
				return false;
			}
		}
		return true;
	}


}
