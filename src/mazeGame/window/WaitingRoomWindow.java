package mazeGame.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mazeGame.Main;
import mazeGame.map.GameRoom;

public class WaitingRoomWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 10L;
	private static WaitingRoomWindow instance;

	/** Main panel of frame */
	private JPanel mainPanel;
	
	/** Panel below info title */
	private JPanel gamePanel;	
	
	/** Left-hand side panel showing player list */
	private JPanel playerPanel;
	
	/** Right-hand side panel showing buttons and map selection list */
	private JPanel controlPanel;
	
	/** Title label */
	private JLabel gameInfo;
	
	/** Host of game room */
	private JTextField hostFld;
	private JLabel hostLbl;
	private JPanel hostPanel;
	
	/** Other player in game room */
	private JTextField guestFld;
	private JLabel guestLbl;
	private JPanel guestPanel;
	
	/** Button for starting game */
	private JButton readyBtn;
	
	/** Button for leaving game room */
	private JButton leaveBtn;
	
	public static WaitingRoomWindow get(){
		if (WaitingRoomWindow.instance == null)
			WaitingRoomWindow.instance = new WaitingRoomWindow();
		return WaitingRoomWindow.instance;
	}
	
	
	private WaitingRoomWindow(){
		this.setSize(400, 200);
		this.setLocation(100, 100);
		this.setTitle("Waiting Room");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		/* Set up panels*/
		this.mainPanel = new JPanel(new BorderLayout());
		this.gamePanel = new JPanel(new GridLayout(0, 2));
		this.playerPanel = new JPanel();
		//this.playerPanel.setLayout(new BoxLayout(this.playerPanel, BoxLayout.PAGE_AXIS));
		this.playerPanel.setLayout(new BorderLayout());
		this.controlPanel = new JPanel();
		
		this.add(this.mainPanel);
		this.mainPanel.add(this.gamePanel, BorderLayout.CENTER);
		this.gamePanel.add(this.playerPanel);
		this.gamePanel.add(this.controlPanel);
		
		/* Set up title label*/
		this.gameInfo = new JLabel("Room info: Not in Room");
		this.mainPanel.add(this.gameInfo, BorderLayout.NORTH);
		
		/*  Host field */
		this.hostPanel = new JPanel(new BorderLayout());
		this.playerPanel.add(this.hostPanel, BorderLayout.NORTH);
		
		this.hostLbl = new JLabel("  Host: ");
		this.hostFld = new JTextField("#No Host#");
		this.hostFld.setEditable(false);
		this.hostFld.setBackground(new Color(180, 180, 180));
		this.hostPanel.add(this.hostLbl, BorderLayout.WEST);
		this.hostPanel.add(this.hostFld, BorderLayout.CENTER);
		
		/* Guest field */
		this.guestPanel = new JPanel(new BorderLayout());
		this.playerPanel.add(this.guestPanel, BorderLayout.CENTER);
		
		this.guestLbl = new JLabel("Guest: ");
		this.guestFld = new JTextField("#No Guest#");
		this.guestFld.setEditable(false);
		this.guestPanel.add(this.guestLbl, BorderLayout.WEST);
		this.guestPanel.add(this.guestFld, BorderLayout.CENTER);
		
		/* Buttons */
		this.readyBtn = new JButton("Ready");
		this.readyBtn.setActionCommand("ready");
		this.readyBtn.addActionListener(this);
		this.leaveBtn = new JButton("Leave");
		this.leaveBtn.setActionCommand("leave");
		this.leaveBtn.addActionListener(this);
		
		this.controlPanel.add(this.readyBtn);
		this.controlPanel.add(this.leaveBtn);
	}
	
	
	/** Update text fields */
	public void updateRoom(){
		if (GameRoom.activeRoom == null){
			gameInfo.setText("Room info: Not in Room");
			hostFld.setText("#No host#");
			guestFld.setText("#No guest#");
			
		} else {
			gameInfo.setText("Room info: " + GameRoom.activeRoom.toString());
			hostFld.setText(GameRoom.activeRoom.getCreator());
		}
	}
	
	
	public void enable(){this.updateRoom();this.setVisible(true);}
	public void disable(){this.setVisible(false);}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("ready")){
			/** TODO: Send ready command */
			
		} else if (cmd.equals("leave")){
			Main.srvLeaveRoom();
		}
	}
}
