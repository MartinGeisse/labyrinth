package name.martingeisse.labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View {

    private final MainLogic logic;

    public MainView(Context context, MainLogic logic) {
        super(context);
        setBackgroundColor(Color.parseColor("#000000"));
        this.logic = logic;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return logic.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        logic.onDraw(canvas);
    }


}
