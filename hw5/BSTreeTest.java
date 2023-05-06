import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BSTreeTest {
	
	private BSTree<String> mytree;
	
	@BeforeEach
	void setUp() throws Exception {
		
		class StringComp implements Comparator<String> {

		    public int compare(String s1, String s2) {
		        return (s2.compareTo(s1));
		    }
		}
		
		mytree = new BSTree<String>(new StringComp());
		
		mytree.add("GG");
		mytree.add("AG");
		mytree.add("NG");
		mytree.add("BG");
		mytree.add("BA");
		mytree.add("GA");
		mytree.add("Zi");
		mytree.add("TG");
		mytree.add("YE");
	}


	@Test
	void testAdd() {
		assertEquals("[ GG AG NG BG Zi BA GA TG YE ]" , mytree.toString());
		assertEquals(9, mytree.size());
	}

	@Test
	void testContains() {
		assertTrue(mytree.contains("GG"));
		assertTrue(mytree.contains("AG"));
		assertTrue(mytree.contains("NG"));
		assertTrue(mytree.contains("BG"));
		assertTrue(mytree.contains("BA"));
		assertTrue(mytree.contains("GA"));
		assertTrue(mytree.contains("Zi"));
		assertTrue(mytree.contains("TG"));
		
		assertFalse(mytree.contains("love"));
	}

	@Test
	void testRemove() {
		assertEquals("GG", mytree.remove("GG"));
		assertEquals("[ GA AG NG BG Zi BA TG YE ]" , mytree.toString());
		assertEquals(8, mytree.size());
		
		assertEquals("YE", mytree.remove("YE"));
		assertEquals("[ GA AG NG BG Zi BA TG ]" , mytree.toString());
		assertEquals(7, mytree.size());
		
		assertEquals(null, mytree.remove("love"));
	}

	@Test
	void testGetMax() {
		assertEquals("Zi", mytree.getMax());
	}

	@Test
	void testGetMin() {
		assertEquals("AG", mytree.getMin());
	}

	@Test
	void testSize() {
		assertEquals(9, mytree.size());
	}

	@Test
	void testIsEmpty() {
		assertFalse(mytree.isEmpty());
		mytree.clear();
		assertTrue(mytree.isEmpty());
	}

	@Test
	void testClear() {
		mytree.clear();
		assertEquals(0, mytree.size());
		assertEquals(null, mytree.root);
	}

	@Test
	void testToString() {
		assertEquals("[ GG AG NG BG Zi BA GA TG YE ]" , mytree.toString());
	}

	@Test
	void testToStringIn() {
		assertEquals("[ AG BA BG GA GG NG TG YE Zi ]" , mytree.toStringIn());
	}

	@Test
	void testToStringPre() {
		assertEquals("[ GG AG BG BA GA NG Zi TG YE ]" , mytree.toStringPre());
	}

	@Test
	void testToStringPost() {
		assertEquals("[ BA GA BG AG YE TG Zi NG GG ]" , mytree.toStringPost());
	}

}
