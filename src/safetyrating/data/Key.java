package safetyrating.data;


/**
 * A key of the symbol table. Contains the vehicle model year, hour of collision,
 * and collision weather. Every unique combination of V_HOUR,
 * C_HOUR and C_WTHR produces a unique hash value.
 * 
 * @author Harkeerat Kanwal
 */
public class Key implements Comparable<Key> {
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
	 * Check for equality between keys. Two keys are equal if they have the same V_YEAR,
	 * C_HOUR and C_WTHR.
	 * 
	 * @return A boolean. True if the properties V_YEAR, C_HOUR and C_WTHR of both keys are equal,
	 *   false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Key)) {
			return false;
		}
		Key key = (Key) obj;
		return getVehicleYear() == key.getVehicleYear() && getHour() == key.getHour() && C_WTHR == key.getWeather();
	}
	
	/**
	 * Generates a hash value from this key. Every unique key will have a unique hash value,
	 * meaning every different combination of V_YEAR, C_HOUR and C_WHTR will have a different
	 * hash value.
	 * 
	 * @return An int. The hash value of this key. The range is from 0 to 19823 which is the total
	 *   number of possible combinations of V_YEAR, C_HOUR and C_WTHR.
	 */
	@Override
	public int hashCode() {
		return (V_YEAR-1901)*168 + C_HOUR*7 + C_WTHR - 1;
	}
	
	/**
	 * Keys are compared based on their hash values.
	 * 
	 * @param key The key to compare this object against.
	 * @return A positive integer if the hash code of the given key is less than this object's
	 *   hash code, 0 if the hash codes are equal, and a negative integer otherwise.
	 */
	public int compareTo(Key key) {
		if (hashCode() > key.hashCode()) {
			return 1;
		}
		else if (hashCode() == key.hashCode()) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
	/**
	 * Generate a String representation of the key.
	 * 
	 * @return String representation of the key.
	 */
	@Override
	public String toString() {
		return "V_YEAR: " + getVehicleYear() + ", C_HOUR: " + getHour() + ", C_WTHR: " + getWeather();
	}
	
	/**
	 * Getter for the vehicle model year.
	 * @return The V_YEAR.
	 */
	public int getVehicleYear() {
		return V_YEAR;
	}
	
	/**
	 * Getter for the hour of collision.
	 * @return The C_HOUR.
	 */
	public int getHour() {
		return C_HOUR;
	}
	
	/**
	 * Getter for the weather.
	 * @return The C_WTHR.
	 */
	public int getWeather() {
		return C_WTHR;
	}
}
