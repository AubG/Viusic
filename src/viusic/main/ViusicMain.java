package viusic.main;

import processing.core.PApplet;
import viusic.UI.ScreenManager;
import controlP5.ControlP5;

public class ViusicMain extends PApplet {
	 
	final int screenWidth = 1280;
	final int screenHeight = 720;
	
	ScreenManager sm;
	
	//ControlP5
	ControlP5 cp5;
	
	public void setup(){
		size(screenWidth,screenHeight);
		frameRate(40);
		
		//Initializing screenManager object, passing in PApplet and cp5
		sm = new ScreenManager(this, cp5, screenWidth, screenHeight);
	}
	
	public void draw(){
		
	}
}
