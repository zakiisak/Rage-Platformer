package game.net;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import game.world.entity.PlayerMP;

public class NetServer extends Listener {
	public static final int MAX_NETWORK_TRANSFER_SIZE = 768;// * 768 * 8 * 8 * 2;
	
	public Server server;
	
	public Map<Integer, PlayerMP> players = new HashMap<Integer, PlayerMP>();
	public Map<Integer, String> names = new HashMap<Integer, String>();
	public Map<Integer, String> lowercasedNames = new HashMap<Integer, String>();
	public Map<String, Integer> lowercasedIDs = new HashMap<String, Integer>();
	
	public boolean start(int tcpPort, int udpPort) {
		this.server = new Server(MAX_NETWORK_TRANSFER_SIZE, MAX_NETWORK_TRANSFER_SIZE);
		Network.register(this.server.getKryo());
		try {
			this.server.bind(tcpPort, udpPort);
		} catch (Exception e) {
			System.out.println(
					"Unable to start server on ports: [tcpPort=" + tcpPort
							+ ";udpPort=" + udpPort + "] - \n" + e.getMessage());
			return false;
		}
		this.server.start();
		this.server.addListener(this);
		return true;
	}
	
	public void connected(Connection c) {
		System.out.println("Client connected!");
		players.put(c.getID(), new PlayerMP(null, c.getID()));
		server.sendToAllExceptTCP(c.getID(), new PacketCreatePlayer(c.getID()));
		for(PlayerMP playerMP : players.values())
		{
			if(playerMP.netID != c.getID())
			{
				c.sendTCP(new PacketCreatePlayer(playerMP.netID));
			}
		}
		c.sendTCP("resume");
	}

	public void received(Connection c, Object o) {
		if(o instanceof PacketTransform)
		{
			((PacketTransform) o).id = c.getID();
			server.sendToAllExceptUDP(c.getID(), o);
		}
		else if(o instanceof PacketMessage)
		{
			packetMessage(c, o);
		}
		else if(o instanceof String)
		{
			String lowercase = ((String) o).toLowerCase();
			if(lowercase.startsWith("login"))
			{
				String name = ((String) o).substring("login".length());
				PacketName packet = new PacketName();
				for(int id : names.keySet())
				{
					packet.id = id;
					packet.name = names.get(id);
					c.sendTCP(packet);
				}
				names.put(c.getID(), name);
				lowercasedNames.put(c.getID(), name.toLowerCase());
				lowercasedIDs.put(name.toLowerCase(), c.getID());
				server.sendToAllExceptTCP(c.getID(), new PacketMessage(c.getID(), name + " has joined!"));
				
				packet.id = c.getID();
				packet.name = name;
				server.sendToAllExceptTCP(c.getID(), packet);
			}
		}
	}

	public void disconnected(Connection c) {
		System.out.println("Disconnection id: " + c.getID());
		players.remove(c.getID());
		server.sendToAllExceptTCP(c.getID(), new PacketDestroyPlayer(c.getID()));
		server.sendToAllExceptTCP(c.getID(), new PacketMessage(c.getID(), names.get(c.getID()) + " has squidded E8"));
		names.remove(c.getID());
		lowercasedIDs.remove(lowercasedNames.get(c.getID()));
		lowercasedNames.remove(c.getID());
	}
	
	public void packetMessage(Connection c, Object o)
	{
		PacketMessage packet = (PacketMessage) o;
		String message = packet.message;
		
		if(!handleCommands(c, packet)) server.sendToAllTCP(new PacketMessage(c.getID(), names.get(c.getID()) + ": " + message));
	}
	
	public boolean handleCommands(Connection c, PacketMessage packet)
	{
		String commandApproach = packet.message.toLowerCase();
		for(Command command : Commands.commands)
		{
			if(commandApproach.equalsIgnoreCase(command.getCommand()))
			{
				command.invoke(c, this, commandApproach);
				return true;
			}
		}
//		if(command.startsWith("/kick"))
//		{
//			String name = command.substring("/kick".length());
//		}
		return commandApproach.startsWith("/");
	}
	
	public Server getCommunication() {
		return server;
	}

	public void stop() {
		if(server != null) 
		{
			server.stop();
			server = null;
		}
		players.clear();
	}
	
}
