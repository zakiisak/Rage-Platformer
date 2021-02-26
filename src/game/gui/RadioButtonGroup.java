package game.gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.FontBatch;
import game.graphics.Sprite;
import game.utils.FontUtils;

public class RadioButtonGroup extends Component {
	public static final int LIST_VERTICAL = 0;
	
	protected int listMode = LIST_VERTICAL;
	protected float offsetX, offsetY;
	protected float lastX = -1337, lastY;
	protected float spacing = 24 + 16 * 2;
	protected float topicSpacing = 16;
	protected float spacingBetweenTopicAndDesc = topicSpacing;
	protected BitmapFont tooltipFont = Gui.DefaultFont;
	protected Label groupTopic;
	protected Label groupDescription;
	protected Color tooltipColor = Color.BLACK;
	protected List<RadioButton> radioButtons = new ArrayList<RadioButton>();
	protected ActionListener actionListener = new ActionListener()
	{
		public void invoke(Component component) {
			for(RadioButton radioButton : radioButtons)
			{
				if(radioButton != component && radioButton.isMarked())
				{
					radioButton.setMarked(false);
					radioButton.sprite = Sprite.radiobutton_default_marked;
					radioButton.transitionSprite = Sprite.radiobutton_default;
					radioButton.transitioning = true;
					radioButton.transitionFactor = 0;
				}
			}
		}
	};
	
	public RadioButtonGroup()
	{
		
	}
	
	public RadioButtonGroup(int listMode)
	{
		this(0, 0, listMode);
	}
	
	public RadioButtonGroup(float x, float y, int listMode)
	{
		this.x = x;
		this.y = y;
		this.listMode = listMode;
	}
	
