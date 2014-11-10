package viusic.sound;

import java.util.ArrayList;

import processing.core.PApplet;
import viusic.UI.ScreenManager;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class SoundManager {

	// ArrayList to store all collections
	ArrayList<Collection> collections = new ArrayList<>();
	
	// Holds currentCollection
	Collection<Integer, String> currentCollection;
	
	// PApplet passed from main
	PApplet parent;
	
	// ScreenManager passed from main
	ScreenManager sm;
	
	//Minim passed from main, new audioPlayer created here
	Minim min;
	AudioPlayer player;
	
	public SoundManager(PApplet p, Minim m, ScreenManager s){
		// Passed objects from main, Set to local objects
		parent = p;
		min = m;
		sm = s;
		
		//Debug info, prints user directory
		System.out.println(System.getProperty("user.dir"));
	
		// Creating all default collections
		DefaultCollectionSetup.setup(this);
	}
	
	public void addCollection(Collection<Integer, String> c){
		collections.add(c);
	}
	
	public void playSound(int key){
		
		// Debug key info, path to sound
		System.out.println("keypressed was " + key);
		System.out.println("path to sound  ::  " + ((String) currentCollection.get(key)));
		
		// Get sound from above path, play sound
		player = min.loadFile((String) currentCollection.get(key));
		player.play();
	}
	
	// Returns length of collection ArrayList
	public int getCollectionLength(){
		return collections.size();
	}
	
	// Returns collection at given index from the ArrayList
	public Collection getCollection(int index){
		return collections.get(index);
	}
	
	// Sets the currentCollection
	public void setCurrentCollection(int index){
		currentCollection = collections.get(index);
	}
	
	// Gets the currentCollection
	public Collection getCurrentCollection(){
		return currentCollection;
	}
}
