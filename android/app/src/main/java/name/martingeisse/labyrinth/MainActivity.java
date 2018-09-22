package name.martingeisse.labyrinth;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

// TODO used to extend AppCompatActivity -- remove this comment when it works
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(new MainView(this));
    }

}
