package game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.Sprite;

public class Checkbox extends Component {
	
	protected Sprite sprite;
	protected Sprite transitionSprite;
	protected int transitionFactor = 0;
	protected int transitionDirection = 1;
	protected ActionListener actionListener;
	protected boolean marked = false;
	protected boolean transitioning = false;
	
	public Checkbox(float x, float y, boolean marked)
	{
		setBounds(x, y, 16, 16);
		setMarked(marked);
	}
	
	public Checkbox(float x, float y)
	{
		this(x, y, false);
	}
	
	private void update()
	{
		if(containsMouse())
		{
			if(Gdx.input.justTouched() && Gdx.input.isButtonPressed(Buttons.LEFT)) 
			{
				marked = !marked;
				if(actionListener != null) 
				{
					actionListener.invoke(this);
				}
			}
			setTransitionSprite(marked ? Sprite.checkbox_hovered_marked : Sprite.checkbox_hovered);
		}
		else
		{
			setTransitionSprite(marked ? Sprite.checkbox_default_marked : Sprite.checkbox_default);
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
			sprite.render(batch, x, y, width, height);
			batch.setColor(1, 1, 1, frontAlpha);
			transitionSprite.render(batch, x, y, width, height);
		}
		else
		{	
			batch.setColor(1, 1, 1, 1);
			sprite.render(batch, x, y, width, height);
		}
	}
	
	public Checkbox setActionListener(ActionListener actionListener)
	{
		this.actionListener = actionListener;
		return this;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public ActionListener getActionListener()
	{
		return actionListener;
	}
	
	public boolean isMarked()
	{
		return marked;
	}
	
	private void setBothSprites(Sprite sprite)
	{
		this.sprite = sprite;
		this.transitionSprite = sprite;
	}
	
	public void setMarked(boolean marked)
	{
		setBothSprites(marked ? Sprite.checkbox_default_marked : Sprite.checkbox_default);
		this.marked = marked;
	}
	
}
