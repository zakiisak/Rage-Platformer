package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.FontBatch;
import game.graphics.SheetLoader;
import game.graphics.Sprite;
import game.net.Commands;
import game.net.Network;
import game.scene.SceneManager;
import game.sound.AudioManager;

public class Game implements ApplicationListener {
	public static final String GAME_VERSION = "v0.83";
	public static final String NAME = System.getProperty("user.name");
	public static boolean shuttingDown = false;
	
	public LwjglApplicationConfiguration config;
	private OrthographicCamera camera;
	private SceneManager sceneManager;
	private SpriteBatch batch;
	private AudioManager audioManager;
	
	@Override
	public void create() {
		Sprite.load();
		Gdx.input.setInputProcessor(new Input());
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		audioManager = new AudioManager();
		sceneManager = new SceneManager(this);
		sceneManager.gameLoadAll();
		SheetLoader.construct();
		Commands.init();
		sceneManager.setScene(0);
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(camera.combined);
		sceneManager.resize(width, height);
	}
	
	@Override
	public void render() {
		batch.begin();
		sceneManager.render();
		FontBatch.flushAll(batch);
		batch.end();
		Network.flushPackets();
	}

	@Override
	public void pause() {
		sceneManager.pause();
	}

	@Override
	public void resume() {
		sceneManager.resume();
	}

	@Override
	public void dispose() {
		shuttingDown = true;
		Network.dispose();
		sceneManager.disposeAll();
		Sprite.DisposeSheet();
		batch.dispose();
		audioManager.dispose();
	}
	
	public AudioManager getAudio()
	{
		return audioManager;
	}
	
	public SpriteBatch getSpriteBatch()
	{
		return batch;
	}
	
	public void setScene(int sceneId)
	{
		sceneManager.setScene(sceneId);
	}

}
