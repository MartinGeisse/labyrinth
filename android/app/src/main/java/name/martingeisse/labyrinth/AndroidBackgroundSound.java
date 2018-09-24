package name.martingeisse.labyrinth;

import android.content.res.Resources;

import name.martingeisse.labyrinth.system.BackgroundSound;

// TODO sound stops when changing orientation (recreates activity; should restart sound at least)
// but cannot keep playing onStop() since the sound would continue while the app is in background
public class AndroidBackgroundSound implements BackgroundSound {

    private final Resources resources;
    private final int resourceId;

    public AndroidBackgroundSound(Resources resources, int resourceId) {
        this.resources = resources;
        this.resourceId = resourceId;
    }

    @Override
    public void play() {
        BackgroundSoundPlayer.play(resources, resourceId);
    }

}
