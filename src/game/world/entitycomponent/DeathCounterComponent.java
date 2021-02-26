package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.scene.SceneGame;
import game.world.Map;
import game.world.entity.Entity;
import game.world.entity.Player;

public class DeathCounterComponent extends EntityComponent {
	
	public int deathCount;

	@Override
	public void update(Entity entity, Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill(Entity entity, Map map) {
		deathCount++;
		if(entity instanceof Player)
		{
			SceneGame.deathCount++;
		}
	}

	@Override
	public String getKeyIdentifier() {
		return "deathcounter";
	}
	
	
}
