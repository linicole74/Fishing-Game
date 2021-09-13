/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * RedFish.java
 */

package li.five;

import javafx.animation.AnimationTimer;

public class RedFish extends Fish {

	public RedFish() {
		super("\\li\\five\\fishRed.png", Math.random() * 1 + 1);
	}

	// Moves the fish down.
	// Movement style: constant speed
	@Override
	public void move() {
		baseTranslateY = getTranslateY();
		double topLimit = baseTranslateY - min;
		double bottomLimit = baseTranslateY + max;
		
		AnimationTimer moveFish = new AnimationTimer() {

			@Override
			public void handle(long now) {
				
				// Moves the fish.
				baseTranslateY += speed;
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
			}
		};
        moveFish.start();
	}
}