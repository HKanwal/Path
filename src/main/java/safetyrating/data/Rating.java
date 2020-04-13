package safetyrating.data;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Pesara Amarasekera
 * Revised April 12th 2020
 * 
 * Rate function to provide a rating depending on the start location and end location
 */

public class Rating {

	//the start_weather and end_weather are found in the main object of the OWM JSON output
	public double rating(int user_vehicle_type, int start_weather, int end_weather) throws IOException {
		CollisionDatabase data = new CollisionDatabase();
		//Find a way to convert the users vehicle type into the numbers in the data base
		//Find a way to convert the star weather and end weather into the numbers in the
		//database
		
		/**
		 * Computation
		 * 
		 * Get the total number of crashes (data set size) => x
		 * (get the number of crashes that meet both start_weather and V_type) => y
		 * (get the number of crashes that meet both end_weather and V_type) => z
		 * (Final rating) z := (y+z)/x*5 (take the average between the two points and rate out of 10)
		 * 
		 */
		
		double x  = data.getTotalSize();
		ArrayList<Entry> temp = data.get("V_TYPE", user_vehicle_type);//get the vehicle data
		double y = 0;
		double z = 0;
		for(Entry i : temp) {
			if(i.getAttr("C_WTHR") == start_weather) {
				y++;
			}
			if(i.getAttr("C_WTHR") == end_weather) {
				z++;
			}
		}
		double result = (y+z)/x*5;
		
		return result;
// Open weather only supports these https://openweathermap.org/weather-conditions
	// we need to convert to the bottom ones
		 	
//      clear -> 1 
//		overcast, cloudy no precipitation -> 2
//		raining -> 3
//		snowing, not including drifting snow -> 4
//		freezing rain, sleet, hail -> 5
//		drifting snow, fog, smog -> 6
//		strong wind -> 8
//		choice is other -> Q (but we use -1 for this)
	}
}
