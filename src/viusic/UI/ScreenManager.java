package viusic.UI;

import java.util.ArrayList;

import controlP5.ControlP5;

import processing.core.PConstants;
import processing.core.PImage;
import viusic.main.ViusicMain;
import viusic.sound.SoundManager;

public class ScreenManager {
	
	//Screen Size Variables, passed from main
	int screenWidth, screenHeight;
	
	//One instance of PApplet, passed from main
	ViusicMain parent;
	
	//One instance of controlP5 passed from main
	ControlP5 cp5;
	
	//One Instance of SoundManager, passed from main
	SoundManager sndM;
	
	//Arraylist of Screen Objects
	private ArrayList<ImageButton> screenObjects;
	
	
	//Dem Bools
	boolean drawingCollectionMenu = false;
	boolean drawingSettingsMenu = false;
	
	//SettingsMenu Movement Variables
	int s_posX = 20, s_posY;
	
	public ScreenManager(ViusicMain p, ControlP5 c, int s_W, int s_H){
		parent = p;
		cp5 = c;
		screenWidth = s_W;
		screenHeight = s_H;
		s_posY = screenHeight;
		setup();
	}
	
	void setup(){
		//Font
		//PFont font = parent.createFont("arial", 20);
		screenObjects = new ArrayList<ImageButton>();
		//Getting Images
		PImage [] images = { parent.loadImage("\\Viusic\\data\\images\\settings_gear.png"), //Image 1
				parent.loadImage("\\Viusic\\data\\images\\settings_gear.png"),//Image 2
				parent.loadImage("\\Viusic\\data\\images\\settings_gear.png")};//Image 3
		
		
		screenObjects.add(new ImageButton(parent, images, "gear", 5, screenHeight - 35, 1){
			@Override
			public void onMousePress(int x, int y) {
				if(!getIsDrawingSettingsMenu())
					setIsDrawingSettingsMenu(true);
				
			}
		});
		
		
		// :: SETTING UP BUTTONS :: 
		// Collection button
		cp5.addButton("Collections")
		.setLabelVisible(false)
		.setColorBackground(50)
		.setColorActive(parent.color(150,150,150))
	    .setPosition(screenWidth-201,screenHeight-39)
	    .setSize(200,39)
	    .setVisible(true)
	    ;
		//Record button
		cp5.addButton("record")
		.setLabelVisible(false)
		.setColorBackground(50)
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
		parent.stroke(0);
		parent.fill(100);
		parent.rect(0,screenHeight-40,screenWidth,40);
	}
	
	public void drawSettingsButton(int deltaTime){
		//Load Image of settings button
		//gear.update(deltaTime);
	}
	
	/*public boolean isMouseClickedSettings(int x, int y){
		if(x > 5 && x < 35){
			if(y > screenHeight - 35 && y < screenHeight - 5){
				return true;
			}
		}
		
		return false;
	}*/
	
	public void drawSettingsMenu(){
		parent.stroke(0);
		parent.fill(100);
		parent.rect(s_posX, s_posY, 400, screenHeight/2+100);
		
		if(s_posY >= screenHeight/2) s_posY -= 20;
	}
	
	public void resetSettingsMenu(){
		s_posX = 20;
		s_posY = screenHeight;
	}
	
	public void setIsDrawingSettingsMenu(boolean input){
		drawingSettingsMenu = input;
	}
	
	public boolean getIsDrawingSettingsMenu(){
		return drawingSettingsMenu;
	}
	
