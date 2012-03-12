package model;

import java.awt.Color;

import util.Position;

public class SingleSquarePart implements Part{

	private int height = 1;
	private int width = 1;
	private BasicSquare[][] part = new Square[1][1];
	private Position position = new Position(0, 0);
	private Color color = Color.pink;
	
	public SingleSquarePart(Color background) {
		part[0][0] = new Square(color, background);
		part[0][0].setFilledStatus(true);
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public BasicSquare[][] getPart() {
		return part;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public void setPosition(Position p) {
		position = p;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void rotate() {
		
	}

}
