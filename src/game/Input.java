package game;

import com.badlogic.gdx.InputAdapter;

import game.gui.TextAdapter;

public class Input extends InputAdapter {
	
	private static TextAdapter activeTextAdapter;
	
	public static void bindTextAdapter(TextAdapter adapter)
	{
		activeTextAdapter = adapter;
	}
	
	public static void unbindTextAdapter()
	{
		activeTextAdapter = null;
	}
	
	public static TextAdapter getBoundTextWriter()
	{
		return activeTextAdapter;
	}
	
	
	public boolean keyTyped(char character) {
		if(activeTextAdapter != null) activeTextAdapter.input(character);
		return false;
	}
	
}
