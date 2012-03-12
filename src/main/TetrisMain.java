package main;

import javax.swing.UIManager;

import controller.InputController;

public class TetrisMain {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {}
	    
		new InputController();
	}

}
