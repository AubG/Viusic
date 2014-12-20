package viusic.media;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import viusic.main.ViusicMain;


public class JsonLoader {

	PrintWriter writer;
	BufferedReader reader;
	ViusicMain main;
	Profile profile;
	public JsonLoader(ViusicMain main){
		this.main = main;
		profile = new Profile();
		profile.collections = main.getCollections();
		System.out.println(main.getCollections());

		try {
			reader = new BufferedReader(new FileReader("saveData/saveFile.json"));
			readAndBuild();
		} catch (FileNotFoundException e) {
			
			initializeProfile();
			System.out.println("oauhsnaotouaoeusaoeus");
		}
		
		
		
	}
	
	private void initializeProfile() {
		System.out.println("Initializing the file");
		File file = new File("saveData/saveFile.json");
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.flush();
		writer.close();
		
		
		
	}

	public void readAndBuild(){
		Json json = new Json();
		if(profile == null){
			profile = new Profile();
		}
		try {
			reader = new BufferedReader(new FileReader("saveData/saveFile.json"));
		} catch (FileNotFoundException e) {
			
			initializeProfile();
			System.out.println("oauhsnaotouaoeusaoeus");
		}
		
		try {
			String fileContent = reader.readLine();
			profile = json.fromJson(Profile.class, fileContent);
			profile.applyProfileToProgram(main);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void saveProfile(){
		
		profile.applyProgramToProfile(main);

		Json json = new Json();
		String fileContent = json.toJson(profile);
		System.out.println(fileContent + " aoeuoe");
		
		File file = new File("saveData/saveFile.json");
		try {
			writer = new PrintWriter(file);
		} catch (FileNotFoundException e) {

		}
		writer.print(fileContent);
		
		writer.flush();
		writer.close();

		/*FileHandle handle = Gdx.files.local("profile.json");
		handle.writeString(fileContent, false);*/
		
	}
}
