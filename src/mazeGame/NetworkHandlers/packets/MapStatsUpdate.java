package mazeGame.NetworkHandlers.packets;

import mazeGame.Main;
import mazeGame.NetworkHandlers.NetTable;
import mazeGame.NetworkHandlers.PacketHandler;
import mazeGame.map.MapStats;
import mazeGame.window.MapManagerWindow;

/**
 * Handles MapStats packet
 * @author Nic
 *
 */
public class MapStatsUpdate implements PacketHandler{

	@Override
	public String getMatcherString() {
		return "mapstats";
	}

	@Override
	public void onPacketMatch(NetTable packet) {		
		/* Refresh stats */
		MapStats.statsList.clear();
		
		/* Lookup column indices */
		int nameIndex = packet.getColumnIndex("name");
		int idIndex = packet.getColumnIndex("id");
		int sizeIndex = packet.getColumnIndex("size");
		int winsIndex = packet.getColumnIndex("wins");
		int playsIndex = packet.getColumnIndex("plays");
		int stageIndex = packet.getColumnIndex("staged");
		
		if (nameIndex<0 || idIndex<0 || sizeIndex<0 || winsIndex<0 || playsIndex<0 || stageIndex<0){
			Main.logln("ERROR in map stats update packet, column missing!");
			return;
		}
		
		/* Load map stats from table */
		for (int i = 0; i < packet.getNumberOfRecords(); i++){
			String[] record = packet.getRecord(i);
			MapStats newStats = new MapStats();
			
			newStats.setID(record[idIndex]);
			newStats.setName(record[nameIndex]);
			newStats.setSize(record[sizeIndex]);
			newStats.setPlays(record[playsIndex]);
			newStats.setWins(record[winsIndex]);
			newStats.setStaged(record[stageIndex]);
			
			MapStats.statsList.add(newStats);
		}
		
		/* Refresh map names */
		MapManagerWindow.get().resetMapNames();
		
		MapManagerWindow.get().resetListSelection();
	}

}
