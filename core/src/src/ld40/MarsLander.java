package src.ld40;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import src.ld40.screens.StartScreen;

public class MarsLander extends Game {

    public static MarsLander instance;

    public static final int WIDTH  = 640;
    public static final int HEIGHT = 480;

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont titleFont;
    public BitmapFont largeFont;
    public BitmapFont normalFont;

    
    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        titleFont = new BitmapFont(Gdx.files.internal("fonts/title.fnt"), false);
        largeFont = new BitmapFont(Gdx.files.internal("fonts/large.fnt"), false);
        normalFont = new BitmapFont(Gdx.files.internal("fonts/normal.fnt"), false);

        instance = this;

        setScreen(new StartScreen());
    }
    
    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        titleFont.dispose();
        largeFont.dispose();
        normalFont.dispose();
    }
}
