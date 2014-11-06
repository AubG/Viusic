package viusic.sound;

import processing.core.PApplet;
import viusic.UI.ScreenManager;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class SoundManager {

	PApplet parent;
	ScreenManager sm;
	Minim min;
	AudioPlayer player;
	
	public SoundManager(PApplet p, Minim m, ScreenManager s){
		parent = p;
		min = m;
		sm = s;
		
		System.out.println(System.getProperty("user.dir"));
	}
	
	public void playSound(int key){
		switch(key){
		case 'z':
			player = min.loadFile("\\viusic\\data\\jobro__piano-ff-001.wav");
			player.play();
			break;
		case 'x':
			player = min.loadFile("\\viusic\\data\\jobro__piano-ff-002.wav");
			player.play();
			break;
		case 'c':
			player = min.loadFile("\\viusic\\data\\jobro__piano-ff-003.wav");
			player.play();
			break;
		case 'v':
			player = min.loadFile("\\viusic\\data\\jobro__piano-ff-004.wav");
			player.play();
			break;
		case 'b':
			player = min.loadFile("\\viusic\\data\\jobro__piano-ff-005.wav");
			player.play();
			break;
		case 'n':
			player = min.loadFile("\\viusic\\data\\jobro__piano-ff-006.wav");
			player.play();
			break;
		case 'm':
			player = min.loadFile("\\viusic\\data\\jobro__piano-ff-007.wav");
			player.play();
			break;
		case ',':
			player = min.loadFile("\\viusic\\data\\jobro__piano-ff-008.wav");
			player.play();
			break;
		case '.':
			player = min.loadFile("\\viusic\\data\\jobro__piano-ff-009.wav");
			player.play();
			break;
		case '/':
			player = min.loadFile("\\viusic\\data\\jobro__piano-ff-010.wav");
			player.play();
			break;
		}
	}
}
