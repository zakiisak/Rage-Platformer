package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.entity.Entity;
import game.world.entity.Player;
import game.world.entity.PlayerMP;

public class CollisionKillComponent extends EntityComponent {

	public TransformComponent transform;
	public SizeComponent size;
	public float range;
	public boolean serverSide;
	public Entity ownerEntity;
	
	public CollisionKillComponent(float range, TransformComponent transform, SizeComponent size, boolean serverSide)
	{
		this.transform = transform;
		this.size = size;
		this.range = range;
		this.serverSide = serverSide;
	}
	
	@Override
	public void update(Entity entity, Map map) {
		Player p = map.getClientPlayer();
		if(p != null)
		{
			float distance = getDistance(p);
			if(distance < range) 
			{
				entity.kill();
				ownerEntity = p;
				return;
			}
		}
		if(serverSide)
		{
			for(PlayerMP mp : map.getOnlinePlayers())
			{
				float distance = getDistance(mp);
				if(distance < range) 
				{
					entity.kill();
					ownerEntity = mp;
					return;
				}
			}
		}
		
	}
	
	public float getDistance(Entity entity)
	{
		return (float) Math.abs(Math.sqrt(Math.pow(((TransformComponent)entity.getComponent("transform")).x
				 + ((SizeComponent) entity.getComponent("size")).width / 2 - (transform.x + size.width / 2), 2) +
				Math.pow(((TransformComponent)entity.getComponent("transform")).y
						 + ((SizeComponent) entity.getComponent("size")).height / 2 - (transform.y + size.height / 2), 2)
				));
	}
	
	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill(Entity entity, Map map) {
		
		
	}

	@Override
	public String getKeyIdentifier() {
		return "collisionkill";
	}

}
