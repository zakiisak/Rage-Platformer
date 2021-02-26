package game.world.tile;

import game.world.Tile;

public class TileAir extends Tile {

	public TileAir(short id) {
		super(id);
		solid = false;
	}

}
