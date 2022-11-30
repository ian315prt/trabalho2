package com.mygdx.trabalho2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.trabalho2.MainGame;
import com.mygdx.trabalho2.entities.Bullets;

import java.util.ArrayList;
import java.util.List;

public class MainGameScreen implements Screen {

    public static final Float SPEED = 144F;
    public static final Float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;

    Animation[] rolls;

    int roll;
    float x;
    float y;
    float stateTime;
    MainGame mainGame;

    List<Bullets> bulletsList;



    public MainGameScreen(MainGame mainGame){
        this.mainGame = mainGame;

        y = 15;
        x = MainGame.WIDTH / 2 - SHIP_WIDTH / 2;
        roll = 2;
        rolls = new Animation[5];
        bulletsList = new ArrayList<>();

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);

        rolls[roll] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stateTime += delta;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            bulletsList.add(new Bullets(x + 4));
            bulletsList.add(new Bullets(x + SHIP_WIDTH - 4));
        }

        List<Bullets> bulletsListToRemove = new ArrayList<>();
        for (Bullets bullets : bulletsList){
            bullets.update(delta);

            if (Boolean.TRUE.equals(bullets.remove)) bulletsListToRemove.add(bullets);
        }
        bulletsList.removeAll(bulletsListToRemove);

        ScreenUtils.clear(0,0,0,1);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= SPEED * Gdx.graphics.getDeltaTime();
        }

        mainGame.batch.begin();
        for (Bullets bullets : bulletsList) bullets.render(mainGame.batch);
        mainGame.batch.draw((TextureRegion) rolls[roll].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);
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
        mainGame.batch.dispose();
    }
}
