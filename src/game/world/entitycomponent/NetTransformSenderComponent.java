package game.world.entitycomponent;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.net.Network;
import game.net.PacketTransform;
import game.world.Map;
import game.world.entity.Entity;

public class NetTransformSenderComponent extends EntityComponent {
	
	public TransformComponent transform;
	public PacketTransform transformPacket;
	
	public NetTransformSenderComponent(TransformComponent transform)
	{
		this.transform = transform;
		transformPacket = new PacketTransform();
	}
	
	public void update(Entity entity, Map map) {
		transformPacket.x = transform.x;
		transformPacket.y = transform.y;
		Network.sendUDPPacket(transformPacket);
	}

	@Override
	public void render(Entity entity, Map map, SpriteBatch spriteBatch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill(Entity entity, Map map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getKeyIdentifier() {
		return "nettransformsender";
	}

}
