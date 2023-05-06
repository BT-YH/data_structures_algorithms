/**
 * @Author: Barry Tang
 * @Date: Feb 28, 2023
 * @Description:
 * 	Test code for HashMap.java
 * 
 */
public class HashMapTest {
	public static void main(String[] args) {
		
		
		System.out.println((-22)%7);
		
		HashMap<String, Integer> myMap = new HashMap<>();
		System.out.println(myMap.toString());
		
		// Test put()
		myMap.put("Jasper", 1);
		myMap.put("Givens", 2);
		myMap.put("Giorgi", 3);
		myMap.put("Paresishvili", 4);
		myMap.put("Barry", 5);
		myMap.put("Tang", 6);
		myMap.put("Alondra", 7);
		myMap.put("Rosas", 8);
		myMap.put("Anh", 9);
		myMap.put("Vu", 10);
		myMap.put("Phillip", 11);
		myMap.put("Pham", 12);
		myMap.put("Dan", 13);
		myMap.put("Angelillo", 14);
		myMap.put("John", 15);
		myMap.put("Llano", 16);
		myMap.put("Huy", 17);
		myMap.put("Ngo", 18);
		myMap.put("Ha", 19);
		myMap.put("Duong", 20);
		myMap.put("Kevin", 21);
		myMap.put("Estrada", 22);
		myMap.put("Samuel", 23);
		myMap.put("Hillesland", 24);
		
		System.out.println(myMap.toString());
		
		// Test indexOf()
		System.out.println("\nTest indexOf()");
		System.out.println(myMap.indexOf("Jasper"));
		System.out.println(myMap.indexOf("Barry"));
		System.out.println(myMap.indexOf("Huy"));
		System.out.println(myMap.indexOf("Trump"));
		System.out.println();
		
		// Test containsKey()
		System.out.println("\nTest containsKey()");
		System.out.println(myMap.containsKey("Jasper"));
		System.out.println(myMap.containsKey("Barry"));
		System.out.println(myMap.containsKey("Huy"));
		System.out.println(myMap.containsKey("Trump"));
		
		// Test containsValue()
		System.out.println("\nTest containsValue()");
		System.out.println(myMap.containsValue(1));
		System.out.println(myMap.containsValue(2));
		System.out.println(myMap.containsValue(23));
		System.out.println(myMap.containsValue(25));
		
		// Test get()
		System.out.println("\nTest get()");
		System.out.println(myMap.get("Barry"));
		System.out.println(myMap.get("Berry"));
		
		// Test remove()
		System.out.println("\nTest remove()");
		System.out.println(myMap.remove("Barry"));
		System.out.println(myMap.remove("Berry"));
		System.out.println(myMap.remove("Jasper"));
		System.out.println(myMap);
		
		// Test isEmpty()
		System.out.println("\nTest isEmpty()");
		System.out.println(myMap.isEmpty());
		
		// Test clear()
		System.out.println("\nTest clear()");
		myMap.clear();
		System.out.println(myMap.isEmpty());
		System.out.println(myMap);
	}
}
