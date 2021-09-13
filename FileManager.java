/* Nicole Li
 * Period 5 AP CSA
 * April 24, 2020
 * FileManager.java
 */

package li.five;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public abstract class FileManager {
	
	// Location of the fishFile.txt, a writer, and the numbers of each type of fish.
	private static String fileLocation = "C:\\Users\\linic\\Desktop\\fishFile.txt";
	private static BufferedWriter fishWriter;
	private static int[] fishCounts;
	
	// Read the data from the file or create a new file with 0 of each type of fish.
	public static void setup() {
		try {
			fishCounts = new int[3];
			File fishFile = new File(fileLocation);
			
			// Create a new file with 0 of each type of fish if the file doesn't exist.
			if (fishFile.createNewFile()) {
				fishWriter = new BufferedWriter(new FileWriter(fileLocation));
				fishWriter.write("0\n0\n0");
				fishWriter.close();
			}
			else {
				
				// Read the data from the file into fishCounts.
				BufferedReader fishReader = new BufferedReader(new FileReader(fishFile));
				for (int i = 0; i < 3; i++) {
					fishCounts[i] = Integer.parseInt(fishReader.readLine());
				}
				fishReader.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Increase the number of a type of fish by 1 and write the data to the file.
	public static void increment(int lineNumber) {
		fishCounts[lineNumber]++;
		try {
			fishWriter = new BufferedWriter(new FileWriter(fileLocation));
			fishWriter.write(fishCounts[0] + "\n" + fishCounts[1] + "\n" + fishCounts[2]);
			fishWriter.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Get the number of a type of fish.
	public static int getQuantity(String color) {
		if (color == "blue") return fishCounts[0];
		if (color == "yellow") return fishCounts[1];
		return fishCounts[2];
	}
}
