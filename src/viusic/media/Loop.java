package viusic.media;

import java.util.ArrayList;

public class Loop {
	private ArrayList<Integer> times;
	private ArrayList<Integer> keys;
	private int currentTime;
	private int endTime;
	private float scale;
	private int beginTime;
	private boolean soundToggled = false;
	
	public Loop(){
		times = new ArrayList<Integer>();
		keys = new ArrayList<Integer>();
		scale = 1;
		
	}
	public void setScale(float scale){
		this.scale = scale;
	}
	public float getScale(){
		return scale;
	}
	public void passInput(int key, int currentTime) {
		times.add(new Integer(currentTime));
		keys.add(new Integer(key));
	}
	public void setEndTime(int endTime){
		
		this.endTime = endTime;
		
//		if(endTime < times.get(times.size() - 1)){
//			this.endTime = times.get(times.size() - 1);
//		}
	}
	
	public int getEndTime(){
		return endTime;
	}
	
	public void setBeginTime(int beginTime){
		this.beginTime = beginTime;

//		if(beginTime > times.get(0)){
//			this.beginTime = times.get(0);
//		}
	}
	
	public int getBeginTime(){
		return beginTime;
	}
	
	public ArrayList<Integer> getSoundsToPlay(int deltaTime){
		if(!soundToggled)
			return null;
		
		//To be returned
		ArrayList<Integer> keysToSend = new ArrayList<Integer>();
		int lastTime = currentTime;
		currentTime += deltaTime * scale;		
		
		if(!soundToggled)
			return keysToSend;
			
		//Check each time for sounds that should play this frame
		for(int i = 0; i < times.size(); i++){
			int time = times.get(i);
			
			//If this index's time is between last frames time
			//and now, add it to the sounds to be played
			if(time >= lastTime && time < currentTime){
				keysToSend.add(new Integer(keys.get(i)));
			}
		}
		
		if(currentTime > endTime)
			currentTime = beginTime;

		return keysToSend;
	}
	
	public ArrayList<Integer> getTimes(){
		return times;
	}
	
	public void setSoundToggled(){
		if(soundToggled){
			soundToggled = false;
		} else {
			
			currentTime = beginTime;
			soundToggled = true;
		}
	}
	
	public boolean getSoundToggled(){
		return soundToggled;
	}

}
