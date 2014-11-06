package viusic.UI;

import processing.core.PApplet;
import processing.core.PFont;
import controlP5.ControlP5;

public class ScreenManager {
	
	//Screen Size Variables, passed from main
	int screenWidth, screenHeight;
	
	//One instance of PApplet, passed from main
	PApplet parent;
	
	//One instance of controlP5 passed from main
	ControlP5 cp5;
	
	public ScreenManager(PApplet p, ControlP5 c, int s_W, int s_H){
		parent = p;
		cp5 = c;
		screenWidth = s_W;
		screenHeight = s_H;
		setup();
	}
	
	void setup(){
		//Font
		//PFont font = parent.createFont("arial", 20);
		
		// :: SETTING UP BUTTONS :: 
		// Collection button
		cp5.addButton("Collections")
		.setColorForeground(0)
		.setColorActive(parent.color(150,150,150))
	    .setPosition(screenWidth-200,screenHeight-40)
	    .setSize(200,40)
	    .setVisible(true)
	    ;
		//Record button
		cp5.addButton("record")
		.setColorForeground(0)
		.setColorActive(parent.color(255,0,0))
		.setPosition((screenWidth/2)-25, screenHeight - 35)
		.setSize(50,30)
		.setVisible(true)
		;
	}
	
	public void drawHomeBar(){
		//Setting up HomeBar
		parent.noStroke();
		parent.fill(100,100,100);
		parent.rect(0,screenHeight-40,screenWidth,40);
	}
	
	public void drawCollectionMenu(){
		//Add code for collection menu
	}
}
