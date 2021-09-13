/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * Fisherman.java
 */

package li.five;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Fisherman {
	
	private static int fisherWidth = 300;
	private static int fisherHeight = 300;
	private static int buttonDiameter = fisherWidth / 5;
	
	// Offsets for both the fisherman and the button to be above the taskbar and closer
	// to the right edge.
	private int fisherOffsetX = 5;
	private int fisherOffsetY = -175;
	
	// Offsets for the button to be placed over the fisherman's head.
	private int buttonOffsetX = (fisherWidth / 10) + (buttonDiameter / 2) + 45;
	private int buttonOffsetY = (fisherWidth / 10) + (buttonDiameter / 2);
	
	// Button for when a fish is caught.
	private static Image buttonFishGreen = new Image("\\li\\five\\buttonFishGreen.png",
	buttonDiameter, buttonDiameter, true, false);
	
	/* Unused for now
	private static Image buttonFishWhite = new Image("\\li\\five\\buttonFishWhite.png", 
	buttonDiameter, buttonDiameter, true, false);*/
	
	// Button to click to start fishing.
	private static Image buttonHook = new Image("\\li\\five\\buttonHook.png", 
	buttonDiameter, buttonDiameter, true, false);
	
	// Standing fisherman.
	private static Image standing = new Image("\\li\\five\\standing.png", fisherWidth, 
	fisherHeight, true, false);
	
	// Pulling fisherman.
	private static Image pulling = new Image("\\li\\five\\pulling.png", fisherWidth, 
	fisherHeight, true, false);
	
	// Fishing fisherman.
	private static Image fishing = new Image("\\li\\five\\fishing.png", fisherWidth, 
	fisherHeight, true, false);
	
	private static ImageView fishermanView = new ImageView(standing);
	private static ImageView buttonView = new ImageView(buttonHook);
	
	// This box is placed in the upper section of fishermanAndButtonBox.
	// It is used to center the button.
	private HBox buttonBox = new HBox(buttonView);

	// This box is placed in the right section of bottomOfBackgroundPaneBox.
	// It is used to place the button on top of the fisherman.
	private VBox fishermanAndButtonBox = new VBox(buttonBox, fishermanView);
	
	// This box is placed in the bottom section of backgroundPane.
	// It is used to place the fisherman and button to the right.
	private HBox bottomOfBackgroundPaneBox = new HBox(fishermanAndButtonBox);
	
	// Panes to show the types and amounts of fish.
	private static BaitPane blueBaitPane = new BaitPane("blue");
	private static BaitPane yellowBaitPane = new BaitPane("yellow");
	private static BaitPane redBaitPane = new BaitPane("red");
	
	public Fisherman() {
		buttonBox.setTranslateX(buttonOffsetX);
		buttonBox.setTranslateY(buttonOffsetY);
		fishermanAndButtonBox.setTranslateX(fisherOffsetX);
		fishermanAndButtonBox.setTranslateY(fisherOffsetY);
		
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		bottomOfBackgroundPaneBox.setAlignment(Pos.BOTTOM_RIGHT);
		
		buttonBox.getChildren().add(redBaitPane);
   	    buttonBox.getChildren().add(yellowBaitPane);
   	    buttonBox.getChildren().add(blueBaitPane);
   	    redBaitPane.setVisible(false);
   	    yellowBaitPane.setVisible(false);
   	    blueBaitPane.setVisible(false);
		
   	    // If the button to fish is clicked, it disappears and the BaitPanes show up.
   	    // If the button to select a bait type is clicked, it begins the fishing
   	    // minigame.
		buttonView.addEventHandler(MouseEvent.MOUSE_CLICKED, 
		new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 if (buttonView.getImage() == buttonHook) {
			    	 buttonView.setVisible(false);
			    	 redBaitPane.setVisible(true);
			    	 yellowBaitPane.setVisible(true);
			    	 blueBaitPane.setVisible(true);
		    	 }
		    	 else {
		    		 fishermanView.setImage(pulling);
		    		 buttonView.setVisible(false);
		    		 Main.startMinigame();
		    	 }
		     }
		});
	}
	
	// Fishing waiting process.
	public static void startFishing(String baitColor) {
		blueBaitPane.setVisible(false);
		redBaitPane.setVisible(false);
		yellowBaitPane.setVisible(false);
		
		Task<Void> startFishingTask = new Task<Void>() {
			
	         @Override
	         protected Void call() throws Exception {
	        	 
	        	 // Pulling rod back and begin fishing.
	        	 fishermanView.setImage(pulling);
	        	 Thread.sleep(200);
	        	 fishermanView.setImage(fishing);

	        	 // Waiting for a fish.
	        	 if (baitColor == "blue") {
	        		Fish.setCurrentFishNum(0);
	     			Thread.sleep(3000);
	     		 }
	        	 else if (baitColor == "yellow") {
	        		 Fish.setCurrentFishNum(1);
	        		 Thread.sleep(4000);
	        	 }
	        	 else {
	        		 Fish.setCurrentFishNum(2);
	        		 Thread.sleep(5000);
	        	 }
	        	 
	        	 // A fish has been caught and can be pulled in.
	        	 buttonView.setVisible(true);
	        	 buttonView.setImage(buttonFishGreen);
	             return null;
	         }
	     };
	     Thread startFishingThread = new Thread(startFishingTask);
	     startFishingThread.setDaemon(true);
	     startFishingThread.start();
	}
	
	public HBox getBoat() {
		return bottomOfBackgroundPaneBox;
	}
	
	public void resetImageView() {
		fishermanView.setImage(standing);
	}
	
	public void showFishingButton() {
		buttonView.setImage(buttonHook);
		buttonView.setVisible(true);
		blueBaitPane.updateBaitQuantity();
		yellowBaitPane.updateBaitQuantity();
		redBaitPane.updateBaitQuantity();
	}
}