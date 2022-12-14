package com.mygdx.trabalho2.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.trabalho2.tools.CollisionReact;

public class Bullets {

    public static final int SPEED = 500;
    public static final int DEFAULT_Y = 40;
    public static final int WIDTH = 3;
    public static final int HEIGHT = 12;
    private static Texture texture;

    float x;
    float y;
    CollisionReact collisionReact;
    public Boolean remove = false;

    public Bullets (float x) {
        this.x = x;
        this.y = DEFAULT_Y;
        this.collisionReact = new CollisionReact(x, y, WIDTH, HEIGHT);

        if (texture == null){
            texture = new Texture("bullet.png");
        }
    }

    public void update(float deltaTime){
        y += SPEED * deltaTime;

        if (y > Gdx.graphics.getHeight()){
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
