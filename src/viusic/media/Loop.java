package viusic.media;

import java.util.ArrayList;
import java.util.HashMap;

import viusic.UI.ImageButton;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Loop {
	private ArrayList<Integer> times;
	private ArrayList<Integer> keys;
	private int currentTime;
	private int endTime;
	private float scale;
	private int beginTime;
	private boolean soundToggled = false;
	//private ArrayList<AudioPlayer> audioPlayers;
	private HashMap<Integer, String> sounds;
	private HashMap<Integer, Integer> keyTimes;
	private ImageButton loopButton;
	private Minim min;
	
	public Loop(Minim m){
		min = m;
		times = new ArrayList<Integer>();
		keys = new ArrayList<Integer>();
		//audioPlayers = new ArrayList<AudioPlayer>();
		sounds = new HashMap<Integer, String>();
		keyTimes = new HashMap<Integer, Integer>();
		scale = 1;
		
	}
	public void setScale(float scale){
		this.scale = scale;
	}
	public float getScale(){
		return scale;
	}
	public int getSize(){
		return keys.size();
	}
	
	//Sends in a audio player
	public void passInput(String filePath, Integer key, int currentTime) {
		sounds.put(key, filePath);
		keyTimes.put(currentTime, key);
		times.add(currentTime);
		
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
	
	//Returns the sounds that should be started THIS FRAME
	public void getSoundsToPlay(int deltaTime){
		if(!soundToggled)
			return;
		
		int lastTime = currentTime;
		currentTime += deltaTime * scale;
		
		//Check each time for sounds that should play this frame
		for(int i = 0; i < times.size(); i++){
			int time = times.get(i);
			
			//If this index's time is between last frames time
			//and now, add it to the sounds to be played
			if(time >= lastTime && time < currentTime){
				System.out.println(time +" Loop class");
				//Stuff happening
				Integer key = keyTimes.get(time);
				AudioPlayer temp = min.loadFile(sounds.get(key));
				temp.play();
			}
		}
		
		//Set currentTime to the loop's beginning time
		if(currentTime > endTime)
			currentTime = beginTime;

	}
	public void setNewTime(int index, int newTime){
		
		int keyNumber = keyTimes.get(times.get(index));
		keyTimes.remove(times.get(index));
		keyTimes.put(newTime, keyNumber);
		times.set(index, newTime);
	}
	public ArrayList<Integer> getTimes(){
		return times;
	}
	
	//Toggle method for playing the loop
	//Restarts the loop from beginTime
	public void soundToggle(){
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
	
	public void setLoopButton(ImageButton loopButton){
		this.loopButton = loopButton;
	}
	
	public ImageButton getLoopButton(){
		return loopButton;
	}

}
