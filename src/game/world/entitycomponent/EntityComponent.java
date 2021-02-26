package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.net.Chat;
import game.scene.SceneGame;
import game.world.Map;
import game.world.entity.Entity;

public abstract class EntityComponent {
	public abstract void update(Entity entity, Map map);
	public abstract void render(Entity entity, Map map, SpriteBatch spriteBatch);
	public abstract void kill(Entity entity, Map map);
	public abstract String getKeyIdentifier();
	/**
	 * Called when entity is added to the world.
	 * @param entity
	 * @param map
	 * @param entityIndex
	 */
	public void init(Entity entity, Map map, int entityIndex) {}
	/**
	 * Called after the world has been rendered.
	 * @param entity
	 * @param map
	 * @param spriteBatch
	 */
	public void renderPost(Entity entity, Map map, SpriteBatch spriteBatch) {}
	
	public void printClientChat(String text)
	{
		getChat().appendText(text);
	}
	
	public Chat getChat()
	{
		return SceneGame.chat;
	}
}
