package game.graphics;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Sprite {
	private static boolean FlipY = true;
	public static Texture SHEET;
	public static float uniformX, uniformY;
	
	private static Map<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	public static Sprite get(String name) {
		return sprites.get(name.toLowerCase());
	}
	
	public static void add(Sprite sprite, String name) {
		sprites.put(name.toLowerCase(), sprite);
	}
	
	public static void DisposeSheet() {
		SHEET.dispose();
	}
	
	public static final Sprite white = SheetLoader.add(new int[] {0xffffffff}, 1, 1, "white");
	
	public static Sprite button_default;
	public static Sprite button_hovered;
	public static Sprite button_pressed;
	public static Sprite textbox;
	public static Sprite checkbox_default;
	public static Sprite checkbox_default_marked;
	public static Sprite checkbox_hovered;
	public static Sprite checkbox_hovered_marked;
	public static Sprite radiobutton_default;
	public static Sprite radiobutton_default_marked;
	public static Sprite radiobutton_hovered;
	public static Sprite radiobutton_hovered_marked;
	public static Sprite document;
	public static Sprite scrollbar_back;
	public static Sprite scrollbar_default;
	public static Sprite scrollbar_hovered;
	
	public static Sprite scrollbar_button_back;
	public static Sprite scrollbar_button_default;
	public static Sprite scrollbar_button_hovered;
	
	public static Sprite light_map;
	public static Sprite trollface;
	public static Sprite frame;
	public static Sprite inventory_slot;
	public static Sprite inventory_slot_selected;
	
	public static Sprite item_cheese;
	
	public static final void load() {
		button_default = SheetLoader.add("res/ui/button_default.png");
		button_hovered = SheetLoader.add("res/ui/button_hovered.png");
		button_pressed = SheetLoader.add("res/ui/button_pressed.png");
		textbox = SheetLoader.add("res/ui/textbox.png");
		checkbox_default = SheetLoader.add("res/ui/checkbox_default.png");
		checkbox_default_marked = SheetLoader.add("res/ui/checkbox_default_marked.png");
		checkbox_hovered = SheetLoader.add("res/ui/checkbox_hovered.png");
		checkbox_hovered_marked = SheetLoader.add("res/ui/checkbox_hovered_marked.png");
		radiobutton_default = SheetLoader.add("res/ui/radiobutton_default.png");
		radiobutton_default_marked = SheetLoader.add("res/ui/radiobutton_default_marked.png");
		radiobutton_hovered = SheetLoader.add("res/ui/radiobutton_hovered.png");
		radiobutton_hovered_marked = SheetLoader.add("res/ui/radiobutton_hovered_marked.png");
		document = SheetLoader.add("res/ui/document.png");
		scrollbar_back = SheetLoader.add("res/ui/scrollbar_back.png");
		scrollbar_default = SheetLoader.add("res/ui/scrollbar_default.png");
		scrollbar_hovered = SheetLoader.add("res/ui/scrollbar_hovered.png");
		scrollbar_button_back = SheetLoader.add("res/ui/scrollbar_button_back.png");
		scrollbar_button_default = SheetLoader.add("res/ui/scrollbar_button_default.png");
		scrollbar_button_hovered = SheetLoader.add("res/ui/scrollbar_button_hovered.png");
		light_map = SheetLoader.add("res/sphere.png");
		trollface = SheetLoader.add("res/Trollface.png");
		frame = SheetLoader.add("res/frame.png");
		inventory_slot = SheetLoader.add("res/inventorySlot.png");
		inventory_slot_selected = SheetLoader.add("res/inventorySlotSelected.png");
		item_cheese = SheetLoader.add("res/items/cheese.png");
	}
	
	
	private int[] texels;
	private float[] uv;
	private boolean uvCreated = false;
	private float tint = Color.WHITE.toFloatBits();
	public final float[] vertices;
	public int id;
	
	public Sprite(int x, int y, int width, int height) {
		setTexels(new int[] {x, y, width, height});
		createUV();
		vertices = new float[] {0, 0, width, 0, width, height, 0, height};
	}
	
	public Sprite(Sprite sprite) {
		texels = sprite.texels;
		uv = sprite.uv;
		uvCreated = sprite.uvCreated;
		tint = sprite.tint;
		vertices = sprite.vertices;
		id = sprite.id;
	}
	
	public void createUV() {
		if(!uvCreated) {
			final float u = (float) texels[0] * uniformX;
			final float v = (float) (texels[1] + texels[3]) * uniformY;
			final float u2 = (float) (texels[0] + texels[2]) * uniformX;
			final float v2 = (float) texels[1] * uniformY;
			setUV(new float[] {u, v, u2, v2});
			uvCreated = true;
		}
	}
	
	public void render(SpriteBatch batch, float x, float y, float width, float height) {
		batch.draw(SHEET, x, y, width, height, texels[0], texels[1], texels[2], texels[3], false, FlipY);
	}
	
	public void render(SpriteBatch batch, float x, float y, float width, float height, Color tint) {
		batch.setColor(tint);
		batch.draw(SHEET, x, y, width, height, texels[0], texels[1], texels[2], texels[3], false, FlipY);
	}
	
	public void render(SpriteBatch batch, float x, float y, float width, float height, float tint) {
		batch.setColor(tint);
		batch.draw(SHEET, x, y, width, height, texels[0], texels[1], texels[2], texels[3], false, FlipY);
	}
	
	public void renderUV(SpriteBatch batch, float x, float y, float width, float height) {
		batch.draw(SHEET, x, y, width, height, uv[0], uv[1], uv[2], uv[3]);
	}
	
	public void renderUV(SpriteBatch batch, float x, float y, float width, float height, Color tint) {
		batch.setColor(tint);
		batch.draw(SHEET, x, y, width, height, uv[0], uv[1], uv[2], uv[3]);
	}
	
	public void renderUV(SpriteBatch batch, float x, float y, float width, float height, float tint) {
		batch.setColor(tint);
		batch.draw(SHEET, x, y, width, height, uv[0], uv[1], uv[2], uv[3]);
	}
	
	public void draw(SpriteBatch batch, float x, float y, float width, float height, int srcXOffset, int srcYOffset, int srcWidth, int srcHeight)
	{
		int srcX = texels[0] + srcXOffset;
		int srcY = texels[1] + srcYOffset;
		batch.draw(SHEET, x, y, width, height, srcX, srcY, srcWidth, srcHeight, false, FlipY);
	}
	
	public boolean equals(Sprite sprite)
	{
		return this.id == sprite.id;
	}
	
	public int[] getTexels() {
		return texels;
	}
	public void setTexels(int[] texels) {
		this.texels = texels;
	}
	
	public float[] getUV() {
		return uv;
	}
	public void setUV(float[] uv) {
		this.uv = uv;
	}

	public float getTint() {
		return tint;
	}

	public Sprite setTint(Color tint) {
		this.tint = tint.toFloatBits();
		return this;
	}
	
	public Sprite cpy() {
		return new Sprite(this);
	}
}
