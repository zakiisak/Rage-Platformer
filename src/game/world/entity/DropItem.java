package game.world.entity;

import game.world.Item;
import game.world.entitycomponent.BoundaryKillComponent;
import game.world.entitycomponent.CollisionComponent;
import game.world.entitycomponent.CollisionKillAddItemComponent;
import game.world.entitycomponent.GravityComponent;
import game.world.entitycomponent.KillParticleComponent;
import game.world.entitycomponent.SizeComponent;
import game.world.entitycomponent.SpriteComponent;
import game.world.entitycomponent.TransformComponent;

public class DropItem extends Entity {
	
	public DropItem(Item item, float x, float y, boolean serverSide)
	{
		TransformComponent transform = new TransformComponent(x, y);
		SizeComponent size = new SizeComponent(item.getIcon());
		CollisionComponent collision = new CollisionComponent(transform, size, false, false);
		
		addComponent(transform);
		addComponent(size);
		addComponent(collision);
		addComponent(new GravityComponent(collision, 0.99f));
		addComponent(new CollisionKillAddItemComponent(item, Math.max(item.getIcon().getTexels()[2], item.getIcon().getTexels()[3]), transform, size, serverSide));
		addComponent(new BoundaryKillComponent(transform));
		addComponent(new SpriteComponent(transform, size, item.getIcon()));
		addComponent(new KillParticleComponent(transform, size, 4, 4, 2.0f, 20, 0.4f, item.getRarity().getColor(), 0.2f, 0.2f, 0.2f, 0.5f));
	}
	
	public DropItem(String itemName, float x, float y, boolean serverSide)
	{
		this(Item.get(itemName), x, y, serverSide);
	}
}
