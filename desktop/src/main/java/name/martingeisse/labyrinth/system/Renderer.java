package name.martingeisse.labyrinth.system;

import name.martingeisse.labyrinth.system.lwjgl.LwjglRenderer;

/**
 *
 */
public interface Renderer {

	Renderer INSTANCE = new LwjglRenderer();

}
