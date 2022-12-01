package com.mygdx.trabalho2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.trabalho2.MainGame;

public class GameOverScreen implements Screen {

    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 100;

    int score;

    MainGame mainGame;
    Texture gameOverBanner;
    BitmapFont scoreFont;

    public GameOverScreen(MainGame mainGame, int score){
        this.mainGame = mainGame;
        this.score = score;

        gameOverBanner = new Texture("game_over.png");
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: \n" + score, Color.WHITE, 0, Align.left,false);
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Try Again");

        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        float tryAgainX = Gdx.graphics.getWidth() / 2 - mainMenuLayout.width / 2;
        float tryAgainY = Gdx.graphics.getHeight() / 2 - mainMenuLayout.height / 2;

        ScreenUtils.clear(0,0,0,1);

        mainGame.batch.begin();

        mainGame.batch.draw(gameOverBanner, Gdx.graphics.getWidth() / 2 - BANNER_WIDTH / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - 15, BANNER_WIDTH, BANNER_HEIGHT);
        scoreFont.draw(mainGame.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - BANNER_HEIGHT - 15 * 2);

        if (touchX >= tryAgainX && touchX < tryAgainX + mainMenuLayout.width && touchY >= tryAgainY - mainMenuLayout.height && touchY < tryAgainY)
            mainMenuLayout.setText(scoreFont, "Try Again", Color.YELLOW, 0, Align.left, false);

        if (Gdx.input.isTouched()) {
            if (touchX > tryAgainX && touchX < tryAgainX + mainMenuLayout.width && touchY > tryAgainY - mainMenuLayout.height && touchY < tryAgainY){
                this.dispose();
                mainGame.batch.end();
                mainGame.setScreen(new MainGameScreen(mainGame));
                return;
            }
        }

        scoreFont.draw(mainGame.batch, mainMenuLayout, tryAgainX, tryAgainY);
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
