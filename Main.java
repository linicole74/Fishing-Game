/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * Main.java
 */

package li.five;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;  


public class Main extends Application {
	
	// Pane that contains all of the other panes.
	private static BorderPane backgroundPane;
	
	// Pane to hold the minigame; starts out invisible.
	private static Minigame minigameBox;
	
	// Pane to hold the fisherman and related buttons.
	private static Fisherman fisherman;
	
	private static Scene scene;

    @Override
    public void start(Stage stage) {
    	
    	// background is transparent and covers the whole screen.
       	stage.initStyle(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        stage.setMaximized(true);
        
        
        // Get the text file's data for fish quantities.
        FileManager.setup();
        
        minigameBox = new Minigame();
        fisherman = new Fisherman();
        backgroundPane = new BorderPane();
        
        // Positioning the two panes.
        scene = new Scene(backgroundPane);
        backgroundPane.setTop(minigameBox);
        backgroundPane.setBottom(fisherman.getBoat());
        backgroundPane.setPickOnBounds(false);
        backgroundPane.getChildren().add(minigameBox.getHandle());
        
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
        
        // When the mouse moves, move the handle if the minigame is active.
        backgroundPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
            	if (minigameBox.getIsActivated()) {
            		minigameBox.moveHandle(event.getSceneX(), event.getSceneY());
            	}
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    // Launch the minigame.
    public static void startMinigame() {
    	Task<Void> startFishingTask = new Task<Void>() {
			
	         @Override
	         protected Void call() throws Exception {
	         	 return null;
	         }
	         
	         // succeeded is called on the application Thread, while call is not, and
	         // I need the following to be on the application Thread because it changes
	         // UI elements.
	         @Override
	         protected void succeeded() {
	             super.succeeded();
	             
	             // Creates a fishing minigame.
	             minigameBox.activate();
	             Fish fish = null;
	             switch (Fish.getCurrentFishNum()) {
	             case 0:
	            	 fish = new BlueFish();
	            	 break;
	             case 1:
	            	 fish = new YellowFish();
	            	 break;
	         	 default:
	         		 fish = new RedFish();
	         		 break;
	             }
	             
	             // Sets fields for when the minigame finishes and Fish needs to use
	             // their functions.
	             Fish.setCurrentFish(fish);
	             Fish.setCurrentMinigame(minigameBox);
	             Fish.setCurrentFisherman(fisherman);
	             
	             // Positions the fish and tells it the distance to the dial and the
	             // distance to the arc.
	             fish.setPosition(minigameBox.getAbsoluteCenterX(), 
	             minigameBox.getAbsoluteCenterY() + minigameBox.getHeight() / 4);
	             fish.setMaxMin(minigameBox.getHeight() / 2.2, 
	             minigameBox.getHeight() / 6);
	             
	             backgroundPane.getChildren().add(fish);
	             
	             // Starts the minigame.
	             fish.move();
	        }
    	};
    	
    	Thread startFishingThread = new Thread(startFishingTask);
    	startFishingThread.setDaemon(true);
    	startFishingThread.start();
    }
}