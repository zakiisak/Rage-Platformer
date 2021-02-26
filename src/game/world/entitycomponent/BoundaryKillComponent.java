package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.Tile;
import game.world.entity.Entity;
import game.world.entity.Player;

public class BoundaryKillComponent extends EntityComponent {

	public TransformComponent transform;
	
	public BoundaryKillComponent(TransformComponent transform)
	{
		this.transform = transform;
	}
	
	public void update(Entity entity, Map map) {
		if(transform.x < -Tile.TILE_SIZE || transform.y < -Tile.TILE_SIZE
				|| transform.x > map.getWidth() * Tile.TILE_SIZE
				|| transform.y > map.getHeight() * Tile.TILE_SIZE) 
		{
			entity.kill();
		}
	}

	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill(Entity entity, Map map) {
		if(entity instanceof Player)
		{
			map.gameover();
		}
	}

	@Override
	public String getKeyIdentifier() {
		return "boundarykill";
	}

}
