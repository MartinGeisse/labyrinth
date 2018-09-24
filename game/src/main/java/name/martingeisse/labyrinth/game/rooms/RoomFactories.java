/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game.rooms;

import name.martingeisse.labyrinth.game.*;

/**
 *
 */
public final class RoomFactories {

	// prevent instantiation
	private RoomFactories() {
	}

	public static final int startRoomInitialDoor = 1;
	public static final RoomFactory startRoom = new RoomFactory() {
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
			builder.setDoor(1, 1, Block.DOOR1, Direction.EAST, null, 0);
			builder.setDoor(28, 1, Block.DOOR1, Direction.WEST, null, 0);
			builder.selectDoor(enteringDoor);
			builder.setBackgroundSound(BackgroundSoundSelector.BACKGROUND2);
			return builder.getRoom();
		}
	};

}
