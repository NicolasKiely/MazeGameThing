package mazeGame.window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mazeGame.Main;

public class NewMazeWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 12L;
	
	/* Panels */
	private JPanel upperPanel;
	private JPanel lowerPanel;
	
	/* Inputs */
	private JLabel nameLbl;
	private JLabel sizeLbl;
	private JTextField nameFld;
	private JTextField sizeFld;
	
	private JButton okBtn;
	
	public NewMazeWindow(){
		/* Setup frame */
		this.setSize(400, 140);
		this.setLocation(400, 400);
		this.setTitle("Create New Maze");
		this.setLayout(new BorderLayout());
		
		/* Set up  panels */
		this.upperPanel = new JPanel();
		this.lowerPanel = new JPanel();
		this.upperPanel.setLayout(new BorderLayout());
		this.lowerPanel.setLayout(new BorderLayout());
		this.add(this.upperPanel, BorderLayout.NORTH);
		this.add(this.lowerPanel, BorderLayout.CENTER);
		
		this.okBtn = new JButton("Enter");
		this.okBtn.setActionCommand("ok");
		this.okBtn.addActionListener(this);
		this.add(this.okBtn, BorderLayout.SOUTH);
		
		/* Set up fields */
		this.nameLbl = new JLabel("New name: ");
		this.sizeLbl = new JLabel("New size: ");
		this.nameFld = new JTextField(16);
		this.sizeFld = new JTextField(16);
		
		this.upperPanel.add(this.nameLbl, BorderLayout.WEST);
		this.upperPanel.add(this.nameFld, BorderLayout.EAST);
		this.lowerPanel.add(this.sizeLbl, BorderLayout.WEST);
		this.lowerPanel.add(this.sizeFld, BorderLayout.EAST);
		
		this.pack();
	}
	
	
	public void enable(){this.setVisible(true);}
	public void disable(){this.setVisible(false);}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")){
			Main.sendServerCommand("/maze/play/newMap -size \"" +this.sizeFld.getText()+
					"\" -name \"" +this.nameFld.getText()+"\"");
			
			this.disable();
		}
	}
}
