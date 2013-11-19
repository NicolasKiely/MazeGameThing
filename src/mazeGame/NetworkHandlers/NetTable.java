package mazeGame.NetworkHandlers;


/**
 * Inbound server packet
 * @author Nic
 *
 */
public class NetTable {
	private String[] header;
	private String[] columns;
	private String[][] records;
	
	public NetTable(){
		header = null;
		columns = null;
		records = null;
	}
	
	
	/** Checks if string matches anything in header. Ignores case */
	public boolean hasHeaderValue(String value){
		if (this.header == null || value == null) {return false;}
		for (int i = 0; i < this.header.length; i++){
			if (this.header[i].equalsIgnoreCase(value)){return true;}
		}
		return false;
	}
	
	
	public String[] getFieldsByColumn(String columnName){
		int colIndex = -1;
		
		/* Look up column index by name */
		for (int i = 0; i < this.columns.length; i++){
			if (this.columns[i].equalsIgnoreCase(columnName)){
				colIndex = i;
			}
		}
		
		if (colIndex < 0){
			/* Failed to find column by name */
			return null;
		}
		
		/* Load up fields in records under column */
		String[] results = new String[this.getNumberOfRecords()];
		for (int i = 0; i < results.length; i++){
			results[i] = this.records[i][colIndex];
		}
		
		return results;
	}
	
	
	public void setHeader(String[] newHeader){this.header = newHeader;}
	public void setColumns(String[] newColumns){this.columns = newColumns;}
	
	/**
	 * Internalizes 1d string array to a 2d record array
	 * @param newRecords
	 * @param colNum
	 */
	public void setRecords(String[] newRecords, int colNum){
		if (newRecords == null) return;
		if (newRecords[0] == null) return;
		
		int recordLine = 0;
		int field = 0;
		if (colNum == 0){colNum=1;}
		int recNum = (int) Math.ceil(((double) newRecords.length) / ((double) colNum));
		
		this.records = new String[recNum][colNum];
		
		for (int i = 0; i < newRecords.length; i++){
			/* Set record */
			
			/*try {*/
				this.records[recordLine][field] = newRecords[i];
			/*} catch (ArrayIndexOutOfBoundsException ex) {
				Main.logln("ERROR: netTable.setRecords: i="+i+" f="+field+" c/y="+colNum+" l="+newRecords.length+
						" x="+(this.records.length-1)+" r="+recordLine);
				Main.log("\n");
				Main.log(newRecords);
			}*/
			
			/* Increment field/record line */
			field++;
			if (field >= colNum){
				field = 0;
				recordLine++;
			}
		}
	}
	
	
	/**
	 * Fetches a record by index
	 * @param index
	 * @return Record string array
	 */
	public String[] getRecord(int index){
		return this.records[index];
	}
	
	public String[] getColumns(){return this.columns;}
	public String[] getHeader(){return this.header;}
	
	
	/**
	 * Returns a formatted string
	 */
	public String toString(){
		String res = "{[Header: ";
		
		/* Add header */
		for (int i = 0; i < this.header.length; i++){
			res += this.header[i];
			if (i < this.header.length - 1){res += ", ";}
		}
		
		/* Add columns */
		res += "] [Columns: ";
		for (int i = 0; i < this.columns.length; i++){
			res += this.columns[i];
			if (i < this.columns.length - 1){res += ", ";}
		}
		
		/* Add records */
		res += "] [Records:";
		for (int i = 0; i < this.records.length; i++){
			res += " (";
			for (int j = 0; j < this.records[i].length; j++){
				res += this.records[i][j];
				if (j < this.records[i].length - 1){res += ", ";}
			}
			res += ")";
		}
		
		res += "]";
		return res;
	}
	
	
	/** Returns number of records in table */
	public int getNumberOfRecords(){
		if (this.records == null) {
			return 0;
		}
		
		return this.records.length;
	}
	
	
	/** Returns number of columns*/
	public int getNumberOfColumns(){
		if (this.columns == null){
			return 0;
		}
		
		return this.columns.length;
	}
	
	
	/** Gets column index by name */
	public int getColumnIndex(String columnName){
		if (this.columns == null) {
			return -1;
		}
		
		for (int i = 0; i<this.columns.length; i++){
			if (this.columns[i].equalsIgnoreCase(columnName)){
				return i;
			}
		}
		
		return -1;
	}
}
