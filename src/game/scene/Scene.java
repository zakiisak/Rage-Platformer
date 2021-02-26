package game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.Game;
import game.gui.Document;
import game.sound.AudioManager;

public abstract class Scene extends Document {
	
	public Scene() {
		super(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		border = null; //Disables border around the scene.
	}

	protected int sceneId = -1;
	protected Game game;
	protected LwjglApplicationConfiguration config;
	protected SpriteBatch batch;
	protected AudioManager audio;
	protected SceneManager scenes;
	
	public abstract void gameLoad();
	public abstract void preEnter();
	public abstract void render();
	public abstract void resize(int width, int height);
	public abstract void dispose();
	public abstract void pause();
	public abstract void resume();
	
	
	/**
	 * @return the index of an array of a scene manager.<br>
	 * If the scene has not been added to a scene manager, <br>
	 * this method returns -1.
	 */
	public int getSceneId()
	{
		return sceneId;
	}
	
	public Game getGame()
	{
		return game;
	}
	
	public SpriteBatch getSpriteBatch()
	{
		return batch;
	}
	
	public AudioManager getAudio()
	{
		return audio;
	}
	
	/**
	 * Convenience method for game.setScene();
	 * @param id
	 */
	protected void setScene(int sceneId)
	{
		game.setScene(sceneId);
	}
	
}
