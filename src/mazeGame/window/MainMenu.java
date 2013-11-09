package mazeGame.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mazeGame.Main;


/**
 * Map Manager window
 * @author Nic
 *
 */
public class MainMenu extends JFrame implements ActionListener{
	JPanel mainPanel;
	JButton disconnectBtn;
	
	/* TODO: fetch stats from server */
	
	public MainMenu(){
		this.setSize(600, 440);
		this.setLocation(100, 100);
		this.setTitle("Main Screen");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.mainPanel = new JPanel();
		this.add(this.mainPanel);
		
		/* Setup disconnect button */
		this.disconnectBtn = new JButton("Disconnect");
		this.disconnectBtn.setActionCommand("disconnect");
		this.disconnectBtn.addActionListener(this);
		this.mainPanel.add(this.disconnectBtn);
	}
	
	
	public void enable(){
		this.setVisible(true);
	}
	
	public void disable(){
		this.setVisible(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("disconnect")){
			Main.netMan.disconnect();
		}
		
	}
}