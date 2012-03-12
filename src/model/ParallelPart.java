package model;

import java.awt.Color;

import util.Position;

public class ParallelPart implements Part {

	private int height = 3;
	private int width = 3;
	private Color color = new Color(200, 200, 0);
	private BasicSquare[][] part = new Square[height][width];
	private Position position = new Position(0, 0);
	private boolean isHorizontal = true;
	
	public ParallelPart(Color background) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				part[i][j] = new Square(color, background);
				part[i][j].setFilledStatus(true);
			}
		}
		part[0][1] = new Square(background, background);
		part[1][1] = new Square(background, background);
		part[2][1] = new Square(background, background);
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
				newPart[i][newWidth - 1 - j] = part[j][i];
			}
		}
		
		if (isHorizontal) {
			isHorizontal = false;
		} else {
			isHorizontal = true;
		}
		
		height = newHeight;
		width = newWidth;
		part = newPart;
	}
}
