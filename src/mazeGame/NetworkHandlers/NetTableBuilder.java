package mazeGame.NetworkHandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mazeGame.Main;

public class NetTableBuilder {
	/** Working table instance */
	private NetTable workingTable;
	
	/** List of tables structures ready for processing */
	private List<NetTable> finishedTables;
	
	/** Working state; what part of table is being parsed */
	private int state;
	
	/** Working field buffer */
	private String workingField;
	
	/** Working list of fields */
	private List<String> workingSegment;
	
	private static final int HEADER_STATE = 0;
	private static final int COLUMN_STATE = 1;
	private static final int RECORD_STATE = 2;
	
	
	/** Initialize empty builder */
	public NetTableBuilder(){
		this.workingTable = new NetTable();
		this.finishedTables = new ArrayList<NetTable>();
		this.state = HEADER_STATE;
		this.workingField = "";
		this.workingSegment = new ArrayList<String>();
	}
	
	
	/**
	 * Gets array of finished tables, and resets list
	 */
	public NetTable[] getTables(){
		NetTable[] tableArray = null;
		
		if (this.finishedTables.isEmpty()){
			return null;
			
		} else {
			tableArray = this.finishedTables.toArray(new NetTable[this.finishedTables.size()]);
			this.finishedTables = new ArrayList<NetTable>();
			
			return tableArray;
		}
	}
	
	
	/**
	 * Processes stream of table strings
	 */
	public void processStream(BufferedReader stream){
		int c;
		
		if (stream == null) return;
		
		try {
			while (stream.ready()){
				c = stream.read();
				if (c < 0) break;
			
				this.processChar(c);
			}
		} catch (IOException e){
			Main.logln("Main loop socket read: " + e.getMessage());
		}
	}
	
	
	/**
	 * Process a character in a stream
	 * @param c
	 */
	private void processChar(int c){
		if (c == '\t'){
			/* Add field and clear */
			this.workingSegment.add(this.workingField);
			this.workingField = "";
			
		} else if (c == '\n' || c == '\r'){
			/* Process buffer, then move to next state */
			this.workingSegment.add(this.workingField);
			this.workingField = "";
			
			this.processBuffer();
			this.incrementState();
			
		} else {
			/* Add to buffer */
			this.workingField += (char) c;
		}
	}
	
	
	private void processBuffer(){
		String[] fields = this.workingSegment.toArray(new String[this.workingSegment.size()]);
		this.workingSegment = new ArrayList<String>();
		
		if (this.state == HEADER_STATE){
			this.workingTable.setHeader(fields);
			return;
			
		} else if (this.state == COLUMN_STATE){
			this.workingTable.setColumns(fields);
			return;
			
		} else {
			this.workingTable.setRecords(fields, this.workingTable.getNumberOfColumns());
			
			this.finishedTables.add(this.workingTable);
			this.workingTable = new NetTable();
		}
	}
	
	
	/** Handles state change */
	private void incrementState(){
		if (this.state == HEADER_STATE){
			this.state = COLUMN_STATE;
			return;
			
		} else if (this.state == COLUMN_STATE){
			this.state = RECORD_STATE;
			return;
			
		} else {
			this.state = HEADER_STATE;
		}
	}
}
