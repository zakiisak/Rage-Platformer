package game.world.entitycomponent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.Sprite;
import game.world.Light;
import game.world.Map;
import game.world.entity.Entity;

public class FlashLightComponent extends EntityComponent {
	
	public TransformComponent transform;
	public SizeComponent size;
	public TransformComponent lightTransform;
	public Light light;
	
	public FlashLightComponent(Map map, TransformComponent transform, SizeComponent size, Color lightColor)
	{
		this.transform = transform;
		this.size = size;
		this.lightTransform = new TransformComponent(transform.x + size.width / 2 - Sprite.light_map.getTexels()[2] / 2,
				transform.y + size.height / 2 - Sprite.light_map.getTexels()[3] / 2);
		light = new Light(lightTransform, lightColor);
		map.addLight(light);
		//this.lightIndex = map.addLight(light);
	}
	
	
	@Override
	public void update(Entity entity, Map map) {
		this.lightTransform.setPosition(transform.x + size.width / 2 - Sprite.light_map.getTexels()[2] / 2,
				transform.y + size.height / 2 - Sprite.light_map.getTexels()[3] / 2);
	}

	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void kill(Entity entity, Map map) {
		map.killLight(light);
	}


	@Override
	public String getKeyIdentifier() {
		return "flashlight";
	}

}
