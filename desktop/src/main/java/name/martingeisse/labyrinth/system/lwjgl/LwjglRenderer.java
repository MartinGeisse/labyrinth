package name.martingeisse.labyrinth.system.lwjgl;

import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.Texture;
import name.martingeisse.labyrinth.system.lwjgl.resource.Resources;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Util;

/**
 *
 */
public class LwjglRenderer implements Renderer {

	@Override
	public void beginFrame() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void endFrame() {
		Util.checkGLError();
	}

	@Override
	public void prepareDrawRoom(int screenX, int screenY) {
		GL11.glTranslatef(-screenX, -screenY, 0.0f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255);
	}

	@Override
	public int getWidth() {
		return Display.getWidth();
	}

	@Override
	public int getHeight() {
		return Display.getHeight();
	}

	@Override
	public void drawBlockmapCell(int x, int y, Texture texture) {
		((LwjglTexture)texture).glBindTexture();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2i(x << 5, y << 5);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2i((x + 1) << 5, y << 5);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2i((x + 1) << 5, (y + 1) << 5);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2i(x << 5, (y + 1) << 5);
		GL11.glEnd();
	}

	@Override
	public void drawPlayerSprite(int anchorX, int anchorY, Texture texture) {
		((LwjglTexture)texture).glBindTexture();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2i(anchorX, anchorY);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2i(anchorX + 32, anchorY);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2i(anchorX + 32, anchorY + 32);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2i(anchorX, anchorY + 32);
		GL11.glEnd();
	}

}
