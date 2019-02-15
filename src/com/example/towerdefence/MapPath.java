package com.example.towerdefence;

import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MapPath {
	private int[][] pathCo;
	private static final int LAND_X_COUNT = 10;
	private static final int LAND_Y_COUNT= 12;
	private float PIXEL_WIDTH, PIXEL_HEIGHT;
	private Path sPath = new Path();
	private Position endPosition;

	//Default constructor, pass in screen width and height
	public MapPath(int w, int h)
	{
		//Dependant on constants outlined above, divide screen up
		PIXEL_WIDTH = w/LAND_X_COUNT;
		PIXEL_HEIGHT = h/LAND_Y_COUNT;
		pathCo = new int[LAND_X_COUNT][LAND_Y_COUNT];
		
		for (int x = 0; x < LAND_X_COUNT; x++) { //initialise screen to 0 (free to put towers anywhere at this point)
			for (int y = 0; y < LAND_Y_COUNT; y++) {
				pathCo[x][y] = 0;
			}
		}
	}

	//Given a start and end position, set everything in between as 1 (no towers droppable
	private void setPathLine(Position prevPos, Position newPos) {
		//Grid coordinates
		int xStart = prevPos.getX();
		int yStart = prevPos.getY();
		int xEnd = newPos.getX();
		int yEnd = newPos.getY();
		
		//Set width/height in pixels of screen dimension
		float xF = (xEnd*PIXEL_WIDTH);
		float yF = (yEnd*PIXEL_HEIGHT);
		
		//Check that this grid IS free (ie 0) to draw a path
		if (checkPathCo(newPos) == 0) {
			sPath.lineTo(xF, yF);
			setPathCo(newPos,1);
			while (xStart != xEnd)
			{ //set everything in between as 1 ie undroppable
				Position tmpPos = new Position(xStart, yEnd);
				setPathCo(tmpPos,1);
				if (xStart < xEnd)
					xStart++;
				else
					xStart--;
			}
			
			while(yStart < yEnd)
			{ //same as x above
				Position tmpPos = new Position(xEnd,yStart);
				setPathCo(tmpPos,1);
				yStart++;
			}
		}
		
	}
	
	//Easily set 2d array to either 0 (free), 1 (path) or 2 (tower) to indicate its status
	public void setPathCo(Position pos, int val) {
		int x = pos.getX();
		int y = pos.getY();
		pathCo[x][y] = val;
	}
	
	//Make sure we do not go beyond the screen
	public int checkPathCo(View v, Position pos) {
		int check = 1;
		int x = pos.getX();
		int y = pos.getY();
		
		try {
			if (y == 0 || x == 0)
				check = 1;
			else
				check = pathCo[x][y];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			Toast.makeText(v.getContext(),"Out of bounds. Tower cannot be placed here",Toast.LENGTH_SHORT).show();
		}
		
		return check;
	}
	
	//Same functionality as above
	public int checkPathCo(Position pos) {
		int check = 0;
		int x = pos.getX();
		int y = pos.getY();
		
		try {
			if (y == 0 || x == 0)
				check = 1;
			else
				check = pathCo[x][y];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			Log.e("checking coordinates", "Out of bounds!");
		}
		
		return check;
	}	
	
	//Set the path for level one
	public Path setLevel1() {
		int xVal = (LAND_X_COUNT/2);//5
		int yVal = 0;
		
		//pixel width/height for grid coordinate
		float xPx = (float) (PIXEL_WIDTH*(xVal));
		float yPx = (float) (PIXEL_HEIGHT*(yVal));
		
		sPath.moveTo(xPx, yPx); //LANDWIDTH MIDDLE X
		Position prevPos = new Position(xVal,yVal);
		Position newPos;// = new Position(5,2);

		LevelOne one = new LevelOne(); //level one contains more information, just path details
		
		setPathLine(prevPos,prevPos); //first point

		for (Position pos : one.getLevelPositions())
		{
			newPos = pos;
			setPathLine(prevPos,newPos);
			prevPos = newPos;
		}
	
		newPos = new Position(LAND_X_COUNT/2,LAND_Y_COUNT-1); //middle of the bottom of the screen
		setPathLine(prevPos,newPos);
		endPosition = newPos; //store end position
		newPos = new Position(LAND_X_COUNT/2,LAND_Y_COUNT);
		sPath.lineTo((float) (PIXEL_WIDTH*newPos.getX()), (float) (PIXEL_HEIGHT*newPos.getY()));//END POINT
		return sPath;	
	}
	
	
	//Return center values in pixels for random x and y 
	public float[] getCenterCo(float x, float y)
	{
		float[] co = new float[2];
		int xco = getXVal(x);
		co[0] = (xco*PIXEL_WIDTH) - (PIXEL_WIDTH/2);
		
		int yco = getYVal(y);
		co[1] = (yco*PIXEL_HEIGHT) - (PIXEL_HEIGHT/2);
		
		return co;
	}
	
	// get grid coordinate
	public int getXVal(float x) {
		float xf = (x/PIXEL_WIDTH);
		xf = (float) Math.round(xf);
		return (int) xf;
	}
	
	//get grid coordinate
	public int getYVal(float y) {
		float yf = (y/PIXEL_HEIGHT);
		yf = (float) Math.round(yf);
		
		
		return (int) yf;
	}
	
	public float getPixelHeight() {
		return PIXEL_HEIGHT;
	}
	
	public float getPixelWidth() {
		return PIXEL_WIDTH;
	}
	
	public Position getEndPosition() {
		return this.endPosition;
	}
	
	
	
	public static int getLandWidth() {
		return LAND_X_COUNT;
	}
	
	public static int getLandHeight() {
		return LAND_Y_COUNT;
	}
	
	//Check whether a bullet has overlapped an enemy ie a hit
	public static boolean checkOverlap(Bullet aBullet, Enemy e) {
		Rect enemyRect = e.getDstRect();
		
		int bulletX = (int) aBullet.getBulletX();
		int bulletY = (int) aBullet.getBulletY();
		
		return enemyRect.contains(bulletX, bulletY);
	}
	
	//Mainly used to see whether an enemy has hit the main tower 
	public static boolean checkOverlap(Character d, Character e) {
		Rect dRec = d.getDstRect();
		Rect eRec = e.getDstRect();
		
		int intX = (int) eRec.exactCenterX();
		int intY = (int) eRec.exactCenterY();
		
		return dRec.contains(intX,intY);
	}
	
	//Check whether an enemy is in range for shooting
	public static boolean inRange (Position defPos, Position atkPos, int range) {
		int defX = defPos.getX();
		int defY = defPos.getY();
		
		int atkX = atkPos.getX();
		int atkY = atkPos.getY();
		
		if (defX -range <= atkX &&
				defX + range >= atkX)
		{
			if (defY -range <= atkY &&
					defY+range >= atkY)
				return true;
		}
		return false;
	}
}
