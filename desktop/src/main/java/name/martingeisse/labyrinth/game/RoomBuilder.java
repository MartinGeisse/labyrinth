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

	//
	// drawing operations
	//

	public void fill(int blockNumber) {
		Arrays.fill(room.getBlockMatrix(), (byte) blockNumber);
	}

	public void hline(int x, int y, int length, int blockNumber) {
		for (int i = 0; i < length; i++) {
			room.setBlockNumber(x + i, y, blockNumber);
		}
	}

	public void vline(int x, int y, int length, int blockNumber) {
		for (int i = 0; i < length; i++) {
			room.setBlockNumber(x, y + i, blockNumber);
		}
	}

	public void roomOutline(int blockNumber) {
		rectangleOutline(0, 0, room.getWidth(), room.getHeight(), blockNumber);
	}

	public void rectangleOutline(int x, int y, int width, int height, int blockNumber) {
		for (int i = 0; i < width; i++) {
			room.setBlockNumber(x + i, y, blockNumber);
			room.setBlockNumber(x + i, y + height - 1, blockNumber);
		}
		for (int i = 0; i < height; i++) {
			room.setBlockNumber(x, y + i, blockNumber);
			room.setBlockNumber(x + width - 1, y + i, blockNumber);
		}
	}

	public void rectangleFilled(int x, int y, int width, int height, int blockNumber) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				room.setBlockNumber(x + i, y + j, blockNumber);
			}
		}
	}

}
