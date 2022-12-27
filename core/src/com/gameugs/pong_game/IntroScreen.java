package com.gameugs.pong_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class IntroScreen implements Screen {

	private final Pong game;
	private OrthographicCamera cam;

	public static Music backMusic;
	private BitmapFont font;
	private GlyphLayout fontLayout;
	private Sprite background;
	private Sprite menuIcon;
	private long initTime;
	private float opacity = 0;
	private int width;
	private int height;

	public IntroScreen(Pong pong) {
		game = pong;

		fontLayout = new GlyphLayout();
		loadAssets(game.assets);

		initTime = TimeUtils.millis();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 960, 540);

	}

	@Override
	public void show() {
		backMusic.play();
		backMusic.setVolume(0.5f);
	}

	@Override
	public void render(float delta) {
		if (game.assets.update(17)) {
			ScreenUtils.clear(1, 1, 1, 1);
			cam.update();
			game.batch.setProjectionMatrix(cam.combined);
			font = game.assets.get("amatic32.ttf", BitmapFont.class);
			font.setColor(Color.WHITE);
			fontLayout.setText(font, "Click Or Press Any Key to Start!");

			if (TimeUtils.timeSinceMillis(initTime) > 1000) {
				game.batch.begin();
				background.draw(game.batch, opacity);
				if (opacity < 1)
					opacity += 0.2f * delta;
				else {
					opacity = 1;
					menuIcon.draw(game.batch);
					font.draw(game.batch, "Click Or Press SPACE Key to Start!", width / 2 - fontLayout.width / 2,
							height / 2 - fontLayout.height * 2);
					font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

				}
				game.batch.end();
				if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) {
					game.setScreen(new MenuScreen(game));
					dispose();
				}
			}

		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	private void loadAssets(AssetManager assets) {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		assets.load("hit_effect.wav", Sound.class);
		assets.load("scoreUp.wav", Sound.class);
		assets.load("gameovereffect.mp3", Sound.class);

		backMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundmusic.mp3"));
		Texture menuTexture = new Texture("menuImg.png");
		Texture menuIconTexture = new Texture("menuIconjpg.jpg");
		menuIconTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		background = new Sprite(menuTexture, 1000, 600);
		menuIcon = new Sprite(menuIconTexture);
		menuIcon.setBounds(width / 2 - 250 / 2, height / 2 - 25, 250, 250);
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		FreeTypeFontLoaderParameter titleFont = new FreeTypeFontLoaderParameter();
		titleFont.fontFileName = "fonts/amatic.ttf";
		titleFont.fontParameters.size = 32;

		assets.load("amatic32.ttf", BitmapFont.class, titleFont);
		titleFont.fontFileName = "fonts/amatic.ttf";
		titleFont.fontParameters.size = 48;
		assets.load("amatic48.ttf", BitmapFont.class, titleFont);
	}

}
