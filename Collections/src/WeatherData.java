import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WeatherData {
	
	public static final int DATE = 1;
	public static final int MULTI = 3;
	public static final int PRECIP = 4;
	public static final int SNOW = 5;
	public static final int TMAX = 7;
	public static final int TMIN = 8;

	// Data structures we'll use go here
	private Map<Integer, Double> snowfallPerMonth;
	private int maxTemp = Integer.MIN_VALUE;
	private String dateOfMaxTemp;
	private int minTemp = Integer.MAX_VALUE;
	private String dateOfMinTemp;
	private Map<Integer, List<Integer>> highTempByMonth;
	private Map<Integer, Double> avgHighByMonth;
	private int[] medianHighByMonth;

	/*
	 * The constructor will take a scanner already linked to a file it will run
	 * through the scanner and construct all the data structures we need to analyze
	 * the data then we'll write methods that actually access those structures.
	 */
	public WeatherData(Scanner data) {
		
		snowfallPerMonth = new HashMap<>();
		highTempByMonth = new HashMap<>();

		//advance the scanner past the header row
		data.nextLine();
		
		while (data.hasNext()) {
			// First we split the row by commas
			String[] row = data.nextLine().split(",");
			
			// then we remove the quotation marks from each string
			for (int i = 0; i < row.length; i++) {
				if (!row[i].isEmpty()) {
					row[i] = row[i].substring(1, row[i].length() - 1);
				}
			}
			//System.out.println(Arrays.toString(row));
			
			
			int month = Integer.parseInt(row[DATE].split("-")[1]);
			
			
			//Snowfall stuff
			if(row.length>SNOW && !row[SNOW].isEmpty())
			{
				//Then there's snow data for that day
				double snow = Double.parseDouble(row[SNOW]);
				
				if(!snowfallPerMonth.containsKey(month))
				{
					snowfallPerMonth.put(month, 0.0);
				}
				
				//I need to add the snow we just found to that running value
				double current = snowfallPerMonth.get(month);
				snowfallPerMonth.put(month, current + snow);
				
			}
			
			//if the row contains enough columns for temperature data
			if(row.length > TMAX && !row[TMAX].isEmpty())
			{
				//get the high temp
				int temp = Integer.parseInt(row[TMAX]);
				
				//If there's no mapping for this month
				if(!highTempByMonth.containsKey(month))
				{
					//add one
					highTempByMonth.put(month, new ArrayList<Integer>());
				}
				
				//add the current recorded temp to the list
				highTempByMonth.get(month).add(temp);
				
				
				//if it's higher than the highest recorded
				if(temp > maxTemp)
				{
					//replace the old highest temp and date with the current one
					maxTemp = temp;
					dateOfMaxTemp = row[DATE];
				}
			}
			
			//Check for a mintemp column
			if(row.length > TMIN && !row[TMIN].isEmpty())
			{
				//get the low temp
				int temp = Integer.parseInt(row[TMIN]);
				
				//if it's higher than the highest recorded
				if(temp < minTemp)
				{
					//replace the old highest temp and date with the current one
					minTemp = temp;
					dateOfMinTemp = row[DATE];
				}
			}
		}
		
		avgHighByMonth = new HashMap<Integer,Double>();
			for(int key : highTempByMonth.keySet())
			{
				//add up all the temps
				int total = 0;
				for(int i : highTempByMonth.get(key))
				{
					total += i;
				}
				
				double avg = ((double)total) / highTempByMonth.get(key).size();
				
				avgHighByMonth.put(key, avg);
			}
			
			medianHighByMonth = new int[12];
			for(int i : highTempByMonth.keySet())
			{
				List<Integer> temps = highTempByMonth.get(i);
				temps.sort(null);
				int median = temps.get(temps.size()/2);
				medianHighByMonth[i-1] = median;
			}
	}
	
	public int getMinTemp() {
		return minTemp;
	}

	public String getDateOfMinTemp() {
		return dateOfMinTemp;
	}

	public int getMaxTemp() {
		return maxTemp;
	}

	public String getDateOfMaxTemp() {
		return dateOfMaxTemp;
	}
	
	public double medianHighForMonth(int month)
	{
		if(month < 1 || month >12)
		{
			throw new IllegalArgumentException("Month must be between 1 and 12.");
		}
		return medianHighByMonth[month-1];
	}
	
	public double averageHighForMonth(int month)
	{
		if(month < 1 || month >12)
		{
			throw new IllegalArgumentException("Month must be between 1 and 12.");
		}
		
		return avgHighByMonth.get(month);
	}

	public double averageSnowfall(int month)
	{
		
		if(month < 1 || month >12)
		{
			throw new IllegalArgumentException("Month must be between 1 and 12.");
		}
		//the map contains total for 100 years, so the average is that over 100
		return snowfallPerMonth.get(month) / 100;
	}

	public static void main(String[] args) throws FileNotFoundException {

		File f = new File("brem100.csv");
		Scanner data = new Scanner(f);
		WeatherData wa = new WeatherData(data);
		
		System.out.println(wa.averageSnowfall(12));
		System.out.println(wa.getDateOfMaxTemp());
		System.out.println(wa.getMaxTemp());
		System.out.println(wa.getDateOfMinTemp());
		System.out.println(wa.getMinTemp());
		
		System.out.println(wa.averageHighForMonth(8));	
		System.out.println(wa.medianHighForMonth(8));

	}

}
