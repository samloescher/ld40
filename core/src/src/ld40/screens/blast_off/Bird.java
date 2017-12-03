package src.ld40.screens.blast_off;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import src.ld40.AnimationUtils;
import src.ld40.MarsLander;

public class Bird implements Pool.Poolable {

    enum State{
        Gliding,
        Gliding_Animated,
        Flapping_Animated,
    }

    private State birdState;

    private Sprite bird;
    private Animation<TextureRegion> glidingAnimation;
    private Animation<TextureRegion> flappingAnimation;
    private float animationTimer;

    private Vector2 velocity;

    private float initialSpeed;
    private float slowestSpeed;

    private boolean flyingRight;

    public Bird() {
        Texture birdTexture = new Texture(Gdx.files.internal("sprites/bird/bird.png"));
        Texture birdFlappingTexture = new Texture(Gdx.files.internal("sprites/bird/bird_flapping.png"));
        Texture birdGlidingTexture = new Texture(Gdx.files.internal("sprites/bird/bird_gliding.png"));


        TextureRegion flappingArray [] = AnimationUtils.getAnimationTextureRegionArray(birdFlappingTexture, 2, 24, false);
        flappingAnimation = new Animation<TextureRegion>(.25f, flappingArray);

        TextureRegion glidingArray [] = AnimationUtils.getAnimationTextureRegionArray(birdGlidingTexture, 5, 24, false);
        glidingAnimation = new Animation<TextureRegion>(.25f, glidingArray);


        bird = new Sprite(birdTexture);
        velocity = new Vector2();
    }

    private void flap(){
        float currentSpeed = Math.abs(velocity.x);
        currentSpeed *= 1.002f;
        if (flyingRight){
            velocity.x = currentSpeed;
        } else {
            velocity.x = -currentSpeed;
        }
    }

    void glide(){
        float currentSpeed = Math.abs(velocity.x);
        currentSpeed *= 0.999f;
        if (flyingRight){
            velocity.x = currentSpeed;
        } else {
            velocity.x = -currentSpeed;
        }
    }

    public void update(float delta){
        animationTimer += delta;

        if (birdState == State.Gliding){
            glide();
            velocity.y = -4;
            if (Math.abs(velocity.x) < slowestSpeed){
                birdState = State.Flapping_Animated;
                animationTimer = 0;
            }

        } else if (birdState == State.Gliding_Animated){
            glide();
            velocity.y = -3;
            if (glidingAnimation.isAnimationFinished(animationTimer)){
                birdState = State.Gliding;
            }

        } else if (birdState == State.Flapping_Animated){
            flap();
            velocity.y = 4;
            if (Math.abs(velocity.x) > initialSpeed){
                birdState = State.Gliding_Animated;
                animationTimer = 0;
            }else if (flappingAnimation.isAnimationFinished(animationTimer)){
                animationTimer = 0;
            }
        }

        bird.setPosition(bird.getX() + delta * velocity.x, bird.getY() + delta * velocity.y);
    }

    public void draw(SpriteBatch batch) {
        switch (birdState){
            case Gliding:
                bird.draw(batch);
                break;
            case Gliding_Animated:
                TextureRegion gliding = glidingAnimation.getKeyFrame(animationTimer);
                if(!flyingRight && !gliding.isFlipX()){
                    gliding.flip(true, false);
                }
                batch.draw(gliding, bird.getX(), bird.getY());
                break;
            case Flapping_Animated:
                TextureRegion flapping = flappingAnimation.getKeyFrame(animationTimer);
                if(!flyingRight && !flapping.isFlipX()){
                    flapping.flip(true, false);
                }
                batch.draw(flapping, bird.getX(), bird.getY());
                break;
        }
    }

    private void flipSpriteToMatchVelocity(boolean flyingRight) {
        if (flyingRight)
            bird.setFlip(false, false);
        else
            bird.setFlip(true, false);
    }

    private void setBirdOnCorrectSideOfScreen(boolean flyingRight){
        if (flyingRight) {
            bird.setX(-bird.getWidth());
        }else{
            bird.setX(MarsLander.WIDTH);
        }
    }

    public void init(float height, Vector2 velocity) {
        birdState = State.Gliding;
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
