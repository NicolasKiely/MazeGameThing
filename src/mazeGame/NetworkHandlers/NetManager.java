package mazeGame.NetworkHandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import mazeGame.Main;
import mazeGame.NetworkHandlers.packets.MapStatsUpdate;
import mazeGame.NetworkHandlers.packets.ServerDialogUpdate;

/** Manages the networking */
public class NetManager {
	/* Socket IO stuff */
	private Socket servSock;
	private PrintWriter sockWriter;
	private BufferedReader sockReader;
	
	private List<PacketHandler> handlers;
	
	/**
	 * Initializes stuff
	 */
	public NetManager(){
		servSock = null;
		sockWriter = null;
		sockReader = null;
		handlers = new LinkedList<PacketHandler>();
		
		this.registerHandler(new MapStatsUpdate());
		this.registerHandler(new ServerDialogUpdate());
	}
	
	
	/**
	 * Adds a new packet handler
	 * @param newHandler
	 */
	public void registerHandler(PacketHandler newHandler){
		handlers.add(newHandler);
	}
	
	
	/**
	 * Attempt connection with server
	 * @param address
	 * @return
	 */
	public boolean attemptConnection(String address, int port){
		try {
			this.servSock = new Socket(address, port);
			this.sockWriter = new PrintWriter(servSock.getOutputStream(), true);
			this.sockReader = new BufferedReader(new InputStreamReader(servSock.getInputStream()));
		
			/* Try to login */
			this.sockWriter.print("JAVA|/acc/log/login -account \""+Main.serverSelection.getUserName()
					+ "\" -password \"" + Main.serverSelection.getPassword() + "\";");
		
			/* Try to load map stats */
			this.sockWriter.println("JAVA|/maze/play/mapStats;");
		
			this.sockWriter.flush();
		} catch (UnknownHostException e) {
			Main.logln("Connection attempt: " + e.getMessage());
			return false;
			
		} catch (IOException e) {
			Main.logln("Connection attempt: " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Attempt to read socket
	 */
	public void readSocket(){
		/* Read socket */
		try {
			int state = 0;
			NetTable table = null;
			
			while (sockReader != null && sockReader.ready()){
				if (table == null){table = new NetTable();}
				
				String s = sockReader.readLine();
				String[] fields = s.split("\t");
				
				if (state == 0){
					table.setHeader(fields);
					
				} else if (state == 1){
					table.setColumns(fields);
					
				} else {
					table.setRecords(fields, table.getColumns().length);
					state = -1;
				}
				
				state++;
			}

			if (table != null) {
				Main.logAbridged(table.toString());
				
				/* Check handlers */
				for (PacketHandler handler : this.handlers){
					if (table.hasHeaderValue(handler.getMatcherString())){
						/* Fire off matching handlers */
						handler.onPacketMatch(table);
					}
				}
			}
			
			
			
		} catch (IOException e) {
			Main.logln("Main loop socket read: " + e.getMessage());
		}
	}
	
	
	/**
	 * Disconnect from server, if applicable
	 */
	public void disconnect(){
		if (this.servSock != null) {
			try {
				this.servSock.close();
			} catch (IOException e) {
				Main.logln("Bad disconnect: " + e.getMessage());
			}
			
			this.servSock = null;
		}
		
		this.sockWriter = null;
		this.sockReader = null;
		Main.serverSelection.enable();
		Main.mapManWin.disable();
		Main.mainWin.disable();
	}
	
	
	/**
	 * Seavax server listening port = 6282
	 * @return
	 */
	public int getDefaultPort(){return 6282;}
	
	
	/**
	 * Sends command to server. Adds echo string and flushes
	 * @param msg
	 */
	public void sendCommand(String msg){
		if (this.sockWriter == null) return;
		
		this.sockWriter.println("MAZE|" +msg+ ";");
		
		this.sockWriter.flush();
	}
}
