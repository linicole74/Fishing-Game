/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * Dial.java
 */

package li.five;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Dial extends StackPane implements IMinigameComponent {
	
	// Radius of the dial circle (only the part with the image; does not include the
	// border), radius of the dot markings, and radius of the circle that is used as
	// the border for the dial circle.
	private static int dialCircleRadius = 100;
	private static int dotRadius = 4;
	private static int borderCircleRadius = dialCircleRadius + (dotRadius * 2);
	
	// The dial circle and the circle that acts as its border.
	private static Circle dialCircle = new Circle(dialCircleRadius, 
	dialCircleRadius, dialCircleRadius);
	private static Circle borderCircle = new Circle(dialCircleRadius,
	dialCircleRadius, borderCircleRadius);
	
	// Picture of the ocean and sky and an ImageView for it.
	private static Image surface = new Image("\\li\\five\\surface.png", 
	dialCircleRadius * 2, dialCircleRadius * 2, true, false);
	private static ImageView surfaceView = new ImageView(surface);
	
	// This controls the dots.
	private DotManager dm = new DotManager(dotRadius);
	
	// Color of the borderCircle. This is declared here because dm needs it.
	private static Color borderCircleColor = Color.SLATEGRAY;
	
	public Dial() {
		
		// Adds the picture of the ocean and sky to the dial circle.
		surfaceView.setClip(dialCircle);
		
		borderCircle.setFill(Color.SLATEGRAY);
		
		// Positions the dm.
		dm.getDotPane().setTranslateX(borderCircleRadius);
		dm.getDotPane().setTranslateY(borderCircleRadius);
		
		// Adds the circles and the dots to the dial.
		getChildren().addAll(borderCircle, surfaceView, dm.getDotPane());
		
		deactivate();
	}
	
	public double getAbsoluteCenterX() {
		return (localToScene(this.getBoundsInLocal()).getMinX() + localToScene(this.getBoundsInLocal()).getMaxX()) / 2;
	}
	
	public double getAbsoluteCenterY() {
		return (localToScene(this.getBoundsInLocal()).getMinY() + localToScene(this.getBoundsInLocal()).getMaxY()) / 2;
	}
	
	@Override
	public void moveHandle(double mouseX, double mouseY) {
		dm.moveHandle(mouseX, mouseY);
	}
	
	@Override
	public Node getHandle() {
		return dm.getHandle();
	}
	
	@Override
	public void activate() {
		dm.activate();
	}

	@Override
	public void deactivate() {
		dm.deactivate();
	}
	
	@Override
	public boolean getIsActivated() {
		return dm.getIsActivated();
	}
	
	public static int getDialCircleRadius() {
		return dialCircleRadius;
	}
	
	public static Color getBorderCircleColor() {
		return borderCircleColor;
	}
}
