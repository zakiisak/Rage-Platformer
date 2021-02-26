package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.entity.Entity;

public class MotionDamperComponent extends EntityComponent {
	
	public MotionComponent motion;
	public float damper;
	
	public MotionDamperComponent(MotionComponent motion, float damper)
	{
		this.motion = motion;
		this.damper = damper;
	}
	
	public void update(Entity entity, Map map) {
		motion.mx *= damper;
		motion.my *= damper;
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
		return "motiondamper";
	}

}
