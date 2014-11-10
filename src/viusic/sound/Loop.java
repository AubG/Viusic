package viusic.sound;

import java.util.ArrayList;

public class Loop {
	private ArrayList<Integer> times;
	private ArrayList<Integer> keys;
	private int currentTime;
	private int endTime;
	
	public Loop(){
		times = new ArrayList<Integer>();
		keys = new ArrayList<Integer>();
	}

	public void passInput(int key, int currentTime) {
		times.add(new Integer(currentTime));
		keys.add(new Integer(key));
	}
	public void setEndTime(int endTime){
		this.endTime = endTime;
	}
	
	public ArrayList<Integer> getSoundsToPlay(int deltaTime){
		//To be returned
		ArrayList<Integer> keysToSend = new ArrayList<Integer>();
		int lastTime = currentTime;
		currentTime += deltaTime;
		
		
		
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
			currentTime = 0;

		return keysToSend;
	}

}
