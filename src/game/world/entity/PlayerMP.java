package game.world.entity;

import com.badlogic.gdx.graphics.Color;

import game.graphics.Sprite;
import game.scene.SceneGame;
import game.world.Map;
import game.world.entitycomponent.CollisionComponent;
import game.world.entitycomponent.FlashLightComponent;
import game.world.entitycomponent.InventoryComponent;
import game.world.entitycomponent.NameComponent;
import game.world.entitycomponent.ParticleComponent;
import game.world.entitycomponent.SizeComponent;
import game.world.entitycomponent.SpriteComponent;
import game.world.entitycomponent.TransformComponent;

public class PlayerMP extends Entity {
	
	public TransformComponent transform;
	public CollisionComponent collision;
	public InventoryComponent inventory;
	public NameComponent name;
	private NameComponent nameHud;
	public int netID;
	
	public PlayerMP(Map map, int netID)
	{
		this.netID = netID;
		transform = new TransformComponent();
		SizeComponent size = new SizeComponent(Sprite.radiobutton_hovered_marked);
		collision = new CollisionComponent(transform, size, false);
		name = new NameComponent("", transform, size, SceneGame.chatFont);
		inventory = new InventoryComponent(transform, size, 4, 1);
		addComponent(transform);
		addComponent(size);
		addComponent(collision);
		addComponent(inventory);
		addComponent(new SpriteComponent(transform, size, Sprite.radiobutton_hovered_marked));
		nameHud = new NameComponent("", transform, size, SceneGame.chatFont);
		addComponent(nameHud);
		addComponent(new ParticleComponent(transform, size, 4, 4, 4.0f, 60, 0.6f, new Color(0.0f, 0.25f, 0.5f, 0.1f), 0, 0.2f, 0.3f, 1));
		if(map != null) 
		{
			addComponent(new FlashLightComponent(map, transform, size, Color.LIGHT_GRAY));
			map.addPlayerMP(this);
		}
	}
	
	public void setName(String name)
	{
		nameHud.name = name;
	}
	
	public String getName()
	{
		return nameHud.name;
	}
	
	public void setPosition(float x, float y)
	{
		transform.setPosition(x, y);
	}
	
}
