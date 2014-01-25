package mazeGame.NetworkHandlers.packets;

import mazeGame.NetworkHandlers.NetTable;
import mazeGame.NetworkHandlers.PacketHandler;

public class MazeRoomUpdate implements PacketHandler{

	@Override
	public String getMatcherString() {
		return "JOINMAZEROOM";
	}

	@Override
	public void onPacketMatch(NetTable packet) {
		// TODO Auto-generated method stub
		
	}

}
