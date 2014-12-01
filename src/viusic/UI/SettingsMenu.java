package viusic.UI;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import processing.core.PApplet;
import processing.core.PConstants;
import viusic.media.AudioCollection;
import viusic.media.SoundManager;
import controlP5.ControlP5;
import controlP5.Textfield;

public class SettingsMenu {

	PApplet parent;
	SoundManager sndM;
	ControlP5 cp5;

	float posX, posY, screenHeight, screenWidth;

	boolean mainTab = true, collectionTab = false, keyboardTab = false;

	// Collection Authoring Variables
	AudioCollection<Integer, String> newCollection;
	ArrayList<String> audioFilePaths = new ArrayList<>();
	ArrayList<String> videoFilePaths = new ArrayList<>();
	boolean creatingCollection = false, editingCollection = false,
			isSettingKey = false;
	String selectedPath;
	int indexSelected = -1;

	// File chooser stuff
	JFileChooser jfile = new JFileChooser();

	SettingsMenu(PApplet p, SoundManager s, int height, int width) {

		// PApplet and SoundManager instance
		parent = p;
		sndM = s;

		// Screen Size constants
		screenHeight = (float) height;
		screenWidth = (float) width;

		init();

	}

	private void init() {
		// cp5 stuff
		cp5 = new ControlP5(parent);

		// Settings menu position constants
		posY = screenHeight;
		posX = 40;

		// creating file browser in the home directory
		jfile.setCurrentDirectory(new File(System.getProperty("user.home")));

		cp5.setColorBackground(0xe1e1e1e1);
		cp5.setColorLabel(0x64646464);
		cp5.setColorForeground(0xffffffff);
		cp5.setColorActive(0x000000);

		// Text Field for New Collection
		cp5.addTextfield("collectionName").setSize(300, 25)
				.setFont(parent.createFont("arial", 12)).setFocus(false)
				.setVisible(false);
	}

	// Called to put the menu off screen when not in use
	public void resetMenuPosition() {
		posY = screenHeight;
		cp5.getController("collectionName").setVisible(false);
		mainTab = true;
		collectionTab = false;
		keyboardTab = false;
		creatingCollection = false;
		editingCollection = false;
		indexSelected = -1;
		selectedPath = "null";
	}

	// Where text field input arrives
	public void setNewCollectionName(String input) {
		newCollection.setCollectionName(input);
	}

	// Draws everything setting menu related
	public void drawWindow() {
		parent.stroke(0);
		parent.fill(100);
		parent.rect(posX, posY, 540.0f, (float) (screenHeight - posY));

		if (posY >= screenHeight - screenHeight / 1.2f)
			posY -= (screenHeight / 1.2f) / 15.0f;
		else {

			drawTabs();

			// Switching between tabs
			if (mainTab) {

			} else if (collectionTab) {

				// New Collection Button and text
				parent.fill(175);
				parent.stroke(0);
				parent.rect(posX + 40, posY + 45, 100, 25);

				parent.fill(0);
				parent.textAlign(PConstants.CENTER);
				parent.text("New Collection", posX + 90, posY + 62);

				// Edit Collection Button and text
				parent.fill(175);
				parent.stroke(0);
				parent.rect(posX + 165, posY + 45, 100, 25);

				parent.fill(0);
				parent.textAlign(PConstants.CENTER);
				parent.text("Edit Collection", posX + 215, posY + 62);

				// If edit or new has been pressed
				if (creatingCollection) {

					// Drawing Textfield for colletion name
					cp5.getController("collectionName").setPosition(posX + 36,
							posY + 100);
					cp5.getController("collectionName").setVisible(true);

					// Prints name of current Collection
					parent.fill(255);
					parent.textAlign(PConstants.CENTER);
					parent.text(newCollection.getCollectionName(), posX + 400,
							posY + 92);

					// Draws file paths in window
					parent.stroke(0);
					parent.fill(225);
					parent.rect(posX + 35, posY + 150, 470, 300);

					// Add sound Button
					parent.fill(175);
					parent.rect(posX + 35, posY + 465, 100, 25);
					parent.fill(0);
					parent.text("Add Sound", posX + 85, posY + 482);

					// Add Video Button
					parent.fill(175);
					parent.rect(posX + 150, posY + 465, 100, 25);
					parent.fill(0);
					parent.text("Add Video", posX + 200, posY + 482);

					// Set Key Button
					parent.fill(175);
					parent.rect(posX + 265, posY + 465, 100, 25);
					parent.fill(0);
					parent.text("Set Key", posX + 315, posY + 482);

					// Putting Paths into Databox (Name subject to change)
					float start = posY + 167;
					int count = 0;
					for (String path : audioFilePaths) {
						
						if(indexSelected == count){
							parent.noStroke();
							parent.fill(190);
							parent.rect(posX + 36, posY + 150 + count*(25), 469, 25);
						}
						
						parent.textAlign(PConstants.LEFT);
						parent.fill(0);
						parent.text(path, posX + 40, start + count * (25));
						
						count++;
					}

				} else if (editingCollection) {

				}
			} else if (keyboardTab) {

			} else {

			}
		}
	}

