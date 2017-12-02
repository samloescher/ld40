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

    private float initialSpeed;
    private float slowestSpeed;

    private boolean flyingRight;
    private boolean gliding = true;

    public Bird() {
        Texture birdTexture = new Texture(Gdx.files.internal("sprites/bird/bird.png"));
        bird = new Sprite(birdTexture);
        velocity = new Vector2();
    }

    void increaseSpeed(){
        float currentSpeed = Math.abs(velocity.x);
        currentSpeed *= 1.002f;
        if(currentSpeed >= initialSpeed){
            currentSpeed = initialSpeed;
            gliding = true;
        }
        if(flyingRight){
            velocity.x = currentSpeed;
        }else{
            velocity.x = -currentSpeed;
        }
    }

    void decreaseSpeed(){
        float currentSpeed = Math.abs(velocity.x);
        currentSpeed *= 0.999f;
        if (currentSpeed < slowestSpeed){
            currentSpeed = slowestSpeed;
            gliding = false;
        }
        if(flyingRight){
            velocity.x = currentSpeed;
        }else{
            velocity.x = -currentSpeed;
        }
    }

    public void update(float delta){
        if(gliding){
            decreaseSpeed();
            bird.setY(bird.getY() - 4f * delta);
        }else{
            increaseSpeed();
            bird.setY(bird.getY() + 4f * delta);
        }
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
        initialSpeed = Math.abs(velocity.x);
        slowestSpeed = initialSpeed * 0.8f;
        flyingRight = velocity.x > 0.0f;
        flipSpriteToMatchVelocity(flyingRight);
        setBirdOnCorrectSideOfScreen(flyingRight);
        bird.setY(height);
    }

    @Override
    public void reset() {

    }
}
