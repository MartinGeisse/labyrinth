package name.martingeisse.labyrinth.system;

/**
 *
 */
public interface Renderer {

	void beginFrame();
	void endFrame();
	void prepareDrawRoom(int screenX, int screenY);
	int getWidth();
	int getHeight();
	void drawBlockmapCell(int x, int y, Texture texture);
	void drawPlayerSprite(int anchorX, int anchorY, Texture texture);

	class Holder {
		public static Renderer INSTANCE;
	}

}
