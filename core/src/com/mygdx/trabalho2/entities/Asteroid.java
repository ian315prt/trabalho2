package com.mygdx.trabalho2.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.trabalho2.tools.CollisionReact;

public class Asteroid {
    public static final int SPEED = 250;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 16;
    private static Texture texture;

    float x;
    float y;
    public Boolean remove = false;

    CollisionReact collisionReact;

    public Asteroid(float x) {
        this.x = x;
        this.y = Gdx.graphics.getHeight();
        this.collisionReact = new CollisionReact(x, y, WIDTH, HEIGHT);

        if (texture == null){
            texture = new Texture("asteroid.png");
        }
    }

    public void update(float deltaTime){
        y -= SPEED * deltaTime;

        if (y < -HEIGHT){
            remove = true;
        }

        collisionReact.move(x, y);
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, x ,y);
    }

    public CollisionReact getCollisionReact() {
        return collisionReact;
    }
}
