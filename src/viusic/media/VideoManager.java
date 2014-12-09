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
	
	//Initialize the videos HashMap, the playing video Keys
	public VideoManager(ViusicMain parent) {
		videos = new HashMap<Integer, Movie>();
		playingVideoKeys = new ArrayList<Integer>();
		this.parent = parent;

	}
	
	//Called every frame to play the video
	public void draw(){
		for(int i = 0; i < playingVideoKeys.size(); i++){
			//plays the video image at a time
			parent.image(videos.get(playingVideoKeys.get(i)), 0, 0);
		}
	}

	//Returns true if the videos hashmap contained the key
	public boolean playVideo(Integer key, boolean fromUser) {
		
		if(videos.containsKey(key)){
			videos.get(key);
			return true;
		}
		
		//Sorry no video founde
		return false;
	}

	/**
	 * This method grabs each video in the currentCollection and builds up the videos HashMap.
	 * This currently is capable of .MP4's and .MOV's. it.remove() was really gay.
	 * @param currentCollection
	 */
	public void grabVideos(Collection<Integer, String> currentCollection) {
		videos.clear();
		playingVideoKeys.clear();
		
		//Iterator through the HashMap
		Iterator it = currentCollection.getMedia().entrySet().iterator();
	    while (it.hasNext()) {
	    	//Contains the key and the filePath to the video
	        Map.Entry pairs = (Map.Entry)it.next();

	        //filePath
	        String fileName = (String)(pairs.getValue());
	         
	        //Assures this file is a video
	        if(fileIsVideo(fileName)){
	        	
	        	System.out.println("We have infact found a movie");
	        	System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        	
	        	//pushes another video into the videos HashMap
	        	videos.put((Integer)pairs.getKey(), new Movie(parent, fileName));
	        }
	        // avoids a ConcurrentModificationException
	        // also destroys our hashmap and causes everything to be null
	        // therefore fuck that shit. Spent too long trying to debug that bitch.
	        //
	        // --->  it.remove();  <---
	    }
	}
	
	//Returns true if the string is an .mov or a .mp4
	private boolean fileIsVideo(String string) {
		if(string.endsWith(".mov") || string.endsWith(".MOV") || string.endsWith(".mp4") || string.endsWith(".MP4"))
			return true;
		
		return false;
	}

}
