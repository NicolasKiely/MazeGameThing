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
import mazeGame.NetworkHandlers.packets.ChatUpdate;
import mazeGame.NetworkHandlers.packets.GameRoomDescUpdate;
import mazeGame.NetworkHandlers.packets.MapStatsUpdate;
import mazeGame.NetworkHandlers.packets.MazeDataPacket;
import mazeGame.NetworkHandlers.packets.MazeRoomUpdate;
import mazeGame.NetworkHandlers.packets.RoomListUpdate;
import mazeGame.NetworkHandlers.packets.ServerDialogUpdate;
import mazeGame.window.ServerSelection;

/** Manages the networking */
public class NetManager {
	/* Socket IO stuff */
	private Socket servSock;
	private PrintWriter sockWriter;
	private BufferedReader sockReader;
	
	private List<PacketHandler> handlers;
	private NetTableBuilder builder;
	private int state;
	private NetTable table;
	String bufferLine;
	
	/**
	 * Initializes stuff
	 */
	public NetManager(){
		servSock = null;
		sockWriter = null;
		sockReader = null;
		handlers = new LinkedList<PacketHandler>();
		builder = new NetTableBuilder();
		
		this.state = 0;
		this.table = null;
		this.bufferLine = "";
		this.registerHandler(new MapStatsUpdate());
		this.registerHandler(new ServerDialogUpdate());
		this.registerHandler(new MazeDataPacket());
		this.registerHandler(new ChatUpdate());
		this.registerHandler(new RoomListUpdate());
		this.registerHandler(new GameRoomDescUpdate());
		//this.registerHandler(new MazeRoomUpdate());
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
			this.sockWriter.print("JAVA|/acc/log/login -account \""+ServerSelection.get().getUserName()
					+ "\" -password \"" + ServerSelection.get().getPassword() + "\";");
		
			/* Try to load map stats */
			this.sockWriter.print("JAVA|/maze/play/mapStats;");
		
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
		NetTable[] results;
		
		/* Read from socket */
		this.builder.processStream(this.sockReader);
		results = this.builder.getTables();
		
		if (results == null) return;
		
		for (NetTable result : results){
			this.processTablePacket(result);
		}
		
		
		// Read socket
		/*
		try {
			//state = 0;
			//NetTable table = null;
			
			while (this.sockReader != null && this.sockReader.ready()){
				String[] fields = null;
				int chr;
				boolean readyToProcess = false;
				
				if (this.table == null){this.table = new NetTable();}
				
				// Read line
				while(true){
					chr = sockReader.read();
					
					if (chr == '\n' || chr == '\r'){
						// Finished reading line
						readyToProcess = true;
						break;
					}
					*/
					/* Handle break case */
					//if (chr < 0) break;
					
					/* Add character to string */
					//this.bufferLine = this.bufferLine + (char) chr;
				//}
				
				/* Don't do anything if input line is still incomplete */
				//if (readyToProcess == false) break;
				
				/* Split string into fields */
				/*if (!this.bufferLine.equals("")){
					fields = this.bufferLine.split("\t");
					this.bufferLine = "";
					
				} else {*/
					/* Debug info. NULL line getting inserted before header? */
					/*String st = "???";
					if (state == 0) st = "header";
					if (state == 1) st = "columns";
					if (state != 2) Main.logln("Empty line for " + st);
				}
				
				if (state == 0){
					if (fields != null) table.setHeader(fields);
					
				} else if (state == 1){
					if (fields != null) table.setColumns(fields);
					
				} else {
					if (fields != null && table.getColumns() != null) table.setRecords(fields, table.getColumns().length);
					state = -1;
					
					this.processTablePacket(table);
					table = null;
				}
				
				state++;
			}
			
		} catch (IOException e) {
			Main.logln("Main loop socket read: " + e.getMessage());
		}*/
	}
	
	
	/** Processes table packet */
	public void processTablePacket(NetTable inputTable){
		if (inputTable == null) return;
		boolean packetWasHandled = false;
		
		/* Run against packet handlers */
		for (PacketHandler handler : this.handlers){
			if (inputTable.hasHeaderValue(handler.getMatcherString())){
				/* Fire off matching handlers */
				handler.onPacketMatch(inputTable);
				packetWasHandled = true;
			}
		}
	
		/* Debugging stuff */
		if (!packetWasHandled && !inputTable.getColumnString(0).equals("ACK")) Main.logAbridged(inputTable.toString());
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
		Main.serverLoginSetup();
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
