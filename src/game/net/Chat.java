package game.net;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.Input;
import game.graphics.FontBatch;
import game.graphics.Sprite;
import game.gui.TextAdapter;
import game.utils.FontUtils;

public class Chat implements TextAdapter {
	public static final int MAX_LINES = 8;
	public static final int MAX_WIDTH = 320;
	public static final int LOCATION_X = 8;
	public static final int LOCATION_Y_OFFSET = 32;
	
	protected List<Text> text = new ArrayList<Text>();
	protected int index;
	protected BitmapFont font;
	protected float height;
	protected Color color = Color.WHITE;
	protected float backColor = Color.toFloatBits(0, 0, 0, 0.25f);
	protected boolean active = false;
	protected boolean wrapWords = true;
	protected String textboxText = "";
	protected PacketMessage packetMessage;
	
	public Chat(BitmapFont font)
	{
		setFont(font);
		packetMessage = new PacketMessage();
	}
	
	public void input(char character)
	{
		if((int) character == 8)
		{
			if(textboxText.length() > 0)
				textboxText = textboxText.substring(0, textboxText.length() - 1);
		}
		else if((int) character >= 32)
		{
			textboxText += character;
		}
	}
	
	public void update()
	{
		if(Gdx.input.isKeyJustPressed(Keys.ENTER))
		{
			if(active)
			{
				if(textboxText.isEmpty())
				{
					Input.unbindTextAdapter();
					active = false;
					return;
				}
				//appendText(Game.NAME + ": " + textboxText);
				packetMessage.message = textboxText;
				Network.sendTCPPacket(packetMessage);
				//TODO Send text here
				Input.unbindTextAdapter();
			}
			active = !active;
			
			if(active)
			{
				Input.bindTextAdapter(this);
				textboxText = "";
			}
		}
	}
	
	public void render(SpriteBatch spriteBatch)
	{
		Sprite.white.render(spriteBatch, LOCATION_X - 2, Gdx.graphics.getHeight() - LOCATION_Y_OFFSET - height - 2, MAX_WIDTH + 4, height + 4, backColor);
		if(active) 
		{
			Sprite.white.render(spriteBatch, 8, Gdx.graphics.getHeight() - 28, Gdx.graphics.getWidth() - 16, 24);
			font.setColor(1, 1, 1, 1);
			FontBatch.addText(font, textboxText + "_", 8, Gdx.graphics.getHeight() - 28 + 2 + 12 - font.getLineHeight() / 2);
		}
		for(int i = MAX_LINES; i >= 0; i--)
		{
			int index = text.size() - MAX_LINES + i;
			if(index >= text.size() || index < 0) continue;
			Text text = this.text.get(index);
			if(!active) text.update();
			float y = Gdx.graphics.getHeight() - LOCATION_Y_OFFSET - height + i * font.getLineHeight();
			FontBatch.addText(font, text.text, LOCATION_X, y, text.getColor(this));
		}
	}
	
	public void appendText(String text)
	{
		if(text.contains("\n"))
		{
			String[] splitter = text.split("\n");
			for(int i = 0; i < splitter.length; i++)
			{
				appendText(splitter[i]);
			}
			return;
		}
		
		float width = FontUtils.getWidth(font, text);
		if(width > MAX_WIDTH)
		{
			if(wrapWords)
			{
				String[] splitter = text.split(" ");
				float x = 0;
				int index = 0;
				for(int i = 0; i < splitter.length; i++)
				{
					String word = splitter[i];
					System.out.println(word.length());
					if(word.trim().isEmpty())
					{
						x += font.getSpaceWidth();
						index++;
						continue;
					}
					x += FontUtils.getWidth(font, word);
					if(x > MAX_WIDTH)
					{
						this.text.add(new Text(text.substring(0, index), color));
						this.text.add(new Text(text.substring(index), color));
						return;
					}
					x += font.getSpaceWidth();
					index += word.length() + 1;
				}
			}
			else
			{
				for(int i = 0; i < text.length(); i++)
				{
					float tempWidth = FontUtils.getWidth(font, text.substring(0, i + 1));
					if(tempWidth > MAX_WIDTH)
					{
						this.text.add(new Text(text.substring(0, i + 1), color));
						this.text.add(new Text(text.substring(i + 1), color));
						return;
					}
				}
			}
		}
		this.text.add(new Text(text, color));
	}
	
	public List<Text> getText()
	{
		return text;
	}
	
	public String getText(int index)
	{
		return this.text.get(index).text;
	}
	
	public BitmapFont getFont()
	{
		return font;
	}
	
	public void setFont(BitmapFont font)
	{
		this.font = font;
		this.height = font.getLineHeight() * MAX_LINES;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	private class Text
	{
		private static final int WAIT_TIME = 5 * 60;
		private static final int FADE_TIME = 2 * 60;
		public String text;
		private Color color;
		public int waitCounter;
		public int fadeCounter;
		
		public Text(String text, final Color color)
		{
			this.text = text;
			this.color = color.cpy();
		}
		
		public void update()
		{
			if(waitCounter < WAIT_TIME) waitCounter++;
			else
			{
				if(fadeCounter < FADE_TIME) fadeCounter++;
			}
		}
		
		public Color getColor(Chat chat)
		{
			color.a = !chat.isActive() ? 1.0f - (float) fadeCounter / (float) FADE_TIME : 1.0f;
			return color;
		}
	}
	
}
