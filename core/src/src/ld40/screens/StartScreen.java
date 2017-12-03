package src.ld40.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import src.ld40.MarsLander;
import src.ld40.screens.blast_off.GameScreen;

public class StartScreen extends AbstractScreen {

    private Texture backgroundImage;
    private Music music;

    int titleProgress = 0;

    public StartScreen() {
        backgroundImage = new Texture(Gdx.files.internal("main_menu/start_screen.png"));
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/title.wav"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (titleProgress == 5) {
                MarsLander.instance.setScreen(new GameScreen());
                dispose();
            } else {
                titleProgress++;
            }
        }
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ui.begin();
        ui.draw(backgroundImage, 0, 0);
        if (titleProgress == 0) {
            titleFont.draw(ui, "Escape earth", 120, 400);
        } else if (titleProgress == 1) {
            String instructions = "...The year is 2018\n" +
                    "You are set to embark on a\n" +
                    "colonisation mission to mars\n";
            largeFont.draw(ui, instructions, 60, 400);

        } else if (titleProgress == 2) {
            String instructions = "Humanity is eagerly watching\n" +
                    "as the launch draws closer...\n" +
                    "             today is the day\n";
            largeFont.draw(ui, instructions, 60, 400);

        } else if (titleProgress == 3) {
            String instructions = "!!!";
            largeFont.draw(ui, instructions, 320, 300);

        }else if (titleProgress == 4) {
            String instructions = "NUCLEAR WAR!!";
            largeFont.draw(ui, instructions, 220, 300);

        }else if (titleProgress == 5) {
            String instructions = "People clamber towards the ship\n" +
                    "But is there room for them all?!\n" +
                    "      How many can you save?";
            largeFont.draw(ui, instructions, 60, 400);

        }
        ui.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundImage.dispose();
        music.dispose();
    }
}
