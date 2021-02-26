package game.world.entitycomponent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.Sprite;
import game.world.Map;
import game.world.entity.Entity;

public class SpriteComponent extends EntityComponent {
	
	public TransformComponent transform;
	public SizeComponent size;
	public Sprite sprite;
	public Color color;
	
	public SpriteComponent(TransformComponent transform, SizeComponent size, Sprite sprite, Color color)
	{
		this.transform = transform;
		this.size = size;
		this.sprite = sprite;
		this.color = color;
	}
	
	public SpriteComponent(TransformComponent transform, SizeComponent size, Sprite sprite)
	{
		this(transform, size, sprite, Color.WHITE);
	}
	
	public SpriteComponent(TransformComponent transform, Sprite sprite, Color color)
	{
		this(transform, new SizeComponent(sprite.getTexels()[2], sprite.getTexels()[3]), sprite, color);
	}
	
	public SpriteComponent(TransformComponent transform, Sprite sprite)
	{
		this(transform, sprite, Color.WHITE);
	}
	
	
	public void update(Entity entity, Map map) {
		
	}

	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		sprite.render(spriteBatch, transform.x, transform.y, size.width, size.height, color);		
	}

	@Override
	public void kill(Entity entity, Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getKeyIdentifier() {
		return "sprite";
	}

}
