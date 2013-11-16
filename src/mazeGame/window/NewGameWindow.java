package mazeGame.window;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * GUI for creating a new game room
 * Game properties: room size, game type, map size
 * @author Nic
 *
 */
public class NewGameWindow extends JFrame {
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
	
	/* Combo box selections */
	private String[] mapSizeList = {"10 x 10", "20 x 20", "30 x 30", "40 x 40", "50 x 50"};
	private String[] gameTypeList = {"1v1 Race"};
	private String[] maxPlayersList = {"  1 vs 1"};
	
	public NewGameWindow(){
		this.setSize(330, 150);
		this.setLocation(300, 500);
		this.setTitle("New Game");
		this.mainPanel = new JPanel(new GridLayout(3, 0));
		this.add(this.mainPanel);
		
		/* Set up panels */
		this.typePanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.typePanel);
		this.sizePanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.sizePanel);
		this.maxPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.maxPanel);
		
		/* Set up combo boxes */
		this.sizeBox = new JComboBox<String>(this.mapSizeList);
		this.typeBox = new JComboBox<String>(this.gameTypeList);
		this.maxBox = new JComboBox<String>(this.maxPlayersList);
		
		/* Set up labels */
		this.sizeLabel = new JLabel("Maze Size :");
		this.typeLabel = new JLabel("Game Type (more in development!):");
		this.maxLabel = new JLabel("Max Players:");
		
		/* Add components */
		this.typePanel.add(this.typeLabel, BorderLayout.WEST);
		this.typePanel.add(this.typeBox, BorderLayout.EAST);
		this.sizePanel.add(this.sizeLabel, BorderLayout.WEST);
		this.sizePanel.add(this.sizeBox, BorderLayout.EAST);
		this.maxPanel.add(this.maxLabel, BorderLayout.WEST);
		this.maxPanel.add(this.maxBox, BorderLayout.EAST);
	}
	
	
	public void enable(){this.setVisible(true);}
	public void disable(){this.setVisible(false);}
}
