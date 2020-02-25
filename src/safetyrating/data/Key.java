package safetyrating.data;


/**
 * A key of the hash table. Stores C_HOUR, C_WTHR, V_YEAR and is can be compared to
 * other keys using any one of these attributes.
 * 
 * @author Harkeerat Kanwal
 */
public class Key {
	private int V_YEAR;
	private int C_HOUR;
	private int C_WTHR;
	
	/**
	 * Initialize the key.
	 * 
	 * @param vYear The V_YEAR.
	 * @param cHour The C_HOUR.
	 * @param cWthr The C_WTHR.
	 */
	public Key(int vYear, int cHour, int cWeather) {
		V_YEAR = vYear;
		C_HOUR = cHour;
		C_WTHR = cWeather;
	}
	
	/**
	 * Generate a String representation of the key.
	 * @return String representation of the key.
	 */
	public String toString() {
		return "V_YEAR: " + V_YEAR + ", C_HOUR: " + C_HOUR + ", C_WTHR: " + C_WTHR;
	}
	
	/**
	 * Compares two keys by their year.
	 * 
	 * @param key The key with which to compare vehicle model years.
	 * @return 0 if they are equal, 1 if this key is greater, -1 if this key is less.
	 */
	public int compareByYearTo(Key key) {
		if (V_YEAR == key.V_YEAR()) {
			return 0;
		}
		else if (V_YEAR > key.V_YEAR()) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Compares two keys by their hour.
	 * 
	 * @param key The key with which to compare hour of day.
	 * @return 0 if they are equal, 1 if this key is greater, -1 if this key is less.
	 */
	public int compareByHourTo(Key key) {
		if (C_HOUR == key.C_HOUR()) {
			return 0;
		}
		else if (C_HOUR > key.C_HOUR()) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Compares two keys by their weather.
	 * 
	 * @param key The key with which to compare weather.
	 * @return 0 if they are equal, 1 if this key is greater, -1 if this key is less.
	 */
	public int compareByWeatherTo(Key key) {
		if (C_WTHR == key.C_WTHR()) {
			return 0;
		}
		else if (C_WTHR > key.C_WTHR()) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Check for equality against another key.
	 * 
	 * @param key The key against which to check equality.
	 * @return A boolean. Weather or not the two keys are equal.
	 */
	public boolean isEqual(Key key) {
		return V_YEAR == key.V_YEAR() && C_HOUR == key.C_HOUR() && C_WTHR == key.C_WTHR();
	}
	
	/**
	 * Getter for the vehicle model year.
	 * @return The V_YEAR.
	 */
	public int V_YEAR() {
		return V_YEAR;
	}
	
	/**
	 * Getter for the hour of collision.
	 * @return The C_HOUR.
	 */
	public int C_HOUR() {
		return C_HOUR;
	}
	
	/**
	 * Getter for the weather.
	 * @return The C_WTHR.
	 */
	public int C_WTHR() {
		return C_WTHR;
	}
}
