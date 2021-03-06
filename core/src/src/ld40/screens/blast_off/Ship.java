package src.ld40.screens.blast_off;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ship {

    private Sprite ship;
    private Exhaust exhaust;
    private Sound lowFuelSound;
    private long lowFuelSoundId;

    public float thrust = 0;
    public float passengerWeight = 0;
    public float fuel = 100;

    private float SHIP_WEIGHT = 100000;
    private float GRAVITY = 0.00008f;
    private float MIN_THRUST = 0f;
    private float MAX_THRUST = 100f;
    private float THRUST_SCALE_FACTOR = 0.11f;

    private Vector2 velocity;

    private float GROUND_Y = 100;

    private boolean launched = false;

    public Ship() {
        Texture shipTexture = new Texture(Gdx.files.internal("sprites/ship/ship.png"));
        shipTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ship = new Sprite(shipTexture);
        ship.setRotation(90);
        ship.setPosition(280, GROUND_Y);
        thrust = GRAVITY;
        velocity = new Vector2();
        exhaust = new Exhaust();
        lowFuelSound = Gdx.audio.newSound(Gdx.files.internal("sound/low_fuel.wav"));
        lowFuelSoundId = lowFuelSound.play(0f);
        lowFuelSound.setLooping(lowFuelSoundId, true  );
    }

    public void launch() {
        launched = true;
    }

    public boolean isCollidingWithGround(){
        return ship.getY() < GROUND_Y;
    }

    public float getHeight() {
        return ship.getY();
    }

    public void decreaseThrust() {
        thrust -= 0.5;
        thrust = MathUtils.clamp(thrust, MIN_THRUST, MAX_THRUST);
    }

    public void increaseThrust() {
        if (fuel > 0) {
            thrust += 1;
            fuel -= 0.05f;
            if(fuel < 20f){
                lowFuelSound.setVolume(lowFuelSoundId, 1f);
            }
            if(fuel == 0){
                lowFuelSound.setVolume(lowFuelSoundId, 0f);
            }

            thrust = MathUtils.clamp(thrust, MIN_THRUST, MAX_THRUST);
            if (fuel < 0) {
                fuel = 0f;
            }
        }
    }

    private float gravity() {
        return GRAVITY * (SHIP_WEIGHT + 10 * passengerWeight);
    }

    public void steerLeft() {
        float rotation = ship.getRotation();
        float newRotation = rotation + 0.5f;
        ship.setRotation(newRotation);
        exhaust.setRotation(newRotation);
    }

    public void steerRight() {
        float rotation = ship.getRotation();
        float newRotation = rotation - 0.5f;
        ship.setRotation(newRotation);
        exhaust.setRotation(newRotation);
    }

    private void setVelocityUnscaledRotation() {
        float rotation = MathUtils.degreesToRadians * ship.getRotation();
        velocity.x = MathUtils.cos(rotation);
        velocity.y = MathUtils.sin(rotation);
    }

    public void update(float delta) {
        if (!launched) {
            return;
        }
        decreaseThrust();

        if (ship.getY() > GROUND_Y) {
            setVelocityUnscaledRotation();
            velocity.scl(thrust * THRUST_SCALE_FACTOR);
            velocity.y -= gravity();

        } else {
            if (thrust * THRUST_SCALE_FACTOR > gravity()) {
                velocity.x = 0;
                velocity.y = thrust * THRUST_SCALE_FACTOR;
                velocity.y -= gravity();
            }
        }

        ship.setPosition(ship.getX() + velocity.x, ship.getY() + velocity.y);
        exhaust.moveTo(ship.getX() + ship.getWidth() / 2, ship.getY() + 5);
        exhaust.emit(thrust);
        exhaust.update(delta);
    }

    public void draw(SpriteBatch batch) {
        ship.draw(batch);
        exhaust.draw(batch);
    }

    public void dispose(){
        exhaust.dispose();
        ship.getTexture().dispose();
        lowFuelSound.dispose();
    }
}
