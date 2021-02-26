package game.world.entity;

import game.graphics.Sprite;
import game.world.Map;
import game.world.Tile;
import game.world.entitycomponent.CollisionComponent;
import game.world.entitycomponent.MotionComponent;
import game.world.entitycomponent.MoverComponent;
import game.world.entitycomponent.SizeComponent;
import game.world.entitycomponent.SpriteComponent;
import game.world.entitycomponent.TransformComponent;

public class Platform extends Entity {
	
	public Platform(Map map, float x, float y, float width, float height, float distanceX, float distanceY, float speed)
	{
		TransformComponent transform = new TransformComponent(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
		SizeComponent size = new SizeComponent(width, height);
		MotionComponent motion = new CollisionComponent(transform, size, true);
		addComponent(size);
		addComponent(motion);
		addComponent(new MoverComponent(motion, size, distanceX, distanceY, speed));
		addComponent(new SpriteComponent(transform, size, Sprite.button_hovered));
		map.addEntity(this);
	}
}
