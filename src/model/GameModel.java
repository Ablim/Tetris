package model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import util.Position;
import view.MessageDialog;


public class GameModel extends Observable implements ActionListener {

	private int rows = 24;
	private int columns = 10;
	private BasicSquare[][] grid = new Square[rows][columns];
	private boolean isRunning = false;
	private static GameModel model;
	private Part activePart;
	private Part nextPart;
	private boolean canAdd;
	private Timer delay = new Timer(1000, this);
	private double score = 0;
	private Color background = Color.black;
	private Scanner scanner;
	private LinkedList<String> highscoreList = new LinkedList<String>();
	private String activePlayer = "Unnamed player";
	private boolean isPaused = false;
	private int amountOfParts = 0;
	private boolean specialMode = false;
	
	private GameModel() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				grid[i][j] = new Square(background, background);
			}
		}
		readHighscoreFromFile();
	}
	
	public static GameModel getInstance() {
		if (model == null) {
			model = new GameModel();
		}
		return model;
	}
	
	public BasicSquare[][] getField() {
		return grid;
	}
	
	public void start() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				grid[i][j] = new Square(background, background);
			}
		}
		
		score = 0;
		amountOfParts = 0;
		delay.setDelay(1000);
		addPart();
		if (!isPaused) {
			delay.start();
		}
		isRunning = true;	
	}
	
	public void stop() {
		isRunning = false;
		delay.stop();
		activePart = null;
		activePlayer = JOptionPane.showInputDialog(null, "Your score: " + (int)score + "\nEnter name:", "Game Over!", JOptionPane.PLAIN_MESSAGE);
		saveScore();
		String highScore = "";
		for (int i = 0; i < 5; i++) {
			highScore = highScore + (i + 1) + ": " + highscoreList.get(i) + "\n";
		}
		new MessageDialog("High Score!", highScore);
	}
	
	
	/**
	 * Saves the current score in a high score list, and prints it to a file
	 */
	private void saveScore() {
		for (int i = 0; i < 5; i++) {
			Scanner sc = new Scanner(highscoreList.get(i));
			if (score > sc.nextInt()) {
				if (activePlayer == null) {
					activePlayer = "Unnamed player";
				}
				highscoreList.add(i, (int)score + "\t" + activePlayer);
				break;
			}
		}
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("highscore.txt"));
			
			for (int i = 0; i < 5; i++) {
				if (i < 4) {
					out.write(highscoreList.get(i));
					out.newLine();
				} else {
					out.write(highscoreList.get(i));
				}
			}
			out.close();
		} catch (Exception e) {}
	}

	
	
	private void readHighscoreFromFile() {
		try {
			scanner = new Scanner(new File("highscore.txt"));
		} catch (FileNotFoundException e) {}
		
		for (int i = 0; i < 5; i++) {
			if (scanner != null) {
				if (scanner.hasNext()) {
					highscoreList.add(scanner.nextLine());
				} else {
					highscoreList.add(0 + "\t" + "No player");
				}
			} else {
				highscoreList.add(0 + "\t" + "No player");
			}
		}
	}
	
	public int getNumberOfRows() {
		return rows;
	}
	
	public int getNumberOfColumns() {
		return columns;
	}
	
	private void addPart() {
		if (activePart == null) {
			activePart = randomizePart();
		} else {
			activePart = nextPart;
		}
		nextPart = randomizePart();
		canAdd = true;
		amountOfParts++;
		
		activePart.setPosition(new Position(0, (columns / 2) - (activePart.getWidth() / 2)));
		if (isRoomForPart(activePart, activePart.getPosition())) {
			addPartToPosition(activePart, activePart.getPosition());
		} else {
			canAdd = false;
		}
	}
	
	private void addPartToPosition(Part p, Position pos) {
		for (int i = 0; i < p.getHeight(); i++) {
			for (int j = 0; j < p.getWidth(); j++) {
				if (p.getPart()[i][j].isFilled()) {
					grid[pos.getRow() + i][pos.getColumn() + j] = p.getPart()[i][j];
				}
			}
		}
		setChanged();
		notifyObservers();
	}
	
	private void clearPart(Part p) {
		for (int i = 0; i < p.getHeight(); i++) {
			for (int j = 0; j < p.getWidth(); j++) {
				if (p.getPart()[i][j].isFilled()) {
					grid[p.getPosition().getRow() + i][p.getPosition().getColumn() + j] = new Square(background, background);
				}
			}
		}
	}
	
	public boolean moveDown() {
		boolean canMove = false;
		if (activePart != null) {
			if (activePart.getPosition().getRow() + activePart.getHeight() - 1 < rows - 1) {
				int newRow = activePart.getPosition().getRow() + 1;
				Position newPos = new Position(newRow, activePart.getPosition().getColumn());

				clearPart(activePart);
				if (isRoomForPart(activePart, newPos)) {
					addPartToPosition(activePart, newPos);
					activePart.setPosition(newPos);
					canMove = true;
				} else {
					addPartToPosition(activePart, activePart.getPosition());
				}
			}
		}
		return canMove;
	}
	
	public void rotate() {
		if (activePart != null) {
			clearPart(activePart);
			activePart.rotate();
			if (isRoomForPart(activePart, activePart.getPosition())) {
				addPartToPosition(activePart, activePart.getPosition());
			} else {
				activePart.rotate();
				activePart.rotate();
				activePart.rotate();
				addPartToPosition(activePart, activePart.getPosition());
			}
		}
	}
	
	public void moveLeft() {
		if (activePart != null) {
			if (activePart.getPosition().getColumn() > 0) {
				int newColumn = activePart.getPosition().getColumn() - 1;
				Position newPos = new Position(activePart.getPosition()
						.getRow(), newColumn);

				clearPart(activePart);
				if (isRoomForPart(activePart, newPos)) {
					addPartToPosition(activePart, newPos);
					activePart.setPosition(newPos);
				} else {
					addPartToPosition(activePart, activePart.getPosition());
				}
			}
		}
	}

	public void moveRight() {
		if (activePart != null) {
			if (activePart.getPosition().getColumn() + activePart.getWidth()
					- 1 < columns - 1) {
				int newColumn = activePart.getPosition().getColumn() + 1;
				Position newPos = new Position(activePart.getPosition()
						.getRow(), newColumn);

				clearPart(activePart);
				if (isRoomForPart(activePart, newPos)) {
					addPartToPosition(activePart, newPos);
					activePart.setPosition(newPos);
				} else {
					addPartToPosition(activePart, activePart.getPosition());
				}
			}
		}
	}
	
	public void pause() {
		if (isRunning) {
			if (isPaused) {
				delay.start();
			} else {
				delay.stop();
			}
			isPaused = !isPaused;
			setChanged();
			notifyObservers();
		}
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	private boolean isRoomForPart(Part p, Position pos) {
		boolean ok = true;
		for (int i = 0; i < p.getHeight(); i++) {
			for (int j = 0; j < p.getWidth(); j++) {
				if (p.getPart()[i][j].isFilled()) {
					if (pos.getRow() + i < 0 || pos.getRow() + i > rows - 1) {
						ok = false;
						break;
					}
					if (pos.getColumn() + j < 0 || pos.getColumn() + j > columns - 1) {
						ok = false;
						break;
					}
					if (grid[pos.getRow() + i][pos.getColumn() + j].isFilled()) {
						ok = false;
					}
				}
			}
		}
		return ok;
	}
	
	private int random() {
		int i = 10;
		if (specialMode) {
			while (i > 9) {
				i = (int)(Math.random() * 10);
				
				if (9 >= i && i >= 7) {
					i = (int)(Math.random() * 10);
				}
			}
		} else {
			while (i > 6) {
				i = (int)(Math.random() * 10);
			}
		}
		return i;
	}
	
	private Part randomizePart() {
		switch (random()) {
		case 0: return new IPart(background);
		case 1: return new JPart(background);
		case 2: return new LPart(background);
		case 3: return new OPart(background);
		case 4: return new SPart(background);
		case 5: return new TPart(background);
		case 6: return new ZPart(background);
		case 7: return new SingleSquarePart(background);
		case 8: return new SpecialSPart(background);
		case 9: return new ParallelPart(background);
		default: return null;
		}
	}
	
	private void removeFilledRows() {
		double numberOfLines = 0;
		for (int i = 0; i < rows; i++) {
			boolean rowFilled = true;
			for (int j = 0; j < columns; j++) {
				if (!grid[i][j].isFilled()) {
					rowFilled = false;
				}
			}
			if (rowFilled) {
				flashRow(i);
				removeRow(i);
				numberOfLines++;
			}
		}
		score = score + (Math.pow(numberOfLines, 2.0));
	}
	
	private void removeRow(int row) {
		for (int i = row; i >= 0; i--) {
			for (int j = 0; j < columns; j++) {
				if (i > 0) {
					grid[i][j] = grid[i - 1][j];
				} else {
					grid[i][j] = new Square(background, background);
				}
			}
		}
		setChanged();
		notifyObservers();
	}
	
	private void flashRow(int row) {
		for (int j = 0; j < columns; j++) {
			grid[row][j] = new Square(Color.white, Color.white);
			grid[row][j].setFilledStatus(true);
		}
		setChanged();
		notifyObservers();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Position lastPos = activePart.getPosition();
		moveDown();
		if (lastPos.equals(activePart.getPosition())) {
			removeFilledRows();
			addPart();
			
			if (delay.getDelay() > 0) {
				delay.setDelay(delay.getDelay() - 1);
			}
		}
		
		if (!canAdd) {
			stop();
		}
	}
	
	public double getScore() {
		return score;
	}
	
	public void moveToBottom() {
		while (moveDown()) {
			moveDown();
		}
		removeFilledRows();
		addPart();
		
		if (delay.getDelay() > 0) {
			delay.setDelay(delay.getDelay() - 1);
		}
	}
	
	public int getAmountOfParts() {
		return amountOfParts;
	}
	
	public void addIPart() {
		clearPart(activePart);
		activePart = new IPart(background);
		canAdd = true;
		amountOfParts++;
		
		activePart.setPosition(new Position(0, (columns / 2) - (activePart.getWidth() / 2)));
		if (isRoomForPart(activePart, activePart.getPosition())) {
			addPartToPosition(activePart, activePart.getPosition());
		} else {
			canAdd = false;
		}
	}

	public void activateSpecialMode() {
		if (isRunning) {
			specialMode = !specialMode;
			setChanged();
			notifyObservers();
		}
	}
	
	public boolean isSpecialMode() {
		return specialMode;
	}
	
	public Part getNextPart() {
		return nextPart;
	}
}














