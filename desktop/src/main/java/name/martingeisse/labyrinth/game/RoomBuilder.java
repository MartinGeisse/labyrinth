/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import java.util.Arrays;

/**
 *
 */
public class RoomBuilder {

	private final Room room;

	public RoomBuilder(int width, int height) {
		this.room = new Room(width, height);
	}

	public Room getRoom() {
		return room;
	}

	//
	// mirrors for block modification methods in class Room
	//

	public int getBlockIndex(int x, int y) {
		return room.getBlockIndex(x, y);
	}

	public byte convertBlockNumberToBlockByte(int blockNumber) {
		return Room.convertBlockNumberToBlockByte(blockNumber);
	}

	public int convertBlockByteToBlockNumber(byte blockByte) {
		return Room.convertBlockByteToBlockNumber(blockByte);
	}

	public void setBlockNumber(int x, int y, int blockNumber) {
		room.setBlockNumber(x, y, blockNumber);
	}

	public int getBlockNumber(int x, int y) {
		return room.getBlockNumber(x, y);
	}

	public void setBlock(int x, int y, Block block) {
		room.setBlock(x, y, block);
	}

	public Block getBlock(int x, int y) {
		return room.getBlock(x, y);
	}

	//
	// drawing operations
	//

	public void fill(Block block) {
		Arrays.fill(room.getBlockMatrix(), (byte) block.ordinal());
	}

	public void hline(int x, int y, int length, Block block) {
		for (int i = 0; i < length; i++) {
			room.setBlock(x + i, y, block);
		}
	}

	public void vline(int x, int y, int length, Block block) {
		for (int i = 0; i < length; i++) {
			room.setBlock(x, y + i, block);
		}
	}

	public void roomOutline(Block block) {
		rectangleOutline(0, 0, room.getWidth(), room.getHeight(), block);
	}

	public void rectangleOutline(int x, int y, int width, int height, Block block) {
		for (int i = 0; i < width; i++) {
			room.setBlock(x + i, y, block);
			room.setBlock(x + i, y + height - 1, block);
		}
		for (int i = 0; i < height; i++) {
			room.setBlock(x, y + i, block);
			room.setBlock(x + width - 1, y + i, block);
		}
	}

	public void rectangleFilled(int x, int y, int width, int height, Block block) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				room.setBlock(x + i, y + j, block);
			}
		}
	}

}
