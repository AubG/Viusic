package viusic.effects.Tint;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;


public class TestTint extends PApplet
{	
	String filepath;
	PImage img;
	Tint tint;
	
	
	public void setup ()
	{	
		filepath = System.getProperty("user.dir") + "/viusic/data/images/data-uri-fail.png";
		
		// load image file
		img = loadImage(filepath);
		
		// create window
		size(img.width, img.height);
		
		// initialize Tint values
		tint = new Tint(this, PConstants.HSB, 100, 203, 104, 200);
	}
	
	
	public void draw () 
	{	
		image(img, 0, 0);
		tint.draw(true);
		
		// transparency offset
		image(img, 15, 0);
	}
}
