package game.world.tile;

import game.world.Map;
import game.world.Tile;
import game.world.entity.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class TileLava extends Tile {

	public TileLava(short id) {
		super(id);
		solid = false;
		setSprites(Gdx.files.local("res/tiles/lava.png"));
	}
	
	public boolean onCollision(Map map, Entity entity, int x, int y)
	{
		entity.kill();
		return true;
	}
	
	public Color getLight()
	{
		return new Color(0.2f, 0, 0, 1.0f);
	}

}
