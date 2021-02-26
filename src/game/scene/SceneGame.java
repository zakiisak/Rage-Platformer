package game.scene;

import java.net.InetAddress;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import game.graphics.FontLoader;
import game.main.Main;
import game.net.Chat;
import game.net.Network;
import game.utils.FontUtils;
import game.world.ItemRegistry;
import game.world.Map;
import game.world.TileRegistry;
import game.world.entity.Platform;
import game.world.entity.Player;
import game.world.entity.Test;
import game.world.maps.Map001;

public class SceneGame extends Scene {

	public static int deathCount;
	public Map map;
	public static BitmapFont deathCountFont;
	public static BitmapFont chatFont;
	public static BitmapFont inventoryFont;
	public boolean paused = false;
	public static Chat chat;
	
	@Override
	public void gameLoad() {
		TileRegistry.load();
		ItemRegistry.load();
		deathCountFont = FontLoader.loadFontWithOutline(Gdx.files.local("res/CONSOLA.TTF"), 24, Color.WHITE, new Color(0, 0, 0, 0.5f), 2, false);
		chatFont = FontLoader.loadFontWithOutline(Gdx.files.local("res/CONSOLA.TTF"), 12, Color.BLACK, 1, false);
		inventoryFont = FontLoader.loadFontWithOutline(Gdx.files.local("res/CONSOLA.TTF"), 18, Color.BLACK, 2, false);
		chat = new Chat(chatFont);
		createWorld();
		startNetwork();
	}
	
	public void startNetworkAndSearch()
	{
		InetAddress address = Network.scanForFirstServer(Network.NetworkPort, 500);
		if(address != null)
		{
			System.out.println("Address: " + address.getHostAddress());
			System.out.println("Host Name: " + address.getHostName());
			Network.beginClient(address.getHostAddress(), Main.predifPort, this, false);
		}
		else
		{
			Network.beginServer(Main.predifPort);
			Network.beginClient("localhost", Main.predifPort, this, true);
		}
	}
	
	
	public void startNetwork()
	{
		String ip = null;
		if(Main.predifIP != null)
		{
			ip = Main.predifIP;
			Network.beginClient(ip, Main.predifPort, this, false);
		}
		else
		{
			InetAddress address = Network.scanForFirstServer(Network.NetworkPort, 500);
			if(address != null)
			{
				System.out.println("Address: " + address.getHostAddress());
				System.out.println("Host Name: " + address.getHostName());
				ip = address.getHostAddress();
				Network.beginClient(ip, Main.predifPort, this, false);
			}
			else
			{
				Network.beginServer(Main.predifPort);
				Network.beginClient("localhost", Main.predifPort, this, true);
			}
		}
	}
	
	public void createWorld()
	{
		map = new Map001();
		new Test(map);
		new Player(map, 7, 127 - 42);
		new Platform(map, 4, 127 - 4, 24, 16, 64, 0, 4.0F);
	}

	@Override
	public void preEnter() {
		// TODO Auto-generated method stub
		
	}

	private long lastTime = System.currentTimeMillis();
	
	@Override
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		chat.update();
		if(!paused && Network.client != null && Network.client.isConnected())
		{
			map.update();
		}
		map.render(batch);
		
		chat.render(batch);
		FontUtils.addText(deathCountFont, "death count: " + deathCount, Gdx.graphics.getWidth() - 16, 16, Color.WHITE, FontUtils.ALLIGN_RIGHT);
	
		if(System.currentTimeMillis() - lastTime > 1000)
		{
			this.lastTime = System.currentTimeMillis();
			System.out.println("entities: " + map.getEntities().size());
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		deathCountFont.dispose();
		chatFont.dispose();
		inventoryFont.dispose();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
