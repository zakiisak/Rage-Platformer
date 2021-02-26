package game.world.entitycomponent;

import game.world.Map;
import game.world.Tile;
import game.world.entity.Entity;

public class GravityComponent extends MotionDamperComponent {

	public CollisionComponent collision;
	public TransformComponent transform;
	public SizeComponent size;
	public boolean wasStandingOnGround = false;

	public GravityComponent(CollisionComponent collision, float horizontalDamper) {
		super(collision, horizontalDamper);
		this.collision = collision;
		this.transform = collision.transform;
		this.size = collision.size;
	}

	public void update(Entity entity, Map map) {
		motion.mx *= damper;
		boolean standingOnSolidGround = isStandingTileSolid(map);
		if (isHeadTileSolid(map))
			motion.my = 0;
		if (standingOnSolidGround && !wasStandingOnGround) {
			motion.my = 0;
		}
		if (!standingOnSolidGround) {
			motion.my += map.getGravity();
		}
		wasStandingOnGround = standingOnSolidGround;
	}

	public boolean isStandingTileSolid(Map map) {
		int offsetX = (int) (transform.x - (float) (int) (transform.x / Tile.TILE_SIZE) * Tile.TILE_SIZE);
		int offsetXRight = (int) ((transform.x + size.width)
				- (float) (int) ((transform.x + size.width) / Tile.TILE_SIZE) * Tile.TILE_SIZE);
		int offsetY = (int) (transform.y + size.height + 1
				- (float) (int) ((transform.y + size.height + 1) / Tile.TILE_SIZE) * Tile.TILE_SIZE);
		if (map.getTile((int) (transform.x) / Tile.TILE_SIZE, (int) (transform.y + size.height + 1) / Tile.TILE_SIZE)
				.isSolid(offsetX, offsetY)
				|| map.getTile((int) (transform.x + size.width) / Tile.TILE_SIZE,
						(int) (transform.y + size.height + 1) / Tile.TILE_SIZE).isSolid(offsetXRight, offsetY))
			return true;
		return collision.standingOnEntity(map);
	}

	public boolean isHeadTileSolid(Map map) {
		int offsetX = (int) (transform.x - (float) (int) (transform.x / Tile.TILE_SIZE) * Tile.TILE_SIZE);
		int offsetXRight = (int) ((transform.x + size.width)
				- (float) (int) ((transform.x + size.width) / Tile.TILE_SIZE) * Tile.TILE_SIZE);
		int offsetY = (int) (transform.y - 1 - (float) (int) ((transform.y - 1) / Tile.TILE_SIZE) * Tile.TILE_SIZE);
		if (map.getTile((int) (transform.x) / Tile.TILE_SIZE, (int) (transform.y - 1) / Tile.TILE_SIZE).isSolid(offsetX,
				offsetY)
				|| map.getTile((int) (transform.x + size.width) / Tile.TILE_SIZE,
						(int) (transform.y - 1) / Tile.TILE_SIZE).isSolid(offsetXRight, offsetY))
			return true;
		return collision.headingEntity(map);
	}

}
