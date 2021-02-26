package game.world.entitycomponent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.Sprite;
import game.gui.TiledRenderer;
import game.scene.SceneGame;
import game.utils.FontUtils;
import game.world.Item;
import game.world.ItemStack;
import game.world.Map;
import game.world.entity.DropItem;
import game.world.entity.Entity;

public class InventoryComponent extends EntityComponent {
	public static final int SLOT_SPACING = 2;
	
	public TransformComponent transform;
	public SizeComponent size;
	public Slot[] slots;
	private boolean active = false;
	public int width;
	public int height;
	public int x;
	public int y;
	
	public InventoryComponent(TransformComponent transform, SizeComponent size, int width, int height)
	{
		this.transform = transform;
		this.size = size;
		this.width = width;
		this.height = height;
		this.slots = new Slot[width * height];
		for(int i = 0; i < slots.length; i++)
			this.slots[i] = new Slot();
	}
	
	@Override
	public void update(Entity entity, Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
	}
	
	public void renderPost(Entity entity, Map map, SpriteBatch spriteBatch)
	{
		if(active)
		{
			float width = this.width * (Sprite.inventory_slot.getTexels()[2] + SLOT_SPACING);
			float height = this.height * (Sprite.inventory_slot.getTexels()[3] + SLOT_SPACING);
			float x = Gdx.graphics.getWidth() / 2 - width / 2;
			float y = Gdx.graphics.getHeight() / 2 - height / 2;//Gdx.graphics.getHeight() - 4 - height - 32;
			spriteBatch.setColor(0, 0, 0, 0.6f);
			Sprite.white.render(spriteBatch, x - 4, y - 4, width + 8, height + 8);
			spriteBatch.setColor(1, 1, 1, 1);
			TiledRenderer.drawTiledBorder(spriteBatch, Sprite.frame, x + 16, y + 16, width - 32, height - 32, 6);
			FontUtils.addText(SceneGame.deathCountFont, "Inventory", Gdx.graphics.getWidth() / 2, y - SceneGame.deathCountFont.getLineHeight() - 4, FontUtils.ALLIGN_MIDDLE);
			for(int i = 0; i < slots.length; i++)
			{
				slots[i].active = false;
				if(i == this.x + this.y * this.width) slots[i].active = true;
				slots[i].render(spriteBatch, x + (i % this.width) * (Sprite.inventory_slot.getTexels()[2] + SLOT_SPACING), y + (i / this.width) * (Sprite.inventory_slot.getTexels()[3] + SLOT_SPACING));
			}
		}
	}

	@Override
	public void kill(Entity entity, Map map) {
		for(Slot slot : slots)
		{
			if(slot.stack != null)
			{
				for(int i = 0; i < slot.stack.amount; i++)
				{
					map.addEntity(new DropItem(slot.stack.item, transform.x + size.width / 2, transform.y - size.height / 2, false/*For now, later we'll add a server sided array of drop items, and make the client request for the item stack on collision. The request may be denied if another player has already taken that particular drop item.*/));
				}
			}
		}
	}
	
	public boolean allSlotsContainItems()
	{
		for(Slot slot : slots)
		{
			if(slot.stack == null)
				return false;
		}
		return true;
	}
	
	public boolean contains(String itemName)
	{
		String name = itemName.toLowerCase();
		for(Slot slot : slots)
		{
			ItemStack stack = slot.stack;
			if(stack == null) continue;
			if(name.equals(stack.item.getName().toLowerCase()))
				return true;
		}
		return false;
	}
	
	public boolean contains(Item item)
	{
		return contains(item.getName());
	}
	
	public int getSlotContainingItem(Item item)
	{
		for(int i = 0; i < slots.length; i++)
		{
			Slot slot = slots[i];
			if(slot.stack == null) continue;
			if(slot.stack.item.getName().equalsIgnoreCase(item.getName()))
			{
				return i;
			}
		}
		return -1;
	}
	
	public int getFirstEmptySlot()
	{
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i].stack == null) return i;
		}
		return -1;
	}
	
	public boolean enoughSpaceAvailable(ItemStack stack)
	{
		return contains(stack.item) || !allSlotsContainItems();
	}
	
	public boolean add(ItemStack stack)
	{
		if(contains(stack.item))
		{
			int index = getSlotContainingItem(stack.item);
			slots[index].stack.amount += stack.amount;
			return true;
		}
		else if(!allSlotsContainItems())
		{
			int index = getFirstEmptySlot();
			slots[index].stack = new ItemStack(stack.item, stack.amount);
		}
		return false;
	}
	
	public boolean remove(ItemStack stack)
	{
		for(Slot slot : slots)
		{
			if(slot.stack == null) continue;
			if(slot.stack.item.getName().equalsIgnoreCase(stack.item.getName()))
			{
				slot.stack.amount -= stack.amount;
				if(slot.stack.amount <= 0) slot.stack = null;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getKeyIdentifier() {
		return "inventory";
	}
	
	public void open()
	{
		x = 0;
		y = 0;
		active = true;
		slots[0].active = true;
	}
	
	public void close()
	{
		active = false;
		for(Slot slot : slots)
			slot.active = false;
	}
	
	public boolean toggle()
	{
		if(active)
			close();
		else open();
		return active;
	}
	
	public void up()
	{
		y--;
		if(y < 0) y = height - 1;
	}
	
	public void down()
	{
		y++;
		if(y >= height) y = 0;
	}
	
	public void left()
	{
		x--;
		if(x < 0) x = width - 1;
	}
	
	public void right()
	{
		x++;
		if(x >= width) x = 0;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	private class Slot
	{
		public ItemStack stack;
		public boolean active = false;
		
		public void render(SpriteBatch spriteBatch, float x, float y)
		{
			if(active)
				Sprite.inventory_slot_selected.render(spriteBatch, x, y, Sprite.inventory_slot.getTexels()[2], Sprite.inventory_slot.getTexels()[3]);
			else
				Sprite.inventory_slot.render(spriteBatch, x, y, Sprite.inventory_slot.getTexels()[2], Sprite.inventory_slot.getTexels()[3]);
			if(stack != null)
			{
				stack.item.render(spriteBatch, x + 4 + stack.item.getIcon().getTexels()[2] / 2, y + 4 + stack.item.getIcon().getTexels()[3] / 2, 2);
				if(stack.amount > 1)
				{
					FontUtils.addText(SceneGame.inventoryFont, "" + stack.amount, x + Sprite.inventory_slot.getTexels()[2] - 2, y + Sprite.inventory_slot.getTexels()[3] - SceneGame.inventoryFont.getLineHeight() + 4, FontUtils.ALLIGN_RIGHT);
				}
			}
		}
	}

}
