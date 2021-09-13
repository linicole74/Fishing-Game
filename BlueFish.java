/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * BlueFish.java
 */

package li.five;

import javafx.animation.AnimationTimer;

public class BlueFish extends Fish {

	// Variables for the animation.
	private double changingSpeed;		// actual speed of fish
	private boolean accelerating;		// whether fish should be accelerating
	
	public BlueFish() {
		super("\\li\\five\\fishBlue.png", Math.random() * 1.5 + 1);
	}

	// Moves the fish down.
	// Movement style: accelerates and decelerates in pulses.
	@Override
	public void move() {
		changingSpeed = speed;
		accelerating = false;
		baseTranslateY = getTranslateY();
		double topLimit = baseTranslateY - min;
		double bottomLimit = baseTranslateY + max;
		
		AnimationTimer moveFish = new AnimationTimer() {

			@Override
			public void handle(long now) {
				
				// Moves the fish.
				baseTranslateY += changingSpeed;
				setTranslateY(baseTranslateY);
				
				// Checks if user has won or lost yet.
				if (baseTranslateY < topLimit) {
					endFishing(true);
					stop();
				}
				else if (baseTranslateY > bottomLimit) {
					endFishing(false);
					stop();
				}
				
				// Accelerates and decelerates.
				if (changingSpeed < 0.1 * speed) accelerating = true;
				else if (changingSpeed > 1.5 * speed) accelerating = false;
				
				if (accelerating) changingSpeed += 0.1;
				else changingSpeed -= 0.1;
			}
			
		};
        moveFish.start();
	}
}