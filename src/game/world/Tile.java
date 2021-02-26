package game.world;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.SheetLoader;
import game.graphics.Sprite;
import game.world.entity.Entity;

public abstract class Tile {
	public static final int TILE_SIZE = 32;
	public static Tile[] tiles = new Tile[256];
	
	protected short id;
	protected Sprite[] sprites;
	protected boolean solid = false;
	private int[] collisionBox = new int[] {0, 0, TILE_SIZE, TILE_SIZE};
	
	public Tile(short id)
	{
		this.id = id;
	}
	
	public short getID()
	{
		return id;
	}
	
	protected void setSprites(FileHandle handle)
	{
		this.sprites = SheetLoader.addTile(handle);
	}
	
	public void render(SpriteBatch spriteBatch, int x, int y, byte spriteID)
	{
		sprites[spriteID].render(spriteBatch, x, y, TILE_SIZE, TILE_SIZE);
	}
	
	public boolean onCollision(Map map, Entity entity, int x, int y)
	{
		return false;
	}

	public boolean isSolid(int offsetX, int offsetY) {
		if(!solid) return false;
		return offsetX >= collisionBox[0] && offsetY >= collisionBox[1]
				&& offsetX < collisionBox[2] && offsetY < collisionBox[3];
	}
	
	public boolean isColliding(int offsetX, int offsetY)
	{
		return offsetX >= collisionBox[0] && offsetY >= collisionBox[1]
				&& offsetX < collisionBox[2] && offsetY < collisionBox[3];
	}
	
	public Color getLight()
	{
		return null;
	}
	
	protected void setBounds(int offsetX, int offsetY, int width, int height)
	{
		collisionBox[0] = offsetX;
		collisionBox[1] = offsetY;
		collisionBox[2] = width;
		collisionBox[3] = height;
	}
	
	public int getHeight()
	{
		return collisionBox[3] - collisionBox[1];
	}
	
	public static int getOffset(float x)
	{
		return (int) (x - (float) ((int) ((x / (float) TILE_SIZE) * TILE_SIZE)));
	}
	
}
