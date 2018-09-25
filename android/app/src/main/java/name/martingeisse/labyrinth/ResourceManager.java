package name.martingeisse.labyrinth;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import name.martingeisse.labyrinth.game.BackgroundSoundSelector;
import name.martingeisse.labyrinth.game.Block;
import name.martingeisse.labyrinth.game.Direction;
import name.martingeisse.labyrinth.game.PlayerSprite;
import name.martingeisse.labyrinth.system.SoundEffects;
import name.martingeisse.labyrinth.system.Texture;

/**
 * Manages textures and sounds as well as the objects needed to use them. These two concepts are
 * not separated since android classes don't separate them to begin with (an example would be
 * {@link SoundPool} which both stores and plays sound effects).
 */
public class ResourceManager {

    private Context context;
    private SoundPool mainSoundPool;

    public ResourceManager(Context context) {
        this.context = context;
    }

    public void initialize() {

        // initialize texture resources
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

        // initialize background sound resources
        BackgroundSoundPlayer.initialize();
        BackgroundSoundSelector.BACKGROUND1.setSound(loadBackgroundSound(R.raw.atmoseerie02_ogg));
        BackgroundSoundSelector.BACKGROUND2.setSound(loadBackgroundSound(R.raw.atmoseerie04_ogg));
        // TODO should crash due to background 3 missing

        // initialize sound effect resources
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mainSoundPool = createNewSoundPool();
        } else {
            mainSoundPool = createOldSoundPool();
        }
        SoundEffects.door = loadSoundEffect(R.raw.door);

    }

    private AndroidTexture loadTexture(int resourceId) {
        return new AndroidTexture(BitmapFactory.decodeResource(context.getResources(), resourceId));
    }

    private AndroidBackgroundSound loadBackgroundSound(int resourceId) {
        return new AndroidBackgroundSound(context.getResources(), resourceId);
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

    private AndroidSoundEffect loadSoundEffect(int resourceId) {
        int soundId = mainSoundPool.load(context, resourceId, 0);
        return new AndroidSoundEffect(mainSoundPool, soundId);
    }

    public void destroy() {

        // sound effects
        if (mainSoundPool != null) {
            mainSoundPool.release();
            mainSoundPool = null;
        }

        // background sound
        BackgroundSoundPlayer.destroy();

        // player textures
        if (PlayerSprite.getTextures() != null) {
            for (Texture[] textures : PlayerSprite.getTextures()) {
                if (textures != null) {
                    for (Texture texture : textures) {
                        ((AndroidTexture) texture).getBitmap().recycle();
                    }
                }
            }
            PlayerSprite.setTextures(null);
        }

        // block textures
        for (Block block : Block.TABLE) {
            ((AndroidTexture) block.getTexture()).getBitmap().recycle();
            block.setTexture(null);
        }

    }

}
