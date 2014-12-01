package viusic.main;

import java.util.Random;

import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.video.Movie;
import viusic.UI.ScreenManager;
import viusic.media.SoundManager;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import ddf.minim.Minim;

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
	public ControlP5 cp5;
	
	//Minim
	Minim min;
	
	//Time variables
	private int timePassed, lastTime;
	private boolean recording;
	
	//Movie Testing
	Movie theMov;
	boolean playing;
	
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
	}
	
	public void draw(){
		
		//Calculate time for this frame
		timePassed = millis() - lastTime;
		lastTime += timePassed;
		sndM.update(timePassed);
		
		
		drawUI();
		sm.updateObjects(timePassed);

		//Send sm mouse Input
		sm.mousePosition(mouseX, mouseY);
		if(sm.getIsDrawingSettingsMenu()){
			sm.drawSettingsMenu();
		} 
		
		// User cannot change screen dimensions
		size(screenWidth,screenHeight);
	}
	
	public void keyPressed(){
		
		/*
		 * Plays sound on keyPressed
		 * Wont play sound if in CollectionMenu
		 */
		if(key == 32){
			sndM.toggleRecord();
			return;
		}
		
		//Movie testing
		if( keyCode == UP){
			System.out.println("Video On");
			theMov = new Movie(this, System.getProperty("user.dir") + "/viusic/data/videos/skullShuffle.mp4");
			theMov.play();
			playing = true;
			
		} else if (key != CODED && !sm.getIsDrawingCollectionMenu() && !sm.getIsDrawingSettingsMenu()){
			sndM.playSound(key, true);
			drawUI(key);
		} else if (sm.getIsSettingKey()){
			sm.passKey(key);
		}
	}
	
	public void keyReleased(){
		
		/*
		 * Sets new random Background Color on keyReleased
		 * Wont draw anything if in collectionMenu
		 */
		if(!sm.getIsDrawingCollectionMenu()){
//			rand1 = rnd.nextInt(256);
//			rand2 = rnd.nextInt(256);
//			rand3 = rnd.nextInt(256);
			System.out.println("R :: " + rand1 + "\tG :: " + rand2 + "\tB :: " + rand3);
		}
	}
	
	public void mouseClicked(){
		
		/*
		 * Checks for mouse clicks in CollectionMenu
		 * Sets Current Collection to Selected Collection
		 */
		sm.mouseClicked(mouseX, mouseY);
		if(sm.getIsDrawingCollectionMenu()){
			for(int i = 0; i < sndM.getCollectionLength(); i++){
				if(sndM.getCollection(i).isMouseOver(mouseX, mouseY)){
					sm.setIsDrawingCollectionMenu(false);
				}
			}
		} 
			
		 /*else {
			sm.resetSettingsMenu();
			sm.setIsDrawingCollectionMenu(false);
			sm.setIsDrawingSettingsMenu(false);
		}*/
	}
	
	@Override
	public void mouseReleased(MouseEvent event) {
		sm.mouseReleased(event.getX(), event.getY());
	}
	
	public void drawUI(){
		background(rand1,rand2,rand3);
		
		//Movie testing
		if(playing)
		image(theMov, 0,0);
		
		sm.drawButtonIndicators(0);
		
		if(sm.getIsDrawingCollectionMenu())
			sm.drawCollectionMenu();
		else
			sm.drawCurrentCollectionTab();
		
		sm.drawHomeBar();
		sm.updateObjects(timePassed);
	}
	
	public void drawUI(int key){
		background(rand1,rand2,rand3);
		sm.drawButtonIndicators(key);
		
		if(sm.getIsDrawingCollectionMenu())
			sm.drawCollectionMenu();
		else
			sm.drawCurrentCollectionTab();
		
		sm.drawHomeBar();
		sm.updateObjects(timePassed);
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
			break;
		
		// Record Button
		case "record":
			// Start recording
			sndM.toggleRecord();
			break;
		
		//Settings Menu Button
		case "gear":
			if(!sm.getIsDrawingSettingsMenu()){
				sm.setIsDrawingSettingsMenu(true);
				sm.drawSettingsMenu();
			}else{
				// Fix the settings menu
				//sm.resetSettingsMenu();
				sm.setIsDrawingSettingsMenu(false);
			}
			break;
		case "collectionName":
			sm.passName(event.getStringValue());
		}
	}
	
	public int getDeltaTime(){
		return timePassed;
	}
	
	public void movieEvent(Movie m){
		m.read();
	}
}
