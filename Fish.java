/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * Fish.java
 */

package li.five;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Fish extends ImageView {
	private Image image;
	private final int fishWidth = 50;
	
	// 0 for blue, 1 for yellow, 2 for red
	private static int currentFishNum;
	
	private static Fish currentFish;
	private static Minigame currentMinigame;
	private static Fisherman currentFisherman;
	
	// Number of frames the won animation has taken already.
	private int victoryCounter;

	// Maximum and minimum heights the fish can go to without ending the minigame.
	protected double max;
	protected double min;
	
	// Y position for fish to be set to.
	protected double baseTranslateY;
	
	// Speed of the fish.
	protected double speed;
	
	public Fish(String imageSource, double speed) {
		this.image = new Image(imageSource, fishWidth, fishWidth, true, false);
		setImage(this.image);
		setManaged(false);
		this.speed = speed;
	}
	
	// Move the fish downwards.
	public abstract void move();
	
	// Set the highest and lowest points the fish can reach.
	// max = how low below the original translateY the Fish can go before it hits the
	// arc.
	// min = how high above the original translateY the Fish can go before it hits the
	// dial.
	public void setMaxMin(double max, double min) {
		this.max = max;
		this.min = min;
	}
	
	// Move the fish upwards.
	public void scrolled() {
		baseTranslateY -= 10;
	}
	
	// Fish has been caught or escaped.
	public void endFishing(boolean won) {
		currentMinigame.deactivate();
		currentFisherman.resetImageView();
		
		if (won) {
			System.out.print("+1 ");
			switch (currentFishNum) {
			case 0:
				System.out.print("blue ");
				break;
			case 1:
				System.out.print("yellow ");
				break;
			default:
				System.out.print("red ");
				break;
			}
			System.out.println("fish");
			
			// Variables for the animation.
			double startX = this.getTranslateX();
			double startY = this.getTranslateY();
			victoryCounter = 0;
			int maximum = 50;
			
			// Animation of the fish going to the fisherman's corner.
			AnimationTimer fishToInventory = new AnimationTimer() {

				@Override
				public void handle(long now) {
					if (victoryCounter > maximum) {
						FileManager.increment(currentFishNum);
						setVisible(false);
						currentFisherman.showFishingButton();
						stop();
					}
					setTranslateX(startX + 48 * victoryCounter);
					setTranslateY(startY + 32 * victoryCounter);
					victoryCounter++;
				}
			};
			
			fishToInventory.start();
		}
		else {
			System.out.println("fish escaped");
			currentFisherman.showFishingButton();
			setVisible(false);
		}
	}
	
	// Positions the fish under the horizontal center of the dial, above the arc.
	public void setPosition(double x, double y) {
		this.setTranslateX(x - 1.5 * fishWidth);
		this.setTranslateY(y - 0.5 * getFitHeight());
	}
	
	public static void setCurrentMinigame(Minigame minigame) {
		currentMinigame = minigame;
	}
	
	public static int getCurrentFishNum() {
		return currentFishNum;
	}
	
	public static void setCurrentFishNum(int fish) {
		currentFishNum = fish;
	}
	
	public static void setCurrentFisherman(Fisherman fisherman) {
		currentFisherman = fisherman;
	}
	
	public static Fish getCurrentFish() {
		return currentFish;
	}
	
	public static void setCurrentFish(Fish fish) {
		currentFish = fish;
	}
}
