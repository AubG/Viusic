package viusic.media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import viusic.UI.ScreenManager;
import viusic.main.ViusicMain;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class SoundManager {
	// PApplet passed from main
	ViusicMain parent;

	// ScreenManager passed from main
	ScreenManager sm;

	// Minim passed from main, new audioPlayer created here
	Minim min;
	AudioPlayer player;
	private HashMap<Integer, AudioPlayer> sounds;
	
	private boolean recording;

	private ArrayList<Loop> loops;
	private Loop currentLoop;
	private int currentTime;

	public SoundManager(ViusicMain p, Minim m, ScreenManager s) {
		// Passed objects from main, Set to local objects
		parent = p;
		min = m;
		sm = s;
		loops = new ArrayList<Loop>();
		
		sounds = new HashMap<Integer, AudioPlayer>();
	}

	public void playSound(int key, boolean fromUser) {
		// for building a new loop
		if (fromUser && recording) {
			currentLoop.passInput(sounds.get(key), currentTime);
		}

		// Get sound from above path, play sound
		sounds.get(key).play(0);
	}

	public void grabAudio(Collection currentCollection) {
		sounds.clear();

		Iterator it = currentCollection.getMedia().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();

			String fileName = (String) (pairs.getValue());

			if (fileIsAudiofileName(fileName)) {
				System.out.println(pairs.getKey() + " = " + pairs.getValue());

				sounds.put((Integer)pairs.getKey(), min.loadFile(fileName));
			}
			// avoids a ConcurrentModificationException
			// also destroys our hashmap and causes everything to be null
			// therefore fuck that shit. Spent too long trying to debug that
			// bitch.
			//
			// ---> it.remove(); <---
		}
	}

	private boolean fileIsAudiofileName(String fileName) {
		if(fileName.endsWith("MP3") || fileName.endsWith("mp3") || fileName.endsWith(".wav") || fileName.endsWith(".WAV")){
			System.out.println("Sound found " + fileName);
			return true;
		}
		return false;
	}

	public void toggleRecord() {
		// Only want to execute this method if
		// we have a collection open and running
		if (parent.getCurrentCollection() == null)
			return;

		// Set the end time for this loop to stop
		if (recording) {
			recording = false;

			currentLoop.setEndTime(currentTime);
			loops.add(currentLoop);
			sm.addLoop(loops.size());

			System.out.println("Recording Ended");

			// Start recording and listening for inputs
		} else {
			if (loops.size() < 4) {
				currentTime = 0;
				currentLoop = new Loop();
				recording = true;
				System.out.println("Recording Started");
			}
		}

	}

	public void update(int deltaTime) {
		currentTime += deltaTime;

		ArrayList<Integer> sounds;
		for (Loop curLoop : loops) {

			sounds = curLoop.getSoundsToPlay(deltaTime);
			/*// Make sure there is at least one sound to play
			if (sounds != null) {
				// Play all sounds for this loop in this frame
				for (int i = 0; i < sounds.size(); i++) {
					playSound(sounds.get(i), false);
				}
			}*/

		}

	}

	public Loop getLoop(int number) {
		return loops.get(number);
	}
}
