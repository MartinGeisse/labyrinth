package name.martingeisse.labyrinth.system.lwjgl;

import name.martingeisse.labyrinth.system.BackgroundSound;
import org.newdawn.slick.openal.Audio;

/**
 *
 */
public class LwjglBackgroundSound implements BackgroundSound {

	private static Audio playing;

	private final Audio audio;

	public LwjglBackgroundSound(Audio audio) {
		this.audio = audio;
	}

	@Override
	public void play() {
		if (playing == audio) {
			// don't restart the sound when "switching" to the same sound
			return;
		}
		if (playing != null) {
			playing.stop();
		}
		playing = audio;
		audio.playAsMusic(1.0f, 1.0f, true);
	}

}
