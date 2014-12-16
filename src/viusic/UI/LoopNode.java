package viusic.UI;

import java.awt.Rectangle;

import processing.core.PConstants;
import viusic.main.ViusicMain;

public class LoopNode {

	private int distance;
	private int startX;
	private float time;
	private ViusicMain main;
	private float endTime;
	private Rectangle rect;
	private int index;
	public LoopNode(ViusicMain main, int index, float time, float endTime, int startX, int y, int distance) {
		this.setTime(time);
		this.main = main;
		this.endTime = endTime;
		this.index = index;
		
		this.startX = startX;
		rect = new Rectangle((int) (startX + distance * time / endTime), y, 5, 15);
		this.distance = distance;
	}
	
	public void draw(){
		
		main.stroke(0);
		main.fill(0,0,100);
		main.rect(rect.x, rect.y, rect.width, rect.height);
		
		float temp = time / 1000f;
		String time = "" + temp;
		
		main.fill(0);
		main.textAlign(PConstants.CENTER);
		main.text(time, rect.x, rect.y + 27);
	}
	
	public void setPosition(int x){
		System.out.println(time);
		if(x < startX){
			x = startX;
		}else if(x > startX + distance){
			x = startX + distance;
		}
		rect.x = x;
		
		this.time = (x - startX) * endTime / distance;
		System.out.println(time);
	}
	
	public boolean mouseClicked(int x, int y){
		System.out.println("Works");

		if(rect.contains(x, y)){
			return true;
		}
		return false;
	}
	
	public boolean mouseRelease(int x, int y){
		return false;
	}

	public float getTime() {
		return time;
	}


	public void setTime(float time) {
		this.time = time;
	}

	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}

}
