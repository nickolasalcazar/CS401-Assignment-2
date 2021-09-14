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
	// Note: this might be redundant because loadData accepts
	// 		 filename as a parameter
	private String sourceName;
	
	/** Boolean flag to indicate whether the DVD collection was
	    modified since it was last saved. */
	private boolean modified;

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
	
	public String toString() {
		String output = String.format("numdvds = %d%ndvdArray.length = %d%n", 
										numdvds, dvdArray.length);
		for (int i = 0; i < dvdArray.length; i++) {
			output += String.format("dvdArray[%d] = %s/%s/%s%n", i, 
										dvdArray[i].getTitle(), 
										dvdArray[i].getRating(),
										dvdArray[i].getRunningTime());
		}
		return output;
	}

	// TODO
	/**
	 * Given the title, rating and running time of a DVD, add this DVD to the 
	 * collection if the title is not present in the DVD collection, or modify 
	 * the DVD's rating and running time if the title is present in the collection. 
	 * 
	 * Do this operation only if the rating and running time are valid. If a new DVD 
	 * is added to this collection, insert the DVD so that all DVDs are in alphabetical 
	 * order by title. (NOTE: The collection should already be in alphabetical order 
	 * when this method is called since this is the only method available to insert DVDs.) 
	 */
	public void addOrModifyDVD(String title, String rating, String runningTime) {
		// Check if runningTime valid
		int runningTimeInt;
		try { runningTimeInt = Integer.parseInt(runningTime); }
		catch(NumberFormatException ex) { return; }

		if (!isRatingValid(rating)) return;
		
		// Search for title in dvdArray
		boolean foundFlag = false;
		for (int i = 0; i < dvdArray.length; i++) {
			if (dvdArray[i].equals(title)) {
				dvdArray[i].setRating(rating);
				dvdArray[i].setRunningTime(runningTimeInt);
				modified = true;
				foundFlag = true;
			}
		}
		// If title not found, create new DVD object in dvdArray
		if (!foundFlag) {
			// If dvdArray full, double its length
			if (dvdArray.length == numdvds) {
				dvdArray = Arrays.copyOf(dvdArray, dvdArray.length * 2);
			}
			dvdArray[numdvds] = new DVD(title, rating, runningTimeInt);
			numdvds++;
			modified = true;
		}
	}
	
	// TODO
	/**
	 * Given the title, this method should remove the DVD with this title from the
	 * collection if present. The title must match exactly (in uppercase). 
	 * If no title matches, do not change the collection.
	 * 
	 */
	public void removeDVD(String title) {
		
	}
	
	// TODO
	/*
	 * Given the rating, this method should return a string containing all DVDs that
	 * match the given rating in the order that they appear in the collection,
	 * separated by newlines.
	 */
	public String getDVDsByRating(String rating) {

		return null;	// STUB: Remove this line.
	}

	// TODO
	/*
	 * This method should return the total running time of all DVDs in the collection.
	 * If there are no DVDs in the collection, return 0.
	 */
	public int getTotalRunningTime() {

		return 0;	// STUB: Remove this line.
	}
	
	// TODO
	/*
	 * Given a file name, this method should try to open this file and read
	 * the DVD data contained inside to create an initial alphabetized DVD collection.
	 *
	 * HINT: Read each set of three values (title, rating, running time) and use the 
	 * addOrModifyDVD method above to insert it into the collection. If the file cannot
	 * be found, start with an empty array for your collection. If the data in the file
	 * is corrupted, stop initializing the collection at the point of corruption.
	 */
	public void loadData(String filename) {
		Scanner scanner = new Scanner(sourceName);
		scanner.useDelimiter(",");

		String title = new String();
		String rating = new String();
		int runningTime;

		while(scanner.hasNext()) {
			title = scanner.next();

			if (!scanner.hasNext()) break;
			rating = scanner.next();

			if (!scanner.hasNextInt() /*|| !isRatingValid(rating)*/) break;
			runningTime = scanner.nextInt();

			// All checks passed, add data to dvdArray
			//addOrModifyDVD(title, rating, runningTime);
		}
	}
	
	// TODO
	/*
	 * Save the DVDs currently in the array into the same file specified during 
	 * the load operation, overwriting whatever data was originally there.
	 */
	public void save() {

	}

	// Additional private helper methods go here:

	// Returns true if rating is formatted correctly, false if otherwise
	private boolean isRatingValid(String rating) {
		for (int i = 0; i < ratings.length; i++) {
			if (ratings[i].equals(rating)) return true;
		}
		return false;
	}
}
