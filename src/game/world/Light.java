package game.world;

import com.badlogic.gdx.graphics.Color;

import game.graphics.Sprite;
import game.world.entity.Entity;
import game.world.entitycomponent.SpriteComponent;
import game.world.entitycomponent.TransformComponent;

public class Light extends Entity {
	
	public Light(int x, int y, Color color)
	{
		this(new TransformComponent(x * Tile.TILE_SIZE + Tile.TILE_SIZE / 2 - Sprite.light_map.getTexels()[2] / 2,
				y * Tile.TILE_SIZE + Tile.TILE_SIZE / 2 - Sprite.light_map.getTexels()[3] / 2), color);
	}
	
	public Light(TransformComponent transform, Color color)
	{
		addComponent(new SpriteComponent(transform, Sprite.light_map, color));
	}
	
}
