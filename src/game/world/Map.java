package game.world;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.entity.Entity;
import game.world.entity.Player;
import game.world.entity.PlayerMP;
import game.world.entitycomponent.CollisionComponent;
import game.world.entitycomponent.TransformComponent;
import game.world.tile.TileWaypoint;

public class Map {
	
	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Entity> entitiesToBeAdded = new ArrayList<Entity>();
	protected List<CollisionComponent> collisionContacts = new ArrayList<CollisionComponent>();
	protected List<Light> lights = new ArrayList<Light>();
	protected List<PlayerMP> players = new ArrayList<PlayerMP>();
	private List<Entity> entityCache = new ArrayList<Entity>();
	protected short[] backTiles;
	protected byte[] backTileSprites;
	protected short[] tiles;
	protected byte[] tileSprites;
	protected Vector2f camera = new Vector2f();
	protected int width;
	protected int height;
	protected float gravity = 1.0f;
	protected Color backgroundColor = Color.DARK_GRAY;
	protected Color foregroundColor = Color.LIGHT_GRAY;
	protected short backgroundTile = 0;
	protected byte backgroundTileSprite = 0;
	protected boolean backgroundLoaded = false;
	
	public Map(FileHandle mapHandle, boolean backgroundLoad)
	{
		this.backgroundLoaded = backgroundLoad;
		try {
			ObjectInputStream oos = new ObjectInputStream(mapHandle.read());
			width = (int) oos.readObject();
			height = (int) oos.readObject();
			if(backgroundLoad)
			{
				backTiles = (short[]) oos.readObject();
				backTileSprites = (byte[]) oos.readObject();
				tiles = (short[]) oos.readObject();
				tileSprites = (byte[]) oos.readObject();
			}
			else
			{
				tiles = (short[]) oos.readObject();
				tileSprites = (byte[]) oos.readObject();
			}
			oos.close();
		} catch (Exception ex) {
			width = 0;
			height = 0;
			ex.printStackTrace();
			System.out.println("[World]: Unable to load world! (Map.java)");
			return;
		}
		
		short[] clonedTiles = tiles.clone();
		byte[] clonedTileSprites = tileSprites.clone();
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				int index = x + (height - 1 - y) * width;
				tiles[x + y * width] = clonedTiles[index];
				tileSprites[x + y * width] = clonedTileSprites[index];
			}
		}
		
		createLights();
	}
	
	protected void createLights()
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				if(getTile(x, y).getLight() != null)
				{
					addLight(new Light(x, y, getTile(x, y).getLight()));
				}
			}
		}
	}
	
	protected void applyTransformation(SpriteBatch spriteBatch)
	{
		spriteBatch.getTransformMatrix().setTranslation((int) -camera.x, (int) -camera.y, 0);
		spriteBatch.setTransformMatrix(spriteBatch.getTransformMatrix());
	}
	
	protected void revertTransformation(SpriteBatch spriteBatch)
	{
		spriteBatch.getTransformMatrix().setTranslation(0, 0, 0);
		spriteBatch.setTransformMatrix(spriteBatch.getTransformMatrix());
	}
	
	protected void renderBackground(SpriteBatch spriteBatch, int x0, int y0, int x1, int y1)
	{
		if(backgroundTile == 0) return;
		spriteBatch.setColor(backgroundColor);
		for(int x = x0; x < x1; x++)
		{
			for(int y = y0; y < y1; y++)
			{
				if(backgroundLoaded)
				{
					if(x < 0 || y < 0 || x >= width || y >= height) continue;
					short id = backTiles[x + y * width];
					if(id == 0) continue;
					Tile.tiles[id].render(spriteBatch, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, backTileSprites[x + y * width]);
				}
				else Tile.tiles[backgroundTile].render(spriteBatch, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, backgroundTileSprite);
				
			}
		}
	}
	
	protected void renderTiles(SpriteBatch spriteBatch, int x0, int y0, int x1, int y1)
	{
		spriteBatch.setColor(foregroundColor);
		for(int x = x0; x < x1; x++)
		{
			for(int y = y0; y < y1; y++)
			{
				if(x < 0 || y < 0 || x >= width || y >= height) continue;
				short id = tiles[x + y * width];
				if(id == 0) continue;
				Tile.tiles[id].render(spriteBatch, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, tileSprites[x + y * width]);
			}
		}
	}
	
	protected void updateEntities()
	{
		Iterator<Entity> toBeAdded = entitiesToBeAdded.iterator();
		while(toBeAdded.hasNext())
		{
			this.entities.add(toBeAdded.next());
			toBeAdded.remove();
		}
		for(int i = 0; i < entities.size(); i++)
		{
			Entity entity = entities.get(i);
			entity.update(this);
			if(entity.isDead())
			{
				entity.killComponents(this);
				entities.remove(i--);
			}
		}
		
		for(int i = 0; i < players.size(); i++)
		{
			players.get(i).update(this);
		}
	}
	
	protected void renderEntities(SpriteBatch spriteBatch)
	{
		
		for(Entity entity : entities)
		{
			entity.render(this, spriteBatch);
		}
		
		for(PlayerMP onlinePlayer : players)
		{
			onlinePlayer.render(this, spriteBatch);
		}
	}
	
	protected void renderPostEntities(SpriteBatch spriteBatch)
	{
		for(Entity entity : entities)
		{
			entity.renderPost(this, spriteBatch);
		}
		
		for(PlayerMP onlinePlayer : players)
		{
			onlinePlayer.renderPost(this, spriteBatch);
		}
	}
	
	protected void renderLights(SpriteBatch spriteBatch)
	{
		spriteBatch.setBlendFunction(GL11.GL_DST_COLOR, GL11.GL_ONE);
		for(Light light : lights)
		{
			light.render(this, spriteBatch);
		}
		spriteBatch.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		spriteBatch.setColor(Color.WHITE);
	}
	
	public void update()
	{
		updateEntities();

	}
	
	public void render(SpriteBatch spriteBatch)
	{
		int x0 = (int) camera.x / Tile.TILE_SIZE - 2;
		int y0 = (int) camera.y / Tile.TILE_SIZE - 2;
		int x1 = x0 + (int) Math.ceil((float)Gdx.graphics.getWidth() / (float) Tile.TILE_SIZE) + 4;
		int y1 = y0 + (int) Math.ceil((float)Gdx.graphics.getHeight() / (float) Tile.TILE_SIZE) + 4;
		applyTransformation(spriteBatch);
		renderBackground(spriteBatch, x0, y0, x1, y1);
		renderEntities(spriteBatch);
		renderTiles(spriteBatch, x0, y0, x1, y1);
		renderLights(spriteBatch);
		revertTransformation(spriteBatch);
		renderPostEntities(spriteBatch);
	}
	
	public int addEntity(Entity entity)
	{
		this.entitiesToBeAdded.add(entity);
		int index = this.entities.size() + this.entitiesToBeAdded.size() - 1;
		entity.initComponents(this, index);
		return index;
	}
	
	public int addLight(Light light)
	{
		this.lights.add(light);
		return this.lights.size() - 1;
	}
	
	public int addPlayerMP(PlayerMP playerMP)
	{
		this.players.add(playerMP);
		int index = this.players.size() - 1;
		playerMP.initComponents(this, index);
		return index;
	}
	
	public void removePlayerMP(int index)
	{
		this.players.get(index).killComponents(this);
		this.players.remove(index);
	}
	
	public int addCollisionContact(CollisionComponent collision)
	{
		this.collisionContacts.add(collision);
		return this.collisionContacts.size() - 1;
	}
	
	public void killLight(int index)
	{
		this.lights.remove(index);
	}
	
	public void killLight(Light light)
	{
		for(int i = 0; i < lights.size(); i++)
		{
			if(lights.get(i) == light)
			{
				lights.remove(i);
				return;
			}
		}
	}
	
	public void killEntity(int index)
	{
		this.entities.get(index).kill();
	}
	
	public void killEntity(Entity entity)
	{
		for(int i = 0; i < entities.size(); i++)
		{
			if(entities.get(i) == entity)
			{
				entities.get(i).kill();
				break;
			}
		}
	}
	
	public void killEntityOfType(Class<?> cls)
	{
		for(int i = 0; i < entities.size(); i++)
		{
			if(cls.isInstance(entities.get(i)))
			{
				entities.get(i).kill();
				break;
			}
		}
	}
	
	public Entity getEntity(int index)
	{
		return this.entities.get(index);
	}
	
	public Player getClientPlayer()
	{
		for(Entity e : entities)
			if(e instanceof Player) return (Player) e;
		return null;
	}
	
	public Light getLight(int index)
	{
		return this.lights.get(index);
	}
	
	public CollisionComponent getCollisionContact(int index)
	{
		return this.collisionContacts.get(index);
	}
	
	public List<Entity> getEntities()
	{
		return entities;
	}
	
	public List<Light> getLights()
	{
		return lights;
	}
	
	public List<PlayerMP> getOnlinePlayers()
	{
		return players;
	}
	
	public List<CollisionComponent> getCollisionContacts()
	{
		return collisionContacts;
	}
	
	public void killCollisionContact(int index)
	{
		this.collisionContacts.remove(index);
		for(CollisionComponent collisionContact : collisionContacts)
		{
			if(collisionContact.collisionIndex > index) collisionContact.collisionIndex--;
		}
	}
	
	public Vector2f getCameraPosition()
	{
		return camera;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public short getTileID(int x, int y)
	{
		if(x < 0 || y < 0 || x >= width || y >= height) return 0;
		return tiles[x + y * width];
	}
	
	public Tile getTile(int x, int y)
	{
		if(x < 0 || y < 0 || x >= width || y >= height) return Tile.tiles[0];
		return Tile.tiles[tiles[x + y * width]];
	}
	
	public void setTile(int x, int y, short id)
	{
		tiles[x + y * width] = id;
	}
	
	public void setTile(int x, int y, Tile tile)
	{
		setTile(x, y, tile.getID());
	}
	
	public float getGravity()
	{
		return gravity;
	}
	
	public boolean isPointColliding(float x, float y)
	{
		boolean solid = getTile((int) x / Tile.TILE_SIZE, (int) y / Tile.TILE_SIZE).isSolid((int) ((x - (float) (int) x) * Tile.TILE_SIZE), (int) ((y - (float) (int) y) * Tile.TILE_SIZE));
		if(solid) return true;
		for(CollisionComponent collisionContact : collisionContacts)
		{
			if(collisionContact.contains(x, y))
				return true;
		}
		return false;
	}
	
	public void gameover()
	{
		float tileX = TileWaypoint.waypointX * Tile.TILE_SIZE + Tile.TILE_SIZE / 2 - 8;
		float tileY = TileWaypoint.waypointY * Tile.TILE_SIZE + 4;
		for(int i = 0; i < 10; i++)
		{
			boolean colliding = false;
			for(int c = 0; c < 4; c++)
			{
				float xt = tileX + (c % 2) * 16;
				float yt = tileY + (c / 2) * 16;
				if(isPointColliding(xt, yt))
				{
					colliding = true;
					break;
				}
			}
			if(colliding) tileY -= Tile.TILE_SIZE;
		}
		new Player(this, tileX / Tile.TILE_SIZE, tileY / Tile.TILE_SIZE);
	}
	
	public List<Entity> getEntities(Entity srcEntity, float x, float y, float range)
	{
		entityCache.clear();
		for(Entity entity : entities)
		{
			TransformComponent transform = (TransformComponent) entity.getComponent("transform");
			if(entity == srcEntity || transform == null) continue;
			float distance = (float) Math.sqrt(Math.pow(x - transform.x, 2) + Math.pow(y - transform.y, 2));
			if(distance < range)
				entityCache.add(entity);
		}
		return entityCache;
	}
	
	public List<CollisionComponent> getCollisionContacts(CollisionComponent srcCollisionEntity, float range)
	{
		List<CollisionComponent> collisionContactCache = new ArrayList<CollisionComponent>();
		float x = srcCollisionEntity.transform.x;
		float y = srcCollisionEntity.transform.y;
		
		for(CollisionComponent collisionContact : collisionContacts)
		{
			float distance = (float) Math.sqrt(Math.pow(x - collisionContact.transform.x, 2) + Math.pow(y - collisionContact.transform.y, 2));
			if(distance < range)
				collisionContactCache.add(collisionContact);
		}
		return collisionContactCache;
	}
	
}
