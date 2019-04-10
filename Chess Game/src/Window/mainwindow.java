package Window;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Board.Board;

@SuppressWarnings("serial")
public class mainwindow extends Frame implements ActionListener, WindowListener{
	
	private Button startGame;
	private Button exitGame;
	
	public mainwindow(){
		
		Panel mainDisplay = new Panel(new FlowLayout());
		Panel mainButtons = new Panel();
		mainButtons.setLayout(new BoxLayout(mainButtons, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel("Chess");
		startGame = new Button("Start Game");
		exitGame = new Button("Exit Game");
		
		mainDisplay.add(title);
		
		mainButtons.add(startGame);
		mainButtons.add(exitGame);
		
		setLayout(new BorderLayout());
		
		add(mainDisplay, BorderLayout.NORTH);
		add(mainButtons, BorderLayout.CENTER);
		
		setTitle("Chess");
		setSize(300,315);
		setVisible(true);
		setFocusable(true);
		addWindowListener(this);
		startGame.addActionListener(this);
		
		
	}
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		dispose();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Button source = (Button)e.getSource();
		
		if(source.equals(startGame)){
			Board board = new Board();
			board.generateBoard();
		}else if(source.equals(exitGame)){
			
		}
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}	
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	
}
