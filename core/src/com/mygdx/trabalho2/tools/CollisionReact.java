package com.mygdx.trabalho2.tools;

public class CollisionReact {

    float x, y;
    int width, height;

    public CollisionReact(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean collidesWith (CollisionReact collisionReact) {
        return x < collisionReact.x + collisionReact.width && y < collisionReact.y + collisionReact.height && x + width > collisionReact.x && y + height > collisionReact.y;
    }
}
