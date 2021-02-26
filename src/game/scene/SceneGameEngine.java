package game.scene;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import game.Game;
import game.graphics.FontLoader;
import game.gui.Button;
import game.gui.Checkbox;
import game.gui.Document;
import game.gui.Label;
import game.gui.RadioButton;
import game.gui.RadioButtonGroup;
import game.gui.TextField;
import game.utils.FontUtils;
import game.utils.Incrementer;

public class SceneGameEngine extends Scene {
	
	private BitmapFont font;
	private long lastTime = System.currentTimeMillis();
	private Incrementer rainbow = new Incrementer(0, 0, 1.0f, 0.001f, Incrementer.LIMIT_START_FROM_MIN);
	private Button button;
	private TextField box;
	private Checkbox checkBox;
	private RadioButtonGroup group;
	private Document document;
	
	@Override
	public void gameLoad() {
		font = FontLoader.loadFontWithOutline(Gdx.files.local("AGENCYR.ttf"), 110, Color.BLACK, 1, false);
		button = new Button(128, 256, "buti kati");
		box = new TextField(320, 227, 200, 20);
		checkBox = new Checkbox(256, 192);
		BitmapFont groupFont = FontLoader.loadFontWithOutline(Gdx.files.local("arial.ttf"), 16, Color.WHITE, Color.BLACK, 2, false);
		BitmapFont groupFont2 = FontLoader.loadFontWithOutline(Gdx.files.local("arial.ttf"), 48, Color.WHITE, Color.BLACK, 2, false);
		group = new RadioButtonGroup(512, 360).setGroupTopic(new Label("Test Radio Group").setFont(groupFont2).setColor(Color.BLACK)).setGroupDescription(new Label("This is a pretty\nepic\ndescription! :D")).setTooltipColor(Color.WHITE).setTooltipFont(groupFont).addRadioButtons(new RadioButton("Hi, this is a tooltip\nwow i'm on a new line! :D"), new RadioButton("Second tooltip, not much to show on this one."), new RadioButton("Well, this is the third..."));
		document = new Document(32, 192, 256, 256);
		add(button).add(box).add(checkBox).add(group);
		add(document);
	}

	@Override
	public void preEnter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		rainbow.increment();
		
		java.awt.Color color = java.awt.Color.getHSBColor(rainbow.getValue(), 1, 1);
		font.setColor((float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f, (float) color.getBlue() / 255.0f, 1.0f);
		FontUtils.addText(font, "Game Engine v" + Game.GAME_VERSION, Gdx.graphics.getWidth() / 2, 64, FontUtils.ALLIGN_MIDDLE);
		//FontUtils.draw(font, batch, "Game Engine v" + Game.GAME_VERSION, Gdx.graphics.getWidth() / 2, 64, FontUtils.ALLIGN_MIDDLE);
//		TiledRenderer.drawTiled(batch, Sprite.button_default, 128, 256, 200, 200, 1);
		if(System.currentTimeMillis() - lastTime > 1000)
		{
			lastTime = System.currentTimeMillis();
			Gdx.graphics.setTitle(config.title + " v" + Game.GAME_VERSION  + " | Heap Memory: " + Gdx.app.getNativeHeap());
		}
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void dispose() {
		font.dispose();
		
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
