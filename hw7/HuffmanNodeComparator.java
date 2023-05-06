import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class HuffmanNodeComparator implements Comparator<HuffmanNode>{
	
	/**
	 * given two HuffmanNodes compare them and return an associated 
	 * integer comp value
	 */
	@Override
	public int compare(HuffmanNode o1, HuffmanNode o2) {
		
		// when weights are the same, compare in lexicographical order
		if (o1.getWeight() == o2.getWeight()) {
			TreeSet<Character> set1 = o1.getData();
			TreeSet<Character> set2 = o2.getData();
			
			String str1 = "";
			String str2 = "";
			
			Iterator<Character> itr = set1.iterator();
			
			while(itr.hasNext()) {
				str1 += itr.next();
			}
			
			itr = set2.descendingIterator();
			
			while(itr.hasNext()) {
				str2 += itr.next();
			}
			return str1.compareTo(str2);
		} 
		
		return o1.getWeight() - o2.getWeight();
	}


}
