package com.example.towerdefence;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GameEngine extends SurfaceView implements Runnable {
	
	
	private Thread t = null; // thread for game logic
	private SurfaceHolder holder; //holder

	private int score = 50; //starting score
	private int lives = 9; //starting lives
	private float x=0,y=0; //x and y co-ordinates
	int iCurStep = 0; //current step
	private int enemyCount = 9, currentEnemy = 0;
	
	private float pxWidth, pxHeight; // individual box pixels

	private boolean ok = false;
	
	//List of characters
	private Map<Defender,Position> defenders = null; 
	private ArrayList<Enemy> enemies = null;
	private Character tardis;
	private Position endPos; //End position on map for tardis
	
	//Path
	private Path sPath = new Path();
	private PathMeasure pm;
	
	Paint pathPaint = new Paint();
	Paint ballPaint = new Paint();
	Paint txtPaint = new Paint(); 
	
	private MapPath mPath; //shared functions class
	
	private String strStatus;
	
	//Duration for bullet updates
	private long lastFrameStart;
	private long lastEnemy;
	private float deltaTime;
	private float deltaEnemy;
	
	public GameEngine(Context context, int w, int h) {
		super(context);
		holder = getHolder();
		score = 50;
		
		//initialise
		strStatus = new String();
		defenders = new HashMap<Defender,Position>();
		enemies = new ArrayList<Enemy>();
		
		mPath = new MapPath(w,h);
		this.sPath = mPath.setLevel1(); // create path for level 1
		this.endPos = mPath.getEndPosition();
		pxWidth = mPath.getPixelWidth();
		pxHeight = mPath.getPixelHeight();
		
		//Set main tower/Tardis
		Bitmap bmptower = BitmapFactory.decodeResource(getResources(),R.drawable.tardis);
		float xpos = (float) ((this.endPos.getX()-0.5)*pxWidth);
		float ypos = (this.endPos.getY()-1)*pxHeight;
		tardis = new Character(bmptower,xpos,h, pxWidth, pxHeight);
		
		//Create enemies
		enemies.add(new EnemyOne(getResources(),0,0,pxWidth,pxHeight));
		lastEnemy = 1;
		//Set paint variables and properties
		txtPaint.setColor(Color.WHITE); 
		txtPaint.setTextSize(48); 
		ballPaint.setColor(Color.rgb(0, 182, 237));
		pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pathPaint.setStyle(Paint.Style.STROKE);
		pathPaint.setStrokeWidth(pxWidth);
		pathPaint.setColor(Color.GRAY);
		pm = new PathMeasure(sPath, false);
	}
	
	private void setStatusText() {
		this.strStatus = "Points: " + this.score + " Lives: " + this.lives;
	}
	
	protected void onDraw(Canvas canvas) {
		//Used for finding distance in path
		float fSegmentLen = pm.getLength()/500;
		float afP[] = {0f, 0f};
		
		try
		{
			//Calculate time elapsed since last canvas drawing
			long currentFrameStart = System.nanoTime(); 
            deltaTime = (currentFrameStart-lastFrameStart) / 1000000000.0f;
            lastFrameStart = currentFrameStart;

			//Draw the path
			canvas.drawColor(Color.DKGRAY);
			canvas.drawPath(sPath, pathPaint);
			
			//Find the last time an enemy was painted
			deltaEnemy = (currentFrameStart-lastEnemy)/ 1000000000.0f;
			Log.d("Delta enemy", "Last enemy:" + deltaEnemy);
			if (deltaEnemy > 2 && currentEnemy < enemyCount) //if more than 2 seconds, add a new enemy
			{
				enemies.add(new EnemyOne(getResources(),0,0,pxWidth,pxHeight));
				currentEnemy++;
				lastEnemy = currentFrameStart;
			}
			
			//ArrayList used to store Enemy classes that have reached the tardis
			ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
			for(Enemy e : enemies)
			{
				float fPos[] = {0f, 0f};
				pm.getPosTan(fSegmentLen*(e.getPathStep()), fPos, null);
				e.updatePosition((fPos[0]-pxWidth),(fPos[1]-pxHeight));
				e.setPathStep(e.getPathStep()+1);
				
				if (!MapPath.checkOverlap(tardis, e))
					canvas.drawBitmap(e.getBMapCharacter(), e.getSrcRect(), e.getDstRect(), null);
				else
				{
					enemyList.add(e);
					lives -= 1;
				}
				
				if (e.getPathStep() > 500)
					e.setPathStep(0);
				
			}
			//Remove enemies
			for (Enemy e : enemyList)
			{
				enemies.remove(e);
			}
			//Set list to null for later use
			enemyList = null;
			
			//For each defender we have on the map..
			for(Defender aDefender : defenders.keySet())
			{
				enemyList = new ArrayList<Enemy>();
				for (Bullet aBullet : aDefender.getBullets())
				{ //Get each defenders bullets
					
					for(Enemy e:enemies) { //if enemy shot enough times, remove from screen
						if (MapPath.checkOverlap(aBullet, e)) //check whether the bullet hit a character
							if(!e.hitEnemy(aBullet.getStrength()))
							{//Check whether enemy has no more health
								enemies.remove(e);//remove enemy
								score += 10; //give user points
							}
					}
					
					for(Enemy e : enemies)
					{//Check whether an enemy is in range of a defender, if so start shooting
						if (MapPath.inRange(aDefender.getMapPos(), e.getMapPos(),aDefender.getRange()))
						{
							aDefender.setEnemy(e);
							canvas.drawCircle(aBullet.getBulletX(), aBullet.getBulletY(), 10,ballPaint); //this line shoots
							break;
						}
					}	
					aDefender.updateBulletPosition(deltaTime); //using delta time calculated above, move the bullet
					
				}

				canvas.drawBitmap(aDefender.getBMapCharacter(),aDefender.getSrcRect(),aDefender.getDstRect(),null);
			}
			
			//update score
			this.setStatusText();
			canvas.drawText(strStatus, (pxWidth/2), (pxHeight/2), txtPaint);
			//Draw main tower
			canvas.drawBitmap(tardis.getBMapCharacter(), tardis.getSrcRect(), tardis.getDstRect(), null);
		}
		catch(ConcurrentModificationException e) 
		{
			Log.e("TowerIterate", "error, retrying");
		}
	}
	
	
	//Getters and setters
	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Set<Defender> getTowers() {
		return defenders.keySet();
	}

	public void removeTower(Character aTower) {
		if (this.defenders != null)
			this.defenders.remove(aTower);
	}
	
	public ArrayList<Enemy> getEnemies() {
		return this.enemies;
	}

	public void setEnemy(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	public void addEnemy(Enemy anEnemy) {
		if (this.enemies == null) 
			this.enemies = new ArrayList<Enemy>();
		this.enemies.add(anEnemy);
	}
	
	public void removeEnemy(Enemy anEnemy) {
		if (this.enemies != null) 
			this.enemies.remove(anEnemy);
	}
	
	public Path getPath() {
		return this.sPath;
	}
	
	public void setPath(Path sPath) {
		this.sPath = sPath;
	}

	@SuppressLint("WrongCall")
	@Override
	public void run() {
		while (ok == true) {
			//perform canvas drawing
			if(!holder.getSurface().isValid()) { //if surface is not valid
				continue; //skip anything below
			}
			Canvas c = holder.lockCanvas(); //lock canvas, paint canvas, unlock canvas
			
			this.onDraw(c);
			holder.unlockCanvasAndPost(c);
		}
		
	}
	
	public void pause() {
		ok = false;
		while(true) {
			try {
				t.join(); //join previous thread here
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			break;
		}
		t = null;
	}
	
	public void resume() {
		ok = true;
		t = new Thread(this);
		t.start();
	}
	
	//an OnTouchEvent
	public void addTower(float xPos, float yPos) {
		
		//touch coordinates
		this.x = xPos;
		this.y = yPos;

		//Get center coordinates (x and y axis)
		float[] f = mPath.getCenterCo(x,y);
		this.x = f[0];
		this.y = f[1];
		
		//Get defending object, also get grid value (Position class)
		Defender twr = new DefenderOne(getResources(),x, y,pxWidth,pxHeight);
		Position towerPos = new Position(mPath.getXVal(x),mPath.getYVal(y));
		
		
		//Check whether a tower or a path exists on this grid
		if (mPath.checkPathCo(this,towerPos) == 0) //Nothing exists in this space, add a tower
		{
			if (score - twr.getCost() >= 0)
			{//Check user has enough points first before adding a tower
				twr.setMapPos(towerPos);
				defenders.put(twr,towerPos);
				mPath.setPathCo(towerPos,2);
				score -= twr.getCost();
			}
			else
				Toast.makeText(getContext(), "Not enough credit", Toast.LENGTH_SHORT).show();
		}
		else if (mPath.checkPathCo(this,towerPos)==2) //Tower already exists, upgrade existing tower
		{
			Defender aDefender = null;
			
			for(Defender aDef : defenders.keySet()) {
				Position aPos = defenders.get(aDef); //Find existing tower object at this point
				if (aPos.equals(towerPos)) {
					aDefender = aDef;
					break;
				}
			}
			Log.d("TowerAdd", "Tower already exists. Shooting instead");
			
			switch (aDefender.getLevel()) //Find what level we're currently on, increase it if possible
			{
				case 1: twr = new DefenderTwo(getResources(),x,y,pxWidth,pxHeight);
					break;
				case 2: twr = new DefenderThree(getResources(),x,y,pxWidth,pxHeight);
					break;
				case 3: twr = new DefenderFour(getResources(),x,y,pxWidth,pxHeight);
					break;
				default: Toast.makeText(getContext(), "Maximum reached",Toast.LENGTH_SHORT).show();
					break;
			}
			if (score - twr.getCost() >= 0) { //if user has enough points, upgrade tower
				defenders.remove(aDefender);
				defenders.put(twr, towerPos);
				score -= twr.getCost();
			}
		}
		else if (mPath.checkPathCo(this,towerPos)==1) // path has been selected, do nothing
		{
			Log.d("TowerAdd", "Path selected. Cannot place tower");
		}
	}
	
}
