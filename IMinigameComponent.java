/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * IMinigameComponent.java
 */

package li.five;

import javafx.scene.Node;

public interface IMinigameComponent {
	
	// Start doing handle and dot movements and calculations. Set everything to
	// visible.
	public abstract void activate();
	
	// Stop doing handle and dot movements and calculations. Set everything to
	// invisible.
	public abstract void deactivate();
	
	// Moves the handle.
	public abstract void moveHandle(double mouseX, double mouseY);
	
	// Gets whether the minigame should be active or not.
	public abstract boolean getIsActivated();
	
	// Gets the handle.
	public abstract Node getHandle();
}
