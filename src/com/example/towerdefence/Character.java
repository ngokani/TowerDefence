package com.example.towerdefence;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Character {
	protected Bitmap bMapCharacter;
	protected int charPoint, maxPower;
	protected int radius;
	protected float xPos, yPos;
	protected float widthBMP, heightBMP;
	protected Rect srcRec = new Rect();
	protected Rect dstRec = new Rect();
	protected Position mapPos;
	protected float pixel_width, pixel_height;
	
	//Generic class for all characters including main tower
	public Character(Bitmap bMapCharacter,float xPos, float yPos, float pixel_width, float pixel_height) {
		this.bMapCharacter = bMapCharacter;
		this.charPoint = 1;
		this.maxPower = 1;
		
		this.xPos = xPos;
		this.yPos = yPos;
		
		this.pixel_width = pixel_width;
		this.pixel_height = pixel_height;
		
		int xMapPos = (int) (xPos/pixel_width); //get grid position
		int yMapPos = (int) (yPos/pixel_height);//get grid position
		
		this.mapPos = new Position(xMapPos,yMapPos); //store grid position
		
		this.widthBMP = bMapCharacter.getWidth(); //scale according to bitmap size
		this.heightBMP = bMapCharacter.getHeight();

		srcRec = new Rect(0,0,(int)widthBMP,(int)heightBMP);
		dstRec = new Rect((int)this.xPos,(int)(this.yPos-heightBMP),(int)this.xPos+(int)widthBMP,(int)this.yPos);
	}
	
	public Bitmap getBMapCharacter() {
		return this.bMapCharacter;
	}
	
	public void setbMapCharacter(Bitmap bMapCharacter) {
		this.bMapCharacter = bMapCharacter;
	}
	
	public int getPower() {
		return this.charPoint;
	}
	
	protected void increaseCharacterPoint() {
		if (charPoint < maxPower)
		{
			charPoint++;
			radius*=charPoint;
		}
	}
	
	protected boolean decreaseCharacterPoint() {
		if (charPoint > 0)
			charPoint--;
		
		if (charPoint == 0)
			return false;
		else
			return true;
	}
	
	public float getXPos() {
		return this.xPos;
	}
	
	public void setXPos(float xPos) {
		this.xPos = xPos;
	}
	
	public float getYPos() {
		return this.yPos;
	}
	
	public void setYPos(float yPos) {
		this.yPos = yPos;
	}
	
	public Rect getSrcRect() {
		return this.srcRec;
	}
	public Rect getDstRect() {
		return this.dstRec;
	}
	
	public Position getMapPos() {
		return this.mapPos;
	}
	public void setMapPos(Position mapPos) {
		this.mapPos = mapPos;
	}
	
	public float getPixelWidth() {
		return this.pixel_width;
	}
	
	public float getPixelHeight() {
		return this.pixel_height;
	}
}
