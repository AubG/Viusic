package viusic.UI;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import processing.core.PApplet;
import processing.core.PConstants;
import viusic.main.ViusicMain;
import viusic.media.Collection;
import viusic.media.SoundManager;
import controlP5.ControlP5;
import controlP5.Textfield;

public class SettingsMenu {

	ViusicMain parent;
	SoundManager sndM;
	ControlP5 cp5;

	float posX, posY, screenHeight, screenWidth;

	boolean mainTab = true, collectionTab = false, keyboardTab = false;

	// Collection Authoring Variables
	Collection<Integer, String> newCollection;
	ScreenManager screenManager;
	ArrayList<String> filePaths = new ArrayList<>();
	boolean creatingCollection = false;
	boolean editingCollection = false;
	boolean isSettingKey = false;
	String selectedPath;
	int indexSelected = -1;

	// File chooser stuff
	JFileChooser jfile = new JFileChooser();
	private boolean selectedCollection;

	SettingsMenu(ViusicMain p, SoundManager s,
			ScreenManager screenManager, int height, int width) {

		// PApplet and SoundManager instance
		parent = p;
		this.screenManager = screenManager;
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

		cp5.setColorBackground(0x000000);
		cp5.setColorForeground(0xffffffff);
		cp5.setColorActive(0xffffffff);

		// Text Field for New Collection
		cp5.addTextfield("enter collection name").setSize(300, 25)
				.setFont(parent.createFont("arial", 12)).setFocus(false)
				.setVisible(false);
	}

	// Called to put the menu off screen when not in use
	public void resetMenuPosition() {
		posY = screenHeight;
		cp5.getController("enter collection name").setVisible(false);
		mainTab = true;
		collectionTab = false;
		keyboardTab = false;
		creatingCollection = false;
		editingCollection = false;
		indexSelected = -1;
		selectedPath = "null";
		filePaths.clear();
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

		/*
		 * Draws the transition from no settings menu to fully up Logic for
		 * transition is in this first if the else handles drawing everything
		 * post animation
		 */
		if (posY >= screenHeight - screenHeight / 1.2f)
			posY -= (screenHeight / 1.2f) / 15.0f;
		else {

			// Draws tabs for selection
			drawTabs();

			// Switching between tabs
			// MAINTAB SELECTED
			if (mainTab) {

			} else if (collectionTab) {

				// If edit or new has been pressed
				if (creatingCollection) {

					// Draw collection creation stuff
					drawCollectionCreation();

				} else if (editingCollection) {

					if (selectedCollection) {
						// Add logic for once collection has been selected
					} else {
						// If no collection selected draw the collection
						// selection menu
						drawEditCollectionSelection();
					}
				}

				// KEYBOARDTAB SELECTED
			} else if (keyboardTab) {

			}
		}
	}

	// Draws collection creation stuff
	private void drawCollectionCreation() {

		// Drawing Textfield for colletion name
		cp5.getController("enter collection name").setPosition(posX + 36,
				posY + 100);
		cp5.getController("enter collection name").setVisible(true);

		// Prints name of current Collection
		parent.fill(255);
		parent.textAlign(PConstants.CENTER);
		parent.text(newCollection.getCollectionName(), posX + 438, posY + 117);

		// Draws file paths in window
		parent.stroke(0);
		parent.fill(225);
		parent.rect(posX + 35, posY + 150, 470, 300);

		// Putting Paths into Databox (Name subject to change)
		float start = posY + 167;
		int count = 0;
		Integer tempKey;
		for (String path : filePaths) {

			if (indexSelected == count) {
				parent.noStroke();
				parent.fill(190);
				parent.rect(posX + 36, posY + 150 + count * (25), 469, 25);
			}

			parent.textAlign(PConstants.LEFT);
			parent.fill(0);
			parent.text(path, posX + 40, start + count * (25));

			tempKey = newCollection.getKey(path);
			if (tempKey != null) {
				parent.textAlign(PConstants.CENTER);
				parent.text("" + (char) tempKey.intValue(), posX + 470, start
						+ count * (25));
			}

			count++;
		}

	}

