package src.ld40.screens.blast_off;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import net.dermetfan.gdx.physics.box2d.PositionController;
import src.ld40.MarsLander;
import src.ld40.screens.AbstractScreen;
import src.ld40.screens.EndScreen;

import java.util.ArrayList;

public class GameScreen extends AbstractScreen {

    private Texture backgroundImage;

    private Ship ship;
    private Pool<Bird> birds;
    private ArrayList<Bird> activeBirds;

    private boolean shipLaunched = false;
    private int peopleOnShip = 0;
    private float timeSinceLastPersonGotOnShip = 0;
    private float timeBetweenPeopleGettingOnShip = 1;

    private float timeSinceLastBirdSpawned = 0;
    private float timeBetweenBirdSpawns = 1;

    private float CAMERA_BUFFER = 100f;
    private float CAMERA_LOWER_THRESHOLD = MarsLander.HEIGHT/2f - CAMERA_BUFFER/2f;
    private float CAMERA_UPPER_THRESHOLD = MarsLander.HEIGHT/2f + CAMERA_BUFFER/2f;
    private boolean cameraFollowingShip = false;

    public GameScreen() {
        backgroundImage = new Texture(Gdx.files.internal("launch_stage/background.png"));
        ship = new Ship();
        birds = new Pool<Bird>() {
            @Override
            protected Bird newObject() {
                return new Bird();
            }
        };
        activeBirds = new ArrayList<Bird>();
    }

    void addPerson() {
        peopleOnShip += 1;
        ship.passengerWeight += MathUtils.random(50, 110);
    }

    void addBird() {
        Bird b = birds.obtain();
        float birdSpeed = MathUtils.random(30.0f, 50.0f);
        b.init(MathUtils.random(300, 450), new Vector2(MathUtils.randomSign() * birdSpeed, 0));
        activeBirds.add(b);
    }

    void updateCamera(float height){
        boolean updateCamera = false;
        float cameraHeight = camera.position.y + camera.viewportHeight/2;
        if (cameraHeight > CAMERA_UPPER_THRESHOLD + height){
            float dy = CAMERA_UPPER_THRESHOLD + height - camera.viewportHeight/2;
            camera.position.y = dy;
            updateCamera = true;
        } else if(cameraHeight < CAMERA_LOWER_THRESHOLD + height){
            float dy = CAMERA_LOWER_THRESHOLD + height - camera.viewportHeight/2;
            camera.position.y = dy;
            updateCamera = true;
        }

        if(updateCamera) {
            camera.update();
        }
    }

    @Override
    public void update(float delta) {
        timeSinceLastPersonGotOnShip += delta;
        timeSinceLastBirdSpawned += delta;

        if (timeSinceLastPersonGotOnShip > timeBetweenPeopleGettingOnShip && !shipLaunched) {
            addPerson();
            timeSinceLastPersonGotOnShip = 0;
            timeBetweenPeopleGettingOnShip = MathUtils.random(0.01f, 0.5f);
        }

        if (timeSinceLastBirdSpawned > timeBetweenBirdSpawns) {
            addBird();
            timeSinceLastBirdSpawned = 0;
            timeBetweenBirdSpawns = MathUtils.random(5f, 7f);
        }

        ship.update(delta);

        for (Bird b : activeBirds) {
            b.update(delta);
        }

        if (shipLaunched){
            if(ship.isCollidingWithGround()){
                gameOver();
            }
        }

        if (!shipLaunched) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                shipLaunched = true;
                ship.launch();
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                ship.increaseThrust();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                ship.steerLeft();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                ship.steerRight();
            }


            if(ship.getHeight() > CAMERA_LOWER_THRESHOLD) {
                cameraFollowingShip = true;
            }
        }

        if(cameraFollowingShip){
            updateCamera(ship.getHeight());
        }
    }

    private void gameOver() {
        MarsLander.instance.setScreen(new EndScreen());
        dispose();
    }

    @Override
    public void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0);

        for (Bird b : activeBirds) {
            b.draw(batch);
        }
        ship.draw(batch);

        batch.end();

        ui.begin();
        float SCREEN_BUFFER = 20;
        float ITEM_SIZE = 30;
        float ITEM_BUFFER = 10;
        String s = String.format("People on the ship : %5d", peopleOnShip);
        normalFont.draw(ui, s,
                SCREEN_BUFFER,
                MarsLander.HEIGHT - SCREEN_BUFFER);

        s = String.format("Passenger Weight : %5d KG", (int)ship.passengerWeight);
        normalFont.draw(ui, s,
                SCREEN_BUFFER,
                MarsLander.HEIGHT - SCREEN_BUFFER - ITEM_SIZE);

        s = String.format("Thrust : %3d %%", (int)ship.thrust);
        normalFont.draw(ui, s,
                SCREEN_BUFFER,
                MarsLander.HEIGHT - SCREEN_BUFFER - 2*ITEM_SIZE - ITEM_BUFFER);

        s = String.format("Fuel : %3d %%", (int)ship.fuel);
        normalFont.draw(ui, s,
                SCREEN_BUFFER,
                MarsLander.HEIGHT - SCREEN_BUFFER - 3*ITEM_SIZE - ITEM_BUFFER);
        if (!shipLaunched) {
            largeFont.draw(ui, "Press space to launch !", 130, 80);
        }

        ui.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundImage.dispose();
    }
}
