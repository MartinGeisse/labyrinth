package name.martingeisse.labyrinth;

import android.media.SoundPool;

import name.martingeisse.labyrinth.system.SoundEffect;

public class AndroidSoundEffect implements SoundEffect {

    private final SoundPool soundPool;
    private final int soundId;

    public AndroidSoundEffect(SoundPool soundPool, int soundId) {
        this.soundPool = soundPool;
        this.soundId = soundId;
    }

    @Override
    public void play() {
        soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
    }

}
