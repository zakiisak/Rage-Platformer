package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.entity.Entity;

public class LifeTimeControlComponent extends EntityComponent {
	
	public int tick;
	public int lifeTimeInTicks;
	public SpriteComponent sprite;
	
	public LifeTimeControlComponent(int lifeTimeInTicks, SpriteComponent sprite)
	{
		this.lifeTimeInTicks = lifeTimeInTicks;
		this.sprite = sprite;
	}

	@Override
	public void update(Entity entity, Map map) {
		tick++;
		if(tick >= lifeTimeInTicks)
			entity.kill();
		if(sprite != null)
		{
			sprite.color.a = 1.0f - (float) tick / (float) lifeTimeInTicks;
		}
		
	}

	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill(Entity entity, Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getKeyIdentifier() {
		return "lifetimecontrol";
	}
	
}
