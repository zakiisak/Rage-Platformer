package game.world.entitycomponent;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.utils.FontUtils;
import game.world.Map;
import game.world.entity.Entity;

public class NameComponent extends EntityComponent {
	private static final Random RAND = new Random();
	
	public String name;
	public TransformComponent transform;
	public SizeComponent size;
	public BitmapFont font;
	
	public Color color = new Color(0.5f + RAND.nextFloat() * 0.5f, 0.5f + RAND.nextFloat() * 0.5f, 0.5f + RAND.nextFloat() * 0.5f, 0.75f);
	
	public NameComponent(String name, TransformComponent transform, SizeComponent size, BitmapFont font)
	{
		this.name = name;
		this.transform = transform;
		this.size = size;
		this.font = font;
	}
	
	@Override
	public void update(Entity entity, Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		FontUtils.addText(font, name, transform.x + size.width / 2 - map.getCameraPosition().x, transform.y - font.getLineHeight() - 2 - map.getCameraPosition().y, color, FontUtils.ALLIGN_MIDDLE);
	}

	@Override
	public void kill(Entity entity, Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getKeyIdentifier() {
		return "name";
	}

}
