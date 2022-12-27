package com.gameugs.pong_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

public class TwoPlayerGameScreen implements Screen {

	final Pong game;
	OrthographicCamera cam;

	private BitmapFont scoreFont;
	public static int scorePlayer = 0;
	public static int scorePlayer2 = 0;
	private GlyphLayout playerLayout;
	private GlyphLayout player2Layout;
	private float width;
	private float height;
	private Paddle playerPaddle;
	private Paddle player2Paddle;
	private Ball ball;
	public static String winner;

	public TwoPlayerGameScreen(Pong game) {
		Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();
		ball = new Ball(width / 2, height / 2, 7, Color.WHITE);
		scoreFont = game.assets.get("amatic32.ttf", BitmapFont.class);
		playerLayout = new GlyphLayout();
		player2Layout = new GlyphLayout();
		playerPaddle = new Paddle(15, height / 2 - 80 / 2, 15, 80, "PLAYER1", Color.GREEN);
		player2Paddle = new Paddle(width - 30, height / 2 - 80 / 2, 15, 80, "PLAYER2", Color.BLUE);

		this.game = game;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 960, 540);

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		cam.update();
		game.batch.setProjectionMatrix(cam.combined);
		game.shaper.setProjectionMatrix(cam.combined);

		playerLayout.setText(scoreFont, "" + scorePlayer);
		player2Layout.setText(scoreFont, "" + scorePlayer2);

		game.batch.begin();

		scoreFont.draw(game.batch, "" + scorePlayer, width / 2 - playerLayout.width / 2 - 50, height - 25);
		scoreFont.draw(game.batch, "" + scorePlayer2, width / 2 - player2Layout.width / 2 + 50, height - 25);

		game.batch.end();

		if (scorePlayer == 5 || scorePlayer2 == 5) {
			game.assets.get("gameovereffect.mp3", Sound.class).play();

			if (scorePlayer == 5)
				winner = "Player 1";
			else if (scorePlayer2 == 5)
				winner = "Player 2";
			game.setScreen(new GameOverScreen(game));
			dispose();

		}

		game.shaper.begin(ShapeType.Filled);
		game.shaper.setColor(Color.RED);
		game.shaper.rect(0, 0, 5, height);
		game.shaper.rect(width - 5, 0, 5, height);

		playerPaddle.update(ball);
		playerPaddle.draw(game.shaper);
		player2Paddle.update(ball);
		player2Paddle.draw(game.shaper);
		ball.update(playerPaddle.getSize(), player2Paddle.getSize(), game);
		ball.draw(game.shaper);
		game.shaper.end();
		game.shaper.begin(ShapeType.Line);
		game.shaper.setColor(Color.SKY);
		game.shaper.circle(width / 2, height / 2, 100);
		game.shaper.rectLine(width / 2, 0, width / 2, height, 2);
		game.shaper.end();

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
		scorePlayer = 0;
		scorePlayer2 = 0;

	}

}
