package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.Sprite;
import game.world.Map;
import game.world.entity.Entity;

public class SizeComponent extends EntityComponent {
	
	public float width;
	public float height;
	
	public SizeComponent(float width, float height)
	{
		setSize(width, height);
	}
	
	public SizeComponent(Sprite sprite)
	{
		setSize(sprite.getTexels()[2], sprite.getTexels()[3]);
	}
	
	public void update(Entity entity, Map map) {}
	
	public void setSize(float width, float height)
	{
		this.width = width;
		this.height = height;
	}

	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill(Entity entity, Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getKeyIdentifier() {
		return "size";
	}
}
