package game.world.entitycomponent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.entity.Entity;
import game.world.entity.Player;

public class JumpKeyboardComponent extends EntityComponent {
	
	public MotionComponent motion;
	public GravityComponent gravityComp;
	public InventoryComponent inventory;
	public boolean inventoryActive = false;
	public float movementSpeed;
	public float jumpingSpeed;
	
	public JumpKeyboardComponent(MotionComponent motion, GravityComponent gravityComp, float movementSpeed, float jumpingSpeed)
	{
		this.motion = motion;
		this.gravityComp = gravityComp;
		this.movementSpeed = movementSpeed;
		this.jumpingSpeed = jumpingSpeed;
	}
	
	@Override
	public void init(Entity entity, Map map, int entityIndex) {
		if(entity.getComponent("inventory") != null)
			this.inventory = (InventoryComponent) entity.getComponent("inventory");
	}
	
	public void update(Entity entity, Map map) {
		if(Gdx.input.isKeyJustPressed(Keys.E) || Gdx.input.isKeyJustPressed(Keys.I))
		{
			if(inventory != null && !getChat().isActive())
			{
				inventoryActive = inventory.toggle();
			}
		}
		if(!inventoryActive && !getChat().isActive())
		{
			if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE))
			{
				if(gravityComp.isStandingTileSolid(map))
				{
					motion.my = -jumpingSpeed;
					System.out.println("jumping!");
				}
			}
			if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
			{
				motion.mx -= movementSpeed;
			}
			if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				motion.mx += movementSpeed;
			}
			if(Gdx.input.isButtonPressed(Buttons.LEFT))
			{
				float dx = (map.getCameraPosition().x + Gdx.input.getX()) - (map.getCameraPosition().x + Gdx.graphics.getWidth() / 2);
				float dy = (map.getCameraPosition().y + Gdx.input.getY()) - (map.getCameraPosition().y + Gdx.graphics.getHeight() / 2);
				motion.mx += dx * 0.001f;
				motion.my = dy * 0.1f;
			}
			if(Gdx.input.isKeyJustPressed(Keys.R)) 
			{
				map.killEntityOfType(Player.class);
				printClientChat("You died!");
			}
		}
		else if(inventoryActive && !getChat().isActive())
		{
			if(Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.UP))
			{
				inventory.up();
			}
			if(Gdx.input.isKeyJustPressed(Keys.A) || Gdx.input.isKeyJustPressed(Keys.LEFT))
			{
				inventory.left();
			}
			if(Gdx.input.isKeyJustPressed(Keys.D) || Gdx.input.isKeyJustPressed(Keys.RIGHT))
			{
				inventory.right();
			}
			if(Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyJustPressed(Keys.DOWN))
			{
				inventory.down();
			}
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
		return "jumpkeyboard";
	}

}
