import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SListTest {
	
	private SList<String> empty, single, multi;
	
	@BeforeEach
	void setUp() throws Exception {
		empty = new SList<>();
		single = new SList<>();
		multi = new SList<>();
		
		single.addFront("K");
		
		multi.addFront("H");
		multi.addFront("E");
		multi.addFront("L");
		multi.addFront("L");
		multi.addFront("O");
		multi.addFront("!");
		
	}

	@Test
	void testAddFront() {
		assertEquals("[ K ]", single.toString());
		assertTrue(single.size() == 1);
		
		assertEquals("[ ! O L L E H ]", multi.toString());
		assertTrue(multi.size() == 6);
		
		assertEquals("[ ]", empty.toString());
		assertTrue(empty.size() == 0);
	}
	
	@Test
	void testAddBack() {
		multi.addBack("!");
		assertEquals("[ ! O L L E H ! ]", multi.toString());
		assertTrue(multi.size() == 7);
	}
	
	@Test
	void testAdd() {
		multi.add(2, "H");
		assertEquals("[ ! O H L L E H ]", multi.toString());
		assertTrue(multi.size() == 7);
		
		multi.add(0, "H");

	}
	
	@Test
	void testRemoveFront() {
		multi.removeFront();
		assertEquals("[ O L L E H ]", multi.toString());
		
		single.removeFront();
		assertEquals("[ ]", single.toString());
		
	}
	
	@Test
	void testRemoveBack() {
		multi.removeBack();
		assertEquals("[ ! O L L E ]", multi.toString());
		multi.removeBack();
		assertEquals("[ ! O L L ]", multi.toString());
		
		single.removeBack();
		assertEquals("[ ]", single.toString());
	}
	
	@Test
	void testRemoveBackFor() {
		multi.removeBackFor();
		assertEquals("[ ! O L L E ]", multi.toString());
		multi.removeBackFor();
		assertEquals("[ ! O L L ]", multi.toString());
		
		single.removeBackFor();
		assertEquals("[ ]", single.toString());
	}
}





