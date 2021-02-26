package game.world.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.Sprite;
import game.world.Item;

public class ItemCheese extends Item {

	public ItemCheese() {
		super("Cheese");
		icon = Sprite.item_cheese;
	}

	protected void renderEffects(SpriteBatch spriteBatch, float x, float y, float scale) {
		
	}

}
