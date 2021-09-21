/* Nicole Li
 * BaitPane.java
 */

package li.five;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class BaitPane extends StackPane {
	
	// Circle that shows what type of fish there is.
	private Circle baitCircle;
	
	// Text that shows the quantity of fish.
	private Text baitQuantity;
	
	// Position of the pane. -1 is left, 0 is center, and 1 is right.
	private int position;
	
	// A String for the color so I can just put "red" instead of a specific Color red.
	private String circleColor;
	
	// The number of fish of this type.
	private int quantity;

	// TODO: I don't remember how I got these numbers.
	// Distance between the centers of the bait circles.
	private static final int unitsX = 80;
	
	// Shift in positioning and the radius of the bait circle.
	private static final int offsetY = 0;
	private static final int offsetX = -30;
	private static final int radius = 15;
	
	// Offset of the number for it to be in the corner of the bait circle.
	private static final int offsetNumber = (int) (radius / 1.414);
	
	public BaitPane(String circleColor) {
		baitCircle = new Circle(radius);
		this.circleColor = circleColor;
		
		// Places and colors the bait circle.
		if (circleColor == "red") {
			baitCircle.setFill(Color.CRIMSON);
			baitCircle.setStroke(Color.DARKMAGENTA);
			this.position = 1;
		}
		else if (circleColor == "yellow") {
			baitCircle.setFill(Color.GOLD);
			baitCircle.setStroke(Color.CHOCOLATE);
			this.position = 0;
		}
		else {
			baitCircle.setFill(Color.LIGHTSKYBLUE);
			baitCircle.setStroke(Color.NAVY);
			this.position = -1;
		}
		
		// I would set the center coordinates, but that messes up the positioning.
		setTranslateX((position * unitsX) + offsetX - 45);
		setTranslateY(offsetY);
		
		// Gets the most recent fish quantity for the Text.
		baitQuantity = new Text();
		updateBaitQuantity();

		// Puts the number on the circle, positions it, and styles it.
		getChildren().addAll(baitCircle, baitQuantity);
		baitQuantity.setTranslateX(offsetNumber);
		baitQuantity.setTranslateY(offsetNumber);
		baitQuantity.setStyle("-fx-font: 20 arial;");
		
		// When clicked, begin the waiting process for a fish with the same color as
		// the circle clicked.
		addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 setVisible(false);
		    	 Fisherman.startFishing(circleColor);
		     }
		});
	}
	
	// Gets the most recent fish quantity and updates the Text to match it.
	public void updateBaitQuantity() {
		quantity = FileManager.getQuantity(circleColor);
		baitQuantity.setText(String.valueOf(this.quantity));
	}
}
