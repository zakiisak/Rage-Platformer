package game.world.entity;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

import game.graphics.Sprite;
import game.world.Map;
import game.world.entitycomponent.DirectionMoverComponent;
import game.world.entitycomponent.LifeTimeControlComponent;
import game.world.entitycomponent.MotionComponent;
import game.world.entitycomponent.MotionDamperComponent;
import game.world.entitycomponent.SizeComponent;
import game.world.entitycomponent.SpriteComponent;
import game.world.entitycomponent.TransformComponent;

public class Particle extends Entity {
	private static Random rand = new Random();
	
	public Particle(float x, float y, float width, float height, Sprite sprite, float velocityDamper, float angle, float speed, int lifeTimeInTicks, boolean fadeWithLife, Color color)
	{
		TransformComponent transform = new TransformComponent(x, y);
		SizeComponent size = new SizeComponent(width, height);
		MotionComponent motion = new MotionComponent(transform);
		MotionDamperComponent damper = new MotionDamperComponent(motion, velocityDamper);
		DirectionMoverComponent directionMover = new DirectionMoverComponent(motion, angle, speed);
		
		addComponent(transform);
		addComponent(size);
		addComponent(motion);
		addComponent(damper);
		addComponent(directionMover);
		SpriteComponent spriteComponent = new SpriteComponent(transform, size, sprite, color);
		addComponent(spriteComponent);
		addComponent(new LifeTimeControlComponent(lifeTimeInTicks, fadeWithLife ? spriteComponent : null));
	}
	
	public Particle(float x, float y, Sprite sprite, float velocityDamper, float angle, float speed, int lifeTimeInTicks, boolean fadeWithLife, Color color)
	{
		this(x, y, sprite.getTexels()[2], sprite.getTexels()[3], sprite, velocityDamper, angle, speed, lifeTimeInTicks, fadeWithLife, color);
	}
	
	public static void spawn(Map map, float x, float y, float width, float height, float velocityDamper, int lifeTimeInTicks, Sprite sprite, Color color, float rVariation, float gVariation, float bVariation, float aVariation, int amount)
	{
		for(int i = 0; i < amount; i++)
		{
			map.addEntity(new Particle(x - width / 2, y - height / 2, width, height, sprite, velocityDamper, rand.nextFloat() * 360.0f, rand.nextFloat() * 4.0f, lifeTimeInTicks, true, new Color(color.r + rand.nextFloat() * rVariation, color.g + rand.nextFloat() * gVariation, color.b + rand.nextFloat() * bVariation, color.a + rand.nextFloat() * aVariation)));
		}
	}
	
	public static void spawn(Map map, float x, float y, float width, float height, float speed, float velocityDamper, int lifeTimeInTicks, Sprite sprite, Color color, float rVariation, float gVariation, float bVariation, float aVariation, int amount)
	{
		for(int i = 0; i < amount; i++)
		{
			map.addEntity(new Particle(x - width / 2, y - height / 2, width, height, sprite, velocityDamper, rand.nextFloat() * 360.0f, rand.nextFloat() * speed, lifeTimeInTicks, true, new Color(color.r + rand.nextFloat() * rVariation, color.g + rand.nextFloat() * gVariation, color.b + rand.nextFloat() * bVariation, color.a + rand.nextFloat() * aVariation)));
		}
	}
	
	public static void spawnRandom(Map map, float x, float y, float width, float height, float velocityDamper, int lifeTimeInTicks, Sprite sprite, int amount)
	{
		for(int i = 0; i < amount; i++)
		{
			map.addEntity(new Particle(x, y, width, height, sprite, velocityDamper, rand.nextFloat() * 360.0f, rand.nextFloat() * 4.0f, lifeTimeInTicks, true, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 0.4f + rand.nextFloat() * 0.6f)));
		}
	}
	
}
