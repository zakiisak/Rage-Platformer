package game.net.commands;

import com.esotericsoftware.kryonet.Connection;

import game.net.Command;
import game.net.NetServer;

public class CommandKick extends Command {

	@Override
	public String getCommand() {
		return "/kick";
	}

	@Override
	public void invoke(Connection c, NetServer server, String message) {
		
	}

	public String getUsage() {
		return getCommand() + "<playerName> (kicks a chosen player)";
	}
	
	
	
}
