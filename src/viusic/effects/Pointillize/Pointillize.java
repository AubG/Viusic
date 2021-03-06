package viusic.effects.Pointillize;

import processing.core.PApplet;
import processing.core.PImage;


public class Pointillize 
{
	// data members
	int backgroundColor, pSize;
	String filepath;
	PApplet master;
	PImage img;
	
	
	/** 
	 * Constructor
	 * 
	 * @param  {PApplet}  papplet  references the parent processing applet
	 * @param  {String}   fpath    source image filepath
	 * @param  {Integer}  bgColor  initial background color
	 * @param  {Integer}  pSz      ellipse width/height (n pixels)
	 */
	public Pointillize (PApplet papplet, String fpath, int bgColor, int pSz) 
	{
		filepath = fpath;
		backgroundColor = bgColor;
		master = papplet;
		pSize = pSz;
	}
	
	
	// Pre-draw configurations
	public void setup ()
	{	
		int windowW, windowH;
		
		// load image 
		img = master.loadImage(filepath);
		 
		// window dimensions
		windowW = img.width;
		windowH = img.height;
		
		// adjust the parent applet's window dimensions
		master.size(windowW, windowH);
		 
		// update the parent applet's background color
		master.background(backgroundColor);
	}
	
	
	// Initiate pointillization
	public void draw () 
	{
		int x, y, point;
		  
		// Pick a random point w/in the image
		x = (int) (master.random(img.width));
		y = (int) (master.random(img.height));
		
		// adjust for single dimension array storage
		point = x + y * img.width;
	  
		float r, g, b;
		
		// Look up an arbitrary point and extract its RGB color w/in the image
		master.loadPixels();
		r = master.red(img.pixels[point]);
		g = master.green(img.pixels[point]);
		b = master.blue(img.pixels[point]);
	
		master.noStroke();
	  
		// Draw an ellipse at that location with that color
		master.fill(r, g, b, 100);
		master.ellipse(x, y, pSize, pSize);
	}
}

