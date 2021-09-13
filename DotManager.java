/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * DotManager.java
 */

package li.five;

import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DotManager implements IMinigameComponent {
	
	// Handle that moves in circle according to mouse movement.
	private static Circle handle;

	// The radius of the dot markings, the radius of the handle, and the radius of the
	// handle's movement around the center of the dial.
	private static int dotRadius;
	private static int handleRadius;
	private static int handlePathRadius;
	
	// Colors of dot markings when not touched yet, after being touched, and when
	// flashing because they have all been touched.
	private static Color untouchedColor = Dial.getBorderCircleColor();
	private static Color touchedColor = Color.GOLD;
	private static Color flashingColor = Color.CHOCOLATE;
	
	// The nodes of the dot markings, booleans of whether each one has been touched 
	// yet, and a pane that holds all the dot markings.
	private static Circle[] dots = new Circle[8];
	private static boolean[] dotsTouched = new boolean[8];
	private static Pane dotPane = new Pane();
	
	// Whether or not the function should be moving the circle and checking for dot
	// touches (used to make the calculations stop when the minigame is invisible).
	private static boolean isActivated;
	
	// centerX and centerY of the dial.
	private static int centerX;
	private static int centerY;
	
	public DotManager(int dotCircleRadius) {
		this.dotRadius = dotCircleRadius;
		this.handleRadius = dotRadius * 2;
		
		// Displacement of a dot when it is directly vertical or horizontal from the
		// center of the dial.
		double positionRadius = Dial.getDialCircleRadius() + (dotRadius / 2);
		
		// Displacement of a dot when it is diagonal from the center of the dial.
		double positionRadiusDiagonal = positionRadius / 1.41421356237;
		
		// Sets the formatting of the dot markings.
		for (int i = 0; i < 8; i++) {
			dots[i] = new Circle();
			dots[i].setRadius(dotRadius);
			dots[i].setManaged(false);
			dots[i].setFill(untouchedColor);
			dotPane.getChildren().add(dots[i]);
		}
		
		// Sets the position of the dot markings.
		// dots[0] is the bottom center dot.
		dots[0].setCenterX(0);
		dots[0].setCenterY(positionRadius);
		
		dots[1].setCenterX(-positionRadiusDiagonal);
		dots[1].setCenterY(positionRadiusDiagonal);

		dots[2].setCenterX(-positionRadius);
		dots[2].setCenterY(0);

		dots[3].setCenterX(-positionRadiusDiagonal);
		dots[3].setCenterY(-positionRadiusDiagonal);

		dots[4].setCenterX(0);
		dots[4].setCenterY(-positionRadius);

		dots[5].setCenterX(positionRadiusDiagonal);
		dots[5].setCenterY(-positionRadiusDiagonal);

		dots[6].setCenterX(positionRadius);
		dots[6].setCenterY(0);

		dots[7].setCenterX(positionRadiusDiagonal);
		dots[7].setCenterY(positionRadiusDiagonal);
		
		// TODO: make a better formula
		handlePathRadius = (int) (positionRadius - 0.5 * (dotRadius + handleRadius) - 5);
		
		// Creates and positions the handle, setting it to use absolute positioning.
		handle = new Circle(handleRadius);
		handle.setTranslateX(centerX);
		handle.setTranslateY(centerY + handlePathRadius);
		handle.setManaged(false);
		
		deactivate();
	}
	
	@Override
	public void activate() {
		
		// Gets the centerX and centerY of the dial.
		DotManager.centerX = (int) dots[0].localToScene(dots[0].getBoundsInLocal()).getMinX() + dotRadius;
		DotManager.centerY = (int) dots[2].localToScene(dots[2].getBoundsInLocal()).getMinY() + dotRadius;
		
		// Makes the GUI visible and allows movement to be calculated and carried out.
		handle.setVisible(true);
		setDotsTouchedToFalse();
		handle.setTranslateX(centerX);
		handle.setTranslateY(centerY + handlePathRadius);
		isActivated = true;
	}
	
	@Override
	public Node getHandle() {
		return handle;
	}

	@Override
	public void deactivate() {
		handle.setVisible(false);
		isActivated = false;
	}
	
	@Override
	public boolean getIsActivated() {
		if (isActivated) return true;
		return false;
	}
	
	@Override
	public void moveHandle(double mouseX, double mouseY) {
			
		// Mouse position if the center of the dial is treated as (0, 0)
		double practicalMouseX = mouseX - centerX;
		double practicalMouseY = mouseY - centerY;
		
		// If the mouse is at exactly (0, 0), it'll just ignore it and wait until the
		// next mouse movement.
		if ((practicalMouseX == 0) && (practicalMouseY == 0)) return;
			
    	// handlePathRadius is the magnitude.
		// The denominator is the distance formula sqrt(x^2 + y^2)
    	double magnitudeMultiplier = handlePathRadius / Math.sqrt(Math.pow(practicalMouseX, 2) + Math.pow(practicalMouseY, 2));
    		
    	// Positions the handle at the proper location.
    	handle.setTranslateX(centerX + (practicalMouseX * magnitudeMultiplier));
    	handle.setTranslateY(centerY + (practicalMouseY * magnitudeMultiplier));
    	
    	// Determines if any dots can change color.
    	changeDot(practicalMouseX, practicalMouseY);
	}
	
	public static void changeDot(double mouseX, double mouseY) {
		
		// Determines which section of the circle the mouse is in.
		int section = getSection(mouseX, mouseY);
		
		// If the dot for that section hasn't been touched yet, change it to a touched
		// state.
		if (!dotsTouched[section]) {
			
			// Change the color and boolean to the touched state.
			dots[section].setFill(touchedColor);
			dotsTouched[section] = true;
			
			// Move the fish up a bit.
			Fish.getCurrentFish().scrolled();
			
			// Make the dots flash if all of them have been touched.
			if (getAllDotsTouched()) {
				
				// Make the dots flash.
				Task<Void> flashDots = new Task<Void>() {
					
					@Override
					protected Void call() {
						for (Circle d : dots) {
							d.setFill(flashingColor);
						}
						try {
							Thread.sleep(250 / 2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						for (Circle d : dots) {
							d.setFill(touchedColor);
						}
						try {
							Thread.sleep(200 / 2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						for (Circle d : dots) {
							d.setFill(flashingColor);
						}
						try {
							Thread.sleep(250 / 2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						for (Circle d : dots) {
							d.setFill(untouchedColor);
						}
						
						// Resets whether the dots have been touched.
						setDotsTouchedToFalse();
						return null;
					}
				};
				new Thread(flashDots).start();
			}
		}
	}
	
	// This gets the slice of the "pie" the hook is in when the pie is the circle and
	// the slice is the 1/8 (1/16 on either side) region of the circle surrounding
	// each dot.
	// Basically it gets the closest dot to the handle.
	private static int getSection(double practicalMouseX, double practicalMouseY) {
		
		// Gets the slope of the mouse location.
		double slope = Math.abs(practicalMouseY / practicalMouseX);
		
		// Gets the quadrant the mouse is in.
		int quadrant;
		if (practicalMouseX > 0) {
			if (practicalMouseY > 0) {
				quadrant = 1;
			}
			else quadrant = 4;
		}
		else if (practicalMouseY < 0) {
			quadrant = 3;
		}
		else quadrant = 2;
		
		// sqrt(2) - 1 produces the slope of a 22.5 degree line (0.4142135624)
		// and sqrt(2) + 1 produces the slope of a 45 + 22.5 degree line (2.4142135624)
		if (slope >= 0.4142135624) {
			if (slope >= 2.4142135624) {
				if (practicalMouseY > 0) return 0;
				else return 4;
			}
			else switch (quadrant) {
				case 1: return 7;
				case 2: return 1;
				case 3: return 3;
				default: return 5;
			}
		}
		else if (practicalMouseX < 0) return 2;
		else return 6;
	}
	
	// Gets whether all of the dots have been touched.
	public static boolean getAllDotsTouched() {
		for (boolean d : dotsTouched) if (!d) return false;
	    return true;
	}
	
	// Resets the booleans for whether the each dot has been touched.
	private static void setDotsTouchedToFalse() {
		for (int i = 0; i < dotsTouched.length; i++) {
			dotsTouched[i] = false;
		}
	}
	
	// Gets the pane with the dots in it.
	public Node getDotPane() {
		return dotPane;
	}
}