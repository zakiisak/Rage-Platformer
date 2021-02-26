package game.world.entity;

import game.world.Map;

public class Test extends Entity {
	
	public Test(Map map)
	{
		map.addEntity(this);
	}
	
}
