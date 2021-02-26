package game.net;

import java.util.ArrayList;
import java.util.List;

import game.net.commands.CommandHelp;
import game.net.commands.CommandPlayerList;

public class Commands {
	
	public static List<Command> commands = new ArrayList<Command>();
	
	public static void init()
	{
		commands.add(new CommandHelp());
		commands.add(new CommandPlayerList());
	}
	
}
