package mazeGame;


import javax.swing.ImageIcon;
/**
 * Manages image loading from jar
 *
 */
public class ImageManager {
	public ImageIcon passableTile;
	public ImageIcon impassableTile;
	public ImageIcon startTile;
	public ImageIcon finishTile;
	public ImageIcon trapTile;
	
	
	public ImageManager(){
		try {
			ClassLoader cl = getClass().getClassLoader();
			
			this.passableTile   = new ImageIcon(cl.getResource("passibleTile.png"));
			this.impassableTile = new ImageIcon(cl.getResource("impassibleTile.png"));
			this.startTile      = new ImageIcon(cl.getResource("startTile.png"));
			this.finishTile     = new ImageIcon(cl.getResource("finishTile.png"));
			this.trapTile       = new ImageIcon(cl.getResource("trapTile.png"));

		} catch (IllegalArgumentException e) {
			Main.logln("Image loading arg error: " + e.getMessage());
		}
	}
	
	
	public ImageIcon getImageByName(String name){
		if (name.equals("passable")){return this.passableTile;}
		if (name.equals("impassable")){return this.impassableTile;}
		if (name.equals("start")){return this.startTile;}
		if (name.equals("finish")){return this.finishTile;}
		if (name.equals("trap")){return this.trapTile;}
		return null;
	}
	
	
	public ImageIcon lookupImage(char val){
		if (val == 'p' || val == 'P') return this.passableTile;
		if (val == 'i' || val == 'I') return this.impassableTile;
		if (val == 's' || val == 'S') return this.startTile;
		if (val == 'f' || val == 'F') return this.finishTile;
		if (val == 't' || val == 'T') return this.trapTile;
		
		return null;
	}
}
