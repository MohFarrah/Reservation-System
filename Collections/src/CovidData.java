import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CovidData {

	public static final int STATE = 2;

	public static void main(String[] args) throws FileNotFoundException {

		File data = new File("us-counties-2022.csv");
		Scanner s = new Scanner(data);

		System.out.println(s.nextLine());

		String demo = s.nextLine();

		/*
		 * Index 0 is the date index 
		 * 1 is the county index 
		 * 2 is the state index 
		 * 3 is the FIPS number index 
		 * 4 is the cumulative cases index 
		 * 5 is the cumulative deaths.
		 */

		// System.out.println(Arrays.toString(demo.split(",")));

		Set<String> states = new TreeSet<>();
		Set<String> counties = new TreeSet<>();

		int count = 0;

		Map<String, Set<String>> countiesByState = new TreeMap<>();
		Map<String, Integer> deathsByDateInCounty = new TreeMap<>();
		Map<String, Integer> deathsByDateNational = new TreeMap<>();

		String prevDate = "2022-01-01";
		int nationalCount = 0;
		
		// count the number of rows with only 5 columns
		int fiveColumns = 0;

		while (s.hasNext()) {
			count++;

			// System.out.println(s.nextLine());
			String[] line = s.nextLine().split(",");

			if (line.length == 5) {
				fiveColumns++;
			} else {
				
				String date = line[0];
				String state = line[STATE];
				String county = line[1];
				int deaths = Integer.parseInt(line[5]);
				
				if(date.equals(prevDate))//we're still on the same day
				{
					nationalCount += deaths; //add these to the total for that day
				}
				else //we're on a new day
				{
					deathsByDateNational.put(prevDate, nationalCount); //we're done with that day, add it to the map
					nationalCount = deaths;
					prevDate = date;
				}
				
				
				
				//unique key value for each date, state, county combination
				String key = date + "-" + state + "-" + county;

				deathsByDateInCounty.put(key, deaths);
				
				states.add(state);
				counties.add(county);

				if (!countiesByState.containsKey(state)) {
					countiesByState.put(state, new TreeSet<String>());
				}

				// get the Set of counties from the current state
				Set<String> stateCounties = countiesByState.get(state);
				stateCounties.add(county);
				
				
			}
		}

		System.out.println(count);
		System.out.println(fiveColumns + " rows have less than 6 columns");

		// counties in a chosen state
		System.out.println(countiesByState.get("Utah"));

		int totalCounties = 0;
		for (String state : countiesByState.keySet()) {
			// find the size of that state's set of counties, and add it to the total
			totalCounties += countiesByState.get(state).size();
		}

		System.out.println(totalCounties);
		System.out.println(counties.size());

		System.out.println(states);

		System.out.println(states.size());
		
		System.out.println(deathsByDateInCounty.get("2022-05-14-Washington-Pierce"));
		
		System.out.println(deathsByDateNational.get("2022-01-01"));

		System.out.println(deathsByDateNational.get("2022-01-02"));
		
		
		//tree map sorts alphabetically by date, which is also chronologically
		Map<String, Integer> changeInDeaths = new HashMap<>();
		
		
		int past = 0;
		for(String key : deathsByDateNational.keySet())
		{
			int current = deathsByDateNational.get(key);
			changeInDeaths.put(key, current - past);
			past = current;
		}
		
		System.out.println(changeInDeaths.get("2022-01-02"));
	}

}
