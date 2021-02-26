package game.scene;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

import game.Game;

public class SceneManager {
	
	protected Game game;
	protected int activeScene = 0;
	protected List<Scene> scenes = new ArrayList<Scene>();
	
	public SceneManager(Game game)
	{
		this.game = game;
		addScene(new SceneGame());
		
	}
	
	public void preEnter()
	{
		scenes.get(activeScene).setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		scenes.get(activeScene).preEnter();
	}
	
	public void gameLoadAll()
	{
		for(Scene scene : scenes)
			scene.gameLoad();
	}
	
	public void resize(int width, int height)
	{
		scenes.get(activeScene).resizeDocument(width, height);
		scenes.get(activeScene).resize(width, height);
	}
	
	public void render()
	{
		scenes.get(activeScene).render();
		scenes.get(activeScene).render(game.getSpriteBatch());
	}
	
	public void pause()
	{
		scenes.get(activeScene).pause();
	}
	
	public void resume()
	{
		scenes.get(activeScene).resume();
	}
	
	public void disposeAll()
	{
		for(Scene scene : scenes)
			scene.dispose();
		scenes.clear();
	}
	
	public void setScene(int sceneId)
	{
		this.activeScene = sceneId;
		scenes.get(activeScene).preEnter();
	}
	
	/**
	 * @param scene
	 * @return the id of the newly added scene.
	 */
	public int addScene(Scene scene)
	{
		scene.game = game;
		scene.batch = game.getSpriteBatch();
		scene.audio = game.getAudio();
		scene.config = game.config;
		scene.scenes = this;
		scene.sceneId = scenes.size() - 1;
		scenes.add(scene);
		return scenes.size() - 1;
	}
	
	public Scene getScene(int index)
	{
		return scenes.get(index);
	}
	
}
