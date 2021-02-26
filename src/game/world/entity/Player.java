package game.world.entity;

import com.badlogic.gdx.graphics.Color;

import game.graphics.Sprite;
import game.world.Map;
import game.world.Tile;
import game.world.entitycomponent.BoundaryKillComponent;
import game.world.entitycomponent.CameraComponent;
import game.world.entitycomponent.CollisionComponent;
import game.world.entitycomponent.DeathCounterComponent;
import game.world.entitycomponent.FlashLightComponent;
import game.world.entitycomponent.GravityComponent;
import game.world.entitycomponent.InventoryComponent;
import game.world.entitycomponent.JumpKeyboardComponent;
import game.world.entitycomponent.MotionComponent;
import game.world.entitycomponent.NetTransformSenderComponent;
import game.world.entitycomponent.ParticleComponent;
import game.world.entitycomponent.SizeComponent;
import game.world.entitycomponent.SpriteComponent;
import game.world.entitycomponent.TransformComponent;

public class Player extends Entity {
	
	public InventoryComponent inventory;
	
	public Player(Map map, float x, float y)
	{
		//TODO Make Chat System be controlled from within the jumpkeyboard component, so we sort have 
		//everything in components and so on, if it's possible. :D
		TransformComponent transform = new TransformComponent(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
		SizeComponent size = new SizeComponent(16, 16);
		MotionComponent motion = new CollisionComponent(transform, size, false);
		GravityComponent gravityComp = new GravityComponent((CollisionComponent)motion, 0.95F);
		inventory = new InventoryComponent(transform, size, 4, 1);
		addComponent(transform);
		addComponent(size);
		addComponent(gravityComp);
		addComponent(new JumpKeyboardComponent(motion, gravityComp, 0.5f, 12.0f));
		addComponent(motion);
		addComponent(new SpriteComponent(transform, size, Sprite.radiobutton_hovered_marked));
		addComponent(new CameraComponent(transform, size));
		addComponent(new FlashLightComponent(map, transform, size, Color.LIGHT_GRAY));
		addComponent(new DeathCounterComponent());
		addComponent(new NetTransformSenderComponent(transform));
		addComponent(new ParticleComponent(transform, size, 4, 4, 4.0f, 120, 1.6f, new Color(0.5f, 0.25f, 0, 0.1f), 0.3f, 0.2f, 0, 1));
		addComponent(inventory);
		addComponent(new BoundaryKillComponent(transform)); //Be sure to put this as the last!!!
		map.addEntity(this);
	}
	
}
