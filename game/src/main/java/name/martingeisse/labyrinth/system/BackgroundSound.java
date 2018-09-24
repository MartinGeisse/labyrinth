package name.martingeisse.labyrinth.system;

/**
 * Unlike {@link SoundEffect}, this class always loops the sound and playing any sound stops the
 * previously playing background sound.
 */
public interface BackgroundSound {

    void play();

}
