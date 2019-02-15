package com.example.towerdefence;

import java.util.ArrayList;

public class LevelOne extends Level {
	public LevelOne() {
		ArrayList<Position> positions = new ArrayList<Position>();
		positions.add(new Position(5,2));
		positions.add(new Position(9,2));
		positions.add(new Position(9,4));
		positions.add(new Position(2,4));
		positions.add(new Position(2,8));
		positions.add(new Position(5,8));
		positions.add(new Position(5,9));
		positions.add(new Position(8,9));
		positions.add(new Position(8,10));
		positions.add(new Position(5,10));
		super.setLevelPositions(positions);
		this.enemyPerLevel = 5;
	}
}
