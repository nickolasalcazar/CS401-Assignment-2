import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

public class DVDCollection {
	// Data fields
	/** The current number of DVDs in the array */
	private int numdvds;
	
	/** The array to contain the DVDs */
	private DVD[] dvdArray;
	
	/** The name of the data file that contains dvd data */
	private String sourceName;
	
	/** Boolean flag to indicate whether the DVD collection was
	    modified since it was last saved. */
	private boolean modified;

	/** Array containing all possible ratings */
	private String[] ratings = {"G", "PG", "PG-13", "R", "NC-17"};
	
	/**
	 *  Constructs an empty directory as an array
	 *  with an initial capacity of 7. When we try to
	 *  insert into a full array, we will double the size of
	 *  the array first.
	 */
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[7];
	}

	/**
	 * @return Number of DVDs store in dvdArray
	 */
	public int getNumDvds() { return numdvds; }

	/**
	 * @throws ArrayIndexOutOfBoundsException
	 * @return DVD stored at index i in dvdArray
	 */
	public DVD getDvd(int i) { return dvdArray[i]; }
	
	public String toString() {
		String output = String.format("numdvds = %d%ndvdArray.length = %d%n", 
			numdvds, dvdArray.length);

		for (int i = 0; i < numdvds; i++) {
			output += String.format("dvdArray[%d] = %s/%s/%d%n", i, 
				dvdArray[i].getTitle(), 
				dvdArray[i].getRating(),
				dvdArray[i].getRunningTime());
		}
		return output;
	}

	/**
	 * Given the title, rating and running time of a DVD, add this DVD to the 
	 * collection if the title is not present in the DVD collection, or modify 
	 * the DVD's rating and running time if the title is present in the collection. 
	 */
	public void addOrModifyDVD(String title, String rating, String runningTime) {
		// Check if runningTime is valid
		int runningTimeInt;
		try { 
			runningTimeInt = Integer.parseInt(runningTime);
		} catch(NumberFormatException ex) { 
			System.out.println("ERROR: Invalid running time format");
			return; 
		}

		if (!isRatingValid(rating)) {
			System.out.println("ERROR: Invalid rating format");
			return;
		}
		// Search for title in dvdArray
		boolean titleFound = false;
		for (int i = 0; i < numdvds; i++) {
			if (dvdArray[i].getTitle().equals(title)) {
				dvdArray[i].setRating(rating);
				dvdArray[i].setRunningTime(runningTimeInt);
				modified = true;
				titleFound = true;
			}
		}
		// If title not found, create new DVD object in dvdArray
		if (!titleFound) {
			// If dvdArray full, double its length
			if (dvdArray.length == numdvds) {
				dvdArray = Arrays.copyOf(dvdArray, dvdArray.length * 2);
			}
			dvdArray[numdvds] = new DVD(title, rating, runningTimeInt);
			numdvds++;
			modified = true;
		}
	}
	
	/**
	 * Given the title, this method should remove the DVD with this title from 
	 * the collection if present. The title must match exactly (in uppercase). 
	 * If no title matches, do not change the collection. 
	 */
	public void removeDVD(String title) {
		title = title.toUpperCase();
		boolean titleFound = false;
		// Determine if titleFound
		for (int i = 0; i < numdvds; i++) {
			if (dvdArray[i].getTitle().equals(title)) titleFound = true;
		}
		if (titleFound) {
			DVD[] newDvdArray = new DVD[dvdArray.length];
			for (int i = 0, j = 0; i < numdvds; i++, j++) {
				if (dvdArray[i].getTitle().equals(title)) {
					j--;
					continue;
				}
				newDvdArray[j] = dvdArray[i];
			}
			dvdArray = newDvdArray;
			newDvdArray = null;
			modified = true;
			numdvds--;
		}
	}
	
	/**
	 * Given the rating, this method should return a string containing all DVDs
	 * that match the given rating in the order that they appear in the 
	 * collection, separated by newlines.
	 */
	public String getDVDsByRating(String rating) {
		if (!isRatingValid(rating)) return "";
		String output = new String();
		for (int i = 0; i < numdvds; i++) {
			if (dvdArray[i].getRating().equals(rating)) {
				output += String.format("%s,%s,%d%n", 
					dvdArray[i].getTitle(),
					dvdArray[i].getRating(),
					dvdArray[i].getRunningTime());
			}
		}
		return output;
	}

	/**
	 * This method should return the total running time of all DVDs in the
	 * collection. If there are no DVDs in the collection, return 0.
	 */
	public int getTotalRunningTime() {
		if (numdvds == 0) return 0;
		int total = 0;
		for (int i = 0; i < numdvds; i++) {
			total += dvdArray[i].getRunningTime();
		}
		return total;
	}
	
	/**
	 * Given a file name, this method should try to open this file and read
	 * the DVD data contained inside to create an initial alphabetized DVD collection.
	 */
	public void loadData(String filename) {
		sourceName = filename;
		File file = new File(sourceName);
		Scanner scanner;

		try { scanner = new Scanner(file); }
		catch (FileNotFoundException e) { return; }

		String title = new String();
		String rating = new String();
		int runningTime;

		while(scanner.hasNextLine()) {
			String[] csvArray = scanner.nextLine().split(",");

			if (csvArray.length != 3) break; // Invalid CSV format
	
			title = csvArray[0];

			if (!isRatingValid(csvArray[1])) break; // Invalid rating
			rating = csvArray[1];

			try { runningTime = Integer.parseInt(csvArray[2]); }
			catch(NumberFormatException ex) { break; } // Invalid runningTime

			// All checks passed, add data to dvdArray
			addOrModifyDVD(title, rating, String.valueOf(runningTime));
		}
	}
	
	/**
	 * Save the DVDs currently in the array into the same file specified during 
	 * the load operation, overwriting whatever data was originally there.
	 */
	public void save() {
		try {
			File file = new File(sourceName);
	  		FileOutputStream fos = new FileOutputStream(file);

			if (!file.exists()) file.createNewFile();

			String output = new String();
			for (int i = 0; i < numdvds; i++) {
				output += String.format("%s,%s,%d%n", 
					dvdArray[i].getTitle(),
					dvdArray[i].getRating(),
					dvdArray[i].getRunningTime());
			}
			byte[] bytesArray = output.getBytes();
			fos.write(bytesArray);
			fos.flush();
			fos.close();
		} catch (IOException e) { e.printStackTrace(); }
	}

	/**
	 * Returns true if rating is formatted correctly, false if otherwise
	 */
	private boolean isRatingValid(String rating) {
		rating = rating.toUpperCase();
		for (int i = 0; i < ratings.length; i++) {
			if (ratings[i].equals(rating)) return true;
		}
		return false;
	}
}
