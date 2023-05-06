/**
 * @Author: Barry Tang
 * @Date: Feb 25, 2023
 * @Description:
 * 	represents a hash map using a modified alternate-sign quadratic probing
 * 	as its collision handling method
 * 
 */
public class HashMap<K, V> {
	
	private static final int DEFAULT_INITIAL_CAPACITY = 5;
	private static final double DEFAULT_MAX_LOAD_FACTOR = 0.5;
	private static final HashEntry D = new HashEntry();
	private static final HashEntry E = new HashEntry();
	
	
	private int cap;
	private int size; // number of key-value pairs
	private double lf;
	private double currlf;
	private HashEntry<K, V>[] array;
	
	/**
	 * constructs an empty HashMap with the default initial
	 * capcity (5) and the default load factor (0.5)
	 */
	public HashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
	}
	
	/**
	 * Constructs an empty HashMap with the specified inital capacity
	 * and the default load factor (0.5)
	 * @param cap - the initial capacity
	 * @throws IllegalArgumentException - if the inital capcity is 
	 * negative
	 */
	public HashMap(int cap) throws IllegalArgumentException {
		this(cap, DEFAULT_MAX_LOAD_FACTOR);
		
		if (cap < 0) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * 
	 * @param cap - the initial capacity
	 * @param lf - the load factor
	 * @throws IllegalArgumentException - if the inital capcity is 
	 * negative or load factor is nonpositive
	 */
	@SuppressWarnings("unchecked")
	public HashMap(int cap, double lf) {
		this.cap = cap;
		this.lf = lf;
		
		if (cap < 0) {
			throw new IllegalArgumentException();
		}
		
		array = new HashEntry[cap];
		
		for (int i = 0; i < cap; ++i) {
			array[i] = E;
		}
	}
	
	/**
	 * Returns the primary hash index for key. The returned index must
	 * be in [0, internal_table_capacity-1] (inclusive)
	 * @param key 
	 * @return hash index
	 */
	private int hash(K key) { // Note: this is the hash function
		return key.toString().length() % cap;
	}
	
	/**
	 * Return the final hash index according to the 
	 * modified alternate-sign quadratic probing sequence
	 * @param key
	 * @param stepIndex
	 * @return hash index
	 */
	private int hash(K key, int stepIndex) {
		int hashIndex = hash(key);
		
		if (stepIndex % 2 == 0) {
			hashIndex = (hashIndex + stepIndex * stepIndex) % cap;
		} 
		else {
			hashIndex = (hashIndex - stepIndex * stepIndex) % cap;
			if (hashIndex < 0) {
				hashIndex += cap;
			}
		}
		
		return hashIndex;
	}
	
	/**
	 * Associate a value with a key in the map. If the map previously
	 * contained a mapping for the key, the old value is replaced
	 * @param key - key with which the specified value is to be associated
	 * @param value - value to be associated with the specified key
	 * @return the previous value associated with key, or null if there was 
	 * no mapping for the key. (A null return can also indicate that the 
	 * map previously associated null with key).
	 */
	public V put(K key, V value) {
		
		int hi = 0;
		int firstDel = -1;

		for (int i = 0; i < cap; ++i) {
			hi = hash(key, i);
			//System.out.println("hi: " + hi + ";i: " + i);
			K currKey = array[hi].key;
			if (array[hi] == E) {
				if (firstDel != -1) {
					array[firstDel] = new HashEntry<K, V>(key, value);
				} else {
					array[hi] = new HashEntry<K, V>(key, value);
				}
				
				++size;
				currlf = size * 1.0 / cap;
				if (currlf > lf) {
					rehash();
				}
				
				return null;
				
			} else if (array[hi] == D) {
				
				if (firstDel != -1) {
					firstDel = hi;
				}
				
			} else if (i == cap - 1) {
				
				if (firstDel != -1) {
					array[firstDel] = new HashEntry<K, V>(key, value);
					++size;
					currlf = size * 1.0 / cap;
					if (currlf > lf) {
						rehash();
					}
					return null;
				} else {
					rehash();
					i = 0;
				}
			} else { // collision
				if (key.equals(currKey)) {
					V old = (V) array[hi].value;
					array[hi].value = value;
					return old;
				} 
			}
		}
		
		return null;
	}
	
	/**
	 * Returns the array index in which the key is located using
	 * the modified alternate-sign quadratic probing or -1 if not
	 * found
	 * @param key - the key of the entry
	 * @return the array index in which the key is located
	 */
	public int indexOf(K key) {
		
		int hi = 0;
		for (int i = 0; i < cap; ++i) {
			hi = hash(key, i);
			if (array[hi] == E) {
				return -1;
			}
			if (array[hi] != D && (array[hi].key).equals(key)) {
				return hi;
			}
		}
		
		return -1;
	}
	
	/**
	 * Using the same quadratic probing to determine if 
	 * there is an entry with matching key
	 * @param key - the key whose presence in the map is to 
	 * be tested
	 * @return true if this map contains a mapping for the 
	 * specified key, false otherwise
	 */
	public boolean containsKey(K key) {
		
		int index = indexOf(key);
		if (index != -1) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Return trues true if there is an entry with 
	 * matching value
	 * @param value - the value whose presence in the map
	 * is to be tested
	 * @return true if this map maps one or more keys to 
	 * the specified value
	 */
	public boolean containsValue(V value) {
		
		for (int i = 0; i < cap; ++i) {
			V currVal = array[i].value;
			if (array[i] != D && array[i] != E && value.equals(currVal)) {
				return true;
			}
		}
		
		return false;
	}
	
	public V get(K key) {
		int index = indexOf(key);
		if (index == -1) {
			return null;
		}
		return array[index].value;
	}
	
	public V remove(K key) {
		int index = indexOf(key);
		if (index == -1) {
			return null;
		}
		
		V old = array[index].value;
		array[index] = D;
		--size;
		currlf = size * 1.0 / cap;
		return old;
	}
	
	/**
	 * Returns the number of key-value pairs in
	 * this map
	 * @return number of key-value pairs in this map
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns true if the map is empty and
	 * false otherwise
	 * @return true if this map contains no key-value pairs
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * removes all mappings from the map. The map will 
	 * be empty after this call returns
	 */
	public void clear() {
		for (int i = 0; i < cap; ++i) {
			array[i] = E;
		}
		size = 0;
		currlf = 0;
	}
	
	/**
	 * returns a string representation of the map
	 * @return a string representation of the map
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("cur load factor : %.2f \n", currlf));
		sb.append(String.format("max load factor : %.2f \n", lf));
		sb.append(String.format("current size    : %d\n", size));
		sb.append(String.format("current capacity: %d\n", cap));
		
		for (int i = 0; i < cap; ++i) {
			if (!array[i].equals(E)) {
				sb.append(String.format("%d: %s\n", i, array[i].toString()));
			}
		}
		return sb.toString();
	}
	
	/**
	 * returns the smallest prime number greater than 2n
	 * used in rehash()
	 * @param n
	 * @return the smallest prime number greater than 2n
	 */
	private static int nextCapacity(int n) {
		int newN = -1;
		
		boolean isPrime = false;
		for (int i = 2*n + 1; !isPrime; ++i) {
			isPrime = true;
			for (int j = 2; j < i/2; ++j) {
				if (i % j == 0) {
					isPrime = false;
					break;
				}
			}
			
			if (isPrime) {
				newN = i;
			}
		}
		
		return newN;
	}
	
	/**
	 * increases the internal storage capacity and copy all 
	 * existing entries.
	 */
	private void rehash() {
		int newLength = nextCapacity(cap);
		
		HashEntry<K,V>[] old = array;
		array = new HashEntry[newLength];
		cap = newLength;
		
		clear();
		
		for (int i = 0; i < old.length; ++i) {
			if (old[i] != E && old[i] != D) {
				put(old[i].key, old[i].value);
			}
		}
		
		
	}
}

	
