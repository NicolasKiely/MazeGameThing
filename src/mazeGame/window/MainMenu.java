package mazeGame.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import mazeGame.Main;
import mazeGame.map.GameRoom;


/**
 * Map Manager window
 * @author Nic
 *
 */
public class MainMenu extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = 2L;
	private static MainMenu instance;

	private JPanel mainPanel;
	
	/** Manages chatroom and leaderboard */
	private JPanel upperPanel;
	private JPanel upperChatPanel;
	private JPanel lowerChatPanel;
	private JTextArea   chatRoom;
	private JScrollPane roomScroll;
	private JTextArea   memberList;
	private JScrollPane memberScroll;
	private JTextField  chatFld;
	private JButton     chatEnter;
	
	/** Manages controls and match rooms */
	private JPanel lowerPanel;
	private JPanel buttonPanel;
	private JButton disconnectBtn;
	private JButton createGame;
	private JButton joinGame;
	private JButton openManager;
	
	/** Game room list */
	private JList<String> roomList;
	private JScrollPane roomListScroll;
	private DefaultListModel<String> roomListModel;
	
	
	public static MainMenu get(){
		if (MainMenu.instance == null)
			MainMenu.instance = new MainMenu();
		return MainMenu.instance;
	}
	
	
	private MainMenu(){
		this.setSize(600, 440);
		this.setLocation(100, 100);
		this.setTitle("Main Screen");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Set up panels */
		this.mainPanel = new JPanel(new GridLayout(2,0));
		this.add(this.mainPanel);
		this.upperPanel = new JPanel(new BorderLayout());
		this.lowerPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.upperPanel);
		this.mainPanel.add(this.lowerPanel);
		
		this.upperChatPanel = new JPanel(new BorderLayout());
		this.lowerChatPanel = new JPanel();
		this.upperPanel.add(this.upperChatPanel, BorderLayout.CENTER);
		this.upperPanel.add(this.lowerChatPanel, BorderLayout.SOUTH);
		
		/* Set up chat components */
		this.chatRoom = new JTextArea(8, 32);
		this.chatRoom.setEditable(false);
		this.chatRoom.setText("[#CLIENT#] Entering server lobby chat room\n");
		this.chatRoom.setBackground(new Color(245, 235, 235));
		this.roomScroll = new JScrollPane(this.chatRoom);
		this.roomScroll.setPreferredSize(new Dimension(400, 180));
		this.upperChatPanel.add(this.roomScroll);
		
		this.memberList = new JTextArea(8, 8);
		this.memberList.setEditable(false);
		this.memberList.setText("Unfinished member list panel");
		this.memberList.setForeground(new Color (150, 150, 150));
		this.memberList.setBackground(new Color (200, 200, 200));
		this.memberScroll = new JScrollPane(this.memberList);
		this.memberScroll.setPreferredSize(new Dimension(200, 180));
		this.upperChatPanel.add(this.memberScroll, BorderLayout.EAST);
		
		this.chatFld = new JTextField(46);
		this.lowerChatPanel.add(this.chatFld);
		this.chatFld.addKeyListener(this);
		this.chatEnter = new JButton("Enter");
		this.chatEnter.setActionCommand("chatEnter");
		this.chatEnter.addActionListener(this);
		this.lowerChatPanel.add(this.chatEnter);
		
		
		/* Setup game buttons */
		this.buttonPanel = new JPanel(new GridLayout(4, 0));
		this.lowerPanel.add(this.buttonPanel, BorderLayout.WEST);
		
		this.joinGame = new JButton("Join game");
		this.joinGame.setActionCommand("join");
		this.joinGame.addActionListener(this);
		this.joinGame.setToolTipText("Join the selected game room");
		this.buttonPanel.add(this.joinGame);
		
		this.createGame = new JButton("Create game");
		this.createGame.setActionCommand("create");
		this.createGame.addActionListener(this);
		this.createGame.setToolTipText("Create a new game room");
		this.buttonPanel.add(this.createGame);
		
		this.openManager = new JButton("Open manager");
		this.openManager.setActionCommand("manager");
		this.openManager.addActionListener(this);
		this.openManager.setToolTipText("Open your maze manager/editor");
		this.buttonPanel.add(this.openManager);
		
		this.disconnectBtn = new JButton("Disconnect");
		this.disconnectBtn.setActionCommand("disconnect");
		this.disconnectBtn.addActionListener(this);
		this.disconnectBtn.setToolTipText("Disconnect from current server");
		this.buttonPanel.add(this.disconnectBtn);
		
		/* Set up room list */
		this.roomListModel = new DefaultListModel<String>();
		this.roomList = new JList<String>(this.roomListModel);
		this.roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.roomList.setLayoutOrientation(JList.VERTICAL);
		this.roomList.setVisibleRowCount(-1);
		this.roomList.setBackground(new Color(150, 180, 200));
		this.roomListScroll = new JScrollPane(this.roomList);
		this.lowerPanel.add(this.roomListScroll, BorderLayout.CENTER);
	}
	
	
	/** Logs chat message */
	public void logChat(String author, String message){
		this.chatRoom.append(author + ": " + message.replace('`', '\'') + "\n");
	}
	
	
	public void enable(){
		Main.srvFetchGameRooms(true);
		this.setVisible(true);
	}
	
	public void disable(){
		this.setVisible(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String act = e.getActionCommand();
		
		if (act.equals("disconnect")){
			Main.netMan.disconnect();
		} else if (act.equals("chatEnter")){
			this.sendChat();
		} else if (act.equals("manager")){
			MapManagerWindow.get().enable();
		} else if (act.equals("create")){
			Main.newGame.enable();
		}
		
	}
	
	/** Sends chat message */
	public void sendChat(){
		String msgText = this.chatFld.getText().replace("\"", "`").replace("'", "`");
		if (!msgText.equals(""))Main.srvSendChatMessage(msgText);
		
		this.chatFld.setText("");
	}


	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}


	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == '\n'){
			this.sendChat();
		}
	}
	
	
	/** Refetch data from list */
	public void resetRoomList(){
		/* Get name of current selected room */
		int iRoom = this.roomList.getSelectedIndex();
		String roomName = "";
		if (iRoom >= 0 && !GameRoom.roomList.isEmpty()){
			roomName = GameRoom.roomList.get(iRoom).getCreator();
		}
		
		/* Reset */
		this.roomListModel.removeAllElements();
		for (GameRoom room : GameRoom.roomList){
			this.roomListModel.addElement(room.toString());
		}
		this.repaint();
		
		/* Reselect */
		if (iRoom >= 0) this.roomList.setSelectedIndex(GameRoom.getIndexByHost(roomName));
	}
}