	// Draws edit collection selection
	private void drawEditCollectionSelection() {
		// Draws collection window
		parent.stroke(0);
		parent.fill(225);
		parent.rect(posX + 35, posY + 150, 470, 300);
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

			// MainTab pressed
			if (mouseX > posX && mouseX < posX + 180) {
				mainTab = true;
				collectionTab = false;
				keyboardTab = false;

				stopCollectionTab();
				stopKeyboardTab();
				startMainTab();

			}
			// CollectionTab pressed
			else if (mouseX > posX + 180 && mouseX < posX + 360) {
				mainTab = false;
				collectionTab = true;
				keyboardTab = false;

				stopKeyboardTab();
				stopMainTab();
				startCollectionTab();

			}
			// KeyboardTab pressed
			else if (mouseX > posX + 360 && mouseX < posX + 540) {
				mainTab = false;
				collectionTab = false;
				keyboardTab = true;

				stopCollectionTab();
				stopMainTab();
				startKeyboardTab();
			}
		}

		// Checks for button clicks in each specific menu tab
		// Buttons using the imageButton class would render this useless
		if (mainTab) {

		} else if (collectionTab) {

			// Checking if path selected
			float start = posY + 150;
			int count = 0;
			for (String path : filePaths) {

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

	private void startMainTab() {
		// TODO Auto-generated method stub

	}

	private void startKeyboardTab() {

	}

	private void startCollectionTab() {

		ImageButton collectionButton = new ImageButton(parent,
				"New Collection", (int) (posX + 60), 140, 200, 25, 1) {
			@Override
			public void onMousePress(int x, int y) {

				// CREATES CREATION COLLECTION MENU
				if (!creatingCollection) {
					creatingCollection = true;
					editingCollection = false;
					newCollection = new Collection<Integer, String>("untitled");
					setUpCollectionMenu();

				}

			}
		};
		screenManager.addButton(collectionButton);

		screenManager.addButton(new ImageButton(parent, "Edit Collection",
				(int) (posX + 280), 140, 200, 25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				super.onMousePress(x, y);

				editingCollection = true;
				stopCreatingCollection();
			}
		});
	}

	public void stopCreatingCollection() {
		if (creatingCollection) {
			screenManager.removeImageButton("Add File");
			screenManager.removeImageButton("Set Key");
			screenManager.removeImageButton("Save Collection");
			cp5.getController("enter collection name").setVisible(false);
		}

		creatingCollection = false;
	}

	
	private void setUpCollectionMenu() {
		ImageButton blank = new ImageButton(parent, "Add File", 75, 550, 100,
				25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				// Adding filePath to audioPathsArray
				int result = jfile.showOpenDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfile.getSelectedFile();
					System.out.println("Selected file: "
							+ selectedFile.getAbsolutePath());
					filePaths.add(selectedFile.getAbsolutePath());
				}
			}
		};

		screenManager.addButton(blank);

		ImageButton setKey = new ImageButton(parent, "Set Key", 195, 550, 100,
				25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				isSettingKey = true;
			}
		};
		screenManager.addButton(setKey);

		ImageButton save = new ImageButton(parent, "Save Collection", 315, 550,
				100, 25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				parent.addCollection(newCollection);
			}
		};
		screenManager.addButton(save);

	}

	public boolean getIsSettingKey() {
		return isSettingKey;
	}

	public void setKey(char key) {
		if (selectedPath != null) {

			System.out.println(key + " = " + selectedPath);

			newCollection.set((int) key, selectedPath);
		}

		isSettingKey = false;
	}

	public void setSoundManager(SoundManager s) {
		this.sndM = s;
	}

	public void stopSetMenu() {
		stopCollectionTab();
		stopMainTab();
		stopKeyboardTab();
	}

	private void stopMainTab() {

	}

	private void stopKeyboardTab() {

	}

	private void stopCollectionTab() {
		screenManager.removeImageButton("New Collection");
		screenManager.removeImageButton("Edit Collection");
		stopCreatingCollection();
	}
}
