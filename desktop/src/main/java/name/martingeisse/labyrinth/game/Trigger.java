/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

/**
 *
 */
public final class Trigger {

	private int x;
	private int y;
	private Callback callback;

	public Trigger() {
	}

	public Trigger(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Trigger(Callback callback) {
		this.callback = callback;
	}

	public Trigger(int x, int y, Callback callback) {
		this.x = x;
		this.y = y;
		this.callback = callback;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public void check(Room room) {
		PlayerSprite playerSprite = room.getPlayerSprite();
		if (playerSprite.getPlayerX() == x && playerSprite.getPlayerY() == y && callback != null) {
			callback.onTouch(room);
		}
	}

	public interface Callback {
		void onTouch(Room room);
	}
}
