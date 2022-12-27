package com.gameugs.pong_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Paddle {

	private Rectangle size;
	private float speedY;
	private String type;
	private float initialSpeed = 350;
	private Color color;

	public Paddle(float x, float y, float w, float h, String type, Color color) {
		this.speedY = initialSpeed;
		this.color = color;
		size = new Rectangle(x, y, w, h);
		this.type = type;
	}

	public void draw(ShapeRenderer render) {
		render.setColor(color);
		render.rect(size.x, size.y, size.width, size.height);
	}

	public void update(Ball ball) {
		if (type.equalsIgnoreCase("player1")) {
			if (Gdx.input.isKeyPressed(Keys.S))
				size.y += -speedY * Gdx.graphics.getDeltaTime();
			else if (Gdx.input.isKeyPressed(Keys.W))
				size.y += +speedY * Gdx.graphics.getDeltaTime();
		} else if (type.equalsIgnoreCase("enemy")) {
			speedY = 300;
			if (size.y + size.height / 2 < ball.getLocation().y) {
				size.y += speedY * Gdx.graphics.getDeltaTime();
			} else if (size.y + size.height / 2 > ball.getLocation().y) {
				size.y += -speedY * Gdx.graphics.getDeltaTime();
			}
		} else if (type.equalsIgnoreCase("player2")) {
			if (Gdx.input.isKeyPressed(Keys.DOWN))
				size.y += -speedY * Gdx.graphics.getDeltaTime();
			else if (Gdx.input.isKeyPressed(Keys.UP))
				size.y += +speedY * Gdx.graphics.getDeltaTime();
		}
		if (size.y < 0)
			size.y = 0;
		if (size.y + size.height > Gdx.graphics.getHeight())
			size.y = Gdx.graphics.getHeight() - size.height;

	}

	public Rectangle getSize() {
		return size;
	}

	public void setSize(Rectangle size) {
		this.size = size;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

}
