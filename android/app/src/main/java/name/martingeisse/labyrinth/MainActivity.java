package name.martingeisse.labyrinth;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

// TODO used to extend AppCompatActivity -- remove this comment when it works
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(new MainView(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        File saveFile = getSaveFile();
        if (saveFile.exists()) {
            InputStream saveStream = null;
            try {
                saveStream = new FileInputStream(saveFile);
                ObjectInputStream objectInput = new ObjectInputStream(saveStream);
                getMainView().startGame(objectInput);
            } catch (IOException e) {
                getMainView().startGame(null);
            } finally {
                if (saveStream != null) {
                    try {
                        saveStream.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }
        } else {
            getMainView().startGame(null);
        }
    }

    @Override
    protected void onPause() {
        OutputStream saveStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteArrayOutputStream);
            getMainView().saveGame(objectOutput);
            saveStream = new FileOutputStream(getSaveFile());
            saveStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            // we'll have to ignore this, even though the save file is probably corrupted
        } finally {
            if (saveStream != null) {
                try {
                    saveStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        BackgroundSoundPlayer.destroy();
        super.onStop();
    }

    private File getSaveFile() {
        return new File(getFilesDir(), "savegame");
    }

    private MainView getMainView() {
        MainView mainView = getMainView(getWindow().getDecorView());
        if (mainView == null) {
            throw new IllegalStateException("no main view");
        }
        return mainView;
    }

    private MainView getMainView(View anchor) {
        if (anchor instanceof MainView) {
            return (MainView)anchor;
        } else if (anchor instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)anchor;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                MainView mainView = getMainView(viewGroup.getChildAt(i));
                if (mainView != null) {
                    return mainView;
                }
            }
            return null;
        } else {
            return null;
        }

    }

}
