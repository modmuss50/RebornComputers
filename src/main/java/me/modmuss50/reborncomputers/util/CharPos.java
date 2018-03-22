package me.modmuss50.reborncomputers.util;

public class CharPos {

	public int row;
	public int column;

	public CharPos(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public CharPos() {
		this(0, 0);
	}

	public void set(int row, int column){
		this.row = row;
		this.column = column;
	}
}
