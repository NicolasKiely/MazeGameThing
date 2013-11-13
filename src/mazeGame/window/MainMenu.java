package mazeGame.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mazeGame.Main;


/**
 * Map Manager window
 * @author Nic
 *
 */
public class MainMenu extends JFrame implements ActionListener, KeyListener{
	private JPanel mainPanel;
	private JButton disconnectBtn;
	
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
	
	public MainMenu(){
		this.setSize(600, 440);
		this.setLocation(100, 100);
		this.setTitle("Main Screen");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		/* Set up panels */
		this.mainPanel = new JPanel(new GridLayout(2,0));
		this.add(this.mainPanel);
		this.upperPanel = new JPanel(new BorderLayout());
		this.lowerPanel = new JPanel();
		this.mainPanel.add(this.upperPanel);
		this.mainPanel.add(this.lowerPanel);
		
		this.upperChatPanel = new JPanel(new BorderLayout());
		this.lowerChatPanel = new JPanel();
		this.upperPanel.add(this.upperChatPanel, BorderLayout.CENTER);
		this.upperPanel.add(this.lowerChatPanel, BorderLayout.SOUTH);
		
		/* Set up chat components */
		this.chatRoom = new JTextArea(8, 32);
		this.chatRoom.setEditable(false);
		this.roomScroll = new JScrollPane(this.chatRoom);
		this.roomScroll.setPreferredSize(new Dimension(400, 180));
		this.upperChatPanel.add(this.roomScroll);
		
		this.memberList = new JTextArea(8, 8);
		this.memberList.setEditable(false);
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
		
		
		/* Setup disconnect button */
		this.disconnectBtn = new JButton("Disconnect");
		this.disconnectBtn.setActionCommand("disconnect");
		this.disconnectBtn.addActionListener(this);
		this.lowerPanel.add(this.disconnectBtn);
	}
	
	
	/** Logs chat message */
	public void logChat(String author, String message){
		this.chatRoom.append(author + ": " + message + "\n");
	}
	
	
	public void enable(){
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
		}
		
	}
	
	/** Sends chat message */
	public void sendChat(){
		String msgText = this.chatFld.getText().replace("\"", "`").replace("'", "`");
		if (!msgText.equals("")){
			Main.sendServerCommand("/chat/msg -message \"" +msgText+ "\" -room \"lobby\"");
		}
		
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
}