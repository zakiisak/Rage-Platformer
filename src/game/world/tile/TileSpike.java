package game.world.tile;

import game.world.Map;
import game.world.Tile;
import game.world.entity.Entity;

import com.badlogic.gdx.Gdx;

public class TileSpike extends Tile {

	public TileSpike(short id) {
		super(id);
		solid = false;
		setSprites(Gdx.files.local("res/tiles/spikes.png"));
		setBounds(2, 2, 30, 30);
	}
	
	public boolean onCollision(Map map, Entity entity, int x, int y)
	{
		entity.kill();
		return true;
	}

}
