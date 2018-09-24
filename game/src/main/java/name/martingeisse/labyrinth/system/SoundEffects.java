package name.martingeisse.labyrinth.system;

public final class SoundEffects {

    public static SoundEffect door;

    public static void checkSoundsLoaded() {
        check(door, "door");
    }

    private static void check(SoundEffect sound, String name) {
        if (sound == null) {
            throw new IllegalStateException("missing sound: " + name);
        }
    }

}
