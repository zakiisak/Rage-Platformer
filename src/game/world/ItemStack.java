package game.world;

public class ItemStack {
	
	public Item item;
	public int amount;
	
	public ItemStack(Item item, int amount)
	{
		this.item = item;
		this.amount = amount;
	}
	
	public ItemStack(Item item)
	{
		this(item, 1);
	}
}
