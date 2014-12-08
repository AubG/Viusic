package viusic.UI;

import java.util.ArrayList;

import processing.core.PConstants;
import viusic.main.ViusicMain;
import viusic.media.Loop;

public class LoopMenu {
	private Loop loop;
	private ViusicMain parent;
	private ScreenManager sm;
	private int loopNum;
	
	private ArrayList<ImageButton> controlButtons;

	public LoopMenu(ViusicMain parent, ScreenManager sm, Loop loop, int loopNum) {
		this.loop = loop;
		this.parent = parent;
		this.sm = sm;
		this.loopNum = loopNum;
		
		controlButtons = new ArrayList<ImageButton>();
		
		initButtons();
	}
	
	private void initButtons() {
		
		
		ImageButton title = new ImageButton(parent, "Loop " + (loopNum + 1), ViusicMain.screenWidth -  (int)(ViusicMain.screenWidth / 1.2f) , (int)( ViusicMain.screenHeight / 1.45f), 400, 25, 1);
		
		ImageButton backPane = new ImageButton(parent, "", title.getX(), title.getY(), title.getBounds().width, ViusicMain.screenHeight - title.getY(), 1);
		backPane.setColor(100, 100, 100);
		
		ImageButton scale = new ImageButton(parent, "" + loop.getScale(), title.getX() + 150, title.getY() + 115, 100, 25, 1){
			@Override
			public void update(int deltaTime) {
				this.text = "" + (int)(loop.getScale() / 1) + "." + (int)(loop.getScale() * 100) % 100;
				super.update(deltaTime);
			}
		};
		
		ImageButton leftIncrement = new ImageButton(parent, "-", scale.getX() - 30, scale.getY() + 5, 25, 15, 1){
			@Override
			public void onMousePress(int x, int y) {
				loop.setScale(loop.getScale() - .050f);
			}
		};
		
		ImageButton rightIncrement = new ImageButton(parent, "+", scale.getX() + scale.getBounds().width + 5, leftIncrement.getY(), 25, 15, 1){
			@Override
			public void onMousePress(int x, int y) {
				loop.setScale(loop.getScale() + .05001f);
			}
		};
		
		ImageButton close = new ImageButton(parent, "x", title.getX() + 375, title.getY(), 25, 25, 1){
			@Override
			public void onMousePress(int x, int y){
				sm.setLoopMenuOpen(false);
			}
		};
		
		//Adding all the buttons to controlButtons array for drawing
		controlButtons.add(backPane);
		controlButtons.add(title);
		controlButtons.add(scale);
		controlButtons.add(leftIncrement);
		controlButtons.add(rightIncrement);
		controlButtons.add(close);
		
		//Setting text fields to visible
		parent.cp5.getController("start time").setVisible(true);
		parent.cp5.getController("end time").setVisible(true);

	}
	
	public void destroy(){
		controlButtons.clear();
		parent.cp5.getController("start time").setVisible(false);
		parent.cp5.getController("end time").setVisible(false);
	}
	
	public void mousePress(int x, int y){
		for(ImageButton imgs : controlButtons){
			imgs.mousePressed(x, y);
		}
	}
	
	public void update(int deltaTime){
		if(!sm.getIsLoopMenuOpen()){
			destroy();
			return;
		}
		for(ImageButton objs : controlButtons){
			objs.update(deltaTime);
		}
		
		drawLoopLine();
	}
	
	private void drawLoopLine() {
		parent.noStroke();
		parent.fill(0);
		parent.rect(controlButtons.get(0).getX() + 40, controlButtons.get(2).getY() + 75, 320, 5);
		
		ArrayList<Integer> tempTimes = loop.getTimes();
		for(Integer note : tempTimes){
			
			float distance = 350.0f * ((float) note / (float)loop.getEndTime());
			
			parent.stroke(0);
			parent.fill(0,0,100);
			parent.rect((controlButtons.get(0).getX() + 40) + distance, controlButtons.get(2).getY() + 70, 2, 15);
			
			String time = "" + (float)( note / 1000.0f);
			
			parent.fill(0);
			parent.textAlign(PConstants.CENTER);
			parent.text(time, (controlButtons.get(0).getX() + 40) + distance, controlButtons.get(2).getY() + 97);
		}
	}

	public void setValue(String button, String stringValue) {
		float value = Float.parseFloat(stringValue);
		System.out.println("float value" + value);
		int milis = (int) (value * 1000);

		if(button == "start time"){
			loop.setBeginTime(milis);
		}else if(button == "end time"){
			loop.setEndTime(milis);
		}
	}

}
