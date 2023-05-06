import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DListTest {
	
	private DList<String> empty, single, multi;
	
	@BeforeEach
	void setUp() throws Exception {
		empty = new DList<>();
		single = new DList<>();
		multi = new DList<>();
		
		single.add("K");
		
		multi.add("H");
		multi.add("E");
		multi.add("L");
		multi.add("L");
		multi.add("O");
		multi.add("!");
	}
	
	@Test
	void testToString() {
		assertEquals("[ ]", empty.toString());
		assertEquals("[ ]", empty.toStringBwd());

		assertEquals("[ K ]", single.toString());
	}
	
	@Test
	void testAddE() {
		assertEquals("[ K ]", single.toString());
		assertTrue(single.size() == 1);
		
		assertEquals("[ H E L L O ! ]", multi.toString());
		assertTrue(multi.size() == 6);
		
		assertEquals("[ ]", empty.toString());
		assertTrue(empty.size() == 0);
	}

	@Test
	void testAddIntE() {
		single.add(0, "O");
		assertEquals("[ O K ]", single.toString());
		
		multi.add(0, "0");
		multi.add(5, "5");
		multi.add(multi.size(), "" + multi.size());
		assertEquals("[ 0 H E L L 5 O ! 8 ]", multi.toString());
	}

	@Test
	void testAddAll() {
		ArrayList<String> list = new ArrayList<>();
		list.add(":)");
		list.add(":D");
		list.add(":(");
		
		multi.addAll(0, list);
		multi.addAll(4, list);
		multi.addAll(multi.size(), list);
		assertEquals("[ :) :D :( H :) :D :( E L L O ! :) :D :( ]", multi.toString());
				
	}

	@Test
	void testClear() {
		multi.clear();
		assertEquals("[ ]", multi.toString());
		assertNull(multi.get(0));
	}

	@Test
	void testGet() {
		assertEquals("K", single.get(0));
		
		assertEquals("H", multi.get(0));
		assertEquals("O", multi.get(4));
		assertEquals("!", multi.get(multi.size() - 1));
	}

	@Test
	void testSet() {
		single.set(0, "O");
		assertEquals("[ O ]", single.toString());
		
		for (int i = 0; i < multi.size(); ++i) 
			multi.set(i, multi.get(i).toLowerCase());
		
		assertEquals("[ h e l l o ! ]", multi.toString());
	}

	@Test
	void testContains() {
		assertTrue(single.contains("K"));
		assertFalse(single.contains("O"));
		
		assertTrue(multi.contains("!"));
		assertTrue(multi.contains("L"));
		assertFalse(multi.contains("?"));
	}

	@Test
	void testIndexOf() {
		assertEquals(0, multi.indexOf("H"));
		assertEquals(multi.size() - 1, multi.indexOf("!"));
		assertEquals(2, multi.indexOf("L"));
		
		assertEquals(0, single.indexOf("K"));
	}

	@Test
	void testLastIndexOf() {
		assertEquals(3, multi.lastIndexOf("L"));
		
		assertEquals(0, single.lastIndexOf("K"));
	}

	@Test
	void testIsEmpty() {
		assertTrue(empty.isEmpty());
		
		assertFalse(single.isEmpty());
		single.clear();
		assertTrue(single.isEmpty());
		
		assertFalse(multi.isEmpty());
		multi.clear();
		assertTrue(multi.isEmpty());
	}
	
	@Test
	void testRemoveInt() {
		single.remove(0);
		assertTrue(single.isEmpty());
		
		assertEquals("[ ]", single.toString());
		
		multi.remove(0);
		assertEquals("[ E L L O ! ]", multi.toString());
		multi.remove(4);
		assertEquals("[ E L L O ]", multi.toString());
	}
	
	@Test
	void testRemoveE() {
		single.remove("K");
		assertTrue(single.isEmpty());
		assertEquals("[ ]", single.toString());
		
		multi.remove("H");
		assertEquals("[ E L L O ! ]", multi.toString());
		multi.remove("!");
		assertEquals("[ E L L O ]", multi.toString());
		
		multi.add("L");
		multi.remove("L");
		assertEquals("[ E L O L ]", multi.toString());
		multi.remove("L");
		assertTrue(multi.remove("L"));
		assertEquals("[ E O ]", multi.toString());
	}
	
	@Test
	void testEqualsObject() {
		assertFalse(single.equals(multi));
		DList<String> emptyTest = new DList<>();
		assertTrue(emptyTest.equals(empty));
		
		DList<String> multi1 = new DList<>();
		multi1.add("H");
		multi1.add("E");
		multi1.add("L");
		multi1.add("L");
		multi1.add("O");
		multi1.add("!");
		
		Object multiTest = (Object) multi1;
		assertTrue(multi.equals(multiTest));
	}

	@Test
	void testAddInOrder() {
		
		class StringCompA implements Comparator<String> {

		    public int compare(String s1, String s2) {
		        return (s1.compareTo(s2));
		    }
		}
		
		class StringCompD implements Comparator<String> {

		    public int compare(String s1, String s2) {
		        return (s2.compareTo(s1));
		    }
		}
		
		DList<String> sortedList = new DList<>(new StringCompA());
		sortedList.add("A");
		sortedList.add("B");
		sortedList.add("C");
		sortedList.add("E");
		sortedList.addInOrder("D");
		sortedList.addInOrder("F");
		sortedList.addInOrder("A");
		
		assertEquals("[ A A B C D E F ]", sortedList.toString());
		
		sortedList = new DList<>(new StringCompD());
		sortedList.add("E");
		sortedList.add("E");
		sortedList.add("E");
		sortedList.add("D");
		sortedList.add("C");
		sortedList.add("B");
		sortedList.add("A");
		sortedList.addInOrder("D");
		sortedList.addInOrder("A");

		assertEquals("[ E E E D D C B A A ]", sortedList.toString());
	}
	
	
	@Test
	void testIterator() {
		Iterator<String> itr = multi.iterator();
		int counter = 0;
		while(itr.hasNext()) {
			assertEquals(multi.get(counter), itr.next());
			++counter;
		}
	}
}
