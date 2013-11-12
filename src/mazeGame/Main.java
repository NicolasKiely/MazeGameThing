package mazeGame;

/* TODO:
 *  [.] Map Manager
 *    [X] List Map stats
 *    [X] Create new map
 *    [.] Edit existing map
 *      [ ] Fetch maze data packet
 *      [.] Render top-down maze
 *    [X] Delete map
 *  [ ] Lobby chat
 *    [ ] Show chat
 *    [ ] Send chat
 *  [ ] Game list
 *    [ ] List available rooms
 *    [ ] Join room
 *  [ ] Game play
 *    [ ] Render map
 */


import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;
import mazeGame.NetworkHandlers.NetManager;
import mazeGame.map.MapStats;
import mazeGame.window.EditorPallet;
import mazeGame.window.EditorWindow;
import mazeGame.window.LogViewer;
import mazeGame.window.MainMenu;
import mazeGame.window.MapManagerWindow;
import mazeGame.window.ServerSelection;



public class Main implements ActionListener{
	/* Windowing things */
	public static ServerSelection serverSelection;
	public static LogViewer logViewer;
	public static MapManagerWindow mapManWin;
	public static MainMenu mainWin;
	public static EditorWindow editor;
	public static EditorPallet pallet;
	
	/* Resource managers */
	public static NetManager netMan;
	public static ImageManager imgMan;

	/* Program frame rate */
	public static int deltaT;
	public static int frameRate;
	
	/** Timer loop counter */
	private long loopCounter;
	
	public static void main(String[] args){
		imgMan = new ImageManager();
		
		/* Set up windows */
		logViewer = new LogViewer(); logViewer.enable();// logViewer.setState(JFrame.ICONIFIED);
		serverSelection = new ServerSelection(); serverSelection.enable();
		mapManWin = new MapManagerWindow(); mapManWin.disable();
		mainWin = new MainMenu(); mainWin.disable();
		editor = new EditorWindow(10); editor.disable();
		pallet = new EditorPallet(); pallet.disable();
		
		log(">Starting");
		
		/* Initialize managers */
		netMan = new NetManager();
		MapStats.statsList = new LinkedList<MapStats>();
		
		
		/* Setup timer stuffs */
		frameRate = 20;
		deltaT = 1000/frameRate;
		Timer t = new Timer(deltaT, new Main());
		t.start();
	}
	
	/* Logging methods */
	public static void log(String msg){logViewer.log(msg);}
	public static void logln(String msg){logViewer.logln(msg);}
	public static void logAbridged(String msg){logViewer.logAbridged(msg);}
	
	
	public static void log(String[] msg){
		String newMsg = "[";
		
		for (int i = 0; i < msg.length; i++){
			newMsg += msg[i];
			if (i != msg.length-1){
				newMsg += ", ";
			}
		}
		
		newMsg += "]";
		log(newMsg);
	}


	/**
	 * Main event loop
	 */
	public void actionPerformed(ActionEvent event) {
		this.loopCounter += 1;
		if (this.loopCounter >= Main.frameRate){this.loopCounter = 0;}
		
		Main.netMan.readSocket();
	}
	
	
	public static void sendServerCommand(String msg){
		logln("& " + msg + ";");
		Main.netMan.sendCommand(msg);
	}
	
	
	public static void serverLoginSetup(){
		Main.logViewer.enable();
		Main.serverSelection.enable();
		Main.mapManWin.disable();
		Main.mainWin.disable();
		Main.editor.disable();
		Main.pallet.disable();
	}
	
	
	public static void mainLobbySetup(){
		Main.logViewer.enable();
		Main.serverSelection.disable();
		Main.mapManWin.enable();
		Main.mainWin.enable();
		Main.editor.disable();
		Main.pallet.disable();
	}
	
	
	public static void editMazeSetup(){
		Main.logViewer.enable();
		Main.serverSelection.disable();
		Main.mapManWin.disable();
		Main.mainWin.enable();
		Main.editor.enable();
		Main.pallet.enable();
	}
}
