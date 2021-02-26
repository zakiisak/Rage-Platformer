package game.graphics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;

public class SheetLoader {
	
	private static List<Pixmap> sheet = new ArrayList<Pixmap>();
	private static int sheetIndex = 0;
	private static int idIndex = 0;
	
	public static void construct() {
		int width = 0;
		int height = 0;
		for(Pixmap map : sheet) {
			width += map.getWidth();
			if(map.getHeight() > height) height = map.getHeight();
		}
		Pixmap buffer = new Pixmap(width, height, Format.RGBA8888);
		int offset = 0;
		for(Pixmap map : sheet) {
			for(int x = 0; x < map.getWidth(); x++) {
				for(int y = 0; y < map.getHeight(); y++) {
					buffer.drawPixel(x + offset, y, map.getPixel(x, y));
					
				}
			}
			offset += map.getWidth();
			map.dispose();
		}
		Sprite.SHEET = new Texture(buffer, true);
		Sprite.SHEET.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		Sprite.uniformX = 1.0f / (float) Sprite.SHEET.getWidth();
		Sprite.uniformY = 1.0f / (float) Sprite.SHEET.getHeight();
		buffer.dispose();
	}
	
	public static Sprite add(FileHandle handle) {
		Pixmap map = new Pixmap(handle);
		sheet.add(map);
		Sprite sprite = new Sprite(sheetIndex, 0, map.getWidth(), map.getHeight());
		sprite.id = idIndex;
		sheetIndex += map.getWidth();
		
		//Adding to registration
		Sprite.add(sprite, handle.nameWithoutExtension());
		idIndex++;
		return sprite;
	}
	
	public static Sprite add(String localPath)
	{
		return add(Gdx.files.local(localPath));
	}
	
	public static Sprite add(int[] pixels, int width, int height, String spriteName)
	{
		Pixmap map = new Pixmap(width, height, Format.RGBA8888);
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				map.drawPixel(x, y, pixels[x + y * width]);
			}
		}
		sheet.add(map);
		Sprite sprite = new Sprite(sheetIndex, 0, map.getWidth(), map.getHeight());
		sprite.id = idIndex;
		sheetIndex += map.getWidth();
		
		//Adding to registration
		Sprite.add(sprite, spriteName);
		idIndex++;
		return sprite;
	}
	
	public static Sprite[] addTile(FileHandle handle) {
		Pixmap map = new Pixmap(handle);
		sheet.add(map);
		int width = map.getWidth() / 16;
		int height = map.getHeight() / 16;
		Sprite[] sprites = new Sprite[width * height];
		
		//Reading the map to separate tiles
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Sprite sprite = new Sprite(sheetIndex + x * 16, y * 16, 16, 16);
				sprites[x + ((height - 1) - y) * width] = sprite;
			}
		}
		sheetIndex += map.getWidth();
		return sprites;
	}
}
