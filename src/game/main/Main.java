package game.main;

import game.Game;
import game.net.Network;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;

public class Main {
	
	public static String predifIP;
	public static short predifPort = Network.NetworkPort;
	public static void main(String[] args)
	{
		System.out.println(args.length);
		System.out.println(args);
		if(args.length == 1)
		{
			predifIP = args[0];
			System.out.println(predifIP);
		}
		else if(args.length == 2)
		{
			predifIP = args[0];
			predifPort = Short.parseShort(args[1]);
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.initialBackgroundColor = new Color(0.3f, 0.3f, 0.3f, 1);
		config.samples = 0;
		config.vSyncEnabled = true;
		config.title = "Finale MP Platformer " + Game.GAME_VERSION;
		Game game = new Game();
		game.config = config;
		new LwjglApplication(game, config);
	}
	
}
