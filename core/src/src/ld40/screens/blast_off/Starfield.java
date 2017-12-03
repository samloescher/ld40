package src.ld40.screens.blast_off;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Starfield {

    private ArrayList<ParticleEffect> starFields;
    private float STARFIELD_HEIGHT = 100f;

    public Starfield(float minHeight, float maxHeight) {
        int totalStarfields = MathUtils.ceil((maxHeight - minHeight) / STARFIELD_HEIGHT);
        starFields = new ArrayList<ParticleEffect>(totalStarfields);

        for (int i = 0; i < totalStarfields; i++) {
            ParticleEffect particleEffect = new ParticleEffect();
            particleEffect.load(Gdx.files.internal("starfield.particle"), Gdx.files.internal(""));
            particleEffect.setPosition(0, minHeight + STARFIELD_HEIGHT * i);
            starFields.add(particleEffect);
        }

    }

    public void update(float delta) {
        for (ParticleEffect particleEffect : starFields) {
            particleEffect.update(delta);
        }
    }

    public void draw(SpriteBatch batch) {
        for (ParticleEffect particleEffect : starFields) {
            particleEffect.draw(batch);
        }
    }

    public void dispose(){
        for (ParticleEffect particleEffect : starFields) {
            particleEffect.dispose();
        }
    }
}
