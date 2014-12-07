package viusic.main;

import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;
import processing.event.MouseEvent;
import processing.video.Movie;
import viusic.UI.LoopMenu;
import viusic.UI.ScreenManager;
import viusic.media.Collection;
import viusic.media.DefaultCollectionSetup;
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
	ArrayList<Collection<Integer, String>> collections;
	
	// Holds currentCollection
	Collection<Integer, String> currentCollection;
	int currentCollectionIndex = 0;

	// ControlP5
	public ControlP5 cp5;

	// Minim
	Minim min;
	
	public boolean loopMenuOpen;


	// Time variables
	private int timePassed, lastTime;
	private boolean recording;

	// Movie Testing
	boolean playing;

	public void setup() {
		size(screenWidth, screenHeight);
		frameRate(40);

		// Initializing screenManager object, passing in PApplet and cp5
		cp5 = new ControlP5(this);
		min = new Minim(this);
		sm = new ScreenManager(this, cp5, screenWidth, screenHeight);
		videoM = new VideoManager(this);
		sndM = new SoundManager(this, min, sm);
		collections = new ArrayList<Collection<Integer, String>>();

		// Loading DefaultCollections & Setup
		dcS = new DefaultCollectionSetup(this);

		sm.giveSoundManager(sndM);

		// Setting first default currentCollection
		setCurrentCollection(0);
	}

	public void draw() {

		// Calculate time for this frame
		timePassed = millis() - lastTime;
		lastTime += timePassed;
		sndM.update(timePassed);

		drawUI();

		// Mouse Position Update in ScreenManager
		sm.mousePosition(mouseX, mouseY);

		if (sm.getIsDrawingSettingsMenu()) {
			sm.drawSettingsMenu();
		}
		sm.updateObjects(timePassed);

		// User cannot change screen dimensions
		size(screenWidth, screenHeight);
	}

	public void keyPressed() {

		/*
		 * Plays sound on keyPressed Wont play sound if in CollectionMenu
		 */
		if (key == 32) {
			sndM.toggleRecord();
			return;
		}

		if (key != CODED && !sm.getIsDrawingCollectionMenu()
				&& !sm.getIsDrawingSettingsMenu() && !loopMenuOpen) {

			// Testing video/audio playback
			if (!videoM.playVideo((int) key, true) )
				sndM.playSound((int) key, true);

			drawUI(key);
		} else if (sm.getIsSettingKey()) {
			sm.passKey(key);
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

		sm.drawButtonIndicators(0);

		if (sm.getIsDrawingCollectionMenu())
			sm.drawCollectionMenu();
		else
			sm.drawCurrentCollectionTab();

		sm.drawHomeBar();
		sm.updateObjects(timePassed);
	}

	public void drawUI(int key) {
		background(rand1, rand2, rand3);
		sm.drawButtonIndicators(key);

		if (sm.getIsDrawingCollectionMenu())
			sm.drawCollectionMenu();
		else
			sm.drawCurrentCollectionTab();

		sm.drawHomeBar();
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
				// sm.resetSettingsMenu();
				sm.setIsDrawingSettingsMenu(false);
			}
			break;
		case "start time":
			sm.passToLoopMenu("start time", event.getStringValue());
			break;
			
		case "end time":
			sm.passToLoopMenu("end time", event.getStringValue());
			break;
		case "collectionName":
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

	public ArrayList<Collection<Integer, String>> getCollections() {
		return collections;
	}
}