	// Draws tabs at top of menu
	// Allows user to cycle through specific settings menus
	private void drawTabs() {

		if (mainTab) {
			parent.rect(posX + 180, posY, 180, 25);
			parent.rect(posX + 360, posY, 180, 25);
		} else if (collectionTab) {
			parent.stroke(0);
			parent.rect(posX, posY, 180, 25);
			parent.stroke(0);
			parent.rect(posX + 360, posY, 180, 25);
		} else if (keyboardTab) {
			parent.stroke(0);
			parent.rect(posX, posY, 180, 25);
			parent.rect(posX + 180, posY, 180, 25);
		} else {

		}

		// Draw text in tabs
		parent.textAlign(PConstants.CENTER);
		parent.fill(255);
		parent.text("Main", posX + 90, posY + 17);
		parent.text("Collection", posX + 270, posY + 17);
		parent.text("Keyboard", posX + 450, posY + 17);
	}

	// Checks for mouse position when clicked in settings menu
	public void tabsClickCheck(int mouseX, int mouseY) {

		// Checking if any tab has been clicked
		if (mouseY > posY && mouseY < posY + 25) {
			if (mouseX > posX && mouseX < posX + 180) {
				mainTab = true;
				collectionTab = false;
				keyboardTab = false;
			} else if (mouseX > posX + 180 && mouseX < posX + 360) {
				mainTab = false;
				collectionTab = true;
				keyboardTab = false;
			} else if (mouseX > posX + 360 && mouseX < posX + 540) {
				mainTab = false;
				collectionTab = false;
				keyboardTab = true;
			}
		}

		// Checks for button clicks in each specific menu tab
		// Buttons using the imageButton class would render this useless
		if (mainTab) {

		} else if (collectionTab) {

			System.out.println("Inside Collection settings");
			System.out.println("size of audioArray " + audioFilePaths.size());

			// Mouse Position for click check on:

			// New Collection button
			// Edit Collection button
			if (mouseY > posY + 45 && mouseY < posY + 70) {
				if (mouseX > posX + 40 && mouseX < posX + 140) {
					creatingCollection = true;
					editingCollection = false;
					newCollection = new AudioCollection<Integer, String>(
							"untitled");

				} else if (mouseX > posX + 165 && mouseX < posX + 265) {
					editingCollection = true;
					creatingCollection = false;
				}
			}

			// Add Sound File
			// Add Video File
			if (mouseY > posY + 465 && mouseY < posY + 490) {
				if (mouseX > posX + 35 && mouseX < posX + 135) {

					// Adding filePath to audioPathsArray
					int result = jfile.showOpenDialog(new JFrame());
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jfile.getSelectedFile();
						System.out.println("Selected file: "
								+ selectedFile.getAbsolutePath());

						audioFilePaths.add(selectedFile.getAbsolutePath());
					}
				} else if (mouseX > 150 && mouseX < 250) {

					// Adding filePath to videoPathsArray
					int result = jfile.showOpenDialog(new JFrame());
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jfile.getSelectedFile();
						System.out.println("Selected file: "
								+ selectedFile.getAbsolutePath());

						videoFilePaths.add(selectedFile.getAbsolutePath());
					}
				} else if (mouseX > 265 && mouseX < 365) {
					isSettingKey = true;
				}
			}

			// Checking if path selected
			float start = posY + 150;
			int count = 0;
			for (String path : audioFilePaths) {


				if (mouseY > start + count * (25)
						&& mouseY < start + (count) * (25) + 25) {
					if (mouseX > posX + 35 && mouseX < posX + 505) {

						selectedPath = path;
						indexSelected = count;
						System.out.println("SelectedPath :: \n" + selectedPath);
						break;
					}
				}
				count++;
			}
		} else if (keyboardTab) {

		}
	}

	public boolean getIsSettingKey() {
		return isSettingKey;
	}

	public void setKey(char key) {
		if (selectedPath != null || !selectedPath.equals("null")) {
			System.out.println("set the new collection sound to the key, " + key);
			newCollection.set((int) key, selectedPath);
		}
		
		sndM.addCollection(newCollection);
		isSettingKey = false;
	}
}
