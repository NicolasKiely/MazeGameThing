package mazeGame.window;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServerDialog extends JFrame {
	private static final long serialVersionUID = 11L;
	
	private JLabel lbl1;
	private JLabel lbl2;
	
	private JPanel mainPanel;
	
	public ServerDialog(String[] msgDat){
		String m0 = "Server Dialog";
		String m1 = "Server sent unspecified message";
		String m2 = "";
		
		if (msgDat.length > 2){m2 = msgDat[2];}
		if (msgDat.length > 1){m1 = msgDat[1];}
		if (msgDat.length > 0){m0 = msgDat[0];}
		
		this.setSize(400, 140);
		this.setLocation(400, 400);
		this.setTitle(m0);
		//this.setLayout(new BorderLayout());
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.add(mainPanel);
		
		this.lbl1 = new JLabel(m1);
		this.lbl2 = new JLabel(m2);
		
		mainPanel.add(lbl1, BorderLayout.NORTH);
		mainPanel.add(lbl2, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}
}
