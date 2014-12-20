package viusic.main;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.video.Movie;
import viusic.UI.ScreenManager;
import viusic.media.Collection;
import viusic.media.DefaultCollectionSetup;
import viusic.media.JsonLoader;
import viusic.media.Loop;
import viusic.media.SoundManager;
import viusic.media.VideoManager;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import ddf.minim.Minim;

public class ViusicMain extends PApplet {

	// Default resolution (Subject to change)
	public static final int screenWidth = 1280;
	public static final int screenHeight = 720;

	// For random numbers
	Random rnd = new Random();
	int rand1 = rnd.nextInt(256);
	int rand2 = rnd.nextInt(256);
	int rand3 = rnd.nextInt(256);

	// Manager Objects
	ScreenManager sm;
	SoundManager sndM;
	VideoManager videoM;
	DefaultCollectionSetup dcS;

	// ArrayList to store all collections
	public ArrayList<Collection<Integer, String>> collections;

	// Holds currentCollection
	Collection<Integer, String> currentCollection;
	public int currentCollectionIndex = 0;

	// ControlP5
	public ControlP5 cp5;

	// Minim
	Minim min;

	// Time variables
	private int timePassed, lastTime;
	private boolean recording;

	// Movie Testing
	boolean playing;

	
	JsonLoader loader;
	public void setup() {
		size(screenWidth, screenHeight);
		frameRate(60);

		// Initializing screenManager object, passing in PApplet and cp5
		cp5 = new ControlP5(this);
		min = new Minim(this);

		// Manager setup
		sm = new ScreenManager(this, cp5, screenWidth, screenHeight);
		sndM = new SoundManager(this, min, sm);
		sm.takeSoundManager(sndM);

		videoM = new VideoManager(this);
		collections = new ArrayList<Collection<Integer, String>>();

		// Loading DefaultCollections & Setup
		dcS = new DefaultCollectionSetup(this);

		// Setting first default currentCollection
		setCurrentCollection(0);
		loader = new JsonLoader(this);
	}

	public void draw() {

		// Calculate time for this frame
		timePassed = millis() - lastTime;
		lastTime += timePassed;

		// update Sound Manager
		sndM.update(timePassed);

		// Mouse Position Update in ScreenManager
		sm.mousePosition(mouseX, mouseY);

		// Draws background
		drawUI();
		// update ScreenManager
		sm.updateObjects(timePassed);

		// User cannot change screen dimensions
		size(screenWidth, screenHeight);
	}

