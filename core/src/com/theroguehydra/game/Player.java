package com.theroguehydra.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Player {
    Texture texture = new Texture(Gdx.files.internal("player_texture.png"));
    Sprite sprite = new Sprite(texture);
    Sound bullet_sound = Gdx.audio.newSound(Gdx.files.internal("laser_shoot.wav"));
    int x;
    int y;
    int speed;
    int score = 0;
    int firedBullets = 0;
    boolean gameLose = false;
    ArrayList<Bullet> bullets;

    public Player(int x, int y, int speed, ArrayList<Bullet> bullets) {
        sprite.setScale(4);
        sprite.setColor(Color.CYAN);
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.bullets = bullets;
    }

    public void update() {
        sprite.setPosition(this.x,this.y);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && this.x>10) {
            this.x -= this.speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && this.x+this.sprite.getWidth()+10<Gdx.graphics.getWidth()) {
            this.x += this.speed;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            bullet_sound.play(1.0f);
            fireBullet();
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void fireBullet() {
        Bullet bullet = new Bullet(this.x+4,this.y+28,25);
        bullets.add(bullet);
        firedBullets += 1;
    }

    public void checkCollision(Enemy enemy) {
        if(this.x+this.sprite.getWidth()*this.sprite.getScaleX()>enemy.x-enemy.sprite.getWidth() && this.x<enemy.x+enemy.sprite.getWidth()*enemy.sprite.getScaleX()-enemy.sprite.getWidth() && this.y+this.sprite.getHeight()*this.sprite.getScaleY()-this.sprite.getHeight()>enemy.y && this.y<enemy.y+enemy.sprite.getHeight()*enemy.sprite.getScaleY()-enemy.sprite.getHeight()) {
            this.gameLose = true;
        }
    }
}
