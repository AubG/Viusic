package viusic.effects.FadeIn;

import processing.core.*;


public class FadeIn
{
	PImage startImage, targetImage;
	PApplet master;
	double velocity, x;


  /**
   * Constructor
   *
   * @param  {PApplet}  master       references the parent processing applet
   * @param  {String}   startImage   the image visible at the beginning of the transition
   * @param  {String}   targetImage  the image visible at the end of the transition
   * @param  {double}   v            the velocity (rate) in which the transition occurs, ranging from 0.0 - 1.0
   */
	public FadeIn (PApplet master, String startImage, String targetImage, double v)
	{
		this.master = master;
		this.startImage = master.loadImage(startImage);
		this.targetImage = master.loadImage(targetImage);
		this.velocity = v;
	}


  /**
   * Initialize the first image's visibility
   */
	public void setup ()
	{
		master.image(startImage, 0, 0);
		x = 0;
	}


  /**
   * Transform the opacities of the starting and ending images over a function of time
   */
	public void draw ()
	{
		// tint from 255 to 0 for the top image
		master.tint(255, PApplet.map((float) x, 0, master.width, 255, 0));

		master.image(startImage, 0, 0, master.width, master.height);

		// tint from 0 to 255 for the bottom image - 'cross fade'
		master.tint(255, PApplet.map((float) x, 0, master.width, 0, 255));

		master.image(targetImage, 0, 0, master.width, master.height);

		x += master.width * velocity;
	}
}
