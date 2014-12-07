package viusic.media;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import processing.video.Movie;
import viusic.main.ViusicMain;

public class VideoManager {

	ArrayList<Movie> videos;
	ArrayList<Integer> keys;
	ArrayList<Integer> playingVideoIndexes;
	ViusicMain parent;
	
	public VideoManager(ViusicMain parent) {
		videos = new ArrayList<Movie>();
		keys = new ArrayList<Integer>();
		playingVideoIndexes = new ArrayList<Integer>();
		this.parent = parent;

	}
	
	public void draw(){
		for(int i = 0; i < playingVideoIndexes.size(); i++){
			parent.image(videos.get(playingVideoIndexes.get(i)), 0, 0);
		}
	}

	public boolean playVideo(Integer key, boolean fromUser) {
		
		System.out.println("VideoManager.playVideo :: START");
		
		for(int i = 0; i < keys.size(); i++){
			
			System.out.println("VideoManager.playVideo :: SEARCHING KEYS");
			
			if(keys.get(i).equals(key)){
				videos.get(i).play();
				playingVideoIndexes.add(i);
				return true;
			}
		}
		
		return false;
	}

	public void grabVideos(Collection<Integer, String> currentCollection) {
		videos.clear();
		keys.clear();
		playingVideoIndexes.clear();
		
		Iterator it = currentCollection.getMedia().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();

	        String fileName = (String)(pairs.getValue());
	         
	        if(fileIsVideo(fileName)){
	        	
	        	System.out.println("We have infact found a movie");
	        	System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        	
	        	Movie mov = new Movie(parent, fileName);
	        	videos.add(mov);
	        	keys.add((Integer)(pairs.getKey()));
	        }
	        // avoids a ConcurrentModificationException
	        // also destroys our hashmap and causes everything to be null
	        // therefore fuck that shit. Spent too long trying to debug that bitch.
	        //
	        // --->  it.remove();  <---
	    }
	}
	private boolean fileIsVideo(String string) {
		if(string.endsWith(".mov") || string.endsWith(".MOV") || string.endsWith(".mp4") || string.endsWith(".MP4"))
			return true;
		
		return false;
	}

}
