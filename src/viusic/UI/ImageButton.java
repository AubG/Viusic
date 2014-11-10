package viusic.UI;

import java.awt.Rectangle;
import java.util.ArrayList;

import processing.core.PImage;
import viusic.main.ViusicMain;

public class ImageButton {

	private int timeBtwnImages;
	private ArrayList<PImage> animation;
	private int stateTime, currentIndex;
	private int x, y, width, height;
	private boolean beingClicked;
	private Rectangle bounds;
	private float scale;
	private PImage[] images;
	private boolean animating;
	private ViusicMain main;

	// For Tweening
	private float targetScale;
	private int targetX, targetY;
	private boolean looping;

	public ImageButton() {

	}

	// For traditional button functionality
	public ImageButton(ViusicMain main, PImage[] buttonImages,
			String buttonName, int x, int y, float scale) {

		// This constructor requires a 3 sized PImage array for button
		// functionality
		if (buttonImages.length != 3) {
			System.out.println("YOU'RE A FUCKING MORON YOU HAVE"
					+ " TO PASS IN 3 IMAGES FOR MOUSE OVER, MOUSE CLICKED"
					+ "AND MOUSE NOT CLICKED FOR IMAGEBUTTONS");
		}

		init(main, buttonName, buttonImages, x, y, scale);

	}

	//
	private void init(ViusicMain main, String buttonName,
			PImage[] buttonImages, int x, int y, float scale) {
		// Variables for controlling size
		this.main = main;
		this.scale = Math.abs(scale);
		images = buttonImages;

		// Set position
		this.x = x;
		this.y = y;
		// Determining size of image
		width = (int) (buttonImages[0].width);
		height = (int) (buttonImages[0].height);
		bounds = new Rectangle(x, y, (int)(width * this.scale), (int)(height * this.scale));
	}

	// timeBtwnImages in milliseconds = 1 second / 1000
	public ImageButton(ViusicMain main, String buttonName,
			ArrayList<PImage> animation, int timeBtwnImages, int x, int y,
			float scale) {
		// Send in image to be evaluated
		PImage[] image = { animation.get(0), animation.get(0), animation.get(0) };
		init(main, buttonName, image, x, y, scale);

		animating = true; // For update() won't animate unless animating = true
		this.timeBtwnImages = timeBtwnImages;
	}
	
	//mouse event handlers
	public boolean mousePressed(int x, int y){
		if(bounds.contains(x, y)){
			beingClicked = true;
			if(!animating){
				currentIndex = 2;
			}
			onMousePress(x, y);
			return true;
		}
		
		return false;
	}
	public boolean mouseOver(int x, int y){
		if(bounds.contains(x, y)){
			if(!animating && !beingClicked){
				currentIndex = 1;
			}
		}
		return false;
	}
	public boolean mouseReleased(int x, int y){
		beingClicked = false;
		if(bounds.contains(x, y)){
			onMouseRelease(x, y);
			return true;
		}
		
		return false;
	}

	//Override for each individual object so they can do different
	//things when they are clicked
	public void onMouseRelease(int x, int y) {
		
	}

	//Override for each individual object so they can do different
	//things when they are clicked
	public void onMousePress(int x, int y) {

	}

	// Updates every frame
	public void update(int deltaTime) {

		stateTime += deltaTime;
		// Animate animation
		if (animating) {
			//Cycles through each image in animation
			while (stateTime > timeBtwnImages) {
				stateTime -= timeBtwnImages;
				currentIndex++;

				// Calculate width of image
				width = (int) (animation.get(currentIndex).width);
				height = (int) (animation.get(currentIndex).height);
			}
			
			main.image(animation.get(currentIndex), x, y, width * scale, height * scale);
		//Traditional Button Rendering
		} else {
			main.image(images[currentIndex], x, y, width * scale, height * scale);
		}

	}
	//When animation completes, either restart or just go back to first image of animation
	private void onAnimationComplete(){
		currentIndex = 0;

		//Set animating to false
		if(!looping){
			animating = false;
		}
		animationCompletion();
	}
	
	//Override on object level for individualized 
	//behavior after animation completes
	private void animationCompletion() {
		
	}

	public void setAnimating(boolean animating) {
		this.animating = animating;
	}

	public boolean getAnimating() {
		return animating;
	}

	// Target setter and getters
	public void setTarget(int x, int y) {
		targetX = x;
		targetY = y;
		bounds.x = x;
		bounds.y = y;
	}

	public int getTargetX() {
		return targetX;
	}

	public int getTargetY() {
		return targetY;
	}

	public void setScale(float scale) {
		this.scale = scale;
		
	}
	public Rectangle getBounds(){
		return bounds;
	}
	public float getScale() {
		return scale;
	}
}
