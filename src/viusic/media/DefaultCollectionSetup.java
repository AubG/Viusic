package viusic.media;

import viusic.main.ViusicMain;
import viusic.media.Collection;

/*
 * Currently, this class should only be called once at the beginning of program to load
 * all of the collections. 
 */
public class DefaultCollectionSetup {
	
	Collection<Integer, String> piano;
	ViusicMain parent;
	String cwd;

	public DefaultCollectionSetup(ViusicMain main) {
		parent = main;
		setup();
	}

	public void setup() {
		piano = new Collection<Integer, String>("piano");
		piano();
	}

	void piano() {

		cwd = System.getProperty("user.dir");
		
		piano.set((int) 'a', cwd + "/viusic/data/piano/shuffle.mp4");
		piano.set((int) 's', cwd + "/viusic/data/piano/piano_14.wav");
		piano.set((int) 'd', cwd + "/viusic/data/piano/piano_15.wav");
		piano.set((int) 'f', cwd + "/viusic/data/piano/piano_16.wav");
		piano.set((int) 'g', cwd + "/viusic/data/piano/piano_17.wav");
		piano.set((int) 'h', cwd + "/viusic/data/piano/piano_18.wav");
		piano.set((int) 'j', cwd + "/viusic/data/piano/piano_19.wav");
		piano.set((int) 'k', cwd + "/viusic/data/piano/piano_20.wav");
		piano.set((int) 'l', cwd + "/viusic/data/piano/piano_21.wav");
		piano.set((int) ';', cwd + "/viusic/data/piano/piano_22.wav");
		piano.set(39, cwd + "/viusic/data/piano/piano_23.wav");

		parent.addCollection(piano);
	}
}
