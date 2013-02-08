package model;

import java.awt.Color;

import util.Position;

public class OPart implements Part {
	
	private Color color = new Color(0, 255, 0);
	private int height = 2;
	private int width = 2;
	private BasicSquare[][] part = new Square[height][width];
	private Position position = new Position(0, 0);
	
	public OPart(Color background) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				part[i][j] = new Square(color, background);
				part[i][j].setFilledStatus(true);
			}
		}
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
	public void rotate() {}
}
