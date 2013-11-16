package mazeGame.NetworkHandlers.packets;

import mazeGame.Main;
import mazeGame.NetworkHandlers.NetTable;
import mazeGame.NetworkHandlers.PacketHandler;

public class ChatUpdate implements PacketHandler{

	@Override
	public String getMatcherString() {
		return "chat";
	}

	@Override
	public void onPacketMatch(NetTable packet) {
		int msgIndex = packet.getColumnIndex("message");
		int authorIndex = packet.getColumnIndex("author");
		
		/* Loop through records */
		for (int r = 0; r < packet.getNumberOfRecords(); r++){
			String[] record = packet.getRecord(r);
			
			Main.mainWin.logChat(record[authorIndex], record[msgIndex]);
			
			/* Update render */
			//if (Main.editor.isEnabled()){Main.editor.repaint();}
		}
	}

}
