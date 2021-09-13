/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * YellowFish.java
 */

package li.five;

import javafx.animation.AnimationTimer;

public class YellowFish extends Fish {

	// Variables for the animation.
	private int changeCounter;		// counter for frames in which fish is moving
	private int changeCounterMax;	// frames in which fish is moving
	private int frozenTime;			// counter for frames in which fish isn't moving
	private int frozenTimeMax;		// frames in which fish isn't moving
	
	public YellowFish() {
		super("\\li\\five\\fishYellow.png", Math.random() * 2 + 2);
	}

	// Moves the fish down.
	// Movement style: starts and stops
	@Override
	public void move() {
		baseTranslateY = getTranslateY();
		double topLimit = baseTranslateY - min;
		double bottomLimit = baseTranslateY + max;
		
		changeCounter = 0;
		changeCounterMax = 50;
		frozenTime = 0;
		frozenTimeMax = 30;
		
		AnimationTimer moveFish = new AnimationTimer() {

			@Override
			public void handle(long now) {
				
				// Moves fish if it should not be stopped at the moment.
				if (frozenTime > frozenTimeMax) {
					baseTranslateY += speed;
					changeCounter++;
				}
				else {
					frozenTime++;
					if (changeCounter != 0) {
						changeCounter = 0;
					}
				}
				
				// Moves the fish.
				setTranslateY(baseTranslateY);
				
				// Determines if fish should stop yet.
				if (changeCounter > changeCounterMax) {
					frozenTime = 0;
				}
				
				// Checks if user has won or lost yet.
				if (baseTranslateY < topLimit) {
					endFishing(true);
					stop();
				}
				else if (baseTranslateY > bottomLimit) {
					endFishing(false);
					stop();
				}
			}
		};
		
        moveFish.start();
	}
}
