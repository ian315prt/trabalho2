package com.mygdx.trabalho2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.trabalho2.MainGame;

public class MainMenuScreen implements Screen {

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 150;
    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_Y = 200;
    private static final int EXIT_BUTTON_Y = 100;

    MainGame mainGame;
    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;

    public  MainMenuScreen(MainGame mainGame){
        this.mainGame = mainGame;
        playButtonActive = new Texture("play_button_active.png");
        playButtonInactive = new Texture("play_button_inactive.png");
        exitButtonActive = new Texture("exit_button_active.png");
        exitButtonInactive = new Texture("exit_button_inactive.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        int exitX = MainGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
        int playX = MainGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        ScreenUtils.clear(0,0,0,1);
        mainGame.batch.begin();

        if (Gdx.input.getX() < playX + PLAY_BUTTON_WIDTH && Gdx.input.getX() > playX && MainGame.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && MainGame.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y) {
            mainGame.batch.draw(playButtonActive, playX, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                mainGame.setScreen(new MainGameScreen(mainGame));
            }
        } else {
            mainGame.batch.draw(playButtonInactive, playX, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        if (Gdx.input.getX() < exitX + EXIT_BUTTON_WIDTH && Gdx.input.getX() > exitX && MainGame.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && MainGame.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y) {
            mainGame.batch.draw(exitButtonActive, exitX, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            mainGame.batch.draw(exitButtonInactive, exitX, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
        mainGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
