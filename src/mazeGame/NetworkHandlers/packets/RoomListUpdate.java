package mazeGame.NetworkHandlers.packets;

import mazeGame.Main;
import mazeGame.NetworkHandlers.NetTable;
import mazeGame.NetworkHandlers.PacketHandler;
import mazeGame.map.GameRoom;

public class RoomListUpdate implements PacketHandler{

	@Override
	public String getMatcherString() {
		return "MAZELIST";
	}

	@Override
	public void onPacketMatch(NetTable packet) {
		int hostI  = packet.getColumnIndex("Host");
		int typeI  = packet.getColumnIndex("Type");
		int countI = packet.getColumnIndex("Count");
		int maxI   = packet.getColumnIndex("Max");
		int sizeI  = packet.getColumnIndex("Size");
		
		/* Reset room list */
		GameRoom.roomList.clear();
		
		if (hostI<0 || typeI<0 || countI<0 || maxI<0 || sizeI<0){
			Main.logln("ERROR in room list update packet, column missing!");
			return;
		}
		
		/* Loop through records */
		for (int i = 0; i < packet.getNumberOfRecords(); i++){
			String[] record = packet.getRecord(i);
			if (record == null) continue;
			if (record[0]==null||record[1]==null||record[2]==null||record[3]==null||record[4]==null){
				continue;
			}
			
			/* Create room object and add to list */
			GameRoom newRoom = new GameRoom();
			newRoom.setHost(record[hostI]);
			newRoom.setType(record[typeI]);
			newRoom.setCurrentPlayers(Integer.valueOf(record[countI]));
			newRoom.setMaxPlayers(Integer.valueOf(record[maxI]));
			newRoom.setMazeSize(Integer.valueOf(record[sizeI]));
			
			GameRoom.roomList.add(newRoom);
		}
		
		Main.mainWin.resetRoomList();
	}

}
