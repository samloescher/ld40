package src.ld40.screens.blast_off;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Exhaust {

    private ParticleEffect particleEffect;
    private float MIN_DISPERSION_ANGLE_DEGREES = 20f;
    private float MAX_DISPERSION_ANGLE_DEGREES = 30f;

    private float currentScale;

    private Sound sound;
    private long soundId;

    public Exhaust() {
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("exhaust.particle"), Gdx.files.internal(""));
        sound = Gdx.audio.newSound(Gdx.files.internal("sound/exhaust.wav"));
        soundId = sound.play();
        sound.setLooping(soundId, true);
        sound.setVolume(soundId, 0f);
        currentScale = 1f;
    }

    public void emit(float strength) {
        if (strength == 0) {
            return;
        }
        float scale = (1f / currentScale) * strength / 100f;
        particleEffect.scaleEffect(scale);
        currentScale *= scale;
        particleEffect.start();
        sound.setVolume(soundId,  currentScale * 0.5f);
    }

    public void moveTo(float x, float y) {
        particleEffect.setPosition(x, y);
    }

    public void setRotation(float rotation) {
        Array<ParticleEmitter> emitters = particleEffect.getEmitters();
        for (ParticleEmitter emitter : emitters) {
            ParticleEmitter.ScaledNumericValue v = emitter.getAngle();
            v.setHigh(rotation - MIN_DISPERSION_ANGLE_DEGREES, rotation + MIN_DISPERSION_ANGLE_DEGREES);
            v.setLow(rotation - MAX_DISPERSION_ANGLE_DEGREES, rotation + MAX_DISPERSION_ANGLE_DEGREES);
        }
    }

    public void update(float delta) {
        particleEffect.update(delta);
    }

    public void draw(SpriteBatch batch) {
        particleEffect.draw(batch);
    }

    public void dispose(){
        particleEffect.dispose();
        sound.dispose();
    }

}
