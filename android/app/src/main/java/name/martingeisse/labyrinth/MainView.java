package name.martingeisse.labyrinth;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.game.Direction;
import name.martingeisse.labyrinth.game.Game;
import name.martingeisse.labyrinth.game.PlayerSprite;
import name.martingeisse.labyrinth.game.rooms.RoomFactories;
import name.martingeisse.labyrinth.system.InputStrategy;
import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.Texture;
import name.martingeisse.labyrinth.system.TouchInputStrategy;

public class MainView extends View {

    private final Handler handler;
    private final Game game;
    private final Runnable frameRunnable;
    private final AndroidTouchInputSource touchInputSource;

    public MainView(Context context) {
        super(context);
        handler = new Handler();

        // initialize system abstraction layer
        Renderer.Holder.INSTANCE = new AndroidRenderer();
        touchInputSource = new AndroidTouchInputSource();
        InputStrategy.Holder.INSTANCE = new TouchInputStrategy(touchInputSource, 50.0f, true);

        // initialize resources
        Block.NOTHING.setTexture(loadTexture(R.drawable.nothing));
        Block.TILE1.setTexture(loadTexture(R.drawable.tile1));
        Block.WALL1.setTexture(loadTexture(R.drawable.wall1));
        Block.DOOR1.setTexture(loadTexture(R.drawable.door1));
        {
            Texture[][] playerTextures = new Texture[4][3];
            playerTextures[Direction.NORTH.ordinal()][0] = loadTexture(R.drawable.player_north_0);
            playerTextures[Direction.NORTH.ordinal()][1] = loadTexture(R.drawable.player_north_1);
            playerTextures[Direction.NORTH.ordinal()][2] = loadTexture(R.drawable.player_north_2);
            playerTextures[Direction.SOUTH.ordinal()][0] = loadTexture(R.drawable.player_south_0);
            playerTextures[Direction.SOUTH.ordinal()][1] = loadTexture(R.drawable.player_south_1);
            playerTextures[Direction.SOUTH.ordinal()][2] = loadTexture(R.drawable.player_south_2);
            playerTextures[Direction.WEST.ordinal()][0] = loadTexture(R.drawable.player_west_0);
            playerTextures[Direction.WEST.ordinal()][1] = loadTexture(R.drawable.player_west_1);
            playerTextures[Direction.WEST.ordinal()][2] = loadTexture(R.drawable.player_west_2);
            playerTextures[Direction.EAST.ordinal()][0] = loadTexture(R.drawable.player_east_0);
            playerTextures[Direction.EAST.ordinal()][1] = loadTexture(R.drawable.player_east_1);
            playerTextures[Direction.EAST.ordinal()][2] = loadTexture(R.drawable.player_east_2);
            PlayerSprite.setTextures(playerTextures);
        }

        game = new Game();
        game.setRoom(RoomFactories.startRoom.buildRoom(RoomFactories.startRoomInitialDoor));

        frameRunnable = new Runnable() {
            @Override
            public void run() {
                game.step();
                invalidate();
                handler.postDelayed(this, 30);
            }
        };
        handler.post(frameRunnable);
    }

    private AndroidTexture loadTexture(int resourceId) {
        return new AndroidTexture(BitmapFactory.decodeResource(getResources(), resourceId));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchInputSource.handleEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ((AndroidRenderer) Renderer.Holder.INSTANCE).setCanvas(canvas);
        game.draw();
        ((AndroidRenderer) Renderer.Holder.INSTANCE).setCanvas(null);
    }

}
