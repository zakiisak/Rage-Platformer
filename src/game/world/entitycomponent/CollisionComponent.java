package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.world.Map;
import game.world.Tile;
import game.world.entity.Entity;

public class CollisionComponent extends MotionComponent {
	
	public SizeComponent size;
	public int collisionIndex;
	/** Whether to push colliding entities or not.
	 * If two colliding entities are penetrating,
	 * they will move through each other. */
	public boolean penetrating = false;
	public boolean enabled = true;
	public boolean enabledWithPlayers = true;
	public boolean isPlayerContact = false;
	public boolean collideEntities;
	
	public CollisionComponent(TransformComponent transform, SizeComponent size, boolean penetrating, boolean collideEntities) {
		super(transform);
		this.size = size;
		this.penetrating = penetrating;
		this.collideEntities = collideEntities;
	}
	
	public CollisionComponent(TransformComponent transform, SizeComponent size, boolean penetrating) {
		this(transform, size, penetrating, true);
	}
	
	@Override
	public void init(Entity entity, Map map, int entityIndex)
	{
		if(collideEntities) this.collisionIndex = map.addCollisionContact(this);
	}
	
	@Override
	public void kill(Entity entity, Map map) {
		if(collideEntities) map.killCollisionContact(this.collisionIndex);
		enabled = false;
		enabledWithPlayers = false;
	}
	
