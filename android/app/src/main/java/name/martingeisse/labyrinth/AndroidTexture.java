package name.martingeisse.labyrinth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import name.martingeisse.labyrinth.system.Texture;

public class AndroidTexture implements Texture {

    private final Bitmap bitmap;

    public AndroidTexture(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }


}