	public RadioButtonGroup(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void render(SpriteBatch batch)
	{
		if(lastX != x || lastY != y)
			updatePositions();
		batch.setColor(1, 1, 1, 1);
		for(RadioButton radioButton : radioButtons)
		{
			radioButton.render(batch);
		}
		for(int i = 0; i < radioButtons.size(); i++)
		{
			String tooltip = radioButtons.get(i).getTooltip();
			float x = radioButtons.get(i).getX();
			float y = radioButtons.get(i).getY();
			FontBatch.addText(tooltipFont, tooltip, x + radioButtons.get(i).getWidth() * 1.25f, y, tooltipColor);
			//tooltipFont.draw(batch, tooltip, x + radioButtons.get(i).getWidth() * 1.25f, y);
		}
		
		if(groupTopic != null)
		{
			batch.setColor(Color.WHITE);
			TiledRenderer.drawTiledBorderWithLabel(batch, Sprite.document, x - 4, y, width, height, 1, groupTopic);
			if(groupDescription != null)
			{
				groupDescription.render(batch, x + 2, y + groupTopic.getHeight() / 2 + spacingBetweenTopicAndDesc, FontUtils.ALLIGN_LEFT);
			}
		}
		
		lastX = x;
		lastY = y;
	}
	
	/**
	 * Main method for adding radio buttons to the group.
	 * @param button
	 * @return
	 */
	public RadioButtonGroup addRadioButton(RadioButton button)
	{
		button.setActionListener(this.actionListener);
		radioButtons.add(button);
		findSize();
		return this;
	}
	
	public RadioButtonGroup addRadioButtons(RadioButton... buttons)
	{
		for(RadioButton radioButton : buttons)
		{
			radioButton.setActionListener(this.actionListener);
			radioButtons.add(radioButton);
		}
		findSize();
		return this;
	}
	
	/**
	 * 
	 * @param button
	 * @return whether or not the given radio button<br>
	 * was removed from the group.<br>
	 * If this returns false, it means that this group <br>
	 * doesn't contain the given radio button.
	 */
	public boolean removeRadioButton(RadioButton button)
	{
		for(int i = 0; i < radioButtons.size(); i++)
		{
			RadioButton radioButton = radioButtons.get(i);
			if(radioButton == button)
			{
				if(radioButton.getActionListener() == this.actionListener) radioButton.setActionListener(null);
				radioButtons.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param index
	 * @return true if the radio button was successfully removed,<br>
	 * false if the given index was out of bounds.
	 */
	public boolean removeRadioButton(int index)
	{
		if(index < 0 || index >= radioButtons.size()) return false;
		
		RadioButton radioButton = radioButtons.get(index);
		if(radioButton.getActionListener() == this.actionListener) radioButton.setActionListener(null);
		radioButtons.remove(index);
		
		return true;
	}
	
	/**
	 * @return the index of the currently marked<br>
	 * radio button in the group. If no button is marked, returns -1.
	 */
	public int getSelectedIndex()
	{
		for(int i = 0; i < radioButtons.size(); i++)
		{
			if(radioButtons.get(i).isMarked()) return i;
		}
		return -1;
	}
	
	/**
	 * @return the currently marked radio button in the group. <br>
	 * If no button is marked, returns null.
	 */
	public RadioButton getSelectedRadioButton()
	{
		for(RadioButton radioButton : radioButtons)
		{
			if(radioButton.isMarked()) return radioButton;
		}
		return null;
	}
	
	protected void findSize()
	{
		float width = 0;
		if(groupDescription != null)
		{
			if(groupDescription.getWidth() > width)
				width = groupDescription.getWidth();
		}
		
		for(RadioButton radioButton : radioButtons)
		{
			float radioButtonWidth = radioButton.getWidth() * 1.25f + FontUtils.getWidth(tooltipFont, radioButton.getTooltip());
			if(radioButtonWidth > width)
				width = radioButtonWidth;
		}
		width += 4;
		
		//Height
		float height = 0;
		if(groupTopic != null)
		{
			if(groupTopic.getHeight() / 2 > topicSpacing)
			{
				spacingBetweenTopicAndDesc = groupTopic.getHeight() / 2 + topicSpacing;
				height += spacingBetweenTopicAndDesc;
			}
			else height += topicSpacing;
		}
		
		if(groupDescription != null)
		{
			height += groupDescription.getHeight();
		}
		
		height += topicSpacing;
		
		for(int i = 0; i < radioButtons.size(); i++)
		{
			RadioButton radioButton = radioButtons.get(i);
			float boxHeight = radioButton.getHeight();
			float textHeight = FontUtils.getHeight(tooltipFont, radioButton.getTooltip());
			float greatest = boxHeight > textHeight ? boxHeight : textHeight;
			float spacingSub = spacing - (greatest - radioButton.getHeight());
			if(i == radioButtons.size() - 1) spacingSub = 0;
			height += greatest + spacingSub;
		}
		
		height += topicSpacing / 2;
		
		setSize(width, height);
		
	}
	
	protected void updatePositions()
	{
		this.offsetX = x;
		this.offsetY = y;
		if(groupTopic != null)
		{
			offsetY += groupTopic.getHeight() / 2 + spacingBetweenTopicAndDesc;
			if(groupDescription != null)
			{
				offsetY += groupDescription.getHeight() + topicSpacing * 2.0f;
			}
		}
		for(RadioButton radioButton : radioButtons)
		{
			radioButton.setPosition(offsetX, offsetY);
			switch(listMode)
			{
				case LIST_VERTICAL:
					this.offsetY += spacing; break;
			}
		}
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getSpacing()
	{
		return spacing;
	}
	
	/**
	 * Sets the spacing between the radio buttons.
	 * @param spacing
	 */
	public RadioButtonGroup setSpacing(float spacing)
	{
		this.spacing = spacing;
		findSize();
		return this;
	}
	
	public RadioButtonGroup setListMode(int listMode)
	{
		this.listMode = listMode;
		return this;
	}
	
	public BitmapFont getTooltipFont()
	{
		return tooltipFont;
	}
	
	public RadioButtonGroup setTooltipFont(BitmapFont tooltipFont)
	{
		this.tooltipFont = tooltipFont;
		findSize();
		return this;
	}
	
	public Color getTooltipColor()
	{
		return tooltipColor;
	}
	
	public RadioButtonGroup setTooltipColor(Color color)
	{
		this.tooltipColor = color;
		return this;
	}
	
	public boolean noButtonSelected()
	{
		for(RadioButton radioButton : radioButtons)
		{
			if(radioButton.isMarked())
				return false;
		}
		return true;
	}
	
	public Label getGroupTopic()
	{
		return groupTopic;
	}
	
	public Label getGroupDescription()
	{
		return groupDescription;
	}
	
	public float getGroupTopicSpacing()
	{
		return topicSpacing;
	}
	
	public RadioButtonGroup setGroupTopic(Label groupTopicLabel)
	{
		this.groupTopic = groupTopicLabel;
		return this;
	}
	
	public RadioButtonGroup setGroupDescription(Label groupDescriptionLabel)
	{
		this.groupDescription = groupDescriptionLabel;
		return this;
	}
	
	public RadioButtonGroup setGroupTopicSpacing(float groupTopicSpacing)
	{
		this.topicSpacing = groupTopicSpacing;
		return this;
	}
	
	
}
