package mazeGame.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mazeGame.Main;


public class EditorPallet extends JFrame implements ActionListener {
	private static final long serialVersionUID = 9L;
	
	private JComboBox<String> tileBox;
	private String[] tileTypes = {"passable", "impassable", "start", "finish", "trap"};
	private char[] tileChars = {'p', 'i', 's', 'f', 't'};
	private JPanel mainPanel;
	private JButton exitBtn;
	
	public JLabel tileLbl;
	
	public EditorPallet(){
		this.setSize(200, 150);
		this.setLocation(100, 500);
		this.setTitle("Maze Editor");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.mainPanel = new JPanel(new BorderLayout());
		this.add(this.mainPanel);
		
		
		/* Setup combo box */
		this.tileBox = new JComboBox<String>(tileTypes);
		this.tileBox.setActionCommand("select");
		this.tileBox.addActionListener(this);
		this.mainPanel.add(this.tileBox, BorderLayout.NORTH);
		
		/* Set up image */
		this.tileLbl = new JLabel(Main.imgMan.passableTile);
		this.mainPanel.add(this.tileLbl, BorderLayout.CENTER);
		
		/* Set up exit button */
		this.exitBtn = new JButton("Exit");
		this.exitBtn.setActionCommand("exit");
		this.exitBtn.addActionListener(this);
		this.mainPanel.add(this.exitBtn, BorderLayout.SOUTH);
	}
	
	
	public char getSelectedTile(){
		return this.tileChars[this.tileBox.getSelectedIndex()];
	}
	
	
	public void enable(){this.setVisible(true);}
	public void disable(){this.setVisible(false);}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("select")){
			String imgName = (String) this.tileBox.getSelectedItem();
			this.tileLbl.setIcon(Main.imgMan.getImageByName(imgName));
			
		} else if (e.getActionCommand().equals("exit")){
			Main.mainLobbySetup();
		}
	}
}
