package name.martingeisse.labyrinth.system;

public final class SoundEffects {

    public static BackgroundSound background1;

    public static SoundEffect door;

    public static void checkSoundsLoaded() {
        check(background1, "background1");
        check(door, "door");
    }

    private static void check(Object sound, String name) {
        if (sound == null) {
            throw new IllegalStateException("missing sound: " + name);
        }
    }

}
