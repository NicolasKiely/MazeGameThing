package mazeGame.window;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mazeGame.Main;

/**
 * GUI for creating a new game room
 * Game properties: room size, game type, map size
 * @author Nic
 *
 */
public class NewGameWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 6L;
	
	private JPanel mainPanel;
	
	/* Combo boxes */
	private JComboBox<String> sizeBox;
	private JComboBox<String> typeBox;
	private JComboBox<String> maxBox;
	
	/* Labels */
	private JLabel sizeLabel;
	private JLabel typeLabel;
	private JLabel maxLabel;
	
	/* Panels */
	private JPanel sizePanel;
	private JPanel typePanel;
	private JPanel maxPanel;
	private JPanel btnPanel;
	
	/* Buttons */
	private JButton okBtn;
	private JButton cancelBtn;
	
	/* Combo box selections */
	private String[] mapSizeList = {"10 x 10", "20 x 20", "30 x 30", "40 x 40", "50 x 50"};
	private String[] gameTypeList = {"1v1 Race"};
	private String[] maxPlayersList = {"  1 vs 1"};
	
	
	public NewGameWindow(){
		this.setSize(330, 150);
		this.setLocation(300, 500);
		this.setTitle("New Game");
		this.mainPanel = new JPanel(new GridLayout(4, 0));
		this.add(this.mainPanel);
		
		/* Set up panels */
		this.typePanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.typePanel);
		this.sizePanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.sizePanel);
		this.maxPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.maxPanel);
		this.btnPanel = new JPanel(new GridLayout(0,2));
		this.mainPanel.add(this.btnPanel);
		
		/* Set up combo boxes */
		this.sizeBox = new JComboBox<String>(this.mapSizeList);
		this.typeBox = new JComboBox<String>(this.gameTypeList);
		this.maxBox = new JComboBox<String>(this.maxPlayersList);
		
		/* Set up labels */
		this.sizeLabel = new JLabel("Maze Size :");
		this.typeLabel = new JLabel("Game Type (more in development!):");
		this.maxLabel = new JLabel("Max Players:");
		
		/* Setup buttons */
		this.okBtn = new JButton("Create");
		this.okBtn.setActionCommand("create");
		this.okBtn.addActionListener(this);
		this.cancelBtn = new JButton("Cancel");
		this.cancelBtn.setActionCommand("cancel");
		this.cancelBtn.addActionListener(this);
		
		/* Add components */
		this.typePanel.add(this.typeLabel, BorderLayout.WEST);
		this.typePanel.add(this.typeBox, BorderLayout.EAST);
		this.sizePanel.add(this.sizeLabel, BorderLayout.WEST);
		this.sizePanel.add(this.sizeBox, BorderLayout.EAST);
		this.maxPanel.add(this.maxLabel, BorderLayout.WEST);
		this.maxPanel.add(this.maxBox, BorderLayout.EAST);
		this.btnPanel.add(this.cancelBtn);
		this.btnPanel.add(this.okBtn);
	}
	
	
	public void enable(){this.setVisible(true);}
	public void disable(){this.setVisible(false);}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("create")){
			String mazeSize = (String) this.sizeBox.getSelectedItem();
			Main.srvCreateRoom((String) this.typeBox.getSelectedItem(), mazeSize.substring(0, 2));
			
			this.disable();
			
		} else if (e.getActionCommand().equals("cancel")){
			this.disable();
		}
	}
}
