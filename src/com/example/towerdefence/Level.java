package com.example.towerdefence;

import java.util.ArrayList;

public class Level {
	private ArrayList<Position> levelPositions;
	protected int enemyPerLevel;
	
	public Level() {
		this.levelPositions = new ArrayList<Position>();
		this.enemyPerLevel = 1;
	}
	
	public Level(ArrayList<Position> levelPositions) {
		this.levelPositions = new ArrayList<Position>();
		this.levelPositions = levelPositions;
	}
	
	public ArrayList<Position> getLevelPositions() {
		return this.levelPositions;
	}
	
	public void setLevelPositions(ArrayList<Position> levelPositions) {
		this.levelPositions = levelPositions;
	}
	
	public void addPositions(Position pos) {
		levelPositions.add(pos);
	}
	
	public void removePositions(Position pos) {
		levelPositions.remove(pos);
	}

}
