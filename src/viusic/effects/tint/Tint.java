package viusic.effects.tint;

import processing.core.PApplet;


public class Tint
{	
	PApplet master;
	
	int colorMode;
	
	float val1, val2, val3, opacity, cmRange; 
	
	/**
	 * Constructor
	 * 
	 * @param  {PApplet}  papp  references the parent processing applet
	 * @param  {Integer}  cm    colorMode: 1 for RGB, 3 for HSB
	 * @param  {Float}    op    image opacity 
	 * @param  {Float}    v1    refers to the RED or HUE value depending on the colorMode
	 * @param  {Float}    v2    refers to the GREEN or BRIGHTNESS value depending on the colorMode
	 * @param  {Float}    v3    refers to the BLUE or VIBRANCY value depending on the colorMode
	 */
	public Tint (PApplet papp, int cm, float op, float v1, float v2, float v3)
	{	
		// parent applet
		master = papp;
		
		// color mode properties
		cmRange = 100;
		master.colorMode(cm, cmRange, cmRange, cmRange);
		
		// tint properties
		opacity = op;
		val1 = v1;
		val2 = v2;
		val3 = v3;
	}
	
	
	public void draw () 
	{
		master.tint(val1, val2, val3, opacity);
	}
	
	
	// data member mutators 
	public void setColorMode (int cm, float cmRange) { master.colorMode(colorMode, cmRange, cmRange, cmRange); }
	public void setOpacity (float op) { opacity = op; } 
	public void setVal1 (float v1) { val1 = v1; }
	public void setVal2 (float v2) { val2 = v2; }
	public void setVal3 (float v3) { val3 = v3; }
	
	
	// data member accessors
	public int getColorMode () { return colorMode; }
	public float getOpacity () { return opacity; }
	public float getVal1 () { return val1; }
	public float getVal2 () { return val2; }
	public float getVal3 () { return val3; }	
}
