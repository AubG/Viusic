package viusic.UI;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.video.Movie;
import viusic.main.ViusicMain;
import viusic.media.Collection;
import viusic.media.SoundManager;
import controlP5.ControlP5;
import controlP5.Textfield;

public class SettingsMenu {

	ViusicMain parent;
	SoundManager sndM;
	ControlP5 cp5;
	ScreenManager screenManager;

	// File chooser stuff
	JFileChooser jfile = new JFileChooser();

	// Collection Authoring Variables
	private Collection<Integer, String> newCollection;
	private Collection<Integer, String> selectedCollection;
	private ArrayList<String> filePaths = new ArrayList<>();

	String selectedPath;
	Integer selectedKey;
	int indexSelected = -1;
	float posX, posY, screenHeight, screenWidth;
	int currentPageNumber;
	

	boolean mainTab = true, collectionTab = false, keyboardTab = false;
	private boolean creatingCollection = false;
	private boolean isSettingKey = false;
	private boolean selectingCollection = false;
	private boolean collectionSelected = false;
	private boolean editingCollection = false;

	SettingsMenu(ViusicMain p, SoundManager s, ScreenManager screenManager,
			int height, int width) {

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

				} else if (selectingCollection) {

					if (editingCollection) {
						// Add logic for once collection has been selected
						drawEditingCollection();
					} else {
						// If no collection selected draw the collection
						// selection menu
						drawSelectingCollection();
					}
				}

