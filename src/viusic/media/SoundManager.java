package viusic.media;

import java.util.ArrayList;

import viusic.UI.ScreenManager;
import viusic.main.ViusicMain;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class SoundManager {
	// PApplet passed from main
	ViusicMain parent;
	
	// ScreenManager passed from main
	ScreenManager sm;
	
	//Minim passed from main, new audioPlayer created here
	Minim min;
	AudioPlayer player;

	
	private boolean recording;
	private ArrayList<Loop> loops;
	private Loop currentLoop;
	private int currentTime;
	
	public SoundManager(ViusicMain p, Minim m, ScreenManager s){
		// Passed objects from main, Set to local objects
		parent = p;
		min = m;
		sm = s;
		loops = new ArrayList<Loop>();
	}
	
	public void playSound(int key, boolean fromUser){
		//for building a new loop
		if(fromUser && recording){
			currentLoop.passInput(key, currentTime);
		}
		
		// Debug key info, path to sound
		System.out.println(key + " :: path to sound  ::  " + parent.getCurrentCollection().get(key));
		
		// Get sound from above path, play sound
		player = min.loadFile(parent.getCurrentCollection().get(key));
		player.play();
	}
	
	
	
		
	public void toggleRecord() {
		//Only want to execute this method if
		//we have a collection open and running
		if(parent.getCurrentCollection() == null)
			return;
		
		//Set the end time for this loop to stop
		if (recording){
			recording = false;
			currentLoop.setEndTime(currentTime);
			loops.add(currentLoop);
			System.out.println("Recording Ended");

		//Start recording and listening for inputs
		}else{
			currentTime = 0;
			currentLoop = new Loop();
			recording = true;
			System.out.println("Recording Started");
		}
		
	}
	
	public void update(int deltaTime){
		currentTime += deltaTime;
		
		
		ArrayList<Integer> sounds;
		for(Loop curLoop : loops){
			
			sounds = curLoop.getSoundsToPlay(deltaTime);
			//Make sure there is at least one sound to play
			if(sounds != null){
				//Play all sounds for this loop in this frame
				for(int i = 0; i < sounds.size(); i++){
					playSound(sounds.get(i), false);
					System.out.println(i);
				}
			}

		}

		
	}
}
