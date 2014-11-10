package viusic.main;

import processing.core.PApplet;
import viusic.UI.ScreenManager;
import viusic.sound.SoundManager;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import ddf.minim.Minim;
import java.util.Random;

public class ViusicMain extends PApplet {
	 
	final int screenWidth = 1280;
	final int screenHeight = 720;
	
	//For random numbers
	Random rnd = new Random();
	int rand1 = rnd.nextInt(256), rand2 = rnd.nextInt(256), rand3 = rnd.nextInt(256);
	
	//Manager Objects
	ScreenManager sm;
	SoundManager sndM;
	
	//ControlP5
	ControlP5 cp5;
	
	//Minim
	Minim min;
	
	//Time variables
	private int timePassed, lastTime;
	private boolean recording;
	
	public void setup(){
		size(screenWidth,screenHeight);
		frameRate(40);
		
		//Initializing screenManager object, passing in PApplet and cp5
		cp5 = new ControlP5(this);
		min = new Minim(this);
		sm = new ScreenManager(this, cp5, screenWidth, screenHeight);
		sndM = new SoundManager(this, min, sm);
		sm.giveSoundManager(sndM);
		
		//Temporarily Setting CurrentCollection
		sndM.setCurrentCollection(0);
		
		//Temporary Draw Functions
		background(rand1,rand2,rand3);
		sm.drawButtonIndicators(false);
		//sm.drawCurrentCollectionTab();
		sm.drawHomeBar();
	}
	
	public void draw(){
		
		//Calculate time for this frame
		timePassed = millis() - lastTime;
		lastTime += timePassed;
		sndM.update(timePassed);
		
		sm.drawSettingsButton(rand1, rand2, rand3);
		
		// User cannot change screen dimensions
		size(screenWidth,screenHeight);
	}
	
	public void keyPressed(){
		System.out.println(key + " from user " + millis());
		/*
		 * Plays sound on keyPressed
		 * Wont play sound if in CollectionMenu
		 */
		if(key == 32){
			sndM.toggleRecord();
			return;
		}
		
		
		if(key != CODED && !sm.getIsDrawingCollectionMenu()){
			
			sndM.playSound(key, true);
		}
	}
	
	

	public void keyReleased(){
		
		/*
		 * Draws on keyReleased
		 * Wont draw anything if in collectionMenu
		 */
		if(!sm.getIsDrawingCollectionMenu()){
			rand1 = rnd.nextInt(256);
			rand2 = rnd.nextInt(256);
			rand3 = rnd.nextInt(256);
			//System.out.println("R :: " + rand1 + "\tG :: " + rand2 + "\tB :: " + rand3);
			background(rand1, rand2, rand3);
			sm.drawCurrentCollectionTab();
			sm.drawButtonIndicators(false);
			sm.drawHomeBar();
			sm.drawSettingsButton(rand1, rand2, rand3);
		}
	}
	
	public void mouseClicked(){
		
		/*
		 * Checks for mouse clicks in CollectionMenu
		 * Sets Current Collection to Selected Collection
		 */
		if(sm.getIsDrawingCollectionMenu()){
			for(int i = 0; i < sndM.getCollectionLength(); i++){
				if(sndM.getCollection(i).isMouseOver(mouseX, mouseY)){
					background(rand1,rand2,rand3);
					sm.setIsDrawingCollectionMenu(false);
					sm.drawCurrentCollectionTab();
					sm.drawButtonIndicators(false);
					sm.drawHomeBar();
					sm.drawSettingsButton(rand1, rand2, rand3);
				}
			}
		}
	}
	
	/*
	 * Receives events from all controlP5 controllers
	 * event.getName() returns name of controller which had an event
	 */
	public void controlEvent(ControlEvent event){
		
		/*
		 * switch( controller name )
		 */
		switch(event.getName()){
		
		// Collections Button
		case "Collections":
			// Open menu, set boolean (Collection Selection state)
			sm.setIsDrawingCollectionMenu(true);
			sm.drawCollectionMenu();
			break;
		
		// Record Button
		case "record":
			// Start recording
			break;
		}
	}
	
	public int getDeltaTime(){
		return timePassed;
	}
}
