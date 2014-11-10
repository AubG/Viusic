package viusic.sound;

import java.util.ArrayList;

import processing.core.PApplet;
import viusic.UI.ScreenManager;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class SoundManager {

	ArrayList<Collection> collections = new ArrayList<>();
	Collection<Integer, String> currentCollection;
	
	PApplet parent;
	ScreenManager sm;
	Minim min;
	AudioPlayer player;
	
	public SoundManager(PApplet p, Minim m, ScreenManager s){
		parent = p;
		min = m;
		sm = s;
		
		System.out.println(System.getProperty("user.dir"));
	
		// Creating all default collections
		DefaultCollectionSetup.setup(this);
	}
	
	public void addCollection(Collection<Integer, String> c){
		collections.add(c);
	}
	
	public void playSound(int key){
		System.out.println("keypressed was " + key);
		System.out.println("path to sound  ::  " + ((String) currentCollection.get(key)));
		player = min.loadFile((String) currentCollection.get(key));
		player.play();
	}
	
	public int getCollectionLength(){
		return collections.size();
	}
	
	public Collection getCollection(int index){
		return collections.get(index);
	}
	
	public void setCurrentCollection(int index){
		currentCollection = collections.get(index);
	}
	
	public Collection getCurrentCollection(){
		return currentCollection;
	}
}
