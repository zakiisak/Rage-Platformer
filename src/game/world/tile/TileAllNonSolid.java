package game.world.tile;

import com.badlogic.gdx.Gdx;

import game.world.Tile;

public class TileAllNonSolid extends Tile {

	public TileAllNonSolid(short id) {
		super(id);
		solid = false;
		setSprites(Gdx.files.local("res/tiles/tileset_1.png"));
	}

}
