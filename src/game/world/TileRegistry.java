package game.world;

import game.world.tile.TileAir;
import game.world.tile.TileAllNonSolid;
import game.world.tile.TileAllSolid;
import game.world.tile.TileLava;
import game.world.tile.TileSpike;
import game.world.tile.TileWaypoint;

public class TileRegistry {
	private TileRegistry() {}
	
	public static void load()
	{
		register(new TileAir((short) 0));
		register(new TileAllSolid((short) 1));
		register(new TileLava((short) 2));
		register(new TileWaypoint((short) 3));
		register(new TileAllNonSolid((short) 4));
		register(new TileSpike((short) 5));
	}
	
	public static void register(Tile tile)
	{
		if(tile == null)
		{
			System.err.println("[TileRegistry] Attempted to register a null tile! Returning.");
			return;
		}
		Tile.tiles[tile.getID()] = tile;
	}
	
	
	
	
	
}
