package mazeGame.map;

/**
 * Stores Maze
 * @author Nic
 *
 */
public class Maze {
	/** Maze used by editor */
	public static Maze editorMaze;
	
	/** Stores raw map data */
	private char[] buf;
	
	/** Reference to stats of map */
	private MapStats refStats;
	
	
	/** Initializes maze data structure */
	public Maze(MapStats newStats){
		this.refStats = newStats;
		
		/* Allocate buffer */
		buf = new char[this.getSize()*this.getSize()];
		
		/* initialize */
		for (int c = 0; c < this.getSize(); c++){
			for (int r = 0; r < this.getSize(); r++){
				this.setTile(r, c, 'p');
			}
		}
	}
	
	
	public int getSize(){
		if (this.refStats == null) return -1;
		return this.refStats.size;
	}
	
	
	/** Gets tile value */
	public char getTile(int row, int col){
		if (this.buf == null || this.refStats == null) return 0;
		if (row<0 || row>=this.getSize() || col<0 || col>=this.getSize()) return 0;
		
		return this.buf[col + this.getSize()*row];
	}
	
	
	/** Sets tile value */
	public void setTile(int row, int col, char value){
		if (this.buf == null || this.refStats == null) return;
		if (row<0 || row>=this.getSize() || col<0 || col>=this.getSize()) return;
		
		this.buf[col + this.getSize()*row] = value;
	}
	
	
	public MapStats getStats(){return this.refStats;}
}
