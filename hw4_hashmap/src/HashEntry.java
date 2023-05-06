/**
 * @Author: Barry Tang
 * @Date: Feb 25, 2023
 * @Description:
 * 	represents a generic hash entry which is to be put
 * 	into a hash map
 * 
 */
public class HashEntry<K, V> {
	
	K key;
	V value;
	
	public HashEntry() {}
	
	public HashEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return key + ", " + value;
	}
}
