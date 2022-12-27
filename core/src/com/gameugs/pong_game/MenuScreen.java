package com.gameugs.pong_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class MenuScreen implements Screen {

	final Pong game;
	private OrthographicCamera cam;

	private long initTime;
	private float opacity = 0;
	private int width;
	private int height;
	private Sprite background;
	private Vector3 touchPos;
	private BitmapFont playerText;
	private BitmapFont controlsText;
	private GlyphLayout onePlayerLayout;
	private GlyphLayout twoPlayerLayout;
	private GlyphLayout controlsLayout;
	private Rectangle onePlayerContainer;
	private Rectangle twoPlayerContainer;
	private Rectangle controlsContainer;

	public MenuScreen(Pong game) {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		touchPos = new Vector3();

		playerText = game.assets.get("amatic48.ttf", BitmapFont.class);
		controlsText = game.assets.get("amatic32.ttf", BitmapFont.class);
		onePlayerLayout = new GlyphLayout(playerText, "1 Player");
		twoPlayerLayout = new GlyphLayout(playerText, "2 Players");
		controlsLayout = new GlyphLayout(controlsText, "How to Play");

		onePlayerContainer = new Rectangle(width / 2 - twoPlayerLayout.width / 2, height / 2 + onePlayerLayout.height,
				onePlayerLayout.width, onePlayerLayout.height);
		twoPlayerContainer = new Rectangle(width / 2 - twoPlayerLayout.width / 2,
				height / 2 - twoPlayerLayout.height / 3 - twoPlayerLayout.height, twoPlayerLayout.width,
				twoPlayerLayout.height);
		controlsContainer = new Rectangle(100, 100 - controlsLayout.height, controlsLayout.width,
				controlsLayout.height);

		this.game = game;

		Texture menuTexture = new Texture("menuImg.png");
		background = new Sprite(menuTexture, 1000, 600);

		initTime = TimeUtils.millis();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 960, 540);

	}

	@Override
	public void show() {
		if (!IntroScreen.backMusic.isPlaying())
			IntroScreen.backMusic.play();

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(1, 1, 1, 1);
		cam.update();
		game.batch.setProjectionMatrix(cam.combined);
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(touchPos);
		game.batch.begin();
		background.draw(game.batch, opacity);

		if (TimeUtils.timeSinceMillis(initTime) > 500) {
			if (opacity < 1)
				opacity += 0.5f * delta;
			else {
				opacity = 1;
				if (controlsContainer.contains(touchPos.x, touchPos.y)) {
					controlsText.setColor(Color.YELLOW);
					controlsText.draw(game.batch, "How to Play", 100, 100);
				} else {
					controlsText.setColor(Color.WHITE);
					controlsText.draw(game.batch, "How to Play", 100, 100);
				}

				if (onePlayerContainer.contains(touchPos.x, touchPos.y)) {
					playerText.setColor(Color.YELLOW);
					playerText.draw(game.batch, "1 Player", width / 2 - onePlayerLayout.width / 2,
							height / 2 + onePlayerLayout.height * 2);
				} else {
					playerText.setColor(Color.WHITE);
					playerText.draw(game.batch, "1 Player", width / 2 - onePlayerLayout.width / 2,
							height / 2 + onePlayerLayout.height * 2);
				}

				if (twoPlayerContainer.contains(touchPos.x, touchPos.y)) {
					playerText.setColor(Color.YELLOW);
					playerText.draw(game.batch, "2 Players", width / 2 - twoPlayerLayout.width / 2,
							height / 2 - twoPlayerLayout.height / 3);
				} else {
					playerText.setColor(Color.WHITE);
					playerText.draw(game.batch, "2 Players", width / 2 - twoPlayerLayout.width / 2,
							height / 2 - twoPlayerLayout.height / 3);
				}

				if (onePlayerContainer.contains(touchPos.x, touchPos.y)
						|| twoPlayerContainer.contains(touchPos.x, touchPos.y)
						|| controlsContainer.contains(touchPos.x, touchPos.y))
					Gdx.graphics.setSystemCursor(SystemCursor.Hand);
				else
					Gdx.graphics.setSystemCursor(SystemCursor.Arrow);

				if (onePlayerContainer.contains(touchPos.x, touchPos.y) && Gdx.input.isTouched()) {
					game.setScreen(new OnePlayerGameScreen(game));
					dispose();
				}
				if (twoPlayerContainer.contains(touchPos.x, touchPos.y) && Gdx.input.isTouched()) {
					game.setScreen(new TwoPlayerGameScreen(game));
					dispose();
				}

				if (controlsContainer.contains(touchPos.x, touchPos.y) && Gdx.input.isTouched()) {
					game.setScreen(new TutorialScreen(game));
					dispose();
				}
			}

		}

		game.batch.end();
		playerText.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		controlsText.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

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
		if (!game.getScreen().toString().contains("TutorialScreen"))
			IntroScreen.backMusic.stop();
	}

}
