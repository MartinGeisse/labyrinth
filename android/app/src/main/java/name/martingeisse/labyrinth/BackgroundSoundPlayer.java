package name.martingeisse.labyrinth;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class BackgroundSoundPlayer {

    private static MediaPlayer mediaPlayer;
    private static int previousResourceId = -1;

    public static void initialize() {
        destroy();
        mediaPlayer = new MediaPlayer();
    }

    public static void destroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        previousResourceId = -1;
    }

    //
    //
    //

    public static void play(Resources resources, int resourceId) {
        if (mediaPlayer == null) {
            return;
        }
        if (previousResourceId == resourceId) {
            mediaPlayer.start();
            return;
        }
        mediaPlayer.reset();
        if (!setSource(resources, resourceId)) {
            Log.e("BackgroundSoundPlayer", "could not set sound source");
        }
        try {
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            previousResourceId = resourceId;
        } catch (IOException e) {
            Log.e("BackgroundSoundPlayer", "could not prepare sound");
        }
    }

    private static boolean setSource(Resources resources, int resourceId) {
        AssetFileDescriptor afd = null;
        try {
            afd = resources.openRawResourceFd(resourceId);
            if (afd == null) {
                return false;
            }
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            if (afd != null) {
                try {
                    afd.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    public static void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public static void resume() {
        if (mediaPlayer != null && previousResourceId >= 0) {
            mediaPlayer.start();
        }
    }

}
