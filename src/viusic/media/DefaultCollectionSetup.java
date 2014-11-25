package viusic.media;

/*
 * Currently, this class should only be called once at the beginning of program to load
 * all of the collections. 
 * 
 * Later in program development users should be able to create custom collections and
 * add them to the collection ArrayList in SoundManager
 */
public class DefaultCollectionSetup {
	static AudioCollection<Integer, String> piano = new AudioCollection<Integer, String>(
			"piano");
	
	static SoundManager snd;
	
	public static void setup(SoundManager s) {
		snd = s;
		piano();
	}

	static void piano() {
		piano.set((int) 'q', "\\viusic\\data\\piano\\piano_1.wav");
		piano.set((int) 'w', "\\viusic\\data\\piano\\piano_2.wav");
		piano.set((int) 'e', "\\viusic\\data\\piano\\piano_3.wav");
		piano.set((int) 'r', "\\viusic\\data\\piano\\piano_4.wav");
		piano.set((int) 't', "\\viusic\\data\\piano\\piano_5.wav");
		piano.set((int) 'y', "\\viusic\\data\\piano\\piano_6.wav");
		piano.set((int) 'u', "\\viusic\\data\\piano\\piano_7.wav");
		piano.set((int) 'i', "\\viusic\\data\\piano\\piano_8.wav");
		piano.set((int) 'o', "\\viusic\\data\\piano\\piano_9.wav");
		piano.set((int) 'p', "\\viusic\\data\\piano\\piano_10.wav");
		piano.set((int) '[', "\\viusic\\data\\piano\\piano_11.wav");
		piano.set((int) ']', "\\viusic\\data\\piano\\piano_12.wav");
		piano.set((int) 'a', "\\viusic\\data\\piano\\piano_13.wav");
		piano.set((int) 's', "\\viusic\\data\\piano\\piano_14.wav");
		piano.set((int) 'd', "\\viusic\\data\\piano\\piano_15.wav");
		piano.set((int) 'f', "\\viusic\\data\\piano\\piano_16.wav");
		piano.set((int) 'g', "\\viusic\\data\\piano\\piano_17.wav");
		piano.set((int) 'h', "\\viusic\\data\\piano\\piano_18.wav");
		piano.set((int) 'j', "\\viusic\\data\\piano\\piano_19.wav");
		piano.set((int) 'k', "\\viusic\\data\\piano\\piano_20.wav");
		piano.set((int) 'l', "\\viusic\\data\\piano\\piano_21.wav");
		piano.set((int) ';', "\\viusic\\data\\piano\\piano_22.wav");
		piano.set((int) '\'', "\\viusic\\data\\piano\\piano_23.wav");
		piano.set((int) 'z', "\\viusic\\data\\piano\\piano_24.wav");
		piano.set((int) 'x', "\\viusic\\data\\piano\\piano_25.wav");
		piano.set((int) 'c', "\\viusic\\data\\piano\\piano_26.wav");
		piano.set((int) 'v', "\\viusic\\data\\piano\\piano_27.wav");
		piano.set((int) 'b', "\\viusic\\data\\piano\\piano_28.wav");
		piano.set((int) 'n', "\\viusic\\data\\piano\\piano_29.wav");
		piano.set((int) 'm', "\\viusic\\data\\piano\\piano_30.wav");
		piano.set((int) ',', "\\viusic\\data\\piano\\piano_31.wav");
		piano.set((int) '.', "\\viusic\\data\\piano\\piano_32.wav");
		
		snd.addCollection(piano);
	}
}
