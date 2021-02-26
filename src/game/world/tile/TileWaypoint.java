package game.world.tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.utils.Incrementer;
import game.world.Map;
import game.world.Tile;
import game.world.entity.Entity;
import game.world.entity.Player;

public class TileWaypoint extends Tile {
	public static int waypointX, waypointY;
	private static final Incrementer rainbow = new Incrementer(0, 0, 1.0f, 0.001f, Incrementer.LIMIT_START_FROM_MIN);
	
	
	public TileWaypoint(short id) {
		super(id);
		solid = true;
		setSprites(Gdx.files.local("res/tiles/waypoint.png"));
		setBounds(0, 24, 32, 32);
	}
	
	public boolean onCollision(Map map, Entity entity, int x, int y)
	{
		if(entity instanceof Player && (waypointX != x || waypointY != y))
		{
			waypointX = x;
			waypointY = y;
		}
		return true;
	}
	
	public void render(SpriteBatch spriteBatch, int x, int y, byte spriteID)
	{
		if(x / Tile.TILE_SIZE == waypointX && y / Tile.TILE_SIZE == waypointY)
		{
			Color lastColor = spriteBatch.getColor();
			rainbow.increment();
			java.awt.Color color = java.awt.Color.getHSBColor(rainbow.getValue(), 1, 1);
			spriteBatch.setColor((float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f, (float) color.getBlue() / 255.0f, 1.0f);
			sprites[spriteID].render(spriteBatch, x, y, TILE_SIZE, TILE_SIZE);
			spriteBatch.setColor(lastColor);
		}
		else
		{
			sprites[spriteID].render(spriteBatch, x, y, TILE_SIZE, TILE_SIZE);
		}
	}

}
