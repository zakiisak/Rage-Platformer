package game.net;

import com.esotericsoftware.kryonet.Connection;

public abstract class Command {

	public abstract String getCommand();
	public abstract String getUsage();
	public abstract void invoke(Connection c, NetServer server, String message);
	
}
