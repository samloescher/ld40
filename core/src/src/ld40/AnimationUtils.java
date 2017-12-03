package src.ld40;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationUtils {
    public static TextureRegion[] getAnimationTextureRegionArray(Texture tex, int frames, int frameWidth, boolean reverse){
        TextureRegion tmp;
        TextureRegion[] array;
        int height = tex.getHeight();
        array = new TextureRegion[frames];
        for(int i = 0; i < frames; i++){
            tmp = new TextureRegion(tex, i * frameWidth, 0, frameWidth, height);
            tmp.flip(reverse, false);
            array[i] = tmp;
        }

        return array;
    }
}
