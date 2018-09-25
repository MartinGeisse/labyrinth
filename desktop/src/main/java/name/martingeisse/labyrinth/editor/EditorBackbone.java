/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.editor;

import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.game.rooms.RoomFactories;
import org.lwjgl.input.Keyboard;

/**
 *
 */
public class EditorBackbone {

	private static final int[] PANE_SELECTOR_KEYS = {
		Keyboard.KEY_F1,
		Keyboard.KEY_F2,
		Keyboard.KEY_F3,
		Keyboard.KEY_F4,
		Keyboard.KEY_F5,
		Keyboard.KEY_F6,
		Keyboard.KEY_F7,
		Keyboard.KEY_F8,
		Keyboard.KEY_F9,
	};

	private final EditorData data;
	private EditorPane[] panes;
	private EditorPane currentPane;
	private boolean savePressed;

	public EditorBackbone() {
		data = new EditorData();

		// TODO remove
		data.setBlock(1, 0, Block.WALL1);
		data.load(RoomFactories.initial1);
		data.setDrawingBlock(Block.WALL2);

		panes = new EditorPane[]{
			new BlockEditPane(data),
			new BlockSelectPane(data),
			new GamePane(),
		};
		currentPane = panes[0];
	}

	public void frame() {
		for (int i = 0; i < Math.min(panes.length, PANE_SELECTOR_KEYS.length); i++) {
			if (Keyboard.isKeyDown(PANE_SELECTOR_KEYS[i])) {
				currentPane = panes[i];
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if (!savePressed) {
				savePressed = true;
				data.save();
			}
		} else {
			savePressed = false;
		}
		currentPane.frame();
	}

}