	public void keyPressed() {
		// attempt to load audio resources
		try {
			/*
			 * Plays sound on keyPressed no sounds played when in menus
			 */
			if (key == 32 && !sm.getIsDrawingCollectionMenu()
					&& !sm.getIsDrawingSettingsMenu()
					&& !sm.getIsLoopMenuOpen()) {
				
				sndM.toggleRecord();
				sm.toggleRecord();
				return;
			}

			// User presses numbers 1 - 4 to toggle the loop playback.
			switch (key) {
			case 49:
				sndM.getLoop(0).soundToggle();
				return;
			case 50:
				sndM.getLoop(1).soundToggle();
				return;
			case 51:
				sndM.getLoop(2).soundToggle();
				return;
			case 52:
				sndM.getLoop(3).soundToggle();
				return;
			case 53:
				loader.saveProfile();
				return;
			}

			// key pressed and not in any menus
			if (key != CODED  && !sm.getIsDrawingCollectionMenu()
					&& !sm.getIsDrawingSettingsMenu()
					&& !sm.getIsLoopMenuOpen()) {

				// Testing video/audio playback
				if (!videoM.playVideo((int) key, true))
					sndM.playSound((int) key, true);

				// Pressed on a key to sound or video
				drawUI(key);
				// Otherwise settings menu receives the key
			} else if (sm.getIsSettingKey()) {
				sm.passKey(key);
			}

		}
		// handle exception
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void keyReleased() {

	}

	public void mouseClicked() {

		/*
		 * Checks for mouse clicks in CollectionMenu Sets Current Collection to
		 * Selected Collection
		 */
		sm.mouseClicked(mouseX, mouseY);
		if (sm.getIsDrawingCollectionMenu()) {
			for (int i = 0; i < getCollectionLength(); i++) {

				// Iterates through each collection and checks if it was
				// clicked.
				if (getCollection(i).isMouseOver(mouseX, mouseY)) {
					setCurrentCollection(i);
					sm.setIsDrawingCollectionMenu(false);

				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		sm.mouseReleased(event.getX(), event.getY());
	}

	
	public void drawUI() {
		background(rand1, rand2, rand3);

		videoM.draw();
		
//		sm.drawButtonIndicators(0);

		if (sm.getIsDrawingCollectionMenu())
			sm.drawCollectionMenu();
		else
			sm.drawCurrentCollectionTab();

		sm.drawHomeBar();
		
		// Updates ImageButton
		sm.updateObjects(timePassed);
	}

	
	public void drawUI(int key) {
		background(rand1, rand2, rand3);

		videoM.draw();

		// Draws button indicators
//		sm.drawButtonIndicators(key);

		// Draws collection menu
		if (sm.getIsDrawingCollectionMenu())
			sm.drawCollectionMenu();

		// Draws collection tab
		else
			sm.drawCurrentCollectionTab();

		sm.drawHomeBar();
		// Updates ImageButton
		sm.updateObjects(timePassed);
	}

	
	/*
	 * Receives events from all controlP5 controllers event.getName() returns
	 * name of controller which had an event
	 */
	public void controlEvent(ControlEvent event) {

		/*
		 * switch( controller name )
		 */
		switch (event.getName()) {

		// Collections Button
		case "Collections":
			// Open menu, set boolean (Collection Selection state)
			sm.setIsDrawingCollectionMenu(true);
			break;

		// Record Button
		case "record":
			// Start recording
			sndM.toggleRecord();
			break;

		// Settings Menu Button
		case "gear":
			if (!sm.getIsDrawingSettingsMenu()) {
				sm.setIsDrawingSettingsMenu(true);
				sm.drawSettingsMenu();
			} else {
				// Fix the settings menu
				sm.setIsDrawingSettingsMenu(false);
			}
			break;
		case "start time":
			sm.passToLoopMenu("start time", event.getStringValue());
			break;

		case "end time":
			sm.passToLoopMenu("end time", event.getStringValue());
			break;
		case "enter collection name":
			sm.passName(event.getStringValue());
		}
	}

	
	public int getDeltaTime() {
		return timePassed;
	}

	
	public void movieEvent(Movie m) {
		m.read();
	}

	// Sets the currentCollection
	
	public void setCurrentCollection(int index) {
		
		currentCollectionIndex = index;

		videoM.grabVideos(collections.get(currentCollectionIndex));
		sndM.grabAudio(collections.get(currentCollectionIndex));
	}

	// Gets the currentCollection
	
	public Collection<Integer, String> getCurrentCollection() {

		return collections.get(currentCollectionIndex);
	}

	// Returns length of collection ArrayList
	
	public int getCollectionLength() {
		return collections.size();
	}

	// Returns collection at given index from the ArrayList
	
	public Collection<Integer, String> getCollection(int index) {
		return collections.get(index);
	}

	
	public void addCollection(Collection<Integer, String> c) {
		collections.add(c);
	}
	
	public void removeCollection(Collection<Integer, String> c){
		collections.remove(c);
	}

	
	public ArrayList<Collection<Integer, String>> getCollections() {
		return collections;
	}

	public void setLoops(ArrayList<Loop> loops) {
		sndM.setLoops(loops);
	}

	public ArrayList<Loop> getLoops() {
		// TODO Auto-generated method stub
		return sndM.getLoops();
	}

	//Called on program exit
	public void stop(){
		loader.saveProfile();
	}
}
