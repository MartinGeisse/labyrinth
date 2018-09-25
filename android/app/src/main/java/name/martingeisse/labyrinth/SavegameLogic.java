package name.martingeisse.labyrinth;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import name.martingeisse.labyrinth.game.Game;

public class SavegameLogic {

    public static void save(File filesDir, Game game) {
        if (game == null) {
            return;
        }
        OutputStream saveStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteArrayOutputStream);
            objectOutput.writeObject(game);
            saveStream = new FileOutputStream(getSaveFile(filesDir));
            saveStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            // we'll have to ignore this, even though the save file might be corrupted
        } finally {
            if (saveStream != null) {
                try {
                    saveStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    public static Game load(File filesDir) {
        File saveFile = getSaveFile(filesDir);
        if (!saveFile.exists()) {
            return new Game();
        }
        InputStream saveStream = null;
        try {
            saveStream = new FileInputStream(saveFile);
            ObjectInputStream objectInput = new ObjectInputStream(saveStream);
            try {
                return (Game) objectInput.readObject();
            } catch (Exception e) {
                Log.e("MainView.startGame", "could not load savegame", e);
                return new Game();
            }
        } catch (IOException e) {
            return new Game();
        } finally {
            if (saveStream != null) {
                try {
                    saveStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    private static File getSaveFile(File filesDir) {
        return new File(filesDir, "savegame");
    }

}
