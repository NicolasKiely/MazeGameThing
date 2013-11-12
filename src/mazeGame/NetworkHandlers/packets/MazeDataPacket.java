package mazeGame.NetworkHandlers.packets;

import mazeGame.Main;
import mazeGame.NetworkHandlers.NetTable;
import mazeGame.NetworkHandlers.PacketHandler;
import mazeGame.map.Maze;

public class MazeDataPacket implements PacketHandler{

	@Override
	public String getMatcherString() {
		return "MAZEDAT";
	}

	@Override
	public void onPacketMatch(NetTable packet) {
		/* Loop through records */
		for (int r = 0; r < packet.getNumberOfRecords(); r++){
			String[] record = packet.getRecord(r);
			
			/* Loop through fields */
			for (int c = 0; c < record.length; c++){
				char field = record[c].charAt(0);
				Maze.editorMaze.setTile(r, c, field);
			}
			
			/* Update render */
			if (Main.editor.isEnabled()){Main.editor.repaint();}
		}
	}

}
