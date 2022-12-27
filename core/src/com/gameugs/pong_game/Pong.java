package com.gameugs.pong_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Pong extends Game {
	SpriteBatch batch;
	AssetManager assets;
	ShapeRenderer shaper;

	@Override
	public void create() {
		batch = new SpriteBatch();
		assets = new AssetManager();
		shaper = new ShapeRenderer();
		this.setScreen(new IntroScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.dispose();
		shaper.dispose();
	}
}
