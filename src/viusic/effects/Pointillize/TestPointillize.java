package viusic.effects.Pointillize;

import processing.core.PApplet;


public class TestPointillize extends PApplet
{	
	// data members
	String filepath = System.getProperty("user.dir") + "/viusic/data/images/map.png";
	int backgroundColor = 0;
	int pSize = 16;
	
	
	// create Pointillize effect
	Pointillize Pointillize = new Pointillize(this, filepath, backgroundColor, pSize);
	
	
	public void setup ()
	{
		// configure Pointillize
		Pointillize.setup();
	}
	
	
	public void draw () 
	{	
		// draw Pointillize
		Pointillize.draw();
	}
}
