package com.gameugs.pong_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Ball {

	private Circle location;
	private Color color;
	private float speedX;
	private float speedY;
	private static float initialSpeed = 250;

	public Ball(float x, float y, float size, Color color) {
		float direction = MathUtils.random();
		if (direction < 0.25f) {
			this.speedX = initialSpeed;
			this.speedY = initialSpeed;
		} else if (direction < 0.5f) {
			this.speedX = -initialSpeed;
			this.speedY = -initialSpeed;
		} else if (direction < 0.75f) {
			this.speedX = initialSpeed;
			this.speedY = -initialSpeed;
		} else {
			this.speedX = -initialSpeed;
			this.speedY = initialSpeed;
		}
		location = new Circle(x, y, size);
		this.color = color;
	}

	public Circle getLocation() {
		return location;
	}

	public void setLocation(Circle location) {
		this.location = location;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public void draw(ShapeRenderer render) {
		render.setColor(color);
		render.circle(location.x, location.y, location.radius);
	}

	public void update(Rectangle rectPlayer, Rectangle rectEnemy, Pong game) {
		location.x += speedX * Gdx.graphics.getDeltaTime();
		location.y += speedY * Gdx.graphics.getDeltaTime();

		if (getLocation().y > Gdx.graphics.getHeight() - location.radius || getLocation().y < location.radius)
			setSpeedY(-speedY);

		if (getLocation().x > Gdx.graphics.getWidth() - 5 - location.radius) {
			speedX = Math.abs(initialSpeed);
			OnePlayerGameScreen.scorePlayer++;
			TwoPlayerGameScreen.scorePlayer++;
			game.assets.get("scoreUp.wav", Sound.class).play();
			resetPosition();
		} else if (getLocation().x < 5 + location.radius) {
			speedX = -Math.abs(initialSpeed);
			OnePlayerGameScreen.scoreEnemy++;
			TwoPlayerGameScreen.scorePlayer2++;
			game.assets.get("scoreUp.wav", Sound.class).play();
			resetPosition();

		}

		if (Intersector.overlaps(location, rectPlayer) || Intersector.overlaps(location, rectEnemy)) {
			speedX = -speedX;
			speedX *= 1.05;
			speedY *= 1.05;
			game.assets.get("hit_effect.wav", Sound.class).play();

		}

	}

	private void resetPosition() {
		float rand = MathUtils.random();
		if (rand < 0.5f)
			speedY = -Math.abs(initialSpeed);
		else
			speedY = Math.abs(initialSpeed);
		location.x = Gdx.graphics.getWidth() / 2;
		location.y = Gdx.graphics.getHeight() / 2;
	}
}
