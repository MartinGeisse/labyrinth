/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import name.martingeisse.labyrinth.resource.Resources;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public class PlayerSprite {

	private final Room room;
	private int playerX = 3;
	private int playerY = 3;
	private int playerFractionX = 0;
	private int playerFractionY = 0;
	private Direction direction = Direction.EAST;
	private boolean walking = false;

	public PlayerSprite(Room room) {
		this.room = room;
	}

	public void step() {
		if (!walking) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				tryStartWalking(Direction.WEST);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				tryStartWalking(Direction.EAST);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				tryStartWalking(Direction.NORTH);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				tryStartWalking(Direction.SOUTH);
			}
		}
		if (walking) {
			playerFractionX += 4 * direction.getDeltaX();
			playerFractionY += 4 * direction.getDeltaY();
			if (playerFractionX == 0 && playerFractionY == 0) {
				walking = false;
			}
		}
	}

	private void tryStartWalking(Direction direction) {
		this.direction = direction;
		int newX = playerX + direction.getDeltaX();
		int newY = playerY + direction.getDeltaY();
		if (room.isValidPosition(newX, newY) && !room.getBlock(newX, newY).isSolid()) {
			walking = true;
			playerX = newX;
			playerFractionX = -32 * direction.getDeltaX();
			playerY = newY;
			playerFractionY = -32 * direction.getDeltaY();
		}
	}

	public void draw() {
		int anchorX = (playerX << 5) + playerFractionX;
		int anchorY = (playerY << 5) + playerFractionY;
		int frame = (walking ? ((((playerFractionX + playerFractionY) >> 2) + 100) % 3) : 0);
		Resources.getTexture("player_" + direction.name().toLowerCase() + '_' + frame + ".png").glBindTexture();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2i(anchorX, anchorY);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2i(anchorX + 32, anchorY);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2i(anchorX + 32, anchorY + 32);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2i(anchorX, anchorY + 32);
		GL11.glEnd();
	}

}
