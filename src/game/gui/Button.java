package game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.Sprite;
import game.utils.FontUtils;

public class Button extends Component {
	
	protected String text;
	protected Color textColor = Color.BLACK;
	protected BitmapFont font;
	protected Sprite sprite;
	protected Sprite transitionSprite;
	protected int transitionFactor = 0;
	protected int transitionDirection = 1;
	protected ActionListener actionListener;
	protected boolean transitioning = false;
	
	public Button(float x, float y, float width, float height, String text, BitmapFont font)
	{
		setBounds(x, y, width, height);
		setText(text);
		setFont(font);
		transitionSprite = Sprite.button_default;
	}
	
	public Button(float x, float y, float width, float height, String text)
	{
		this(x, y, width, height, text, Gui.DefaultFont);
	}
	
	public Button(float x, float y, float width, float height)
	{
		this(x, y, width, height, "", Gui.DefaultFont);
	}
	
	public Button(float x, float y, BitmapFont font, String text)
	{
		this(x, y, FontUtils.getWidth(font, text), FontUtils.getHeight(font, text) * 1.5f, text, font);
	}
	
	public Button(float x, float y, String text)
	{
		this(x, y, Gui.DefaultFont, text);
	}
	
	private void update()
	{
		if(sprite == null) sprite = Sprite.button_default;
		if(containsMouse())
		{
			setTransitionSprite(Sprite.button_hovered);
			if(Gdx.input.justTouched()) 
			{
				if(actionListener != null && Gdx.input.isButtonPressed(Buttons.LEFT)) actionListener.invoke(this);
			}
		}
		else
		{
			setTransitionSprite(Sprite.button_default);
		}
		if(!sprite.equals(transitionSprite))
		{
			if(transitionFactor < 10)
			{
				transitionFactor += transitionDirection;
			}
			else
			{
				sprite = new Sprite(transitionSprite);
				transitioning = false;
			}
		}
	}
	
	private void setTransitionSprite(Sprite sprite)
	{
		if(!transitionSprite.equals(sprite) && !transitioning)
		{
			transitionFactor = 0;
			transitionSprite = new Sprite(sprite);
			transitioning = true;
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		update();
		if(!transitionSprite.equals(sprite))
		{
			float frontAlpha = (float) transitionFactor / 10.0f;
			batch.setColor(1, 1, 1, 1);
			TiledRenderer.drawTiledBox(batch, sprite, x, y, width, height, 1);
			batch.setColor(1, 1, 1, frontAlpha);
			TiledRenderer.drawTiledBox(batch, transitionSprite, x, y, width, height, 1);
		}
		else
		{	
			batch.setColor(1, 1, 1, 1);
			TiledRenderer.drawTiledBox(batch, sprite, x, y, width, height, 1);
		}
		FontUtils.addText(font, text, x + width / 2, y + height / 2, textColor, FontUtils.ALLIGN_MIDDLE_MIDDLE);
		//FontUtils.draw(font, batch, text, x + width / 2, y + height / 2, FontUtils.ALLIGN_MIDDLE_MIDDLE);
	}
	
	public Button setActionListener(ActionListener actionListener)
	{
		this.actionListener = actionListener;
		return this;
	}
	
	public Button setText(String text)
	{
		this.text = text;
		return this;
	}
	
	public Button setFont(BitmapFont font)
	{
		this.font = font;
		return this;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public String getText()
	{
		return text;
	}
	
	public BitmapFont getFont()
	{
		return font;
	}
	
	public ActionListener getActionListener()
	{
		return actionListener;
	}
	
	public Color getTextColor()
	{
		return textColor;
	}
	
	public Button setTextColor(Color textColor)
	{
		this.textColor = textColor;
		return this;
	}
	
}
