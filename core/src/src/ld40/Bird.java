package src.ld40;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Bird implements Pool.Poolable {

    private Sprite bird;
    private Vector2 velocity;

    public Bird() {
        Texture birdTexture = new Texture(Gdx.files.internal("sprites/bird/bird.png"));
        bird = new Sprite(birdTexture);
        velocity = new Vector2();
    }

    public void update(float delta){
        bird.setPosition(bird.getX() + delta * velocity.x, bird.getY() + delta * velocity.y);
    }

    public void draw(SpriteBatch batch) {
        bird.draw(batch);
    }


    void flipSpriteToMatchVelocity(boolean flyingRight) {
        if (flyingRight)
            bird.setFlip(false, false);
        else
            bird.setFlip(true, false);
    }

    void setBirdOnCorrectSideOfScreen(boolean flyingRight){
        if (flyingRight) {
            bird.setX(-bird.getWidth());
        }else{
            bird.setX(MarsLander.WIDTH);
        }
    }

    public void init(float height, Vector2 velocity) {
        this.velocity = velocity;
        boolean flyingRight = velocity.x > 0.0f;
        flipSpriteToMatchVelocity(flyingRight);
        setBirdOnCorrectSideOfScreen(flyingRight);
        bird.setY(height);
    }

    @Override
    public void reset() {

    }
}
