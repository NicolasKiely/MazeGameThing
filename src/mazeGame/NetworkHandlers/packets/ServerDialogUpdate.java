package mazeGame.NetworkHandlers.packets;

import mazeGame.Main;
import mazeGame.NetworkHandlers.NetTable;
import mazeGame.NetworkHandlers.PacketHandler;
import mazeGame.window.NewMazeWindow;
import mazeGame.window.ServerDialog;

public class ServerDialogUpdate implements PacketHandler{
	@Override
	public String getMatcherString() {
		return "DIALOG";
	}

	@Override
	public void onPacketMatch(NetTable packet) {
		int locIndex = packet.getColumnIndex("Location");
		int msgIndex = packet.getColumnIndex("Message");
		int cseIndex = packet.getColumnIndex("Cause");
		
		if (locIndex<0 || msgIndex<0 || cseIndex<0){
			Main.logln("ERROR in server dialog packet, column missing!");
			return;
		}
		
		ServerDialog newWin = new ServerDialog(packet.getRecord(0));
	}
}
