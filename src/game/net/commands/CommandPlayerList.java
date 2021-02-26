package game.net.commands;

import com.esotericsoftware.kryonet.Connection;

import game.net.Command;
import game.net.NetServer;
import game.net.PacketMessage;

public class CommandPlayerList extends Command {

	private PacketMessage packet = new PacketMessage();
	
	@Override
	public String getCommand() {
		return "/list";
	}

	@Override
	public void invoke(Connection c, NetServer server, String message) {
		packet.id = c.getID();
		packet.message = 
		"Players Online:\n";
		for(String name : server.names.values())
		{
			packet.message += name + ", ";
		}
		packet.message = packet.message.substring(0, packet.message.length() - 2);
		c.sendTCP(packet);
	}

	public String getUsage() {
		return getCommand() + " (prints online players)";
	}
	
	
	
}
