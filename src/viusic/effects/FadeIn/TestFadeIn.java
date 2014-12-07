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
		
		System.out.println(System.getProperty("user.dir"));
		
		startImage = System.getProperty("user.dir") + "/viusic/data/images/earth.jpg";
		endImage = System.getProperty("user.dir") + "/viusic/data/images/apollo-landing.jpg";

		v = 0.01;

		fadeIn = new FadeIn(this, startImage, endImage, v);
		fadeIn.setup();
	}


	public void draw ()
	{
		fadeIn.draw();
	}
}
