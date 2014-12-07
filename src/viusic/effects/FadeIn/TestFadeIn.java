package viusic.effects.FadeIn;

import processing.core.*;


public class TestFadeIn extends PApplet
{
	String startImage, endImage;
	FadeIn fadeIn;
	double v;


	public void setup ()
	{
		size(600, 400);

		startImage = "/Users/jhalpert/Dropbox/photos/life/earth.jpg";
		endImage ="/Users/jhalpert/Dropbox/photos/life/apollo-landing.jpg";

		v = 0.01;

		fadeIn = new FadeIn(this, startImage, endImage, v);
		fadeIn.setup();
	}


	public void draw ()
	{
		fadeIn.draw();
	}
}
