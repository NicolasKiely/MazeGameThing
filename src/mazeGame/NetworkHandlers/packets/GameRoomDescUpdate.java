package mazeGame.NetworkHandlers.packets;

import mazeGame.Main;
import mazeGame.NetworkHandlers.NetTable;
import mazeGame.NetworkHandlers.PacketHandler;
import mazeGame.map.GameRoom;


/**
 * Handles update to active game room description
 * This server packet is usually sent to signal that the client
 * either successfully entered a game room or is no longer in one
 * @author Nic
 *
 */
public class GameRoomDescUpdate implements PacketHandler{

	@Override
	public String getMatcherString() {
		return "JOINMAZEROOM";
	}

	@Override
	public void onPacketMatch(NetTable packet) {
		int hostI  = packet.getColumnIndex("Host");
		int typeI  = packet.getColumnIndex("Type");
		int countI = packet.getColumnIndex("Count");
		int maxI   = packet.getColumnIndex("Max");
		int sizeI  = packet.getColumnIndex("Size");
		
		if (hostI<0 || typeI<0 || countI<0 || maxI<0 || sizeI<0){
			Main.logln("ERROR in room update packet, column missing!");
			return;
		}
		
		String[] record = packet.getRecord(0);
		if (record == null) return;
		
		/* Player out of room */
		if (record[hostI].equals("#NULL#")){
			GameRoom.activeRoom = null;
			Main.mainLobbySetup();
			return;
		}
		
		GameRoom.activeRoom = new GameRoom();
		GameRoom.activeRoom.setHost(record[hostI]);
		GameRoom.activeRoom.setType(record[typeI]);
		GameRoom.activeRoom.setCurrentPlayers(Integer.valueOf(record[countI]));
		GameRoom.activeRoom.setMaxPlayers(Integer.valueOf(record[maxI]));
		GameRoom.activeRoom.setMazeSize(Integer.valueOf(record[sizeI]));
		Main.waitingRoomSetup();
	}
	
}
