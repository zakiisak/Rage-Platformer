package game.world;

import game.graphics.Sprite;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Item {
	public static final int ITEM_SIZE = 24;
	public static java.util.Map<String, Item> items = new HashMap<String, Item>();
	
	public static Item get(String name)
	{
		return items.get(name.toLowerCase());
	}
	
	public static enum Rarity
	{
		DEFAULT(Color.LIGHT_GRAY), 
		MAGIC(new Color(0.1f, 0.45f, 0.9f, 1.0f)),
		RARE(new Color(0.9f, 0.45f, 0.1f, 1.0f)),
		LEGENDARY(new Color(0.7765f, 0, 1.0f, 1.0f)),
		UNKNOWN(new Color(1.0f, 0, 0, 1.0f));
		
		private Color rarityColor;
		Rarity(Color rarityColor)
		{
			this.rarityColor = rarityColor;
		}
		
		public Color getColor()
		{
			return rarityColor;
		}
	}
	
	protected Sprite icon;
	protected String name;
	protected Rarity rarity = Rarity.DEFAULT;
	
	protected abstract void renderEffects(SpriteBatch spriteBatch, float x, float y, float scale);
	
	public void render(SpriteBatch spriteBatch, float x, float y, float scale)
	{
		icon.render(spriteBatch, x - ITEM_SIZE / 2, y - ITEM_SIZE / 2, ITEM_SIZE * scale, ITEM_SIZE * scale);
		renderEffects(spriteBatch, x, y, scale);
	}
	
	public Item(String name)
	{
		this.name = name;
	}
	
	
	public Sprite getIcon()
	{
		return icon;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Rarity getRarity()
	{
		return rarity;
	}
	
	
}
