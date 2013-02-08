package model;

import java.awt.Color;

import util.Position;

public class IPart implements Part {

	private int height = 1;
	private int width = 4;
	private Color color = new Color(255, 0, 0);
	private BasicSquare[][] part = new Square[height][width];
	private Position position = new Position(0, 0);
	private boolean isHorizontal = true;
	
	
	public IPart(Color background) {
		for (int i = 0; i < width; i++) {
			part[0][i] = new Square(color, background);
			part[0][i].setFilledStatus(true);
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
	public void rotate() {
		int newWidth = height;
		int newHeight = width;
		BasicSquare[][] newPart = new Square[newHeight][newWidth];
		
		for (int i = 0; i < newHeight; i++) {
			for (int j = 0; j < newWidth; j++) {
				newPart[i][j] = part[j][i];
			}
		}
		
		if (isHorizontal) {
			position = new Position(position.getRow() - 1, position.getColumn() + 1);
			isHorizontal = false;
		} else {
			position = new Position(position.getRow() + 1, position.getColumn() - 1);
			isHorizontal = true;
		}
		
		height = newHeight;
		width = newWidth;
		part = newPart;
	}
}










