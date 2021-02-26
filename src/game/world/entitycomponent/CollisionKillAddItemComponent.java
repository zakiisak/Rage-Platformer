package game.world.entitycomponent;

import game.world.Item;
import game.world.ItemStack;
import game.world.Map;
import game.world.entity.Entity;
import game.world.entity.Player;
import game.world.entity.PlayerMP;

public class CollisionKillAddItemComponent extends CollisionKillComponent {
	
	public ItemStack stack;
	
	public CollisionKillAddItemComponent(Item item, float range, TransformComponent transform, SizeComponent size,
			boolean serverSide) {
		super(range, transform, size, serverSide);
		stack = new ItemStack(item, 1);
	}
	
	@Override
	public void kill(Entity entity, Map map) {
		if(ownerEntity != null) ((InventoryComponent) ownerEntity.getComponent("inventory")).add(stack);
		
	}
	
	@Override
	public void update(Entity entity, Map map) {
		Player p = map.getClientPlayer();
		if(p != null)
		{
			float distance = getDistance(p);
			if(distance < range) 
			{
				if(p.inventory.enoughSpaceAvailable(stack))
				{
					entity.kill();
					ownerEntity = p;
					return;
				}
			}
		}
		if(serverSide)
		{
			for(PlayerMP mp : map.getOnlinePlayers())
			{
				float distance = getDistance(mp);
				if(distance < range) 
				{
					if(mp.inventory.enoughSpaceAvailable(stack))
					{
						entity.kill();
						ownerEntity = mp;
						return;
					}
				}
			}
		}
		
	}

}
