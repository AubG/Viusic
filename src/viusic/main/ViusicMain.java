package viusic.main;

import processing.core.PApplet;
import viusic.UI.ScreenManager;
import viusic.sound.SoundManager;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import ddf.minim.Minim;

public class ViusicMain extends PApplet {
	 
	final int screenWidth = 1280;
	final int screenHeight = 720;
	
	//Manager Objects
	ScreenManager sm;
	SoundManager sndM;
	
	//ControlP5
	ControlP5 cp5;
	
	//Minim
	Minim min;
	
	public void setup(){
		size(screenWidth,screenHeight);
		frameRate(40);
		
		//Initializing screenManager object, passing in PApplet and cp5
		cp5 = new ControlP5(this);
		min = new Minim(this);
		sm = new ScreenManager(this, cp5, screenWidth, screenHeight);
		sndM = new SoundManager(this, min, sm);
	}
	
	public void draw(){
		
		if(keyPressed)
			fill(key);
		sm.drawHomeBar();
		
	}
	
	public void keyPressed(){
		sndM.playSound(key);
	}
	
	public void controlEvent(ControlEvent event){
		switch(event.getName()){
		case "Collections":
			sm.drawCollectionMenu();
			break;
		case "record":
			//Start recording
			break;
		}
	}
}
