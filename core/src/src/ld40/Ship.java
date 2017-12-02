package src.ld40;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ship {

    private Sprite ship;

    public Ship() {
        Texture shipTexture = new Texture(Gdx.files.internal("sprites/ship.png"));
        ship = new Sprite(shipTexture);
        ship.setRotation(90);
        ship.setPosition(280, 100);
    }

    public void draw(SpriteBatch batch) {
        ship.draw(batch);
    }
}
