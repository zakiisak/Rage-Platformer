package game.world.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import game.world.Map;
import game.world.Tile;
import game.world.entity.DropItem;
import game.world.tile.TileWaypoint;

public class Map001 extends Map {

	public Map001() {
		super(Gdx.files.local("res/maps/platformer_map.cgw"), false);
		TileWaypoint.waypointX = 7;
		TileWaypoint.waypointY = 127 - 31;
		backgroundColor = Color.DARK_GRAY;
		backgroundTile = (short) 1;
		backgroundTileSprite = (byte) (4 + (7 - 0) * 8); //Y-axis is reversed
		addEntity(new DropItem("cheese", 19 * Tile.TILE_SIZE, (height - 1 - 59) * Tile.TILE_SIZE, false));
		addEntity(new DropItem("cheese", 19 * Tile.TILE_SIZE, (height - 1 - 59) * Tile.TILE_SIZE, false));
		addEntity(new DropItem("cheese", 19 * Tile.TILE_SIZE, (height - 1 - 59) * Tile.TILE_SIZE, false));
		addEntity(new DropItem("cheese", 19 * Tile.TILE_SIZE, (height - 1 - 59) * Tile.TILE_SIZE, false));
		addEntity(new DropItem("cheese", 19 * Tile.TILE_SIZE, (height - 1 - 59) * Tile.TILE_SIZE, false));
		addEntity(new DropItem("cheese", 19 * Tile.TILE_SIZE, (height - 1 - 59) * Tile.TILE_SIZE, false));
		addEntity(new DropItem("cheese", 19 * Tile.TILE_SIZE, (height - 1 - 59) * Tile.TILE_SIZE, false));
		
	}

}
