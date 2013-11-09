package mazeGame.window;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class LogViewer extends JFrame{
	private JPanel mainPanel;
	private JTextArea logArea;
	
	public LogViewer(){
		/* Setup frame */
		this.setSize(640, 440);
		this.setLocation(600, 250);
		this.setTitle("Logs");
		this.setLayout(new BorderLayout());
		
		/* Add text area */
		this.mainPanel = new JPanel();
		this.add(this.mainPanel, BorderLayout.CENTER);
		this.logArea = new JTextArea(24, 48);
		this.logArea.setBackground(new Color(0,0,0));
		this.logArea.setEditable(false);
		this.logArea.setForeground(new Color(200, 200, 200));
		this.mainPanel.add(logArea);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void logln(String msg){
		this.logArea.setText(msg+ "\n" + this.logArea.getText());
	}
	
	public void log(String msg){
		this.logArea.setText(msg+this.logArea.getText());
	}
	
	
	public void enable(){
		this.setVisible(true);
	}
	
	public void disable(){
		this.setVisible(false);
	}
}
