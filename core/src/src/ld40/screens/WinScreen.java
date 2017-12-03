package src.ld40.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import src.ld40.MarsLander;

public class WinScreen extends AbstractScreen {

    private int livesSaved;
    //private Sound sound;

    public WinScreen(int people) {
        livesSaved = people;
        //sound = Gdx.audio.newSound(Gdx.files.internal("sound/success.wav"));
        //sound.play();
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
        ui.begin();
        if (livesSaved == 0) {
            Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            titleFont.draw(ui, "Mission success!!\n   ... or was it?", MarsLander.WIDTH / 2 - 250, MarsLander.HEIGHT - 100);
        } else if (livesSaved < 5) {
            Gdx.gl.glClearColor(0.5f, 0.9f, 0.5f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            titleFont.draw(ui, "  Mission success!!\nYour crew made it!", MarsLander.WIDTH / 2 - 280, MarsLander.HEIGHT - 100);
        } else {
            Gdx.gl.glClearColor(0.1f, 0.9f, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            titleFont.draw(ui, "Mission success!!\n Super job saving \n            " + livesSaved + " lives!", MarsLander.WIDTH / 2 - 280, MarsLander.HEIGHT - 50);
        }

        ui.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        //sound.dispose();
    }
}
