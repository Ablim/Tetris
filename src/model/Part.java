package model;

import java.awt.Color;

import util.Position;

public interface Part {
	public int getHeight();
	public int getWidth();
	public BasicSquare[][] getPart();
	public Position getPosition();
	public void setPosition(Position p);
	public Color getColor();
	public void rotate();
}
