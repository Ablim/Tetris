package model;

import java.awt.Color;

import util.Position;

public interface BasicSquare {
	
	public void setPosition(Position p);
	
	public Position getPosition();
	
	public void setColor(Color c);

	public Color getColor();
	
	public int getHeight();
	
	public int getWidth();
	
	public void setFilledStatus(boolean b);
	
	public boolean isFilled();
}
