package name.martingeisse.labyrinth.system;

public final class SoundEffects {

    public static SoundEffect background1;
    public static int backgroundStreamId = -1;

    public static SoundEffect door;

    public static void checkSoundsLoaded() {
        check(background1, "background1");
        check(door, "door");
    }

    private static void check(SoundEffect effect, String name) {
        if (effect == null) {
            throw new IllegalStateException("missing sound effect: " + name);
        }
    }

}
