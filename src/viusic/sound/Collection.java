package viusic.sound;

import java.util.HashMap;

public class Collection<K, V> {
	private HashMap<K, V> sounds = new HashMap<K, V>();
	
	//Collection Info
	String collectionName;
	
	//Collection Menu Location
	int posX = 0, posY = 0;
	
	Collection(String name){
		collectionName = name;
	}
	
	public String getCollectionName(){
		return collectionName;
	}
	
	public void setCollectionName(String name){
		collectionName = name;
	}
	
	public V get(K key){
		return sounds.get(key);
	}
	
	public void set(K key, V value){
		sounds.put(key, value);
	}
	
	// Sets position of menu button, easier to check for mouseOver
	public void setMenuPosition(int x, int y){
		posX = x;
		posY = y;
	}
	
	/*
	 *  Returns true if mouse within bounds
	 *  Width = 200
	 *  Height = 40
	 *  posX and posY vary, passed by drawCollectionMenu() in ScreenManager	 
	 */
	public boolean isMouseOver(int mouseX, int mouseY){
		if(mouseX > posX && mouseX < posX+200){
			if(mouseY > posY && mouseY < posY+40){
				return true;
			}
		}
		
		return false;
	}
}
