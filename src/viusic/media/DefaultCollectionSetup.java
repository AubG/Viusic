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
<<<<<<< HEAD
		piano.set((int) 'q', "/viusic/data/piano/piano_1.wav");
		piano.set((int) 'w', "/viusic/data/piano/piano_2.wav");
		piano.set((int) 'e', "/viusic/data/piano/piano_3.wav");
		piano.set((int) 'r', "/viusic/data/piano/piano_4.wav");
		piano.set((int) 't', "/viusic/data/piano/piano_5.wav");
		piano.set((int) 'y', "/viusic/data/piano/piano_6.wav");
		piano.set((int) 'u', "/viusic/data/piano/piano_7.wav");
		piano.set((int) 'i', "/viusic/data/piano/piano_8.wav");
	/*	piano.set((int) 'o', "/viusic/data/piano/piano_9.wav");
		piano.set((int) 'p', "/viusic/data/piano/piano_10.wav");
		piano.set((int) '[', "/viusic/data/piano/piano_11.wav");
		piano.set((int) ']', "/viusic/data/piano/piano_12.wav");
		piano.set((int) 'a', "/viusic/data/piano/piano_13.wav");
		piano.set((int) 's', "/viusic/data/piano/piano_14.wav");
		piano.set((int) 'd', "/viusic/data/piano/piano_15.wav");
		piano.set((int) 'f', "/viusic/data/piano/piano_16.wav");
		piano.set((int) 'g', "/viusic/data/piano/piano_17.wav");
		piano.set((int) 'h', "/viusic/data/piano/piano_18.wav");
		piano.set((int) 'j', "/viusic/data/piano/piano_19.wav");
		piano.set((int) 'k', "/viusic/data/piano/piano_20.wav");
		piano.set((int) 'l', "/viusic/data/piano/piano_21.wav");
		piano.set((int) ';', "/viusic/data/piano/piano_22.wav");
		piano.set((int) '\'', "/viusic/data/piano/piano_23.wav");
		piano.set((int) 'z', "/viusic/data/piano/piano_24.wav");
		piano.set((int) 'x', "/viusic/data/piano/piano_25.wav");
		piano.set((int) 'c', "/viusic/data/piano/piano_26.wav");
		piano.set((int) 'v', "/viusic/data/piano/piano_27.wav");
		piano.set((int) 'b', "/viusic/data/piano/piano_28.wav");
		piano.set((int) 'n', "/viusic/data/piano/piano_29.wav");
		piano.set((int) 'm', "/viusic/data/piano/piano_30.wav");
		piano.set((int) ',', "/viusic/data/piano/piano_31.wav");
		piano.set((int) '.', "/viusic/data/piano/piano_32.wav");*/
=======
		cwd = System.getProperty("user.dir");
		
		piano.set((int) 'q', cwd + "/viusic/data/piano/piano_1.wav");
		piano.set((int) 'w', cwd + "/viusic/data/piano/piano_2.wav");
		piano.set((int) 'e', cwd + "/viusic/data/piano/piano_3.wav");
		piano.set((int) 'r', cwd + "/viusic/data/piano/piano_4.wav");
		piano.set((int) 't', cwd + "/viusic/data/piano/piano_5.wav");
		piano.set((int) 'y', cwd + "/viusic/data/piano/piano_6.wav");
		piano.set((int) 'u', cwd + "/viusic/data/piano/piano_7.wav");
		piano.set((int) 'i', cwd + "/viusic/data/piano/piano_8.wav");
		piano.set((int) 'o', cwd + "/viusic/data/piano/piano_9.wav");
		piano.set((int) 'p', cwd + "/viusic/data/piano/piano_10.wav");
		piano.set((int) '[', cwd + "/viusic/data/piano/piano_11.wav");
		piano.set((int) ']', cwd + "/viusic/data/piano/piano_12.wav");
		piano.set((int) 'a', cwd + "/viusic/data/piano/piano_13.wav");
		piano.set((int) 's', cwd + "/viusic/data/piano/piano_14.wav");
		piano.set((int) 'd', cwd + "/viusic/data/piano/piano_15.wav");
		piano.set((int) 'f', cwd + "/viusic/data/piano/piano_16.wav");
		piano.set((int) 'g', cwd + "/viusic/data/piano/piano_17.wav");
		piano.set((int) 'h', cwd + "/viusic/data/piano/piano_18.wav");
		piano.set((int) 'j', cwd + "/viusic/data/piano/piano_19.wav");
		piano.set((int) 'k', cwd + "/viusic/data/piano/piano_20.wav");
		piano.set((int) 'l', cwd + "/viusic/data/piano/piano_21.wav");
		piano.set((int) ';', cwd + "/viusic/data/piano/piano_22.wav");
		piano.set((int) '\'', cwd + "/viusic/data/piano/piano_23.wav");
		piano.set((int) 'z', cwd + "/viusic/data/piano/piano_24.wav");
		piano.set((int) 'x', cwd + "/viusic/data/piano/piano_25.wav");
		piano.set((int) 'c', cwd + "/viusic/data/piano/piano_26.wav");
		piano.set((int) 'v', cwd + "/viusic/data/piano/piano_27.wav");
		piano.set((int) 'b', cwd + "/viusic/data/piano/piano_28.wav");
		piano.set((int) 'n', cwd + "/viusic/data/piano/piano_29.wav");
		piano.set((int) 'm', cwd + "/viusic/data/piano/piano_30.wav");
		piano.set((int) ',', cwd + "/viusic/data/piano/piano_31.wav");
		piano.set((int) '.', cwd + "/viusic/data/piano/piano_32.wav");
>>>>>>> f65dc95a07bda2dfa615304b635dc0ff113cc9c7

		parent.addCollection(piano);
	}
}
