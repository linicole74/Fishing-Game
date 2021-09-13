/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * Minigame.java
 */

package li.five;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

public class Minigame extends HBox implements IMinigameComponent {
	
	private static Dial dial = new Dial();
	
	// Arc of which if the fish passes, it can escape.
	private static int boundaryArcRadiusX = Dial.getDialCircleRadius() * 5;
	private static int boundaryArcRadiusY = boundaryArcRadiusX;
	private static int boundaryArcOutline = boundaryArcRadiusX / 50;
	private static Arc boundaryArc = new Arc(Dial.getDialCircleRadius(), Dial.getDialCircleRadius(), 
	boundaryArcRadiusX + (boundaryArcOutline / 2), 
	boundaryArcRadiusY  + (boundaryArcOutline / 2), 180, 180);
	
	// The purpose of this is to combine the arc and the dial.
	private Group minigameGroup = new Group();
	
	public Minigame() {
		boundaryArc.setStroke(Color.DARKSLATEGRAY);
		boundaryArc.setStrokeWidth(boundaryArcOutline);
		boundaryArc.setFill(null);
		
		minigameGroup.getChildren().addAll(dial, boundaryArc);
		minigameGroup.setTranslateY(75);
		getChildren().add(minigameGroup);
		
		setAlignment(Pos.TOP_CENTER);
		
		deactivate();
	}
	
	public double getAbsoluteCenterX() {
		return dial.getAbsoluteCenterX();
	}
	
	public double getAbsoluteCenterY() {
		return dial.getAbsoluteCenterY();
	}
	
	@Override
	public void moveHandle(double mouseX, double mouseY) {
		dial.moveHandle(mouseX, mouseY);
	}

	@Override
	public void activate() {
		setVisible(true);
		System.out.println("minigame activated");
		dial.activate();
	}
	
	@Override
	public Node getHandle() {
		return dial.getHandle();
	}

	@Override
	public void deactivate() {
		dial.deactivate();
		setVisible(false);
	}
	
	@Override
	public boolean getIsActivated() {
		return dial.getIsActivated();
	}
}
