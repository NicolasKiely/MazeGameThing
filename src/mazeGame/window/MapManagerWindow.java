package mazeGame.window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mazeGame.Main;
import mazeGame.map.MapStats;


/**
 * Map Manager window
 * @author Nic
 *
 */
public class MapManagerWindow extends JFrame implements ActionListener, ListSelectionListener, FocusListener{
	public String[][] mapStats;
	private MapStats selectedStats;
	
	/* Child windows */
	public NewMazeWindow createMazeWin;
	
	/* Panels */
	private JPanel mainPanel;
	private JPanel mapsPanel;
	private JPanel controlPanel;
	private JPanel upperControlPanel;
	private JPanel lowerControlPanel;
	private JPanel namePanel;
	private JPanel sizePanel;
	private JPanel winPlaysPanel;
	private JPanel stagePanel;
	private JPanel privatePanel;
	private JPanel publicPanel;
	
	/* Buttons */
	private JButton editBtn;
	private JButton deleteBtn;
	private JButton newBtn;
	private JButton swapBtn;
	
	/* Map selection */
	private JList mapPrivateList;
	private JScrollPane mapPrivateScroll;
	private DefaultListModel mapPrivateModel;
	private JList mapPublicList;
	private JScrollPane mapPublicScroll;
	private DefaultListModel mapPublicModel;
	private JLabel privSelLbl;
	private JLabel pubSelLbl;
	
	/* Map stats literals, ie label for the labels */
	private JLabel nameLiteralLbl;
	private JLabel sizeLiteralLbl;
	private JLabel winsPlaysLiteralLbl;
	private JLabel stageLiteralLbl;
	
	/* Map stats uneditable fields */
	private JLabel nameLbl;
	private JLabel sizeLbl;
	private JLabel winsPlaysLbl;
	private JLabel stageLbl;
	
	private Color lightColor;
	private Color darkColor;
	
	public MapManagerWindow(){
		this.createMazeWin = new NewMazeWindow();
		
		lightColor = new Color(250, 250, 250);
		darkColor = new Color(150, 150, 150);
		
		mapStats = null;
		this.setSize(600, 440);
		this.setLocation(300, 500);
		this.setTitle("Map Manager");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.mainPanel = new JPanel(new GridLayout(0, 2));
		this.add(this.mainPanel);
		
		/* Split window into left and right panels */
		this.mapsPanel = new JPanel(new GridLayout(2, 0));
		this.controlPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.mapsPanel);
		this.mainPanel.add(this.controlPanel);
		
		/* Split up control panel */
		this.upperControlPanel = new JPanel(new GridLayout(4, 0));
		this.lowerControlPanel = new JPanel();
		this.lowerControlPanel.setLayout(new BoxLayout(this.lowerControlPanel, BoxLayout.PAGE_AXIS));
		this.controlPanel.add(this.upperControlPanel, BorderLayout.NORTH);
		this.controlPanel.add(this.lowerControlPanel, BorderLayout.CENTER);
		this.namePanel = new JPanel(new GridLayout(0, 2));
		this.sizePanel = new JPanel(new GridLayout(0, 2));
		this.winPlaysPanel = new JPanel(new GridLayout(0, 2));
		this.stagePanel = new JPanel(new GridLayout(0, 2));
		
		/* Set up control panel */
		this.editBtn = new JButton("Edit");
		this.editBtn.setActionCommand("edit");
		this.editBtn.addActionListener(this);
		this.editBtn.setToolTipText("Opens selected maze. Can only edit private mazes");
		this.deleteBtn = new JButton("Delete");
		this.deleteBtn.setActionCommand("delete");
		this.deleteBtn.addActionListener(this);
		this.deleteBtn.setToolTipText("Deletes selected maze. Can only delete private maps");
		this.newBtn = new JButton("New");
		this.newBtn.setActionCommand("new");
		this.newBtn.addActionListener(this);
		this.newBtn.setToolTipText("Creates a new private maze on the server");
		this.swapBtn = new JButton("Public <---> Private");
		this.swapBtn.setToolTipText("Switches selected map between being private and public");
		this.swapBtn.setActionCommand("swap");
		this.swapBtn.addActionListener(this);
		this.upperControlPanel.add(this.newBtn);
		this.upperControlPanel.add(this.editBtn);
		this.upperControlPanel.add(this.swapBtn);
		this.upperControlPanel.add(this.deleteBtn);
		
		/* Set up map selection list */
		this.privatePanel = new JPanel(new BorderLayout());
		this.publicPanel = new JPanel(new BorderLayout());
		this.mapsPanel.add(this.privatePanel);
		this.mapsPanel.add(this.publicPanel);
		
		this.mapPrivateModel = new DefaultListModel();
		this.mapPrivateList = new JList(mapPrivateModel);
		this.mapPrivateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.mapPrivateList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.mapPrivateList.setVisibleRowCount(-1);
		this.mapPrivateList.addListSelectionListener(this);
		this.mapPrivateList.addFocusListener(this);
		this.privatePanel.add(this.mapPrivateList, BorderLayout.SOUTH);
		
		this.privSelLbl = new JLabel("Private (can edit, cannot play) Maps:");
		this.privatePanel.add(this.privSelLbl, BorderLayout.NORTH);
		this.mapPrivateScroll = new JScrollPane(this.mapPrivateList);
		this.mapPrivateScroll.setPreferredSize(new Dimension(250, 80));
		this.mapPrivateList.setBackground(new Color(150, 150, 150));
		this.privatePanel.add(this.mapPrivateScroll, BorderLayout.SOUTH);
		
