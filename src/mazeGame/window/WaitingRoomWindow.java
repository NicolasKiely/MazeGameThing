package mazeGame.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WaitingRoomWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 10L;

	private JPanel mainPanel;
	private JLabel gameInfo;
	
	public WaitingRoomWindow(){
		this.setSize(600, 440);
		this.setLocation(100, 100);
		this.setTitle("Waiting Room");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.mainPanel = new JPanel(new BorderLayout());
		this.add(this.mainPanel);
		
		gameInfo = new JLabel("Room info");
		this.mainPanel.add(gameInfo, BorderLayout.NORTH);
	}
	
	public void enable(){this.setVisible(true);}
	public void disable(){this.setVisible(false);}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
