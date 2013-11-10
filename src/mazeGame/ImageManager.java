package mazeGame;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Manages image loading from jar
 *
 */
public class ImageManager {
	Image passableTile;
	Image impassableTile;
	Image startTile;
	Image finishTile;
	Image trapTile;
	
	
	public ImageManager(){
		try {
			ClassLoader cl = getClass().getClassLoader();
			
			this.passableTile   = ImageIO.read(cl.getResource("passibleTile.png"));
			this.impassableTile = ImageIO.read(cl.getResource("impassibleTile.png"));
			this.startTile      = ImageIO.read(cl.getResource("startTile.png"));
			this.finishTile     = ImageIO.read(cl.getResource("finishTile.png"));
			this.trapTile       = ImageIO.read(cl.getResource("trapTile.png"));
			
		} catch (IOException e) {
			Main.logln("Image loading IO error: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			Main.logln("Image loading arg error: " + e.getMessage());
		}
	}
}
