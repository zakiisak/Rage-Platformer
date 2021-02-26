package game.world.entitycomponent;

import game.world.Map;
import game.world.entity.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MoverComponent extends EntityComponent {
	
	public MotionComponent motion;
	public SizeComponent size;
	public float startX, startY;
	public float distanceX, distanceY;
	public float speed;
	public int directionX = 1;
	public int directionY = 1;
	
	public MoverComponent(MotionComponent motion, SizeComponent size, float distanceX, float distanceY, float speed) {
		this.motion = motion;
		this.size = size;
		this.speed = speed;
		this.startX = motion.transform.x;
		this.startY = motion.transform.y;
		this.distanceX = distanceX;
		this.distanceY = distanceY;
	}
	
	@Override
	public void update(Entity entity, Map map) {
		if(directionX > 0)
		{
			if(motion.transform.x + size.width < startX + distanceX)
				motion.mx = speed;
			else directionX *= -1;
		}
		else if(directionX < 0)
		{
			if(motion.transform.x > startX)
				motion.mx = -speed;
			else directionX *= -1;
		}
		
		if(directionY > 0)
		{
			if(motion.transform.y + size.height < startY + distanceY)
				motion.my = speed;
			else directionY *= -1;
		}
		else if(directionY < 0)
		{
			if(motion.transform.y > startY)
				motion.my = -speed;
			else directionY *= -1;
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
		return "mover";
	}

}
