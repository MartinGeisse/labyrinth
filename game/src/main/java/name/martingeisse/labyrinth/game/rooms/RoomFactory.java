/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game.rooms;

import name.martingeisse.labyrinth.game.*;

/**
 *
 */
public class RoomFactory {

	public static Room start() {
		return initial1(1);
	}

	public static Room initial1(int enteringDoor) {
		RoomBuilder builder = new RoomBuilder(12, 9);
		builder.fill(Block.TILE1);
		builder.roomOutline(Block.WALL1);
		builder.rectangleFilled(6, 3, 3, 3, Block.WALL1);
		builder.setDoor(7, 5, Block.DOOR1, Direction.SOUTH, () -> initial2(0));
		builder.setDoor(3, 3, Block.TILE1, Direction.EAST, (Trigger.Callback) null);
		builder.selectDoor(enteringDoor);
		return builder.getRoom();
	}

	public static Room initial2(int enteringDoor) {
		RoomBuilder builder = new RoomBuilder(15, 9);
		builder.fill(Block.TILE1);
		builder.roomOutline(Block.WALL1);
		builder.rectangleFilled(3, 3, 3, 3, Block.WALL1);
		builder.rectangleFilled(9, 3, 3, 3, Block.WALL1);
		builder.setDoor(10, 5, Block.DOOR1, Direction.SOUTH, () -> initial1(0));
		builder.setDoor(4, 5, Block.DOOR1, Direction.SOUTH, () -> initial3(0));
		builder.selectDoor(enteringDoor);
		return builder.getRoom();
	}

	public static Room initial3(int enteringDoor) {
		RoomBuilder builder = new RoomBuilder(30, 3);
		builder.fill(Block.TILE1);
		builder.roomOutline(Block.WALL1);
		builder.setDoor(15, 0, Block.DOOR1, Direction.SOUTH, () -> initial2(1));
		builder.setDoor(1, 1, Block.DOOR1, Direction.EAST);
		builder.setDoor(28, 1, Block.DOOR1, Direction.WEST);
		builder.selectDoor(enteringDoor);
		return builder.getRoom();
	}

}
