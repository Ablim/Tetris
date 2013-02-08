package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.BasicSquare;
import model.GameModel;
import model.Part;
import model.Square;

public class GameArea implements Observer {

	private GameModel tetrisModel = GameModel.getInstance();
	private BasicSquare[][] grid; 
	private BasicSquare[][] nextPartGrid;
	private Part nextPart;
	private JPanel mainPanel = new JPanel();
	private JPanel infoPanel = new JPanel();
	private JPanel gridPanel = new JPanel();
	private JLabel scoreLabel = new JLabel(" Score: 0");
	private JLabel pausedLabel = new JLabel(" Paused (P)");
	private JLabel numberOfPartsLabel = new JLabel(" Parts: 0");
	private JLabel nextPartLabel = new JLabel(" Next part");
	private JLabel specialModeLabel = new JLabel(" Special mode (S)");
	private JPanel nextPartPanel = new JPanel();
	private JPanel labelPanel = new JPanel();
	private Color infoPanelBackground = new Color(50, 50, 50);
	
	public GameArea() {
		tetrisModel.addObserver(this);
		
		scoreLabel.setForeground(Color.green);
		scoreLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		numberOfPartsLabel.setForeground(Color.green);
		numberOfPartsLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		nextPartLabel.setForeground(Color.green);
		nextPartLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		specialModeLabel.setForeground(new Color(0, 90, 0));
		specialModeLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		pausedLabel.setForeground(new Color(0, 90, 0));
		pausedLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.setBackground(infoPanelBackground);
		labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelPanel.add(scoreLabel);
		labelPanel.add(numberOfPartsLabel);
		labelPanel.add(specialModeLabel);
		labelPanel.add(pausedLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(1, 15)));
		labelPanel.add(nextPartLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(1, 5)));
		
		nextPartPanel.setBackground(infoPanelBackground);
		nextPartPanel.setLayout(new GridLayout(3, 4));
		nextPartPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		nextPartPanel.setPreferredSize(new Dimension(100, 75));
		nextPartPanel.setMaximumSize(new Dimension(100, 75));
		
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(infoPanelBackground);
		infoPanel.add(labelPanel);
		infoPanel.add(nextPartPanel);
		
		gridPanel.setLayout(new GridLayout(tetrisModel.getNumberOfRows(), tetrisModel.getNumberOfColumns()));
		gridPanel.setPreferredSize(new Dimension(250, 600));
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(infoPanel, BorderLayout.EAST);
		mainPanel.add(gridPanel, BorderLayout.WEST);
		refresh();
	}
	
	public void refresh() {
		//Refresh the next part panel
		nextPartPanel.removeAll();
		
		if (tetrisModel.getNextPart() != null) {
			nextPart = tetrisModel.getNextPart();
			nextPartGrid = nextPart.getPart();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					if (i < nextPart.getHeight() && j < nextPart.getWidth()) {
						if (nextPartGrid[i][j].isFilled()) {
							nextPartPanel.add((JPanel)new Square(nextPart.getColor(), infoPanelBackground));
						} else {
							nextPartPanel.add((JPanel)new Square(infoPanelBackground, infoPanelBackground));
						}
					} else {
						nextPartPanel.add((JPanel)new Square(infoPanelBackground, infoPanelBackground));
					}
				}
			}
		} else {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					nextPartPanel.add((JPanel)new Square(infoPanelBackground, infoPanelBackground));
				}
			}
		}
		nextPartPanel.repaint();
		nextPartPanel.revalidate();
		
		gridPanel.removeAll();
		grid = tetrisModel.getField();
		for (int i = 0; i < tetrisModel.getNumberOfRows(); i++) {
			for (int j = 0; j < tetrisModel.getNumberOfColumns(); j++) {
				gridPanel.add((JPanel)grid[i][j]);
			}
		}
		gridPanel.repaint();
		gridPanel.revalidate();
		
		scoreLabel.setText(" Score: " + (int)tetrisModel.getScore());
		numberOfPartsLabel.setText(" Parts: " + tetrisModel.getAmountOfParts());
		
		if (tetrisModel.isPaused()) {
			pausedLabel.setForeground(Color.green);
		} else {
			pausedLabel.setForeground(new Color(0, 90, 0));
		}
		
		if (tetrisModel.isSpecialMode()) {
			specialModeLabel.setForeground(Color.green);
		} else {
			specialModeLabel.setForeground(new Color(0, 90, 0));
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		refresh();
	}
	
	public JPanel getComponent() {
		return mainPanel;
	}
}
