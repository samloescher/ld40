package src.ld40.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import src.ld40.MarsLander;

public class EndScreen extends AbstractScreen {

    @Override
    void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            MarsLander.instance.setScreen(new StartScreen());
            dispose();
        }
    }

    @Override
    void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0.2f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        titleFont.draw(batch, "Game Over", 150, 320);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();

    }
}
