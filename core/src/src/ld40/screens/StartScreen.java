package src.ld40.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import src.ld40.MarsLander;

public class StartScreen extends AbstractScreen {

    private Texture backgroundImage;

    public StartScreen() {
        backgroundImage = new Texture(Gdx.files.internal("start_screen.png"));
    }

    @Override
    void update(float delta) {
        if (Gdx.input.isTouched()) {
            MarsLander.instance.setScreen(new GameScreen());
            dispose();
        }
    }

    @Override
    void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0);
        titleFont.draw(batch, "Mars Lander", 110, 420);
        batch.end();
    }

    @Override
    public void dispose() {
        backgroundImage.dispose();
    }
}