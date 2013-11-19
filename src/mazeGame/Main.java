package mazeGame;

/* TODO:
 *  [X] Map Manager
 *    [X] List Map stats
 *    [X] Create new map
 *    [x] Edit existing map
 *    [X] Delete map
 *  [.] Lobby chat
 *    [X] Show chat
 *    [ ] Show leaderboard
 *    [X] Send chat
 *  [.] Game list
 *    [X] List available rooms
 *    [.] Join room
 *    [X] Create game room
 *    [*] Show waiting room
 *  [ ] Game play
 *    [ ] Render map
 *    
 *    Room string format
 *    <game type>:[<players in room>/<max players>] <creator's name> game
 */


import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;
import mazeGame.NetworkHandlers.NetManager;
import mazeGame.map.GameRoom;
import mazeGame.map.MapStats;
import mazeGame.window.EditorPallet;
import mazeGame.window.EditorWindow;
import mazeGame.window.LogViewer;
import mazeGame.window.MainMenu;
import mazeGame.window.MapManagerWindow;
import mazeGame.window.NewGameWindow;
import mazeGame.window.ServerSelection;
import mazeGame.window.WaitingRoomWindow;



public class Main implements ActionListener{
	/* Windowing things */
	public static NewGameWindow newGame;
	public static WaitingRoomWindow waitRoom;
	
	/* Resource managers */
	public static NetManager netMan;
	public static ImageManager imgMan;

	/* Program frame rate */
	public static int deltaT;
	public static int frameRate;
	
	/** Timer loop counters */
	private long loopCounter;
	private long slowLoopCounter;
	
	
	public static void main(String[] args){
		imgMan = new ImageManager();
		
		/* Set up windows */
		LogViewer.get().disable();
		ServerSelection.get().enable();
		MapManagerWindow.get().disable();
		MainMenu.get().disable();
		EditorWindow.reset(10); EditorWindow.get().disable();
		EditorPallet.get().disable();
		newGame = new NewGameWindow(); newGame.disable();
		waitRoom = new WaitingRoomWindow(); waitRoom.disable();
		
		log(">Starting");
		
		/* Initialize managers */
		netMan = new NetManager();
		MapStats.statsList = new LinkedList<MapStats>();
		GameRoom.roomList = new LinkedList<GameRoom>();
		
		
		/* Setup timer stuffs */
		frameRate = 20;
		deltaT = 1000/frameRate;
		Timer t = new Timer(deltaT, new Main());
		t.start();
	}
	
	/* Logging methods */
	public static void log(String msg){LogViewer.get().log(msg);}
	public static void logln(String msg){LogViewer.get().logln(msg);}
	public static void logAbridged(String msg){LogViewer.get().logAbridged(msg);}
	
	
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
		if (this.loopCounter >= Main.frameRate){
			/* 1-Second ticker */
			this.loopCounter = 0;
			this.slowLoopCounter += 1;
			if (this.slowLoopCounter >= 5){
				/* 5-Second ticker */
				this.slowLoopCounter = 0;
				this.fiveSecondEvent();
			}
			
			this.oneSecondEvent();
		}
		
		Main.netMan.readSocket();
	}
	
	
	/** Called every second */
	public void oneSecondEvent(){
		/* Keep up to date with game rooms */
		if (MainMenu.get().isVisible()){
			Main.srvFetchGameRooms(false);
		}
	}
	
	/** Called every five seconds */
	public void fiveSecondEvent(){
	}
	
	
	private static void sendServerCommand(String msg, boolean doLog){
		if (doLog) logln("& " + msg + ";");
		Main.netMan.sendCommand(msg);
	}
	
	
	public static void serverLoginSetup(){
		ServerSelection.get().enable();
		MapManagerWindow.get().disable();
		MainMenu.get().disable();
		EditorWindow.get().disable();
		EditorPallet.get().disable();
		Main.newGame.disable();
		Main.waitRoom.disable();
	}
	
	
	public static void mainLobbySetup(){
		ServerSelection.get().disable();
		MainMenu.get().enable();
		EditorWindow.get().disable();
		EditorPallet.get().disable();
		Main.newGame.disable();
		Main.waitRoom.disable();
	}
	
	
	public static void editMazeSetup(){
		ServerSelection.get().disable();
		MainMenu.get().enable();
		EditorWindow.get().enable();
		EditorPallet.get().enable();
		Main.newGame.disable();
		Main.waitRoom.disable();
	}
	
	
	/* Server commands */
	public static void srvFetchGameRooms(boolean flag){
		Main.sendServerCommand("/maze/room/fetch", flag);
	}
	public static void srvEditMaze(int id, int row, int col, char tile){
		String msg = "/maze/play/editMaze -id \"" +id+ "\" ";
		msg += " -row \"" +row+ "\" -column \"" +col+ "\" -value \"" +tile+"\"";		
		Main.sendServerCommand(msg, true);
	}
	public static void srvSendChatMessage(String msgText){
		Main.sendServerCommand("/chat/msg -message \"" +msgText+ "\" -room \"lobby\"", true);
	}
	public static void srvSwapMazeStage(int id){
		Main.sendServerCommand("/maze/play/swapStage -id \"" +id+ "\"", true);
	}
	public static void srvDeleteMaze(int id){
		Main.sendServerCommand("/maze/play/deleteMap -id \"" +id+ "\"", true);
	}
	public static void srvGetMaze(int id){
		Main.sendServerCommand("/maze/play/getMaze -id \"" +id+ "\"", true);
	}
	public static void srvCreateRoom(String type, String size){
		String pars = "-type \"" + type + "\" -players \"2\" ";
		pars += "-size \"" + size + "\"";
		Main.sendServerCommand("/maze/room/create "+pars, true);
	}
	public static void srvCreateMaze(String size, String name){
		Main.sendServerCommand("/maze/play/newMap -size \"" +size+
				"\" -name \"" +name+"\"", true);
	}
}
