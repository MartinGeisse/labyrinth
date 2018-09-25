/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.editor;

import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.system.Renderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
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
		int paletteIndex = EditorInputUtil.getBlockPaletteIndexInput();
		if (paletteIndex != -1) {
			data.setDrawingBlock(data.getBlockPalette()[paletteIndex]);
		}
		if (Mouse.isButtonDown(0)) {
			int x = (Mouse.getX() + data.getScreenX()) / 32;
			int y = (Display.getHeight() - Mouse.getY() + data.getScreenY()) / 32;
			if (x >= 0 && x < EditorData.EDIT_WIDTH && y >= 0 && y < EditorData.EDIT_HEIGHT) {
				data.setBlock(x, y, data.getDrawingBlock());
			}
		}

		// draw
		Renderer.Holder.INSTANCE.beginFrame();
		Renderer.Holder.INSTANCE.prepareDrawRoom(data.getScreenX(), data.getScreenY());
		for (int x = 0; x < EditorData.EDIT_WIDTH; x++) {
			for (int y = 0; y < EditorData.EDIT_HEIGHT; y++) {
				Renderer.Holder.INSTANCE.drawBlockmapCell(x, y, data.getBlock(x, y).getTexture());
			}
		}
		BlockPaletteUtil.draw(data);
		Renderer.Holder.INSTANCE.endFrame();

	}

}
