import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

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
		assertNull(multi.getHead());
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
}
