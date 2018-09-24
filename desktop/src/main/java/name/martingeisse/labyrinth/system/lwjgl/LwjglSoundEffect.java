package name.martingeisse.labyrinth.system.lwjgl;

import name.martingeisse.labyrinth.system.SoundEffect;
import org.newdawn.slick.openal.Audio;

/**
 *
 */
public class LwjglSoundEffect implements SoundEffect {

	private final Audio audio;

	public LwjglSoundEffect(Audio audio) {
		this.audio = audio;
	}

	@Override
	public void play() {
		audio.playAsSoundEffect(1.0f, 1.0f, false);
	}

}
