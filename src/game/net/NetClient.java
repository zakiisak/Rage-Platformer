package game.net;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import game.Game;
import game.scene.SceneGame;
import game.world.entity.PlayerMP;

public class NetClient extends Listener {
	public static final int MAX_NETWORK_TRANSFER_SIZE = 768;// * 768 * 8 * 8 * 2;
	
	private boolean loggedIn = false;
	private Client client;
	private Map<Integer, PlayerMP> players = new HashMap<Integer, PlayerMP>();
	public SceneGame game;
	public boolean host = false;
	
	public void stop()
	{
		if(client != null) 
		{
			client.stop();
			client = null;
		}
		if(game != null)
		{
			for(int i = 0; i < game.map.getOnlinePlayers().size(); i++)
			{
				game.map.removePlayerMP(i);
			}
		}
		players.clear();
	}
	
	public void connected(Connection c) {
		loggedIn = true;
		Network.sendTCPPacket("login" + Game.NAME);
	}
	
	public void received(Connection c, Object o) {
		
		if(o instanceof String)
		{
			String lowercase = ((String) o).toLowerCase();
			if(lowercase.contains("resume"))
				game.paused = false;
		}
		else if(o instanceof PacketCreatePlayer)
		{
			System.out.println("Create Player!");
			int netID = ((PacketCreatePlayer) o).id;
			players.put(netID, new PlayerMP(game.map, netID));
		}
		else if(o instanceof PacketDestroyPlayer)
		{
			packetDestroy(o);
		}
		else if(o instanceof PacketTransform)
		{
			PacketTransform packet = ((PacketTransform)o);
			PlayerMP player = players.get(packet.id);
			if(player != null) player.setPosition(packet.x, packet.y);
		}
		else if(o instanceof PacketMessage)
		{
			game.chat.appendText(((PacketMessage)o).message);
		}
		else if(o instanceof PacketName)
		{
			PacketName packet = (PacketName) o;
			players.get(packet.id).setName(packet.name);
		}
	}
	
	public void packetDestroy(Object o)
	{
		System.out.println("Packet Destroy");
		int netID = ((PacketDestroyPlayer) o).id;
		System.out.println("netID : " + netID);
		
		for(int i = 0; i < game.map.getOnlinePlayers().size(); i++)
		{
			int playerNetID = game.map.getOnlinePlayers().get(i).netID;
			if(playerNetID == netID)
			{
				game.map.removePlayerMP(i);
				System.out.println("Size of world online players size: " + game.map.getOnlinePlayers().size());
				break;
			}
		}
		players.remove(netID);
	}
	
	public synchronized void disconnected(Connection c) {
		loggedIn = false;
		if(!host && !Game.shuttingDown)
		{
			JOptionPane.showMessageDialog(null, "The server has been closed...");
			Gdx.app.exit();
		}
		/*
		if(!Game.shuttingDown)
		{
			stop();
			if(!Network.beginServer(Main.predifPort))
			{
				game.startNetworkAndSearch();
			}
			else
			{
				if(Network.server != null) Network.server.stop();
				Network.beginClient("localhost", Main.predifPort, game);
			}
		}
		*/
	}
	
	public void connect(String ip, int tcpPort, int udpPort, boolean host) {
		this.host = host;
		int maxSize = MAX_NETWORK_TRANSFER_SIZE;
		this.client = new Client(maxSize, maxSize);
		Network.register(this.client.getKryo());
		this.client.addListener(this);
		this.client.start();
		
		game.paused = true;
		for(int i = 0; i < game.map.getOnlinePlayers().size(); i++)
		{
			game.map.removePlayerMP(i);
		}
		
		try {
			this.client.connect(1000, ip, tcpPort, udpPort);
		}
		catch(Exception e) {
			this.client = null;
			return;
		}
	}
	
	public InetAddress findFirstServer(short udpPort, int waitingResponse)
	{
		if(this.client == null) return null;
		return client.discoverHost(udpPort, waitingResponse);
	}
	
	public List<InetAddress> scanForServers(short udpPort, int waitingResponse)
	{
		if(this.client == null) return null;
		return client.discoverHosts(udpPort, waitingResponse);
	}
	
	public Client getCommunication()
	{
		return client;
	}
	
	public boolean isConnected()
	{
		if(this.client == null) loggedIn = false;
		return loggedIn;
	}
	
}
