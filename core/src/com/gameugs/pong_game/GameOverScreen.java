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

public class GameOverScreen implements Screen {

	final Pong game;
	private OrthographicCamera cam;

	private int width;
	private int height;
	private Vector3 touchPos;
	private BitmapFont playerText;
	private BitmapFont exitText;
	private GlyphLayout onePlayerLayout;
	private GlyphLayout exitLayout;
	private Rectangle exitContainer;
	private String winner;

	public GameOverScreen(Pong game) {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		touchPos = new Vector3();

		playerText = game.assets.get("amatic48.ttf", BitmapFont.class);
		exitText = game.assets.get("amatic32.ttf", BitmapFont.class);
		if (OnePlayerGameScreen.winner == "")
			this.winner = TwoPlayerGameScreen.winner;
		else
			this.winner = OnePlayerGameScreen.winner;
		onePlayerLayout = new GlyphLayout(playerText, winner + " WON!");
		exitLayout = new GlyphLayout(exitText, "Exit");

		exitContainer = new Rectangle(100, 100 - exitLayout.height, exitLayout.width, exitLayout.height);

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
			exitText.draw(game.batch, "Exit", 100, 100);
		} else {
			exitText.setColor(Color.WHITE);
			exitText.draw(game.batch, "Exit", 100, 100);
		}

		playerText.setColor(Color.WHITE);
		playerText.draw(game.batch, winner + " WON!", width / 2 - onePlayerLayout.width / 2,
				height / 2 + onePlayerLayout.height);

		if (exitContainer.contains(touchPos.x, touchPos.y))
			Gdx.graphics.setSystemCursor(SystemCursor.Hand);
		else
			Gdx.graphics.setSystemCursor(SystemCursor.Arrow);

		if (exitContainer.contains(touchPos.x, touchPos.y) && Gdx.input.isTouched()) {
			game.setScreen(new MenuScreen(game));
			dispose();
		}

		game.batch.end();
		playerText.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
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
		OnePlayerGameScreen.winner = "";
		TwoPlayerGameScreen.winner = "";
		OnePlayerGameScreen.scorePlayer = 0;
		OnePlayerGameScreen.scoreEnemy = 0;
		TwoPlayerGameScreen.scorePlayer = 0;
		TwoPlayerGameScreen.scorePlayer2 = 0;
	}

}
