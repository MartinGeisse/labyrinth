/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.editor;

import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.system.Renderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 *
 */
public class BlockEditPane implements EditorPane {

	private static int SCROLL_SPEED = 16;

	private final EditorData data;

	public BlockEditPane(EditorData data) {
		this.data = data;
	}

	public void frame() {

		// controls
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			data.setScreenX(data.getScreenX() - SCROLL_SPEED);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			data.setScreenX(data.getScreenX() + SCROLL_SPEED);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			data.setScreenY(data.getScreenY() - SCROLL_SPEED);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			data.setScreenY(data.getScreenY() + SCROLL_SPEED);
		}

		// draw
		Renderer.Holder.INSTANCE.beginFrame();
		Renderer.Holder.INSTANCE.prepareDrawRoom(data.getScreenX(), data.getScreenY());
		for (int x = 0; x < EditorData.EDIT_WIDTH; x++) {
			for (int y = 0; y < EditorData.EDIT_HEIGHT; y++) {
				Renderer.Holder.INSTANCE.drawBlockmapCell(x, y, data.getBlock(x, y).getTexture());
			}
		}
		int drawingBlockIndex = -1;
		GL11.glLoadIdentity();
		GL11.glTranslatef(16.0f, 16.0f, 0.0f);
		for (int i = 0; i < data.getBlockPalette().length; i++) {
			Block block = data.getBlockPalette()[i];
			if (block != null) {
				Renderer.Holder.INSTANCE.drawBlockmapCell(i, 13, block.getTexture());
				if (block == data.getDrawingBlock()) {
					drawingBlockIndex = i;
				}
			}
		}
		if (drawingBlockIndex < 0 && data.getDrawingBlock() != null) {
			drawingBlockIndex = data.getBlockPalette().length;
			Renderer.Holder.INSTANCE.drawBlockmapCell(drawingBlockIndex, 13, data.getDrawingBlock().getTexture());
		}
		if (drawingBlockIndex >= 0) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3ub((byte) 0, (byte) 255, (byte) 0);
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex2i(drawingBlockIndex << 5, 13 << 5);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex2i((drawingBlockIndex + 1) << 5, 13 << 5);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex2i((drawingBlockIndex + 1) << 5, 14 << 5);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex2i(drawingBlockIndex << 5, 14 << 5);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
		Renderer.Holder.INSTANCE.endFrame();

	}

}
