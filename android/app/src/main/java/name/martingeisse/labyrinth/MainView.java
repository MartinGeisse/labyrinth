package name.martingeisse.labyrinth;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.io.InputStream;
import java.io.OutputStream;

import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.game.Direction;
import name.martingeisse.labyrinth.game.Game;
import name.martingeisse.labyrinth.game.PlayerSprite;
import name.martingeisse.labyrinth.system.InputStrategy;
import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.SoundEffects;
import name.martingeisse.labyrinth.system.Texture;
import name.martingeisse.labyrinth.system.TouchInputStrategy;

public class MainView extends View {

    private final Handler handler;
    private final AndroidTouchInputSource touchInputSource;
    private final SoundPool mainSoundPool;
    private int soundsLoaded = 0;
    private Game game;
    private Runnable frameRunnable;

    public MainView(Context context) {
        super(context);
        setBackgroundColor(Color.parseColor("#000000"));
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mainSoundPool = createNewSoundPool();
        } else {
            mainSoundPool = createOldSoundPool();
        }
        mainSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundsLoaded++;
                if (soundsLoaded == 2) {
                    // TODO startGame();
                }
            }
        });
        SoundEffects.background1 = loadSound(R.raw.atmoseerie02);
        SoundEffects.door = loadSound(R.raw.door);

    }

    private AndroidTexture loadTexture(int resourceId) {
        return new AndroidTexture(BitmapFactory.decodeResource(getResources(), resourceId));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected SoundPool createNewSoundPool() {
        // For some reason all tutorials recommend playing the sounds as "sonification" even though
        // only a small subset of the sounds in a game are sonified events. A "bonus picked up"
        // sound would be the sonification of the event of picking up a bonus, but in-game sounds
        // made by in-game objects (humming machines, footsteps or the "bang" of a gun) aren't
        // sonified events. I'm not sure if _SOUNDTRACK is more appropriate for them, nor what
        // effects that would have; also this would probably force me to use different sound pools.
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        return new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected SoundPool createOldSoundPool() {
        // For some reason all tutorials recommend playing the sounds as "music" even though it is
        // a sound effect and not music at all... Sadly none of them explains why; they explain
        // about different streams but don't even explain why there is no STREAM_GAME_SOUND_EFFECT
        // or something like that. Since we are talking about API level 20 and less, detailed
        // knowledge on this is likely becoming extinct.
        return new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    private AndroidSoundEffect loadSound(int resourceId) {
        int soundId = mainSoundPool.load(getContext(), resourceId, 0);
        return new AndroidSoundEffect(mainSoundPool, soundId);
    }

    public void startGame(InputStream saveStream) {

        // initialize the game
        if (SoundEffects.backgroundStreamId >= 0) {
            mainSoundPool.stop(SoundEffects.backgroundStreamId);
            SoundEffects.backgroundStreamId = -1;
        }
        SoundEffects.backgroundStreamId = SoundEffects.background1.loop();
        game = new Game(saveStream);

        // start the game loop
        if (handler != null && frameRunnable != null) {
            handler.removeCallbacks(frameRunnable);
        }
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

    public void saveGame(OutputStream saveStream) {
        game.save(saveStream);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchInputSource.handleEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        ((AndroidRenderer) Renderer.Holder.INSTANCE).setCanvas(canvas);
        if (game != null) {
            game.draw();
        }
        ((AndroidRenderer) Renderer.Holder.INSTANCE).setCanvas(null);
    }

}
