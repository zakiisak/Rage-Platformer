package game.net;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import game.scene.SceneGame;

public class Network {
	public static final short NetworkPort = 7665;
	
	public static NetClient client;
	public static NetServer server;
	private static List<Object> packetQueueTCP = new ArrayList<Object>();
	private static List<Object> packetQueueUDP = new ArrayList<Object>();
	
	public static void register(Kryo kryo)
	{
		kryo.register(PacketCreatePlayer.class);
		kryo.register(PacketTransform.class);
		kryo.register(PacketDestroyPlayer.class);
		kryo.register(PacketMessage.class);
		kryo.register(PacketName.class);
	}
	
	public static void sendTCPPacket(Object packet)
	{
		packetQueueTCP.add(packet);
	}
	
	public static void sendUDPPacket(Object packet)
	{
		packetQueueUDP.add(packet);
	}
	
	public static void flushPackets()
	{
		if(client != null && client.isConnected())
		{
			for(Object packet : packetQueueTCP)
			{
				client.getCommunication().sendTCP(packet);
			}
			for(Object packet : packetQueueUDP)
			{
				client.getCommunication().sendUDP(packet);
			}
		}
		packetQueueTCP.clear();
		packetQueueUDP.clear();
	}
	
	public static void beginClient(String ip, short port, SceneGame game, boolean host)
	{
		if(client == null) client = new NetClient();
		else client.stop();
		if(client.game == null) client.game = game;
		client.connect(ip, port, port, host);
	}
	
	public static boolean beginServer(short port)
	{
		if(server == null) server = new NetServer();
		else server.stop();
		return server.start(port, port);
	}
	
	public static InetAddress scanForFirstServer(short port, int timeoutMillis)
	{
		return new Client().discoverHost(port, timeoutMillis);
	}
	
	public static void dispose()
	{
		if(client != null)
		{
			client.stop();
			client = null;
		}
		if(server != null)
		{
			server.stop();
			server = null;
		}
	}
}
