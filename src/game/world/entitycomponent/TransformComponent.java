package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.entity.Entity;

public class TransformComponent extends EntityComponent {
	
	public float x;
	public float y;
	
	public TransformComponent() {}
	
	public TransformComponent(float x, float y)
	{
		setPosition(x, y);
	}
	
	public void update(Entity entity, Map map) {}
	
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void move(float mx, float my)
	{
		this.x += mx;
		this.y += my;
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
		return "transform";
	}
}