				// KEYBOARDTAB SELECTED
			} else if (keyboardTab) {

			}
		}
	}

	// Draws collection creation stuff
	private void drawCollectionCreation() {

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

	private void drawEditingCollection() {

		// Prints name of current Collection
		parent.fill(255);
		parent.textAlign(PConstants.CENTER);
		parent.text(selectedCollection.getCollectionName(), posX + 438,
				posY + 117);

		// Draws file paths in window
		parent.stroke(0);
		parent.fill(225);
		parent.rect(posX + 35, posY + 150, 470, 300);

		// Putting Paths into Databox (Name subject to change)
		float start = posY + 167;
		int count = 0;
		Integer tempKey;
		// Iterator through the HashMap
		Iterator it = selectedCollection.getMedia().entrySet().iterator();
		while (it.hasNext()) {
			// Contains the key and the filePath to the video
			Map.Entry pairs = (Map.Entry) it.next();

			// filePath
			String fileName = (String) (pairs.getValue());
			char file[] = new char[40];
			fileName.getChars(fileName.length() - 40, fileName.length(), file,
					0);
			fileName = String.valueOf(file);

			if (indexSelected == count) {
				parent.noStroke();
				parent.fill(190);
				parent.rect(posX + 36, posY + 150 + count * (25), 469, 25);
				
				selectedPath = (String)(pairs.getValue());
				selectedKey = (Integer)(pairs.getKey());
			}

			parent.textAlign(PConstants.LEFT);
			parent.fill(0);
			parent.text(fileName, posX + 40, start + count * (25));

			tempKey = (Integer) pairs.getKey();
			if (tempKey != null) {
				parent.textAlign(PConstants.CENTER);
				parent.text("" + (char) tempKey.intValue(), posX + 470, start
						+ count * (25));
			}

			count++;
		}
	}

	// Draws edit collection selection
	private void drawSelectingCollection() {

		// Draws collection window
		parent.stroke(0);
		parent.fill(225);
		parent.rect(posX + 270, posY + 150, 235, 300);

		float start = posY + 167;
		for (int i = 0; i < parent.getCollectionLength(); i++) {

			if (indexSelected == i) {
				parent.noStroke();
				parent.fill(190);
				parent.rect(posX + 271, posY + 151 + i * (25), 234, 25);
			}

			String name = parent.getCollection(i).getCollectionName();
			parent.textAlign(PConstants.CENTER);
			parent.fill(0);
			parent.text(name, posX + 387, start + i * (25));
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

			// COLLECTION CREATION PATH SELECTION
			if (creatingCollection) {
				// Checking if path selected
				float start = posY + 150;
				int count = 0;
				for (String path : filePaths) {

					if (mouseY > start + count * (25)
							&& mouseY < start + (count) * (25) + 25) {
						if (mouseX > posX + 35 && mouseX < posX + 505) {

							selectedPath = path;
							indexSelected = count;
							System.out.println("SelectedPath :: \n"
									+ selectedPath);
							break;
						}
					}
					count++;
				}
			}
			// EDITTING COLLECTION, COLLECTION SELECTION
			else {
				
				//Selecting file from collection edit
				if (editingCollection) {
					
					float start = posY + 150;
					for (int i = 0; i < selectedCollection.getCollectionLength(); i++) {

						if (mouseY > start + i * (25)
								&& mouseY < start + (i) * (25) + 25) {
							if (mouseX > posX + 35 && mouseX < posX + 505) {
								
								indexSelected = i;
								break;
							}
						}
					}
				} 
				// Selecting collection from collection selection menus
				else {

					float start = posY + 150;
					for (int i = 0; i < parent.getCollectionLength(); i++) {

						if (mouseY > start + i * (25)
								&& mouseY < start + (i) * (25) + 25) {
							if (mouseX > posX + 270 && mouseX < posX + 505) {

								selectedCollection = parent.getCollection(i);
								collectionSelected = true;
								indexSelected = i;
								System.out.println("SelectedCollection :: \n"
										+ parent.getCollection(i)
												.getCollectionName());
								break;
							}
						}
					}
				}
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

					newCollection = new Collection<Integer, String>("untitled");

					editingCollection = false;
					selectingCollection = false;
					stopEditingCollection();
					creatingCollection = true;
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
				stopCreatingCollection();
				stopEditingCollection();
				selectingCollection = true;
				startCollectionSelection();
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

	private void startCollectionSelection() {

		ImageButton editCollection = new ImageButton(parent, "Edit collection",
				((int) posX + 85), ((int) posY + 150), 100, 25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				if (collectionSelected) {
					editingCollection = true;
					stopEditingCollection();
					startCollectionEditing();
				}
			}
		};

		ImageButton deleteCollection = new ImageButton(parent,
				"Delete collection", ((int) posX + 85), ((int) posY + 250),
				100, 25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				System.out.println("Pressed the Delete, shieeet");
			}
		};

		screenManager.addButton(editCollection);
		screenManager.addButton(deleteCollection);
	}

	private void startCollectionEditing() {
		// Work on shiet

		// Drawing Text field for collection name
		cp5.getController("enter collection name").setPosition(posX + 36,
				posY + 100);
		cp5.getController("enter collection name").setVisible(true);

		// Add buttons for set key, loadFile, delete and save collection
		ImageButton addFile = new ImageButton(parent, "Add File", 75, 550, 100,
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

		ImageButton deleteFile = new ImageButton(parent, "Delete File", 195,
				550, 100, 25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				// Add logic to delete specified file (media)
				// get remove key from collection and thus removes that filePaths
				
			}
		};

		ImageButton setKey = new ImageButton(parent, "Set Key", 315, 550, 100,
				25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				isSettingKey = true;
			}
		};

		ImageButton saveCollection = new ImageButton(parent, "Save Collection",
				435, 550, 100, 25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				parent.addCollection(newCollection);
			}
		};

		screenManager.addButton(addFile);
		screenManager.addButton(deleteFile);
		screenManager.addButton(setKey);
		screenManager.addButton(saveCollection);
	}

	private void stopEditingCollection() {

		// Removes collection edit selection stuff
		if (selectingCollection && editingCollection) {

			screenManager.removeImageButton("Edit collection");
			screenManager.removeImageButton("Delete collection");
		}
		// Remove editingCollection stuff
		else if (!editingCollection && selectingCollection) {
			cp5.getController("enter collection name").setVisible(false);
		}
		// Removes everything!
		else {

			screenManager.removeImageButton("Edit collection");
			screenManager.removeImageButton("Delete collection");
			cp5.getController("enter collection name").setVisible(false);
			selectingCollection = false;
			collectionSelected = false;
			editingCollection = false;
		}
	}

	private void setUpCollectionMenu() {
		ImageButton addFile = new ImageButton(parent, "Add File", 75, 550, 100,
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

		screenManager.addButton(addFile);

		ImageButton setKey = new ImageButton(parent, "Set Key", 195, 550, 100,
				25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				isSettingKey = true;
			}
		};
		screenManager.addButton(setKey);

		ImageButton saveCollection = new ImageButton(parent, "Save Collection",
				315, 550, 100, 25, 1) {
			@Override
			public void onMousePress(int x, int y) {
				parent.addCollection(newCollection);
			}
		};
		screenManager.addButton(saveCollection);

		// Drawing Textfield for colletion name
		cp5.getController("enter collection name").setPosition(posX + 36,
				posY + 100);
		cp5.getController("enter collection name").setVisible(true);
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
		stopEditingCollection();
	}
}
