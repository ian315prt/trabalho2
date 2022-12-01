package com.mygdx.trabalho2.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.trabalho2.MainGame;
import com.mygdx.trabalho2.entities.Asteroid;
import com.mygdx.trabalho2.entities.Bullets;
import com.mygdx.trabalho2.tools.CollisionReact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameScreen implements Screen {

    public static final Float SPEED = 500F;
    public static final Float SHIP_ANIMATION_SPEED = 0.5f;
    public static final int SHIP_WIDTH_PIXEL = 17;
    public static final int SHIP_HEIGHT_PIXEL = 32;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 3;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 3;
    public static final float MIN_ASTEROID_SPWAN_TIME = 0.3f;
    public static final float MAX_ASTEROID_SPWAN_TIME = 0.6f;

    int score;
    int roll;
    float x;
    float y;
    float stateTime;
    float asteroidSpawnTimer;
    float health = 1;

    Random random;
    MainGame mainGame;
    List<Bullets> bulletsList;
    List<Asteroid> asteroidList;
    Animation[] rolls;
    BitmapFont scoreFont;
    CollisionReact playerRect;
    Texture blank;

    public MainGameScreen(MainGame mainGame){
        this.mainGame = mainGame;

        score = 0;
        y = 15;
        x = MainGame.WIDTH / 2 - SHIP_WIDTH / 2;
        roll = 2;
        rolls = new Animation[5];
        bulletsList = new ArrayList<>();
        asteroidList = new ArrayList<>();
        scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        playerRect = new CollisionReact(0, 0, SHIP_WIDTH, SHIP_HEIGHT);
        blank = new Texture("blank.png");

        random = new Random();
        asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPWAN_TIME - MIN_ASTEROID_SPWAN_TIME) + MIN_ASTEROID_SPWAN_TIME;

        TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL);

        rolls[roll] = new Animation<>(SHIP_ANIMATION_SPEED, rollSpriteSheet[0]);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        //Variaveis
        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "" + score);
        List<Bullets> bulletsListToRemove = new ArrayList<>();
        List<Asteroid> asteroidListToRemove = new ArrayList<>();
        stateTime += delta;
        asteroidSpawnTimer -= delta;
        //
        //bullets
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            bulletsList.add(new Bullets(x + 4));
            bulletsList.add(new Bullets(x + SHIP_WIDTH - 4));
        }

        for (Bullets bullet : bulletsList){
            bullet.update(delta);

            if (Boolean.TRUE.equals(bullet.remove)) bulletsListToRemove.add(bullet);
        }
        //
        //Asteroides
        if (asteroidSpawnTimer <= 0){
            asteroidSpawnTimer = random.nextFloat() * (MAX_ASTEROID_SPWAN_TIME - MIN_ASTEROID_SPWAN_TIME) + MIN_ASTEROID_SPWAN_TIME;
            asteroidList.add(new Asteroid(random.nextInt(Gdx.graphics.getWidth()) + Asteroid.WIDTH));
        }
        for (Asteroid asteroid : asteroidList){
            asteroid.update(delta);

            if (Boolean.TRUE.equals(asteroid.remove)) asteroidListToRemove.add(asteroid);
        }
        //
        playerRect.move(x, y);
        //verificar apos updates se tem colisao
        for (Bullets bullets: bulletsList){
            for (Asteroid asteroid: asteroidList){
                if (bullets.getCollisionReact().collidesWith(asteroid.getCollisionReact())){// bullet colidindo com asteroid
                    bulletsListToRemove.add(bullets);
                    asteroidListToRemove.add(asteroid);
                    score += 100;
                }
            }
        }
        bulletsList.removeAll(bulletsListToRemove);//realizando o remove apos os updates

        //colisao com o player
        for (Asteroid asteroid : asteroidList) {
            if (asteroid.getCollisionReact().collidesWith(playerRect)) {
                asteroidListToRemove.add(asteroid);
                health -= 0.5;

                //dps de ficar com zero de vida, tela de game over
                if (health <= 0) {
                    this.dispose();

                    mainGame.setScreen(new GameOverScreen(mainGame, score));
                    return;
                }
            }
        }
        asteroidList.removeAll(asteroidListToRemove);
        //
        ScreenUtils.clear(0,0,0,1);
        //Movimento da nave
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += SPEED * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= SPEED * Gdx.graphics.getDeltaTime();
        }
        //
        mainGame.batch.begin();
        scoreFont.draw(mainGame.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - scoreLayout.height - 10);
        //Renderizacao de Imagens
        for (Bullets bullet : bulletsList) bullet.render(mainGame.batch);
        for (Asteroid asteroid : asteroidList) asteroid.render(mainGame.batch);

        mainGame.batch.draw(blank, 0, 0, Gdx.graphics.getWidth() * health,5);
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
