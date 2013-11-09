package mazeGame;

/* TODO:
 *  [.] Map Manager
 *    [X] List Map stats
 *    [X] Create new map
 *    [.] Edit existing map
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.Timer;

import mazeGame.NetworkHandlers.NetManager;
import mazeGame.map.MapStats;
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
	
	public static NetManager netMan;

	/* Program frame rate */
	public static int deltaT;
	public static int frameRate;
	
	/** Timer loop counter */
	private long loopCounter;
	
	public static void main(String[] args){
		/* Set up windows */
		logViewer = new LogViewer(); logViewer.enable();
		serverSelection = new ServerSelection(); serverSelection.enable();
		mapManWin = new MapManagerWindow(); mapManWin.disable();
		mainWin = new MainMenu(); mainWin.disable();
		
		/* Initialize managers */
		netMan = new NetManager();
		MapStats.statsList = new LinkedList<MapStats>();
		
		logln("Starting");
		logln("Note: New log entries are inserted at top of window, not bottom!");
		
		/* Setup timer stuffs */
		frameRate = 20;
		deltaT = 1000/frameRate;
		Timer t = new Timer(deltaT, new Main());
		t.start();
	}
	
	public static void log(String msg){
		logViewer.log(msg);
	}
	
	public static void logln(String msg){
		logViewer.logln(msg);
	}
	
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
		if (this.loopCounter >= frameRate){this.loopCounter = 0;}
		
		this.netMan.readSocket();
	}
	
	
	public static void sendServerCommand(String msg){
		logln("& " + msg + ";");
		netMan.sendCommand(msg);
	}
}
