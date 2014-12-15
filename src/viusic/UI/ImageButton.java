package viusic.UI;

import java.awt.Rectangle;
import java.util.ArrayList;

import processing.core.PConstants;
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
	protected String text;

	// For Tweening
	private float targetScale;
	private int targetX, targetY;
	private boolean looping;
	private boolean buttonMode;
	private int number;
	private int r, g, b;
	private int index;
	public ImageButton(ViusicMain main, String text, int x, int y, int width, int height,
			float scale) {
		init(main, text, null, x, y, width, height, scale);
		buttonMode = true;
	}

	// timeBtwnImages in milliseconds = 1 second / 1000
	public ImageButton(ViusicMain main, String buttonName,
			ArrayList<PImage> animation, int timeBtwnImages, int x, int y,
			int width, int height, float scale) {
		// Send in image to be evaluated
		PImage[] image = { animation.get(0), animation.get(0), animation.get(0) };
		init(main, buttonName, image, x, y, width, height, scale);

		animating = true; // For update() won't animate unless animating = true
		this.timeBtwnImages = timeBtwnImages;
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

		width = (int) (buttonImages[0].width);
		height = (int) (buttonImages[0].height);
		init(main, buttonName, buttonImages, x, y, width, height, scale);

	}

	//
	private void init(ViusicMain main, String buttonName,
			PImage[] buttonImages, int x, int y, int width, int height,
			float scale) {
		// Variables for controlling size
		this.main = main;
		r = 175; g = 175; b = 175;
		
		// Determining size of image
		this.width = width;
		this.height = height;
		this.scale = Math.abs(scale);
		images = buttonImages;
		this.text = buttonName;
		buttonMode = true;

		// Set position
		this.x = x;
		this.y = y;

		bounds = new Rectangle(x, y, (int) (width * this.scale),
				(int) (height * this.scale));
	}

	

	// mouse event handlers
	public boolean mousePressed(int x, int y) {
		if (bounds.contains(x, y)) {
			beingClicked = true;
			if (!animating && images != null) {
				currentIndex = 2;
			}
			onMousePress(x, y);
			return true;
		}

		return false;
	}

	// Called every frame
	public boolean mouseOver(int x, int y) {
		if (bounds.contains(x, y)) {
			if (!animating) {
				if (!beingClicked)
					currentIndex = 1;

			}
		} else if (buttonMode)
			currentIndex = 0;

		return false;
	}

	public boolean mouseReleased(int x, int y) {
		beingClicked = false;
		if (bounds.contains(x, y)) {
			onMouseRelease(x, y);
			return true;
		}

		return false;
	}

	// Override for each individual object so they can do different
	// things when they are clicked
	public void onMouseRelease(int x, int y) {

	}

	// Override for each individual object so they can do different
	// things when they are clicked
	public void onMousePress(int x, int y) {

	}

	// Updates every frame
	public void update(int deltaTime) {

		stateTime += deltaTime;
		// Animate animation
		if (animating) {
			// Cycles through each image in animation
			while (stateTime > timeBtwnImages) {
				stateTime -= timeBtwnImages;
				currentIndex++;

				// Calculate width of image
				width = (int) (animation.get(currentIndex).width);
				height = (int) (animation.get(currentIndex).height);
			}
			if (currentIndex >= images.length) {
				onAnimationComplete();
			}
			main.image(animation.get(currentIndex), x, y, width * scale, height
					* scale);
			// Traditional Button Rendering
		} else if (images != null) {
			main.image(images[currentIndex], x, y, width * scale, height
					* scale);
		} else {
			main.fill(r, g, b);
			main.stroke(0);

			main.rect(bounds.x, bounds.y, bounds.width, bounds.height);

			main.fill(0);
			main.textAlign(PConstants.CENTER);
			main.text(text, bounds.x + bounds.width / 2, bounds.y + bounds.height / 2 +  5);
		}

	}

	// When animation completes, either restart or just go back to first image
	// of animation
	private void onAnimationComplete() {

		// Set animating to false
		if (!looping) {
			animating = false;

		}
		animationCompletion();
	}

	// Override on object level for individualized
	// behavior after animation completes
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
	
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

	public int getTargetY() {
		return targetY;
	}

	public void setScale(float scale) {
		this.scale = scale;

	}

	public Rectangle getBounds() {
		return bounds;
	}

	public float getScale() {
		return scale;
	}

	public String getText() {

		return text;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getNumber(){
		return number;
	}

	public void setColor(int r, int g, int b) {
		this.r = r; this.g = g; this.b = b;
	}
}
