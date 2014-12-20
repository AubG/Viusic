package viusic.media;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Collection<Integer, String> {
	public Collection(){
		
	}
	private HashMap<Integer, String> media = new HashMap<Integer, String>();

	// Collection Info
	String collectionName;

	// Collection Menu Location
	int posX = 0, posY = 0;

	public Collection(String name) {
		collectionName = name;
	}

	public HashMap<Integer, String> getMedia() {
		return media;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String name) {
		collectionName = name;
	}

	public String get(Integer key) {
		return media.get(key);
	}

	public void set(Integer key, String value) {
		media.put(key, value);
	}
	
	public void delete(Integer key){
		media.remove(key);
	}

	public void changeKey(Integer oldKey, Integer newKey, String value) {
		media.remove(oldKey);
		media.put(newKey, value);
	}

	// Sets position of menu button, easier to check for mouseOver
	public void setMenuPosition(int x, int y) {
		posX = x;
		posY = y;
	}

	/*
	 * Returns true if mouse within bounds Width = 200 Height = 40 posX and posY
	 * vary, passed by drawCollectionMenu() in ScreenManager
	 */
	public boolean isMouseOver(int mouseX, int mouseY) {
		if (mouseX > posX && mouseX < posX + 200) {
			if (mouseY > posY && mouseY < posY + 40) {
				return true;
			}
		}

		return false;
	}

	public int getCollectionLength() {
		return media.size();
	}

	public Integer getKey(String path) {
		// Iterator through the HashMap
		Iterator it = media.entrySet().iterator();
		while (it.hasNext()) {
			// Contains the key and the filePath to the video
			Map.Entry pairs = (Map.Entry) it.next();

			// filePath
			String fileName = (String) (pairs.getValue());
			
			if(path.equals(fileName)){
				return (Integer) pairs.getKey();
			}
		}
		
		return null;
	}
}
