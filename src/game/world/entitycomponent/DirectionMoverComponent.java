package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.entity.Entity;

public class DirectionMoverComponent extends EntityComponent {

	public MotionComponent motion;
	public float speed;
	public float radians;
	
	public DirectionMoverComponent(MotionComponent motion, float angle, float speed)
	{
		this.motion = motion;
		this.radians = (float) Math.toRadians(angle);
		this.speed = speed;
	}
	
	@Override
	public void update(Entity entity, Map map) {
		motion.mx += Math.cos(radians) * speed;
		motion.my += Math.sin(radians) * speed;
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
		return "directionmover";
	}
	
	
	
}
