package mazeGame.window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import mazeGame.Main;
import mazeGame.map.EditorRenderer;
import mazeGame.map.Maze;

/**
 * Manages the maze editor window
 * @author Nic
 */
public class EditorWindow extends JFrame implements MouseListener{
	private static final long serialVersionUID = 13L;
	private static EditorWindow instance;
	
	private EditorRenderer rend;
	private JScrollPane scroll;
	
	
	public static EditorWindow get(){
		return EditorWindow.instance;
	}
	
	
	public static void reset(int nSize){
		EditorWindow.instance = new EditorWindow(nSize);
	}
	
	
	private EditorWindow(int nSize){
		this.setSize(640, 480);
		this.setLocation(300, 500);
		this.setTitle("Maze Editor");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.rend = new EditorRenderer(nSize);
		this.rend.addMouseListener(this);
		this.scroll = new JScrollPane(this.rend);
		this.add(this.scroll);
	}
	
	
	public void enable(){this.setVisible(true);}
	public void disable(){this.setVisible(false);}


	@Override
	public void mouseClicked(MouseEvent e) {
	}


	@Override
	public void mouseEntered(MouseEvent e){}


	@Override
	public void mouseExited(MouseEvent e){}


	@Override
	public void mousePressed(MouseEvent e) {
		Maze edit = Maze.editorMaze;
		
		int row = (e.getY()/33);
		int col = (e.getX()/33);
		char tile = EditorPallet.get().getSelectedTile();
		edit.setTile(row, col, tile);
		this.rend.repaint();
		
		/* Send update to server */
		Main.srvEditMaze(edit.getStats().id, row, col, tile);
	}


	@Override
	public void mouseReleased(MouseEvent e) {}
}
