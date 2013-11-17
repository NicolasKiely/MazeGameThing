package mazeGame.map;

import java.util.List;

/**
 * Manages game rooms
 * @author Nic
 *
 */
public class GameRoom {
	public static List<GameRoom> roomList;
	
	private String host;
	private String gameType;
	private int currentPlayers;
	private int maxPlayers;
	private int mapSize;

	
	public String toString(){
		return "[" +this.currentPlayers+ "/" +this.maxPlayers+ "] (" + this.gameType+ ": " +
				this.mapSize+ "x" +this.mapSize+ ") @" + this.host;
	}
	
	public int getMaxPlayers(){return this.maxPlayers;}
	public String getCreator(){return this.host;}
	
	public void setHost(String newHost){this.host = newHost;}
	public void setType(String newType){this.gameType = newType;}
	public void setCurrentPlayers(int newCurrent){this.currentPlayers = newCurrent;}
	public void setMaxPlayers(int newMax){this.maxPlayers = newMax;}
	public void setMazeSize(int newSize){this.mapSize = newSize;}
	
	/** Looks up room in list by name */
	public static int getIndexByHost(String hostName){
		int i = 0;
		for (GameRoom room : GameRoom.roomList){
			if (room.host.equals(hostName)) return i;
		}
		
		return -1;
	}
}
