import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapsDemo {

	public static void main(String[] args) {
		
		//We want to map from names (Strings) to phone numbers (Integers)
		Map<String, Integer> demo = new TreeMap<>();
		
		//add some names and numbers
		demo.put("Alice", 123);
		demo.put("Bob", 456);
		demo.put("Steve", 555);
		
		System.out.println(demo.get("Bob"));

		demo.put("Fred", 300);
		
		
		//Doing put again with a key that's already in the map will update the value
		demo.put("Fred", 700);
		
		//put if absent won't do that
		demo.putIfAbsent("Fred", 500);
		
		demo.put("Ted", null);
		
		System.out.println(demo.getOrDefault("Ted", 6));
		
		
		System.out.println(demo.get("Fred"));
		System.out.println(demo.getOrDefault("Fred", 5));
		
		System.out.println(demo.size());
		
		String[] s = demo.keySet().toArray(new String[0]);
		System.out.println(Arrays.toString(s));
//		for(String s : demo.keySet())
//		{
//			System.out.println(demo.get(s));
//		}
		
		demo.remove("Steve");
		
		System.out.println(demo.containsValue(555));

	}

}
