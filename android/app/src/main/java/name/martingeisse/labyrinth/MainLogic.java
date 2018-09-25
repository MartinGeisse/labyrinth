package name.martingeisse.labyrinth;

import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;

import java.io.File;

import name.martingeisse.labyrinth.game.Game;
import name.martingeisse.labyrinth.system.InputStrategy;
import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.TouchInputStrategy;

public class MainLogic {

    private final File filesDir;
    private MainView mainView;

    private AndroidTouchInputSource touchInputSource;
    private ResourceManager resourceManager;
    private Game game;
    private Handler handler;
    private FrameRunnable frameRunnable;

    public MainLogic(File filesDir) {
        this.filesDir = filesDir;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public void onStart() {

        // initialize system abstraction layer
        Renderer.Holder.INSTANCE = new AndroidRenderer();
        touchInputSource = new AndroidTouchInputSource();
        InputStrategy.Holder.INSTANCE = new TouchInputStrategy(touchInputSource, 50.0f, true);

        // initialize resources
        resourceManager = new ResourceManager(mainView.getContext());
        resourceManager.initialize();

        // load saved game
        game = SavegameLogic.load(filesDir);

        // prepare the game loop, but don't start yet -- that happens in onResume()
        handler = new Handler();
        frameRunnable = new FrameRunnable();

    }

    public void onResume() {
        game.getRoom().getBackgroundSoundSelector().getSound().play();
        startFrames();
    }

    public void onPause() {
        stopFrames();
        BackgroundSoundPlayer.pause();

        // save game. Even though loading happens in onStart(), we must save in onPause() -- not
        // onStop() -- since the latter might be skipped in OOM conditions. Saving too often
        // doesn't hurt.
        SavegameLogic.save(filesDir, game);
    }

    public void onStop() {
        stopFrames(); // just in case...
        handler = null;
        game = null;
        if (resourceManager != null) {
            resourceManager.destroy();
            resourceManager = null;
        }
        InputStrategy.Holder.INSTANCE = null;
        touchInputSource = null;
        Renderer.Holder.INSTANCE = null;
    }

    public boolean onTouchEvent(MotionEvent event) {
        touchInputSource.handleEvent(event);
        return true;
    }

    protected void onDraw(Canvas canvas) {
        if (game != null) {
            ((AndroidRenderer) Renderer.Holder.INSTANCE).setCanvas(canvas);
            game.draw();
            ((AndroidRenderer) Renderer.Holder.INSTANCE).setCanvas(null);
        }
    }

    //
    // Frame runnables. We re-create the frame runnable on activation and make sure the old one
    // gets disabled -- this is the only scheme I could come up with that is free of (obvious) race
    // conditions. All other schemes would have a slim chance of multiple runnables running
    // concurrently and/or the runnable not stopping on shutdown.
    //

    private void startFrames() {
        stopFrames();
        frameRunnable = new FrameRunnable();
        handler.post(frameRunnable);
    }

    private void stopFrames() {
        if (frameRunnable != null) {
            FrameRunnable old = frameRunnable;
            frameRunnable = null;
            handler.removeCallbacks(old);
        }
    }

    class FrameRunnable implements Runnable {

        @Override
        public void run() {
            if (frameRunnable == this && game != null) {
                game.step();
                if (mainView != null) {
                    mainView.invalidate();
                }
            }
            if (frameRunnable == this && handler != null) {
                handler.postDelayed(this, 30);
            }
        }
    }

}
