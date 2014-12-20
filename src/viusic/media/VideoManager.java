package viusic.media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import processing.core.PConstants;
import processing.video.Movie;
import viusic.effects.Tint.Tint;
import viusic.main.ViusicMain;

public class VideoManager {

	private HashMap<Integer, Movie> videos;
	ArrayList<Integer> playingVideoKeys;
	ArrayList<Integer> stoppedVideoKeys;
	ViusicMain parent;
	
	Tint tint;
	
	//Initialize the videos HashMap, the playing video Keys
	public VideoManager(ViusicMain parent) {
		videos = new HashMap<Integer, Movie>();
		playingVideoKeys = new ArrayList<Integer>();
		stoppedVideoKeys = new ArrayList<Integer>();
		this.parent = parent;
		
		// Only call this setup(); if using triggerable videoEffects
//		setup();
	}
	
	// Only contains video effect setup
	public void setup(){
		// initialize Tint values
		tint = new Tint(parent, PConstants.HSB, 255, 75, 255, 100);
	}
	
	//Called every frame to play the video
	public void draw(){
		for(int i = 0; i < playingVideoKeys.size(); i++){
			
			//plays the video image at a time
			if(!stoppedVideoKeys.contains(playingVideoKeys.get(i)))
				parent.image(videos.get(playingVideoKeys.get(i)), 0, 0); 
		}
	}

	//Returns true if the videos hashmap contained the key
	public boolean playVideo(Integer key, boolean fromUser) {
		
		if(videos.containsKey(key)){
			
			if(!stoppedVideoKeys.contains(key)){
				videos.get(key).pause();;
				stoppedVideoKeys.add(key);
				return true;
			} else {
				videos.get(key).loop();
				stoppedVideoKeys.remove(key);
				return true;
			}
		}
		
		//Returns false if no video was found
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
	        	
	        	int temp = 0;
				if(pairs.getKey() instanceof String){
					System.out.println("astring");
					temp = Integer.parseInt((String) pairs.getKey());
				}else{
					temp = (Integer)pairs.getKey();
					System.out.println(pairs.getKey());
				}
	        	System.out.println("We have infact found a movie");
	        	
	        	//pushes another video into the videos HashMap
	        	videos.put(temp, new Movie(parent, fileName));
	        	playingVideoKeys.add(temp);
	        	stoppedVideoKeys.add(temp);
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
