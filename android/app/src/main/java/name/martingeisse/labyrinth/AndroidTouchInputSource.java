package name.martingeisse.labyrinth;

import android.view.MotionEvent;

import name.martingeisse.labyrinth.system.TouchInputSource;

public class AndroidTouchInputSource implements TouchInputSource {

    private int x, y;
    private boolean active;

    public void handleEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();
                active = true;
                break;

            case MotionEvent.ACTION_UP:
                active = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (active) {
                    x = (int) event.getX();
                    y = (int) event.getY();
                }
                break;

        }
    }

    @Override
    public boolean isTouching() {
        return active;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

}
