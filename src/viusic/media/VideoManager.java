package viusic.media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import processing.video.Movie;
import viusic.main.ViusicMain;

public class VideoManager {

	private HashMap<Integer, Movie> videos;
	ArrayList<Integer> playingVideoKeys;
	ViusicMain parent;
	
	public VideoManager(ViusicMain parent) {
		videos = new HashMap<Integer, Movie>();
		playingVideoKeys = new ArrayList<Integer>();
		this.parent = parent;

	}
	
	public void draw(){
		for(int i = 0; i < playingVideoKeys.size(); i++){
			parent.image(videos.get(playingVideoKeys.get(i)), 0, 0);
		}
	}

	public boolean playVideo(Integer key, boolean fromUser) {
		
		if(videos.containsKey(key)){
			videos.get(key);
			return true;
		}
		
		return false;
	}

	public void grabVideos(Collection<Integer, String> currentCollection) {
		videos.clear();
		playingVideoKeys.clear();
		
		Iterator it = currentCollection.getMedia().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();

	        String fileName = (String)(pairs.getValue());
	         
	        if(fileIsVideo(fileName)){
	        	
	        	System.out.println("We have infact found a movie");
	        	System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        	
	        	videos.put((Integer)pairs.getKey(), new Movie(parent, fileName));
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