	public boolean collidingTile(Map map, float xas, float yas) {
		int xa = (int) xas;
		int ya = (int) yas;
		for (int c = 0; c < 4; c++)
		{
			float xt = ((transform.x + xa) + c % 2 * (size.width)) / (float) Tile.TILE_SIZE;
			float yt = ((transform.y + ya) + c / 2 * (size.height)) / (float) Tile.TILE_SIZE;
			
			int offsetX = (int) ((xt - (float) (int) xt) * Tile.TILE_SIZE);
			int offsetY = (int) ((yt - (float) (int) yt) * Tile.TILE_SIZE);
			
			if (xt < 0 || yt < 0 || xt >= map.getWidth() || yt >= map.getHeight())
				continue;

			if (map.getTile((int) xt, (int) yt).isSolid(offsetX, offsetY))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean collidingEntity(Map map, float xas, float yas)
	{
		if(!collideEntities) return false;
		
		for(int i = 0; i < map.getCollisionContacts().size(); i++)
		{
			if(i == this.collisionIndex) continue;
			CollisionComponent collisionContact = map.getCollisionContact(i);
			for(int c = 0; c < 4; c++)
			{
				float xt = ((transform.x + xas) + (c % 2) * size.width);
				float yt = ((transform.y + yas) + (c / 2) * size.height);
				if(collisionContact.contains(xt, yt))
					return true;
			}
			
		}
		return false;
	}
	
	protected int abs(double value) {
		if (value < 0)
			return -1;
		return 1;
	}
	
	public void pushContacts(Map map, float xas, float yas)
	{
		for(int i = 0; i < map.getCollisionContacts().size(); i++)
		{
			if(i == this.collisionIndex) continue;
			CollisionComponent collisionContact = map.getCollisionContact(i);
			if(collisionContact.penetrating) continue;
			
			float left = ((collisionContact.transform.x));
			float top = ((collisionContact.transform.y));
			float right = ((collisionContact.transform.x) + collisionContact.size.width);
			float bottom = ((collisionContact.transform.y + 1) + collisionContact.size.height);
			if(xas < 0)
			{
				if(contains(right, top) || contains(right, bottom))
				{
					//collisionContact.mx = Math.min(collisionContact.mx, mx);
					collisionContact.move(map, xas, yas);
				}
			}
			if(xas > 0)
			{
				if(contains(left, top) || contains(left, bottom))
				{
					//collisionContact.mx = Math.max(collisionContact.mx, mx);
					collisionContact.move(map, xas, yas);
				}
			}
			if(yas < 0)
			{
				if(contains(left, bottom) || contains(right, bottom))
				{
					//collisionContact.my = Math.min(collisionContact.my, my);
					collisionContact.move(map, xas, yas);
				}
			}
			if(yas > 0)
			{
				if(contains(left, top) || contains(right, top))
				{
					//collisionContact.my = Math.max(collisionContact.my, my);
					collisionContact.move(map, xas, yas);
				}
				if(contains(left, bottom + 2) || contains(right, bottom + 2))
				{
					collisionContact.transform.y += 1;
				}
			}
		}
	}
	
	private float widthAbs(float mx)
	{
		if(mx < 0)
			return abs(mx);
		return size.width + abs(mx);
	}
	
	public void move(Map map, float mx, float my) {
		if (mx != 0 && my != 0) {
			move(map, mx, 0);
			move(map, 0, my);
			return;
		}
		if(!penetrating)
		{
			while (mx != 0) {
				if (Math.abs(mx) > 1) {
					if (!collidingTile(map, abs(mx), my) && !collidingEntity(map, abs(mx), my)) {
						transform.x += abs(mx);
					}
					else
					{
						Tile t = map.getTile((int) (transform.x + widthAbs(mx)) / Tile.TILE_SIZE, (int) (transform.y + size.height / 2) / Tile.TILE_SIZE);
						if(t.getHeight() <= 16)
						{
							transform.y -= t.getHeight(); //FIX
						}
					}
					mx -= abs(mx);
				} else {
					if (!collidingTile(map, abs(mx), my) && !collidingEntity(map, abs(mx), my)) {
						transform.x += mx;
					}
					else
					{
						Tile t = map.getTile((int) (transform.x + widthAbs(mx)) / Tile.TILE_SIZE, (int) (transform.y + size.height / 2) / Tile.TILE_SIZE);
						if(t.getHeight() <= 16)
						{
							transform.y -= t.getHeight(); //FIX
						}
					}
					mx = 0;
				}
			}
			while (my != 0) {
				if (Math.abs(my) > 1) {
					if (!collidingTile(map, mx, abs(my)) && !collidingEntity(map, mx, abs(my))) {
						transform.y += abs(my);
					}
					my -= abs(my);
				} else {
					if (!collidingTile(map, mx, abs(my)) && !collidingEntity(map, mx, abs(my))) {
						transform.y += my;
					}
					my = 0;
				}
			}
			
		}
		else
		{
			while (mx != 0) {
				if (Math.abs(mx) > 1) {
					pushContacts(map, abs(mx), my);
					transform.x += abs(mx);
					mx -= abs(mx);
				} else {
					pushContacts(map, abs(mx), my);
					transform.x += mx;
					mx = 0;
				}
			}
			while (my != 0) {
				if (Math.abs(my) > 1) {
					pushContacts(map, mx, abs(my));
					transform.y += abs(my);
					my -= abs(my);
				} else {
					pushContacts(map, mx, abs(my));
					transform.y += my;
					my = 0;
				}
			}
		}
	}
	
	@Override
	public void update(Entity entity, Map map) {
		if(mx != 0 || my != 0)
			move(map, mx, my);
		for(int c = 0; c < 4; c++)
		{
			//int x = (int) (transform.x + c % 2 * size.width + 1) / Tile.TILE_SIZE;
			//int y = (int) (transform.y + c / 2 * size.height + 1) / Tile.TILE_SIZE;
			float xt = (transform.x - 1 + c % 2 * size.width + 2) / (float) Tile.TILE_SIZE;
			float yt = (transform.y - 1 + c / 2 * size.height + 2) / (float) Tile.TILE_SIZE;
			
			int offsetX = (int) ((xt - (float) (int) xt) * Tile.TILE_SIZE);
			int offsetY = (int) ((yt - (float) (int) yt) * Tile.TILE_SIZE);
			Tile t = map.getTile((int) xt, (int) yt);
			if(t.isColliding(offsetX, offsetY))
				if(t.onCollision(map, entity, (int) xt, (int) yt)) break;
		}
	}

	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
	}
	
	public boolean intersects(CollisionComponent collisionContact)
	{
		/*
		return !(r2.left > r1.right
			    || r2.right < r1.left
			    || r2.top < r1.bottom
			    || r2.bottom > r1.top);
			    */
		return (abs(transform.x - collisionContact.transform.x) * 2 < (size.width + collisionContact.size.width)) &&
		         (abs(transform.y - collisionContact.transform.y) * 2 < (size.height + collisionContact.size.height));
	}
	
	public boolean contains(float x, float y)
	{
		return x >= transform.x &&
				y >= transform.y &&
				x < transform.x + size.width &&
				y < transform.y + size.height;
	}
	
	public boolean standingOnEntity(Map map)
	{
		for(int i = 0; i < map.getCollisionContacts().size(); i++)
		{
			if(i == this.collisionIndex) continue;
			CollisionComponent collisionContact = map.getCollisionContact(i);
			if(collisionContact.contains(transform.x, transform.y + size.height + 1)
					|| collisionContact.contains(transform.x + size.width, transform.y + size.height + 1))
				return true;
			
		}
		return false;
	}
	
	public boolean headingEntity(Map map)
	{
		for(int i = 0; i < map.getCollisionContacts().size(); i++)
		{
			if(i == this.collisionIndex) continue;
			CollisionComponent collisionContact = map.getCollisionContact(i);
			if(collisionContact.contains(transform.x, transform.y - 1)
					|| collisionContact.contains(transform.x + size.width, transform.y - 1))
				return true;
			
		}
		return false;
	}
	
	public boolean isColliding(Map map, float offsetX, float offsetY)
	{
		return collidingTile(map, offsetX, offsetY) || collidingEntity(map, offsetX, offsetY);
	}
	
	@Override
	public String getKeyIdentifier() {
		return "collision";
	}

}
