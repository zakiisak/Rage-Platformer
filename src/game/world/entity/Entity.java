package game.world.entity;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.entitycomponent.EntityComponent;

public abstract class Entity {
	protected boolean dead = false;
	protected java.util.Map<String, EntityComponent> components = new HashMap<String, EntityComponent>();
	public int variable;
	
	public boolean isDead()
	{
		return dead;
	}
	
	public void addComponent(EntityComponent component)
	{
		this.components.put(component.getKeyIdentifier(), component);
	}
	
	public EntityComponent getComponent(String identifier)
	{
		return components.get(identifier);
	}
	
	public void kill()
	{
		this.dead = true;
	}
	
	public void initComponents(Map map, int entityIndex)
	{
		for(EntityComponent component : components.values())
		{
			component.init(this, map, entityIndex);
		}
	}
	
	public void killComponents(Map map)
	{
		for(EntityComponent component : components.values())
		{
			component.kill(this, map);
		}
	}
	
	public void update(Map map)
	{
		for(EntityComponent component : components.values())
		{
			component.update(this, map);
		}
	}
	
	public void render(Map map, SpriteBatch spriteBatch)
	{
		for(EntityComponent component : components.values())
		{
			component.render(this, map, spriteBatch);
		}
	}
	
	public void renderPost(Map map, SpriteBatch spriteBatch)
	{
		for(EntityComponent component : components.values())
		{
			component.renderPost(this, map, spriteBatch);
		}
	}
	
}
