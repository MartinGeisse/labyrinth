/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.editor;

import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.system.Renderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 *
 */
public class BlockSelectPane implements EditorPane {

	private final EditorData data;

	public BlockSelectPane(EditorData data) {
		this.data = data;
	}

	@Override
	public void frame() {

		// handle input
		int paletteIndex = EditorInputUtil.getBlockPaletteIndexInput();
		if (paletteIndex != -1) {
			data.getBlockPalette()[paletteIndex] = getMouseBlock();
		}
		if (Mouse.isButtonDown(0)) {
			data.setDrawingBlock(getMouseBlock());
		}

		// draw
		Renderer.Holder.INSTANCE.beginFrame();
		Renderer.Holder.INSTANCE.prepareDrawRoom(0, 0);
		for (int i = 0; i < Block.TABLE.length; i++) {
			Block block = Block.TABLE[i];
			int x = i % 20;
			int y = i / 20;
			Renderer.Holder.INSTANCE.drawBlockmapCell(x, y, block.getTexture());
		}
		BlockPaletteUtil.draw(data);
		Renderer.Holder.INSTANCE.endFrame();

	}

	private static Block getMouseBlock() {
		int index = getMouseBlockNumber();
		return index < 0 ? null : Block.TABLE[index];
	}

	private static int getMouseBlockNumber() {
		int x = Mouse.getX() / 32;
		int y = (Display.getHeight() - Mouse.getY()) / 32;
		if (x < 0 || x >= 20 || y < 0 || y >= 15) {
			return -1;
		}
		int index = y * 20 + x;
		return (index < Block.TABLE.length ? index : -1);
	}

}
