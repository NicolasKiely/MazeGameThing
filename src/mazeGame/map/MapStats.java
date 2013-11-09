package mazeGame.map;

import java.util.List;

/**
 * 
 * @author Nic
 *
 */
public class MapStats {
	/** List of map stats */
	public static List<MapStats> statsList;
	
	/* Map stats fields */
	public String name;
	public int id;
	public int size;
	public int wins;
	public int plays;
	public int isStaged;
	
	
	public MapStats(){
		this.name = "";
		this.id = -1;
		this.size = 0;
		this.wins = 0;
		this.plays = 0;
		this.isStaged = -1;
	}
	
	public static MapStats getStatsByName(String newName){
		if (statsList == null){
			return null;
		}
		
		for (MapStats ms : statsList){
			if (ms.name.equalsIgnoreCase(newName)){
				return ms;
			}
		}
		
		return null;
	}
	
	
	/** Sets id */
	public void setID(String nId){
		if (nId == null || nId.equals("")){
			this.id = -1;
		} else {
			this.id = Integer.valueOf(nId);
		}
	}
	
	
	/** Sets name */
	public void setName(String nName){
		if (nName == null || nName.equals("")){
			this.name = "No Map Selected";
		} else {
			this.name = nName;
		}
	}
	
	
	/** Sets size */
	public void setSize(String nSize){
		if (nSize == null || nSize.equals("")){
			this.size = 0;
		} else {
			this.size = Integer.valueOf(nSize);
		}
	}
	
	
	/** Sets plays */
	public void setPlays(String nPlays){
		if (nPlays == null || nPlays.equals("")){
			this.plays = 0;
		} else {
			this.plays = Integer.valueOf(nPlays);
		}
	}
	
	
	/** Sets wins */
	public void setWins(String nWins){
		if (nWins == null || nWins.equals("")){
			this.wins = 0;
		} else {
			this.wins = Integer.valueOf(nWins);
		}
	}
	
	
	/** Set isStaged */
	public void setStaged(String nStage){
		if (nStage == null || nStage.equals("")){
			this.isStaged = -1;
		} else {
			this.isStaged = Integer.valueOf(nStage);
		}
	}
}
