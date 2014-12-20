package viusic.media;

import java.util.ArrayList;

import viusic.main.ViusicMain;

public class Profile {
	
	public ArrayList<Collection<Integer, String>> collections;
	public ArrayList<Loop> loops;
	private Integer currentCollectionIndex;
	
	
	
	public Profile(){
		
		collections = new ArrayList<Collection<Integer, String>>();
		currentCollectionIndex = 0;
		loops = new ArrayList<Loop>();
		
	}


	public void applyProfileToProgram(ViusicMain main) {
		
		//This line of code is what makes loops not null
		//when we are saving this class to the profile
		
		//main.sndM.loops = loops;
		main.collections = collections;		
		main.setCurrentCollection(currentCollectionIndex);
	}


	public void applyProgramToProfile(ViusicMain main) {
		//this.loops = main.getLoops();
		System.out.println(loops);
		currentCollectionIndex = main.currentCollectionIndex;

	}

}
