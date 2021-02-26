package game.gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.Sprite;

public class Document extends Component {
	
	protected Sprite border = null;
	protected List<LocalComponent> components = new ArrayList<LocalComponent>();
	protected ComponentLocator locator = null;
	protected float lastX, lastY;
	protected float visibleWidth, visibleHeight;
	
	public Document(float x, float y, float width, float height)
	{
		setBounds(x, y, width, height);
		border = Sprite.document;
	}
	
	public void render(SpriteBatch batch) {
		if(lastX != x || lastY != y)
			updatePositions();
		if(border != null) TiledRenderer.drawTiledBorder(batch, border, x, y, width, height, 1);
		for(LocalComponent localComponent : components)
		{
			localComponent.component.render(batch);
		}
		lastX = x;
		lastY = y;
	}
	
	/**
	 * Main method for adding components to documents.
	 * @param component
	 * @return this document.
	 */
	public Document add(Component component)
	{
		LocalComponent localComponent = new LocalComponent();
		localComponent.component = component;
		localComponent.x = component.getX();
		localComponent.y = component.getY();
		components.add(localComponent);
		return this;
	}
	
	/**
	 * 
	 * @param component
	 * @return whether or not the component was removed from<br>
	 * the list or not. If the component is not a part of <br>
	 * this document, returns false.
	 */
	public boolean remove(Component component)
	{
		for(int i = 0; i < components.size(); i++)
		{
			LocalComponent localComponent = components.get(i);
			if(localComponent.component == component)
			{
				components.remove(i);
				return true;
			}
		}
		return false;
	}
	
	private void updatePositions()
	{
		if(locator == null)
			for(LocalComponent localComponent : components)
			{
				ComponentLocator locator = localComponent.locator;
				if(locator == null) localComponent.component.setPosition(this.x + localComponent.x, this.y + localComponent.y);
				else locator.setPosition(this, localComponent.component, localComponent.x, localComponent.y);
			}
		else
		{
			this.locator.preSetPosition(this);
			for(LocalComponent localComponent : components)
			{
				this.locator.setPosition(this, localComponent.component, localComponent.x, localComponent.y);
			}
			this.locator.postSetPosition(this);
		}
	}
	
	public void resizeDocument(int width, int height)
	{
		updatePositions();
	}
	
	public Sprite getBorderSprite()
	{
		return border;
	}
	
	public Document setBorderSprite(Sprite sprite)
	{
		this.border = sprite;
		return this;
	}
	
	public Component getComponent(int index)
	{
		return components.get(index).component;
	}
	
	public void clearComponents()
	{
		components.clear();
	}
	
	public ComponentLocator getComponentLocator()
	{
		return locator;
	}
	
	public void setComponentLocator(ComponentLocator locator)
	{
		this.locator = locator;
	}
	
	protected class LocalComponent
	{
		public Component component;
		public float x;
		public float y;
		public ComponentLocator locator;
	}

}
