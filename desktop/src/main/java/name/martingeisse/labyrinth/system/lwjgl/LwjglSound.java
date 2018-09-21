package name.martingeisse.labyrinth.system.lwjgl;

import name.martingeisse.labyrinth.system.Sound;
import org.newdawn.slick.openal.Audio;

/**
 *
 */
public class LwjglSound implements Sound {

	private final Audio audio;

	public LwjglSound(Audio audio) {
		this.audio = audio;
	}

}
