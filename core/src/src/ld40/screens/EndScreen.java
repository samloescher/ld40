package src.ld40.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import src.ld40.MarsLander;

public class EndScreen extends AbstractScreen {

    private Sound sound;

    public EndScreen() {
        sound = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.wav"));
        sound.play();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            MarsLander.instance.setScreen(new StartScreen());
            dispose();
        }
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0.4f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ui.begin();
        titleFont.draw(ui, "Mission Disaster!", 50, MarsLander.HEIGHT / 2 + 50);
        ui.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
