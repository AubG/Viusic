package viusic.UI;

import processing.core.PApplet;
import processing.core.PConstants;
import viusic.sound.SoundManager;
import controlP5.ControlP5;

public class ScreenManager {
	
	//Screen Size Variables, passed from main
	int screenWidth, screenHeight;
	
	//One instance of PApplet, passed from main
	PApplet parent;
	
	//One instance of controlP5 passed from main
	ControlP5 cp5;
	
	//One Instance of SoundManager, passed from main
	SoundManager sndM;
	
	//Dem Bools
	Boolean drawingCollectionMenu = false;
	
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
	    .setPosition(screenWidth-201,screenHeight-40)
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
	
	public void giveSoundManager(SoundManager s){
		sndM = s;
	}
	
	public void drawHomeBar(){
		//Setting up HomeBar
		parent.noStroke();
		parent.fill(100,100,100);
		parent.rect(0,screenHeight-40,screenWidth,40);
	}
	
	public void setIsDrawingCollectionMenu(boolean input){
		drawingCollectionMenu = input;
	}
	
	public boolean getIsDrawingCollectionMenu(){
		return drawingCollectionMenu;
	}
	
	public void drawCollectionMenu(){
		
		// Temporary storage
		int initialY = screenHeight - 80;
		int tempX = 0, tempY = 0;
		
		//Add code for collection menu
		for(int i = 0; i < sndM.getCollectionLength(); i++){
			
			//Temp storage for position, passed to collection class for mouseOver detection
			tempX = screenWidth - 201;
			tempY = (initialY - i*(40));
			
			//Drawing button
			parent.stroke(255);
			parent.fill(0);
			parent.smooth();
			parent.rect(tempX, tempY, 200, 40);
			
			//Setting position of button in Collection Class
			sndM.getCollection(i).setMenuPosition(tempX, tempY);
			
			//Drawing text in center
			parent.fill(255);
			parent.textAlign(PConstants.CENTER);
			parent.textFont(parent.createFont("arial", 12));
			parent.text(sndM.getCollection(i).getCollectionName(), tempX+101, ((initialY+25) - i*(40)));
		}
	}
	
	public void drawCurrentCollectionTab(){
		//Draw tab for currentCollection
		if(!drawingCollectionMenu){
			parent.stroke(0);
			parent.fill(200);
			parent.smooth();
			parent.ellipse(screenWidth - 101, screenHeight - 28, 210, 75);
			
			parent.fill(0);
			parent.textAlign(PConstants.CENTER);
			parent.textFont(parent.createFont("arial", 12));
			parent.text(sndM.getCurrentCollection().getCollectionName(), screenWidth - 101, screenHeight - 47);
		}
	}
}