	public void drawButtonIndicators(int key){
		
		int count = 0;
		int movement = screenHeight - key*3;
		
		for(int i = 0; i < 11; i++){
			parent.stroke(0);
			parent.strokeWeight(2);
			parent.fill(255);
			parent.ellipse(i*(80) + 50, screenHeight-40, 50, 50);
		}	
		
		parent.fill(0);
		parent.textAlign(PConstants.CENTER);
		parent.text("A", count*(80) + 50, screenHeight - 43);
		count++;
		parent.text("S", count*(80) + 50, screenHeight - 43);
		count++;
		parent.text("D", count*(80) + 50, screenHeight - 43);
		count++;
		parent.text("F", count*(80) + 50, screenHeight - 43);
		count++;
		parent.text("G", count*(80) + 50, screenHeight - 43);
		count++;
		parent.text("H", count*(80) + 50, screenHeight - 43);
		count++;
		parent.text("J", count*(80) + 50, screenHeight - 43);
		count++;
		parent.text("K", count*(80) + 50, screenHeight - 43);
		count++;
		parent.text("L", count*(80) + 50, screenHeight - 43);
		count++;
		parent.text(";", count*(80) + 50, screenHeight - 43);
		count++;
		parent.text("\'", count*(80) + 50,screenHeight - 43);
		
		switch(key){
		case 'a':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(0*(80) + 50, movement, 50, 50);
			parent.fill(255);
			parent.text("A", 0*(80) + 50, screenHeight - 43);
			break;
		case 's':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(1*(80) + 50, movement, 50, 50);
			parent.fill(255);
			parent.text("S", 1*(80) + 50, screenHeight - 43);
			break;
		case 'd':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(2*(80) + 50, movement, 50, 50);
			parent.fill(255);
			parent.text("D", 2*(80) + 50, screenHeight - 43);
			break;
		case 'f':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(3*(80) + 50, movement, 50, 50);
			
			parent.fill(255);
			parent.text("F", 3*(80) + 50, screenHeight - 43);
			break;
		case 'g':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(4*(80) + 50, movement, 50, 50);
			
			parent.fill(255);
			parent.text("G", 4*(80) + 50, screenHeight - 43);
			break;
		case 'h':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(5*(80) + 50, movement, 50, 50);
			
			parent.fill(255);
			parent.text("H", 5*(80) + 50, screenHeight - 43);
			break;
		case 'j':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(6*(80) + 50, movement, 50, 50);
			
			parent.fill(255);
			parent.text("J", 6*(80) + 50, screenHeight - 43);
			break;
		case 'k':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(7*(80) + 50, movement, 50, 50);
			parent.fill(255);
			parent.text("K", 7*(80) + 50, screenHeight - 43);
			break;
		case 'l':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(8*(80) + 50, movement, 50, 50);
			parent.fill(255);
			parent.text("L", 8*(80) + 50, screenHeight - 43);
			break;
		case ';':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(9*(80) + 50, movement, 50, 50);
			
			parent.fill(255);
			parent.text(";", 9*(80) + 50, screenHeight - 43);
			break;
		case '\'':
			parent.stroke(255);
			parent.fill(0);
			parent.ellipse(10*(80) + 50, movement, 50, 50);
			
			parent.fill(255);
			parent.text("\'", 10*(80) + 50, screenHeight - 43);
			break;
			
		}
		
		// Reseting the strokeWeight back to default
		parent.strokeWeight(1);
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
			// Draws current Collection tab
			parent.stroke(0);
			parent.fill(255);
			parent.smooth();
			parent.strokeWeight(2);
			parent.ellipse(screenWidth - 101, screenHeight - 28, 210, 75);
			
			// Draws Current Collection name inside tab
			parent.fill(0);
			parent.textAlign(PConstants.CENTER);
			parent.textFont(parent.createFont("arial", 12));
			parent.text(sndM.getCurrentCollection().getCollectionName(), screenWidth - 101, screenHeight - 47);
		}
		
		parent.strokeWeight(1);
	}

	//Mouse Events
	public void mouseClicked(int mouseX, int mouseY) {
		for(ImageButton obj : screenObjects){
			obj.mousePressed(mouseX, mouseY);
		}
	}
	public void mouseReleased(int mouseX, int mouseY) {
		for(ImageButton obj : screenObjects){
			obj.mouseReleased(mouseX, mouseY);
		}
	}
	public void mousePosition(int mouseX, int mouseY){
		for(ImageButton obj : screenObjects){
			obj.mouseOver(mouseX, mouseY);
		}
	}

	public void updateObjects(int deltaTime) {
		for(ImageButton obj : screenObjects){
			obj.update(deltaTime);
		}
	}
}
