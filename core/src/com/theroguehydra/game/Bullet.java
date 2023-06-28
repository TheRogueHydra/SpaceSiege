package com.theroguehydra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
    Texture texture = new Texture(Gdx.files.internal("bullet_texture.png"));
    Sprite sprite = new Sprite(texture);
    int x;
    int y;
    int speed;

    public Bullet(int x, int y, int speed) {
        sprite.setScale(4);
        sprite.setColor(Color.ORANGE);
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
        sprite.setPosition(this.x,this.y);
        this.y += this.speed;
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean checkCollision(Enemy enemy) {
        if(this.x+this.sprite.getWidth()*this.sprite.getScaleX()>enemy.x-enemy.sprite.getWidth() && this.x<enemy.x+enemy.sprite.getWidth()*enemy.sprite.getScaleX()-enemy.sprite.getWidth() && this.y+this.sprite.getHeight()*this.sprite.getScaleY()-this.sprite.getHeight()>enemy.y && this.y<enemy.y+enemy.sprite.getHeight()*enemy.sprite.getScaleY()-enemy.sprite.getHeight()) {
            return true;
        } else {
            return false;
        }
    }
}
