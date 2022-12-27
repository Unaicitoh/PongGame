package com.gameugs.pong_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class TutorialScreen implements Screen {

	final Pong game;
	private OrthographicCamera cam;

	private int width;
	private int height;
	private Vector3 touchPos;
	private BitmapFont infoText;
	private BitmapFont exitText;
	private GlyphLayout infoLayout;
	private GlyphLayout infoLayoutPlayer2;
	private GlyphLayout exitLayout;
	private Rectangle exitContainer;

	public TutorialScreen(Pong game) {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		touchPos = new Vector3();

		infoText = game.assets.get("amatic48.ttf", BitmapFont.class);
		exitText = game.assets.get("amatic32.ttf", BitmapFont.class);

		infoLayout = new GlyphLayout(infoText, "Player 1 plays with W & S KEYS");
		infoLayoutPlayer2 = new GlyphLayout(infoText, "Player 2 uses UP & DOWN ARROW KEYS");

		exitLayout = new GlyphLayout(exitText, "Exit");

		exitContainer = new Rectangle(width / 2 - exitLayout.width / 2, 100 - exitLayout.height, exitLayout.width,
				exitLayout.height);

		this.game = game;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, 960, 540);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		cam.update();
		game.batch.setProjectionMatrix(cam.combined);
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(touchPos);
		game.batch.begin();

		if (exitContainer.contains(touchPos.x, touchPos.y)) {
			exitText.setColor(Color.YELLOW);
			exitText.draw(game.batch, "Exit", width / 2 - exitLayout.width / 2, 100);
		} else {
			exitText.setColor(Color.WHITE);
			exitText.draw(game.batch, "Exit", width / 2 - exitLayout.width / 2, 100);
		}

		infoText.setColor(Color.WHITE);
		infoText.draw(game.batch, "Player 1 uses W & A KEYS", width / 2 - infoLayout.width / 2 + 30,
				height / 2 + infoLayout.height * 2);
		infoText.draw(game.batch, "Player 2 uses UP & DOWN ARROW KEYS", width / 2 - infoLayoutPlayer2.width / 2,
				height / 2 + infoLayoutPlayer2.height / 2);

		if (exitContainer.contains(touchPos.x, touchPos.y))
			Gdx.graphics.setSystemCursor(SystemCursor.Hand);
		else
			Gdx.graphics.setSystemCursor(SystemCursor.Arrow);

		if (exitContainer.contains(touchPos.x, touchPos.y) && Gdx.input.isTouched()) {
			game.setScreen(new MenuScreen(game));
			dispose();
		}

		game.batch.end();
		infoText.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		exitText.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

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

	}

}
