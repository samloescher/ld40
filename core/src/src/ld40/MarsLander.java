package src.ld40;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import src.ld40.screens.StartScreen;

public class MarsLander extends Game {

    public static MarsLander instance;

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    
    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        instance = this;

        setScreen(new StartScreen());
    }

    @Override
    public void render () {
        super.render();
    }
    
    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
