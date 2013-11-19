package mazeGame.window;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class LogViewer extends JFrame{
	private static final long serialVersionUID = 1L;
	private static LogViewer instance;
	
	private JPanel mainPanel;
	private JTextArea logArea;
	private JScrollPane scroll;
	
	public static LogViewer get(){
		if (LogViewer.instance == null)
			LogViewer.instance = new LogViewer();
		return LogViewer.instance;
	}
	
	private LogViewer(){
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
		this.scroll = new JScrollPane(this.logArea);
		this.add(this.scroll);

		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public void logAbridged(String msg){
		if (msg.length() >= 130){
			this.logArea.append("\n>" +msg.substring(0, 64)+ " ... " +msg.substring(msg.length()-64));
		} else {
			this.logln(msg);
		}
	}
	
	
	public void logln(String msg){
		this.logArea.append("\n>" + msg);
	}
	
	public void log(String msg){
		this.logArea.append(msg);
	}
	
	
	public void enable(){
		this.setVisible(true);
	}
	
	public void disable(){
		this.setVisible(false);
	}
}
