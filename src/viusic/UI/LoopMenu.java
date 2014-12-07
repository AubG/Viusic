package viusic.UI;

import java.util.ArrayList;

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
		
		
		ImageButton title = new ImageButton(parent, "Loop " + (loopNum + 1), ViusicMain.screenWidth -  (int)(ViusicMain.screenWidth / 1.2f) , ViusicMain.screenHeight - (int)( ViusicMain.screenHeight / 1.2f), 400, 25, 1);
		
		ImageButton backPane = new ImageButton(parent, "", title.getX(), title.getY(), title.getBounds().width, ViusicMain.screenHeight - 40 - title.getY(), 1);
		backPane.setColor(100, 100, 100);
		
		parent.cp5.addTextfield("start time").setPosition(title.getX() + 125, title.getY() + 50)
		.setSize(200, 25);

		parent.cp5.addTextfield("end time").setPosition(title.getX() + 125, title.getY()  + 100)
		.setSize(200, 25);
		
		ImageButton scale = new ImageButton(parent, "" + loop.getScale(), title.getX() + 150, title.getY() + 150, 100, 25, 1){
			@Override
			public void update(int deltaTime) {
				this.text = "" + (int)(loop.getScale() / 1) + "." + (int)(loop.getScale() * 100) % 100;
				super.update(deltaTime);
			}
		};
		
		ImageButton leftIncrement = new ImageButton(parent, "-.05", scale.getX() - 75, scale.getY() + 10, 50, 20, 1){
			@Override
			public void onMousePress(int x, int y) {
				loop.setScale(loop.getScale() - .050f);
			}
		};
		
		ImageButton rightIncrement = new ImageButton(parent, "+.05", scale.getX() + scale.getBounds().width + 20, leftIncrement.getY(), 50, 20, 1){
			@Override
			public void onMousePress(int x, int y) {
				loop.setScale(loop.getScale() + .05001f);
			}
		};
		
		
		

		controlButtons.add(backPane);
		controlButtons.add(title);
		controlButtons.add(scale);
		controlButtons.add(leftIncrement);
		controlButtons.add(rightIncrement);

	}
	public void destroy(){
		
	}
	public void mousePress(int x, int y){
		for(ImageButton imgs : controlButtons){
			imgs.mousePressed(x, y);
		}
	}
	public void update(int deltaTime){
		for(ImageButton objs : controlButtons){
			objs.update(deltaTime);
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
