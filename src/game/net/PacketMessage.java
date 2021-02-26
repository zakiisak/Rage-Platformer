package game.net;

public class PacketMessage {
	public int id;
	public String message;
	
	public PacketMessage() {}
	
	public PacketMessage(int id, String message)
	{
		this.id = id;
		this.message = message;
	}
}
