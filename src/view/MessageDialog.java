package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MessageDialog extends JFrame implements ActionListener, KeyListener{

	private JTextArea messageArea = new JTextArea();
	private JPanel messagePanel = new JPanel();
	private JButton okButton = new JButton("OK");
	private JPanel buttonPanel = new JPanel();
	private JPanel mainPanel = new JPanel();
	
	public MessageDialog(String title, String message) {
		messageArea.setText(message);
		messageArea.setBackground(new Color(240, 240, 240));
		messageArea.setFont(new Font("Verdana", Font.PLAIN, 11));
		messageArea.setEditable(false);
		messagePanel.add(messageArea);
		
		okButton.addActionListener(this);
		okButton.requestFocus();
		buttonPanel.add(okButton);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(new Dimension(250, 150));
		mainPanel.add(messagePanel);
		mainPanel.add(buttonPanel);
		
		addKeyListener(this);
		add(mainPanel);
		setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - getPreferredSize().getWidth() / 2), 
				(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - getPreferredSize().getHeight() / 2));
		setTitle(title);
		setIconImage(new ImageIcon("icon.jpg").getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	
}
