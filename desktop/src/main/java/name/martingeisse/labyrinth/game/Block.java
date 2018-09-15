/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import name.martingeisse.labyrinth.resource.Resources;
import name.martingeisse.labyrinth.system.Texture;

/**
 *
 */
public enum Block {

	TILE1(false),
	WALL1(true);

	// avoid recreating this array over and over
	public static final Block[] TABLE = values();

	private final boolean solid;

	Block(boolean solid) {
		this.solid = solid;
	}

	public boolean isSolid() {
		return solid;
	}

	public Texture getTexture() {
		return Resources.getTexture(name().toLowerCase() + ".png");
	}

	public static Block get(int blockNumber) {
		return TABLE[blockNumber];
	}

}
