import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetsDemo {

	public static void main(String[] args) {
		
		Set<String> hashDemo = new HashSet<>();
		Set<String> treeDemo = new TreeSet<>();
		
		for(int i = 49; i >= 0; i--)
		{
			hashDemo.add("" + i);
			treeDemo.add("" + i);
		}

		System.out.println(hashDemo);
		System.out.println(treeDemo);
		
		System.out.println(hashDemo.size());
		
		System.out.println(treeDemo.size());

	}

}
