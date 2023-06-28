package com.theroguehydra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy {
    Texture texture = new Texture(Gdx.files.internal("enemy_texture.png"));
    Sprite sprite = new Sprite(texture);
    int x;
    int y;
    int speed;
    boolean isDestroyed = false;

    public Enemy(int x, int y, int speed) {
        sprite.setScale(4);
        sprite.setColor(Color.MAGENTA);
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
        sprite.setPosition(this.x,this.y);
        this.y -= this.speed;
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}