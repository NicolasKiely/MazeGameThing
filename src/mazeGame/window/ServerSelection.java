package mazeGame.window;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import mazeGame.Main;


/**
 * Manages server selection window
 * @author Nic
 *
 */
public class ServerSelection extends JFrame implements ActionListener{
	private static final long serialVersionUID = 7L;
	private static ServerSelection instance;
	
	/* Panels */
	private JPanel mainPanel;
	private JPanel addrPanel;
	private JPanel lowerPanel;
	private JPanel connectPanel;
	private JPanel userPanel;
	private JPanel passPanel;
	/* Address */
	private JLabel ipAddressLbl;
	private JTextField ipAddressFld;
	/* Username/password */
	private JLabel userNameLbl;
	private JTextField userNameFld;
	private JLabel passLbl;
	private JTextField passFld;
	/* Connect*/
	private JButton connectBtn;
	private JLabel statusLbl;
	private JButton loggerBtn;
	
	
	public static ServerSelection get(){
		if (ServerSelection.instance == null)
			ServerSelection.instance = new ServerSelection();
		return ServerSelection.instance;
	}
	
	private ServerSelection(){
		/* Setup frame */
		this.setSize(400, 140);
		this.setLocation(100, 100);
		this.setTitle("Server Selection");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Set up panels */
		this.mainPanel = new JPanel();
		this.addrPanel = new JPanel(new BorderLayout());
		this.lowerPanel = new JPanel(new BorderLayout());
		this.connectPanel = new JPanel(new BorderLayout());
		this.userPanel = new JPanel(new BorderLayout());
		this.passPanel = new JPanel(new BorderLayout());
		
		this.add(this.mainPanel, BorderLayout.CENTER);
		this.mainPanel.add(this.addrPanel, BorderLayout.NORTH);
		this.mainPanel.add(this.lowerPanel, BorderLayout.CENTER);
		this.lowerPanel.add(this.userPanel, BorderLayout.NORTH);
		this.lowerPanel.add(this.passPanel, BorderLayout.CENTER);
		this.lowerPanel.add(this.connectPanel, BorderLayout.SOUTH);
		
		/* Set up IP address field */
		ipAddressLbl = new JLabel("IP Address: ");
		ipAddressFld = new JTextField(24);
		ipAddressFld.setText(Main.DEFAULT_IP);
		ipAddressFld.setCaretPosition(9);
		this.addrPanel.add(ipAddressLbl, BorderLayout.WEST);
		this.addrPanel.add(ipAddressFld, BorderLayout.EAST);
		
		/* Set up connect button/label */
		this.connectBtn = new JButton("Connect");
		this.connectBtn.setActionCommand("connect");
		this.connectPanel.add(this.connectBtn, BorderLayout.WEST);
		this.connectBtn.addActionListener(this);
		
		this.statusLbl = new JLabel("       Not connected");
		this.connectPanel.add(this.statusLbl, BorderLayout.CENTER);
		this.loggerBtn = new JButton("Show logs");
		this.loggerBtn.setActionCommand("logger");
		this.loggerBtn.addActionListener(this);
		this.connectPanel.add(this.loggerBtn, BorderLayout.EAST);
		
		/* Set up user/password fields */
		this.userNameFld = new JTextField(24);
		this.passFld = new JPasswordField(24);
		this.userNameFld.setText(Main.DEFAULT_NAME);
		this.passFld.setText(Main.DEFAULT_PASS);
		this.userNameLbl = new JLabel("User name: ");
		this.passLbl = new JLabel("Password: ");
		this.userPanel.add(this.userNameFld, BorderLayout.EAST);
		this.userPanel.add(this.userNameLbl, BorderLayout.WEST);
		this.passPanel.add(this.passFld, BorderLayout.EAST);
		this.passPanel.add(this.passLbl, BorderLayout.WEST);
	}
	
	
	public String getIP(){
		return this.ipAddressFld.getText();
	}
	
	
	public String getUserName(){
		return this.userNameFld.getText();
	}
	
	
	public String getPassword(){
		return this.passFld.getText();
	}
	
	
	public void actionPerformed(ActionEvent e){
		if (e.getActionCommand().equals("connect")){
			this.statusLbl.setText("      Connecting ...");
			if (!Main.netMan.attemptConnection(this.getIP(), Main.netMan.getDefaultPort())){
				this.statusLbl.setText("   Failed to connect");
			} else {
				this.statusLbl.setText("          Connected!");
				Main.mainLobbySetup();
			}
			
		} else if (e.getActionCommand().equals("logger")){
			if (LogViewer.get().isVisible()){
				LogViewer.get().disable();
			} else {
				LogViewer.get().enable();
			}
		}
	}
	
	
	public void enable(){
		this.setVisible(true);
	}
	
	public void disable(){
		this.setVisible(false);
		this.statusLbl.setText("        Disconnected");
	}
}
