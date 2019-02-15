package com.example.towerdefence;

/**
 * Simple class to store x and y coordinates
 *
 */
public class Position {
	int x,y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int[] getPos() {
		int [] pos = new int[2];
		pos[0] = this.x;
		pos[1] = this.y;
		
		return pos;
	}
	
	// basic check
	public boolean equals(Position pos) {
		if (pos.x == this.x &&
				pos.y == this.y)
			return true;
		
		return false;
	}

}
