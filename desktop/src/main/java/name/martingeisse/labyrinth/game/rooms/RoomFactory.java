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

	public static Room initial1(boolean start) {
		RoomBuilder builder = new RoomBuilder(12, 9);
		builder.fill(Block.TILE1);
		builder.roomOutline(Block.WALL1);
		builder.rectangleFilled(5, 2, 5, 5, Block.WALL1);
		builder.setBlock(7, 6, Block.DOOR1);
		Room room = builder.getRoom();
		room.getPlayerSprite().setPlayerX(start ? 3 : 7);
		room.getPlayerSprite().setPlayerY(start ? 3 : 6);
		room.getPlayerSprite().setDirection(Direction.EAST);
		room.addTrigger(new Trigger(7, 6) {
			@Override
			protected void onTouch(Room room) {
				room.getGame().setRoom(initial2(0));
			}
		});
		return room;
	}

	public static Room initial2(int enteringDoor) {
		RoomBuilder builder = new RoomBuilder(15, 9);
		builder.fill(Block.TILE1);
		builder.roomOutline(Block.WALL1);
		builder.rectangleFilled(2, 2, 5, 5, Block.WALL1);
		builder.setBlock(4, 6, Block.DOOR1);
		builder.rectangleFilled(8, 2, 5, 5, Block.WALL1);
		builder.setBlock(10, 6, Block.DOOR1);
		Room room = builder.getRoom();
		room.getPlayerSprite().setPlayerX(enteringDoor == 0 ? 10 : 4);
		room.getPlayerSprite().setPlayerY(6);
		room.getPlayerSprite().setDirection(Direction.EAST);
		room.addTrigger(new Trigger(4, 6) {
			@Override
			protected void onTouch(Room room) {
			}
		});
		room.addTrigger(new Trigger(10, 6) {
			@Override
			protected void onTouch(Room room) {
				room.getGame().setRoom(initial1(false));
			}
		});
		return room;
	}

}
