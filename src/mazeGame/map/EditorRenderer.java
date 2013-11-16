package mazeGame.map;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import mazeGame.Main;

public class EditorRenderer extends Component{
	private static final long serialVersionUID = 4L;
	private int size;
	
	
	public EditorRenderer(int nSize){
		this.size = nSize;
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(size*33+1, size*33+1);
    }
	
	public void paint(Graphics g){
		Maze maze = Maze.editorMaze;
		
		for (int r = 0; r < maze.getSize(); r++){
			for (int c = 0; c < maze.getSize(); c++){
				/* Lookup tile value */
				char tileChar = maze.getTile(r, c);
				
				if (tileChar == 0){continue;}
				Image img = Main.imgMan.lookupImage(tileChar).getImage();
				g.drawImage(img, c*33+1, r*33+1, null);
			}
		}
	}
}
