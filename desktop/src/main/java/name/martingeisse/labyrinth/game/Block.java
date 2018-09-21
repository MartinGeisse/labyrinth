/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import name.martingeisse.labyrinth.system.Texture;

/**
 *
 */
public enum Block {

	NOTHING(true),
	TILE1(false),
	WALL1(true),
	DOOR1(false);

	// avoid recreating this array over and over
	public static final Block[] TABLE = values();

	private final boolean solid;
	private Texture texture;

	Block(boolean solid) {
		this.solid = solid;
	}

	public boolean isSolid() {
		return solid;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public static Block get(int blockNumber) {
		return TABLE[blockNumber];
	}

	public static void checkTexturesLoaded() {
		for (Block block : values()) {
			if (block.texture == null) {
				throw new IllegalStateException("missing block texture");
			}
		}
	}

}
