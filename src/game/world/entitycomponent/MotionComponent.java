package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.entity.Entity;

public class MotionComponent extends EntityComponent {
	
	public TransformComponent transform;
	public float mx, my;
	
	public MotionComponent(TransformComponent transform)
	{
		this.transform = transform;
	}
	
	public void update(Entity entity, Map map) {
		transform.x += mx;
		transform.y += my;
	}
	
	public void setMotion(float mx, float my)
	{
		this.mx = mx;
		this.my = my;
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
		return "motion";
	}

}
