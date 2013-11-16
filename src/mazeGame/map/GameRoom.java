package mazeGame.map;

/**
 * Manages game rooms
 * @author Nic
 *
 */
public class GameRoom {
	private int maxPlayers;
	private String creator;
	
	public GameRoom(int nMaxPlayers, String nCreator){
		this.maxPlayers = nMaxPlayers;
		this.creator = nCreator;
	}
	
	
	public int getMaxPlayers(){return this.maxPlayers;}
	public String getCreator(){return this.creator;}
}
