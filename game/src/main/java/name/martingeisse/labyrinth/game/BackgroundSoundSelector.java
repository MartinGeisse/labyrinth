/*
 * Copyright (c) 2018 Martin Geisse
 * This file is distributed under the terms of the MIT license.
 */
package name.martingeisse.labyrinth.game;

import name.martingeisse.labyrinth.system.BackgroundSound;

/**
 *
 */
public enum BackgroundSoundSelector {

	BACKGROUND1, BACKGROUND2;

	private BackgroundSound sound;

	public BackgroundSound getSound() {
		return sound;
	}

	public void setSound(BackgroundSound sound) {
		this.sound = sound;
	}

	public static void checkSoundsLoaded() {
		for (BackgroundSoundSelector selector : values()) {
			if (selector.sound == null) {
				throw new IllegalStateException("missing background sound");
			}
		}
	}

}
