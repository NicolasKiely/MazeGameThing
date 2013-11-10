package mazeGame.window;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Manages the maze editor window
 * @author Nic
 */
public class EditorWindow extends JFrame {
	public EditorWindow(){
		this.setSize(640, 480);
		this.setLocation(300, 500);
		this.setTitle("Maze Editor");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
	
	public void enable(){this.setVisible(true);}
	public void disable(){this.setVisible(false);}
}
