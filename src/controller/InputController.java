package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.GameModel;
import view.MainFrame;

public class InputController implements KeyListener {
	
	private MainFrame mainFrame;
	private GameModel model = GameModel.getInstance();
	
	public InputController() {
		mainFrame = new MainFrame();
		mainFrame.addKeyListener(this);
	}


	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_N: model.start();
		break;
		case KeyEvent.VK_RIGHT: model.moveRight();
		break;
		case KeyEvent.VK_LEFT: model.moveLeft();
		break;
		case KeyEvent.VK_UP: model.rotate();
		break;
		case KeyEvent.VK_DOWN: model.moveDown();
		break;
		case KeyEvent.VK_SPACE: model.moveToBottom();
		break;
		case KeyEvent.VK_E: System.exit(0);
		break;
		case KeyEvent.VK_P: model.pause();
		break;
		case KeyEvent.VK_I: model.addIPart();
		break;
		case KeyEvent.VK_S: model.activateSpecialMode();
		break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
