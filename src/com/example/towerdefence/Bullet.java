package com.example.towerdefence;


public class Bullet {
	Defender srcTower; //source and destination points
	Enemy dstEnemy;

	private float srcXPos, srcYPos;
	private float dstXPos, dstYPos;
	private float dy, dx;
	
	private float screen_width, screen_height; //screen width and height
	
	private float SHOT_VELOCITY = 50f;
	private int strength;
	boolean firstRun = true;
	
	//Default constructor if Enemy is not yet known
	public Bullet(Defender srcTower, float velocity, int strength) {
		this.srcTower = srcTower;
		
		this.SHOT_VELOCITY = velocity;
		this.srcXPos = srcTower.getDstRect().exactCenterX();
		this.srcYPos = srcTower.getDstRect().exactCenterY();
		this.dstEnemy = null;
		this.dstXPos = 0;
		this.dstYPos = 0;
		this.strength = strength;
		screen_width = srcTower.getPixelWidth() * MapPath.getLandWidth();
		screen_height = srcTower.getPixelHeight() * MapPath.getLandHeight();
	}
	
	//Overloaded constructor if Enemy is known
	public Bullet(Defender srcTower, Enemy dstEnemy, float velocity, int strength) {
		this.srcTower = srcTower;
		this.dstEnemy = dstEnemy;
		
		this.SHOT_VELOCITY = velocity;
		this.srcXPos = srcTower.getDstRect().exactCenterX();
		this.srcYPos = srcTower.getDstRect().exactCenterY();
		
		this.dstXPos = dstEnemy.getDstRect().exactCenterX();
		this.dstYPos = dstEnemy.getDstRect().exactCenterY();
		
		screen_width = srcTower.getPixelWidth() * MapPath.getLandWidth();
		screen_height = srcTower.getPixelHeight() * MapPath.getLandHeight();
	}
	
	
	//Change the target if needed
	public void setTarget(Enemy dstEnemy) {
		this.dstEnemy = dstEnemy;
		
		this.dstXPos = dstEnemy.getDstRect().exactCenterX();
		this.dstYPos = dstEnemy.getDstRect().exactCenterY();
		
		if (firstRun) {
			this.dx = this.dstXPos - this.srcXPos;
			this.dy = this.dstYPos - this.srcYPos;
			firstRun = false;
		}
	}
	
	//Update the position using deltaTime
	public void update(float deltaTime) {
		if (dstEnemy != null)
		{//Update if the enemy IS known
			if (srcXPos > screen_width ||
					srcXPos < 0 ||
					srcYPos > screen_height ||
					srcYPos < 0)
			{
				this.srcXPos = this.srcTower.getDstRect().exactCenterX();
				this.srcYPos = this.srcTower.getDstRect().exactCenterY();
				
				this.dstXPos = this.dstEnemy.getDstRect().exactCenterX();
				this.dstYPos = this.dstEnemy.getDstRect().exactCenterY();
				
				//Update enemy position
				this.dx = this.dstXPos - this.srcXPos;
				this.dy = this.dstYPos - this.srcYPos;
			}
			else {
				//Update the position by calculating dy/dx and then multiplying by the time elapsed (deltaTime) and the velocity
				srcXPos += (dx/MapPath.getLandWidth())*(this.SHOT_VELOCITY*deltaTime);
				srcYPos += (dy/MapPath.getLandHeight())*(this.SHOT_VELOCITY*deltaTime);
				
			}
		}
	
	}
	//Getters and setters
	public float getBulletX() {
		return this.srcXPos;
	}
	
	public float getBulletY() {
		return this.srcYPos;
	}
	
	public int getStrength() {
		return this.strength;
	}
	

}
