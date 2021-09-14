import java.io.*;

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
	 * Given the title, this method should remove the DVD with this title from the
	 * collection if present. The title must match exactly (in uppercase). 
	 * If no title matches, do not change the collection.
	 * 
	 */
	public void addOrModifyDVD(String title, String rating, String runningTime) {
		// NOTE: Be careful. Running time is a string here
		// since the user might enter non-digits when prompted.
		// If the array is full and a new DVD needs to be added,
		// double the size of the array first.
	}
	
	// TODO
	/*
	 * Given the rating, this method should return a string containing all DVDs that
	 * match the given rating in the order that they appear in the collection,
	 * separated by newlines.
	 */
	public void removeDVD(String title) {
		
	}
	
	// TODO
	/*
	 * This method should return the total running time of all DVDs in the collection.
	 * If there are no DVDs in the collection, return 0.
	 */
	public String getDVDsByRating(String rating) {

		return null;	// STUB: Remove this line.
	}

	// TODO
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

	}
	
	// TODO
	public void save() {

	}

	// Additional private helper methods go here:
}
