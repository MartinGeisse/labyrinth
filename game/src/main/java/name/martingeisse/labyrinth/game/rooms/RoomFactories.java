/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game.rooms;

import name.martingeisse.labyrinth.game.BackgroundSoundSelector;
import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.game.Direction;
import name.martingeisse.labyrinth.game.Room;

/**
 *
 */
public final class RoomFactories {

	// prevent instantiation
	private RoomFactories() {
	}

	public static final RoomFactory initial1 = new RoomFactory() {
		@Override
		public Room buildRoom(int enteringDoor) {
			RoomBuilder builder = new RoomBuilder(12, 9);
			builder.fill(Block.TILE1);
			builder.roomOutline(Block.WALL1);
			builder.rectangleFilled(6, 3, 3, 3, Block.WALL1);
			builder.setDoor(7, 5, Block.DOOR1, Direction.SOUTH, initial2, 0);
			builder.setDoor(3, 3, Block.TILE1, Direction.EAST, null, 0);
			builder.selectDoor(enteringDoor);
			builder.setBackgroundSound(BackgroundSoundSelector.BACKGROUND1);
			return builder.getRoom();
		}
	};

	public static final RoomFactory initial2 = new RoomFactory() {
		@Override
		public Room buildRoom(int enteringDoor) {
			RoomBuilder builder = new RoomBuilder(15, 9);
			builder.fill(Block.TILE1);
			builder.roomOutline(Block.WALL1);
			builder.rectangleFilled(3, 3, 3, 3, Block.WALL1);
			builder.rectangleFilled(9, 3, 3, 3, Block.WALL1);
			builder.setDoor(10, 5, Block.DOOR1, Direction.SOUTH, startRoom, 0);
			builder.setDoor(4, 5, Block.DOOR1, Direction.SOUTH, initial3, 0);
			builder.selectDoor(enteringDoor);
			builder.setBackgroundSound(BackgroundSoundSelector.BACKGROUND1);
			return builder.getRoom();
		}
	};

	public static final RoomFactory initial3 = new RoomFactory() {
		@Override
		public Room buildRoom(int enteringDoor) {
			RoomBuilder builder = new RoomBuilder(30, 3);
			builder.fill(Block.TILE1);
			builder.roomOutline(Block.WALL1);
			builder.setDoor(15, 0, Block.DOOR1, Direction.SOUTH, initial2, 1);
			builder.setDoor(1, 1, Block.DOOR1, Direction.EAST, westHub, 0);
			builder.setDoor(28, 1, Block.DOOR1, Direction.WEST, eastIsland, 0);
			builder.selectDoor(enteringDoor);
			builder.setBackgroundSound(BackgroundSoundSelector.BACKGROUND1);
			return builder.getRoom();
		}
	};

	public static final RoomFactory westHub = new RoomFactory() {
		@Override
		public Room buildRoom(int enteringDoor) {
			RoomBuilder builder = new RoomBuilder(14, 14);
			builder.fill(Block.TILE2);
			builder.roomOutline(Block.WALL2);
			builder.rectangleFilled(8, 7, 6, 2, Block.WALL2);
			builder.rectangleFilled(8, 1, 2, 5, Block.WALL2);
			builder.setDoor(9, 8, Block.DOOR1, Direction.SOUTH, initial3, 1);
			builder.setDoor(4, 0, Block.DOOR1, Direction.SOUTH, null, 0);
			builder.setDoor(11, 0, Block.DOOR1, Direction.SOUTH, null, 0);
			builder.setDoor(0, 7, Block.DOOR1, Direction.EAST, null, 0);
			builder.setDoor(7, 13, Block.DOOR1, Direction.NORTH, null, 0);
			builder.setBlock(8, 6, Block.LOCK_GOLD);
			builder.selectDoor(enteringDoor);
			builder.setBackgroundSound(BackgroundSoundSelector.BACKGROUND2);
			return builder.getRoom();
		}
	};

	public static final RoomFactory eastIsland = new RoomFactory() {
		@Override
		public Room buildRoom(int enteringDoor) {
			RoomBuilder builder = new RoomBuilder(13, 13);
			builder.fill(Block.WATER);
			builder.roomOutline(Block.WALL3);
			builder.rectangleOutline(3, 3, 7, 7, Block.TILE3);
			builder.rectangleOutline(4, 4, 5, 5, Block.TILE3);
			builder.rectangleFilled(5, 5, 3, 3, Block.WALL3);
			builder.setDoor(6, 7, Block.DOOR1, Direction.SOUTH, initial3, 2);
			builder.selectDoor(enteringDoor);
			builder.setBackgroundSound(BackgroundSoundSelector.BACKGROUND3);
			return builder.getRoom();
		}
	};

	public static final int startRoomInitialDoor = 1;
	public static final RoomFactory startRoom = initial1;

}
