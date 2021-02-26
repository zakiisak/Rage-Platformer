package game.world.entitycomponent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.entity.Entity;

public class CameraComponent extends EntityComponent {
	
	public TransformComponent transform;
	public SizeComponent size;
	
	public CameraComponent(TransformComponent transform, SizeComponent size)
	{
		this.transform = transform;
		this.size = size;
	}
	
	public void update(Entity entity, Map map) {
		map.getCameraPosition().x += ((transform.x + size.width / 2 - Gdx.graphics.getWidth() / 2) - map.getCameraPosition().x) * 0.25f;
		map.getCameraPosition().y += ((transform.y + size.height / 2 - Gdx.graphics.getHeight() / 2) - map.getCameraPosition().y) * 0.25f;
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
		return "camera";
	}
	
	
}
