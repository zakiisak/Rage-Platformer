package game.world.tile;

import com.badlogic.gdx.Gdx;

import game.world.Tile;

public class TileAllSolid extends Tile {

	public TileAllSolid(short id) {
		super(id);
		solid = true;
		setSprites(Gdx.files.local("res/tiles/tileset_1.png"));
	}

}