		this.mapPublicModel = new DefaultListModel();
		this.mapPublicList = new JList(mapPublicModel);
		this.mapPublicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.mapPublicList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.mapPublicList.setVisibleRowCount(-1);
		this.mapPublicList.addListSelectionListener(this);
		this.mapPublicList.addFocusListener(this);
		this.publicPanel.add(this.mapPublicList, BorderLayout.SOUTH);
		
		this.pubSelLbl = new JLabel("Public (can play, can't edit) Maps: ");
		this.publicPanel.add(this.pubSelLbl, BorderLayout.NORTH);
		this.mapPublicScroll = new JScrollPane(this.mapPublicList);
		this.mapPublicScroll.setPreferredSize(new Dimension(250, 80));
		this.publicPanel.add(this.mapPublicScroll, BorderLayout.SOUTH);
		
		/* Set up map stats panel */
		this.nameLiteralLbl = new JLabel("Map Name:    ");
		this.nameLbl = new JLabel("Select a map");
		this.nameLbl.setForeground(new Color(100, 100, 100));
		this.namePanel.add(this.nameLiteralLbl);
		this.namePanel.add(this.nameLbl);
		
		this.sizeLiteralLbl = new JLabel("Map Size:    ");
		this.sizeLbl = new JLabel("x");
		this.sizeLbl.setForeground(new Color(100, 100, 100));
		this.sizePanel.add(this.sizeLiteralLbl);
		this.sizePanel.add(this.sizeLbl);
		
		this.winsPlaysLiteralLbl = new JLabel("Wins/Losses: ");
		this.winsPlaysLbl = new JLabel("/");
		this.winsPlaysLbl.setForeground(new Color(100, 100, 100));
		this.winPlaysPanel.add(this.winsPlaysLiteralLbl);
		this.winPlaysPanel.add(this.winsPlaysLbl);
		
		this.stageLiteralLbl = new JLabel("Stage: ");
		this.stageLbl = new JLabel("X");
		this.stageLbl.setForeground(new Color(100, 100, 100));
		this.stagePanel.add(this.stageLiteralLbl);
		this.stagePanel.add(this.stageLbl);
		
		this.lowerControlPanel.add(this.namePanel);
		this.lowerControlPanel.add(this.sizePanel);
		this.lowerControlPanel.add(this.winPlaysPanel);
		this.lowerControlPanel.add(this.stagePanel);
		this.lowerControlPanel.setAlignmentX(JFrame.LEFT_ALIGNMENT);
		
		this.pack();
		
		
	}
	
	
	/**
	 * Sets map names
	 * @param mapNames
	 */
	public void resetMapNames(){
		mapPrivateModel.removeAllElements();
		mapPublicModel.removeAllElements();
		
		for (MapStats ms : MapStats.statsList){
			if (ms.id < 0) continue;
			
			if (ms.isStaged == 0){
				this.mapPublicModel.addElement(ms.name);
			} else {
				this.mapPrivateModel.addElement(ms.name);
			}	
		}
	}
	
	
	public void enable(){
		this.setVisible(true);
	}
	
	public void disable(){
		this.setVisible(false);
	}
	
	
	/* Resets stats list selection */
	public void resetListSelection(){
		MapStats firstStats = MapStats.statsList.get(0);
		if (firstStats == null){
			this.mapPrivateList.setSelectedIndex(-1);
		} else {
			this.mapPrivateList.setSelectedIndex(0);
			this.setMapStats(firstStats);
		}
	}
	
	
	public void setMapStats(MapStats newStats){
		if (newStats == null){
			this.nameLbl.setText("No map selected");
			this.sizeLbl.setText("0 x 0");
			this.winsPlaysLbl.setText("0 / 0");
			this.stageLbl.setText("X");
			
		} else {
			this.nameLbl.setText(newStats.name);
			this.sizeLbl.setText("" +newStats.size+ " x " +newStats.size);
			this.winsPlaysLbl.setText("" +newStats.wins+ " / " +newStats.plays);
			if (newStats.isStaged==0){
				this.stageLbl.setText("Public");
			} else {
				this.stageLbl.setText("Private");
			}
		}
	}


	@Override
	public void valueChanged(ListSelectionEvent lsEvent) {
		JList source = (JList) lsEvent.getSource();
		this.selectedStats = MapStats.getStatsByName((String) source.getSelectedValue());
		this.setMapStats(this.selectedStats);
	}
	
	
	public MapStats getFocusedMap(){
		return this.selectedStats;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MapStats ms = this.getFocusedMap();
			
		if (e.getActionCommand().equals("new")) {
			this.createMazeWin.enable();
			
		} else if (e.getActionCommand().equals("swap")){
			if (ms == null) return;
			Main.sendServerCommand("/maze/play/swapStage -id \"" +ms.id+ "\"");
			
		} else if (e.getActionCommand().equals("delete")) {
			if (ms == null) return;
			
			if (ms.isStaged == 0){
				String[] msg = {"Cannot delete map", "Players can only delete private maps"};
				ServerDialog dummy = new ServerDialog(msg);
			} else {
				Main.sendServerCommand("/maze/play/deleteMap -id \"" +ms.id+ "\"");
			}
			
		} else if (e.getActionCommand().equals("edit")){
			if (ms == null) return;
			
			if (ms.isStaged == 0){
				String[] msg = {"Cannot edit map", "Players can only edit private maps"};
				ServerDialog dummy = new ServerDialog(msg);
				
			} else {
				//Main.sendServerCommand("/maze/play/deleteMap -id \"" +ms.id+ "\"");
			}
		}
	}


	@Override
	public void focusGained(FocusEvent focus) {
		JList source = (JList) focus.getSource();
		this.selectedStats = MapStats.getStatsByName((String) source.getSelectedValue());
		this.setMapStats(this.selectedStats);
		
	}


	@Override
	public void focusLost(FocusEvent arg0) {}
}
