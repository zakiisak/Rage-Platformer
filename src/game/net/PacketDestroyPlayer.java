package game.net;

public class PacketDestroyPlayer {
	public int id;
	
	public PacketDestroyPlayer() {}
	
	public PacketDestroyPlayer(int id)
	{
		this.id = id;
	}
}
