package com.theroguehydra.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import sun.jvm.hotspot.utilities.BitMap;

import java.util.ArrayList;
import java.util.Random;

public class Main extends ApplicationAdapter {
	Random rand = new Random();
	BitmapFont font;
	SpriteBatch batch;
	Player player;
	ArrayList<Bullet> bullets = new ArrayList<>();
	ArrayList<Enemy> enemies = new ArrayList<>();
	int enemySpawnRate = 3;
	int enemySpeed = 1;
	float clock = 0;
	String scoreText;
	int enemyWave = 1;
	Music bgm;
	Sound gameover;
	boolean gameoverMusic = false;
	
	@Override
	public void create () {
		System.out.println(String.valueOf(Gdx.graphics.getWidth()));
		System.out.println(String.valueOf(Gdx.graphics.getHeight()));
		batch = new SpriteBatch();
		player = new Player(Gdx.graphics.getWidth()/2,20,5,bullets);
		font = new BitmapFont(Gdx.files.internal("determination.fnt"),false);
		font.setColor(Color.GREEN);
		bgm = Gdx.audio.newMusic(Gdx.files.internal("Epic.ogg"));
		gameover = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		bgm.setLooping(true);
		bgm.play();
		scoreText = "score: "+String.valueOf(player.score);
		clock += Gdx.graphics.getDeltaTime();
		batch.begin();
		font.getData().setScale(.2f);
		updateGameObjects(batch);
		batch.end();
		if(player.gameLose == true) {
			bgm.stop();
			if(gameoverMusic == false) {
				gameover.play(1.0f);
				this.gameoverMusic = true;
			}
			drawGameOverText(batch);
		}
	}

	// Enemy Generation
	public void generateEnemies() {
		if(this.enemyWave%5 == 0 && this.enemyWave>1) {
			this.enemySpawnRate += 1;
		}
		for(int i=0; i<enemySpawnRate; i++) {
			Enemy enemy = new Enemy(rand.nextInt(10+Gdx.graphics.getWidth()-32),500,enemySpeed);
			enemies.add(enemy);
		}
		this.enemyWave += 1;
	}

	public void drawGameOverText(SpriteBatch batch) {
		batch.begin();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		font.getData().setScale(.2f);
		font.draw(batch,"Game Over!",Gdx.graphics.getWidth()/2-60,Gdx.graphics.getHeight()/2);
		batch.end();
	}

	public void updateGameObjects(SpriteBatch batch) {
		if(clock>4) {
			generateEnemies();
			clock = 0;
		}
		player.update();
		this.bullets = player.bullets;
		for(Enemy e : enemies) {
			e.update();
			e.draw(batch);
		}
		for(Bullet b : bullets) {
			b.update();
			b.draw(batch);
		}
		for(Bullet b : bullets) {
			for(Enemy e : enemies) {
				if(e.isDestroyed == false) {
					if(b.checkCollision(e)) {
						e.sprite.setColor(Color.CLEAR);
						player.score += 1;
						b.sprite.setColor(Color.CLEAR);
						e.isDestroyed = true;
					}
				}
			}
		}
		player.draw(batch);
		if(player.x<Gdx.graphics.getWidth()/2){
			font.draw(batch,scoreText,Gdx.graphics.getWidth()/2+50,25);
		} else {
			font.draw(batch,scoreText,10,25);
		}
		for(Enemy e : enemies) {
			if(player.gameLose == false && e.isDestroyed == false) {
				player.checkCollision(e);
			}
			if(e.y<0 && e.isDestroyed == false) {
				player.gameLose = true;
			}
		}
	}
}
