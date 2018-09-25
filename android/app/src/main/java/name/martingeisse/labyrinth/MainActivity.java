package name.martingeisse.labyrinth;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

    private MainLogic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        logic = new MainLogic(getFilesDir());
        MainView mainView = new MainView(this, logic);
        setContentView(mainView);
        logic.setMainView(mainView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logic.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        logic.onResume();
    }

    @Override
    protected void onPause() {
        logic.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        logic.onStop();
        super.onStop();
    }

}
