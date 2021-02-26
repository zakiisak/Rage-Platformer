package game.net.commands;

import com.esotericsoftware.kryonet.Connection;

import game.net.Command;
import game.net.Commands;
import game.net.NetServer;
import game.net.PacketMessage;

public class CommandHelp extends Command {

	private PacketMessage packet = new PacketMessage();
	
	@Override
	public String getCommand() {
		return "/help";
	}

	@Override
	public void invoke(Connection c, NetServer server, String message) {
		packet.id = c.getID();
		packet.message = 
		"*****-Help-*****\n";
		for(Command command : Commands.commands)
		{
			packet.message += " " + command.getUsage() + "\n";
		}
		c.sendTCP(packet);
	}

	public String getUsage() {
		return getCommand() + " (prints available commands)";
	}
	
	
	
}
