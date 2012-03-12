package model;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import util.Position;

@SuppressWarnings("serial")
public class Square extends JPanel implements BasicSquare {

	private Position position = new Position(0, 0);
	private Color bodyColor;
	private Color borderColor;
	private int height = 25;
	private int width = 25;
	private boolean isFilled = false;
	
	public Square(Color body, Color border) {
		bodyColor = body;
		borderColor = border;
	}

	@Override
	public void setPosition(Position p) {
		position = p;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public void setColor(Color c) {
		bodyColor = c;
	}

	@Override
	public Color getColor() {
		return bodyColor;
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
	public boolean isFilled() {
		return isFilled;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(borderColor);
		g.fillRect(0, 0, width, height);
		g.setColor(bodyColor);
		g.fillRect(1, 1, width - 4, height - 4);
		//g.setColor(color);
		//g.fillRect(3, 3, width - 6, height - 6);
	}

	@Override
	public void setFilledStatus(boolean b) {
		isFilled = b;
	}
}
