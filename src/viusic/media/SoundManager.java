package viusic.media;

import java.util.ArrayList;

import processing.core.PApplet;
import viusic.UI.ScreenManager;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class SoundManager {

	// ArrayList to store all collections
	ArrayList<AudioCollection> collections = new ArrayList<>();
	
	// Holds currentCollection
	AudioCollection<Integer, String> currentCollection;
	
	// PApplet passed from main
	PApplet parent;
	
	// ScreenManager passed from main
	ScreenManager sm;
	
	//Minim passed from main, new audioPlayer created here
	Minim min;
	AudioPlayer player;

	
	private boolean recording;
	private ArrayList<Loop> loops;
	private Loop currentLoop;
	private int currentTime;
	
	public SoundManager(PApplet p, Minim m, ScreenManager s){
		// Passed objects from main, Set to local objects
		parent = p;
		min = m;
		sm = s;
		loops = new ArrayList<Loop>();
		
		//Debug info, prints user directory
		System.out.println(System.getProperty("user.dir"));
	
		// Creating all default collections
		DefaultCollectionSetup.setup(this);
	}
	
	public void addCollection(AudioCollection<Integer, String> c){
		collections.add(c);
	}
	
	public void playSound(int key, boolean fromUser){
		//for building a new loop
		if(fromUser && recording){
			currentLoop.passInput(key, currentTime);
		}
		
		// Debug key info, path to sound
		System.out.println("keypressed was " + key);
		System.out.println("path to sound  ::  " + currentCollection.get(key));
		
		//Draw Button Indicator & menuBar
//		sm.drawButtonIndicators(key);
//		sm.drawCurrentCollectionTab();
//		sm.drawHomeBar();
		
		
		// Get sound from above path, play sound
		player = min.loadFile(currentCollection.get(key));
		player.play();
	}
	
	// Returns length of collection ArrayList
	public int getCollectionLength(){
		return collections.size();
	}
	
	// Returns collection at given index from the ArrayList
	public AudioCollection getCollection(int index){
		return collections.get(index);
	}
	
	// Sets the currentCollection
	public void setCurrentCollection(int index){
		currentCollection = collections.get(index);
	}
	
	// Gets the currentCollection
	public AudioCollection<Integer, String> getCurrentCollection(){
		return currentCollection;
	}
	
	public void toggleRecord() {
		//Only want to execute this method if
		//we have a collection open and running
		if(currentCollection == null)
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
