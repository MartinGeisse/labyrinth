package name.martingeisse.labyrinth;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

import name.martingeisse.labyrinth.system.Renderer;
import name.martingeisse.labyrinth.system.Texture;

public class AndroidRenderer implements Renderer {

    private Canvas canvas;
    private Rect rect;

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        this.rect = new Rect();
    }

    @Override
    public void beginFrame() {
        // passing null here does NOT work, contrary to the documentation.
        // see https://issuetracker.google.com/issues/36983429
        canvas.setMatrix(new Matrix());
    }

    @Override
    public void endFrame() {
    }

    @Override
    public void prepareDrawRoom(int screenX, int screenY) {
        canvas.translate(-screenX, -screenY);
    }

    @Override
    public int getWidth() {
        return canvas.getWidth();
    }

    @Override
    public int getHeight() {
        return canvas.getHeight();
    }

    @Override
    public void drawBlockmapCell(int x, int y, Texture texture) {
        x = x << 5;
        y = y << 5;
        rect.left = x;
        rect.right = x + 32;
        rect.top = y;
        rect.bottom = y + 32;
        Bitmap bitmap = ((AndroidTexture)texture).getBitmap();
        canvas.drawBitmap(bitmap, null, rect, null);
    }

    @Override
    public void drawPlayerSprite(int anchorX, int anchorY, Texture texture) {
        rect.left = anchorX;
        rect.right = anchorX + 32;
        rect.top = anchorY;
        rect.bottom = anchorY + 32;
        Bitmap bitmap = ((AndroidTexture)texture).getBitmap();
        canvas.drawBitmap(bitmap, null, rect, null);
    }

}
