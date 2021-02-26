package game.world;

import game.world.item.ItemCheese;

public class ItemRegistry {
	
	public static void load()
	{
		register(new ItemCheese());
	}
	
	public static void register(Item item)
	{
		Item.items.put(item.getName().toLowerCase(), item);
	}
}
