package game.world.entitycomponent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.Sprite;
import game.world.Map;
import game.world.entity.Entity;
import game.world.entity.Particle;

public class ParticleComponent extends EntityComponent {
	
	public TransformComponent transform;
	public SizeComponent size;
	public float width, height;
	public float speed;
	public int amount;
	public int lifeTimeInTicks;
	public Color color;
	public float rVariation, gVariation, bVariation, aVariation;
	
	public ParticleComponent(TransformComponent transform, SizeComponent size, float width, float height, float speed, int amountPerSecond, float lifeTimeInSeconds, Color color, float rVariation, float gVariation, float bVariation, float aVariation)
	{
		this.transform = transform;
		this.size = size;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.amount = amountPerSecond / 60;
		this.lifeTimeInTicks = (int) (lifeTimeInSeconds * 60.0f);
		this.color = color;
		this.rVariation = rVariation;
		this.gVariation = gVariation;
		this.bVariation = bVariation;
		this.aVariation = aVariation;
	}
	
	public void update(Entity entity, Map map) {
		//Particle.spawnRandom(map, transform.x + size.width / 2, transform.y + size.height / 2, 2, 2, 0, lifeTimeInTicks, Sprite.white, amount);
		Particle.spawn(map, transform.x + size.width / 2, transform.y + size.height / 2, width, height, speed, 0, lifeTimeInTicks, Sprite.checkbox_default, color, rVariation, gVariation, bVariation, aVariation, amount);
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
		return "particle";
	}

}
