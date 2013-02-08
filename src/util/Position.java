package util;

public class Position {

	private int row;
	private int column;
	
	public Position(int r, int c) {
		row = r;
		column = c;
	}
	
	public void setColumn(int c) {
		column = c;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public int getRow() {
		return row;
	}
	
	public boolean equals(Position p) {
		if (this.row == p.getRow() && this.column == p.getColumn()) {
			return true;
		} else return false;
	}
}
