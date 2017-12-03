package src.ld40.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import src.ld40.MarsLander;

public abstract class AbstractScreen implements Screen {

    public static SpriteBatch batch = MarsLander.instance.batch;
    public static SpriteBatch ui = MarsLander.instance.ui;
    public static ShapeRenderer shapeRenderer = MarsLander.instance.shapeRenderer;
    public static BitmapFont titleFont = MarsLander.instance.titleFont;
    public static BitmapFont largeFont = MarsLander.instance.largeFont;
    public static BitmapFont normalFont = MarsLander.instance.normalFont;

    public static OrthographicCamera camera = new OrthographicCamera(800, 480);

    @Override
    public void show() {
        camera.setToOrtho(false);
    }

    public abstract void update(float delta);
    public abstract void draw();

    @Override
    public final void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}