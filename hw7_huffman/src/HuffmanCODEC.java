
public class HuffmanCODEC {
	
	private HuffmanTree codeTree;
	
	public HuffmanCODEC(HuffmanTree tree) {
		codeTree = tree;
	}
	
	/**
	 * provide an encrypted string of the given message
	 * based on the huffman tree
	 * @param msg
	 * @return the encrypted message
	 */
	public String encode(String msg) {
		if (codeTree == null) {
			return null;
		}
		
		HuffmanNode root = codeTree.getRoot();
		
		if (root == null) {
			return null;
		}
		
		if (root.isLeaf()) { // check if msg contains chars other than root char
			char rootData = root.leafValue();
			for (int i = 0; i < msg.length(); i++) {
				if (rootData != msg.charAt(i)) {
					return null;
				}
			}
		} 
		else { // check if msg contains letters other than those in the root
			String rootData = root.getLetters();
			for (int i = 0; i < msg.length(); i++) {
				if (rootData.indexOf(msg.charAt(i)) == -1) {
					return null;
				}
			}
		}
		
		String code = "";
		HuffmanNode ptr = root;
		
		for (int i = 0; i < msg.length(); i++) {
			char currChar = msg.charAt(i);
			
			while (!ptr.isLeaf()) {
				
				if (ptr.getLeft().contains(currChar)) {
					// if the letter is in the left subtree
					ptr = ptr.getLeft();
					code += "0";
				} else {
					// if the letter is in the right subtree
					ptr = ptr.getRight();
					code += "1";
				}
			}
			// reset ptr to root to start encoding for the next letter
			ptr = root;
		}
		
		return code;
	}
	
	/**
	 * provide a decrypted message of the given encoding 
	 * based on the huffman tree
	 * @param bits
	 * @return the decrypted message
	 */
	public String decode(String bits) {
		if (codeTree == null) {
			return null;
		}
		
		HuffmanNode root = codeTree.getRoot();
		HuffmanNode ptr = root;
		String msg = "";

		if (root == null) {
			return null;
		}

		// generate a char array of bits
		char[] digits = bits.toCharArray();
		
		for (int i = 0; i < digits.length; i++) {
			
			if (digits[i] == '0') {
				ptr = ptr.getLeft();
			} else if (digits[i] == '1') {
				ptr = ptr.getRight();
			} else { // handle exception
				return null;
			}
			
			// reached the target letter
			// go back to root, decode 
			// next letter
			if (ptr.isLeaf()) { 
				msg += ptr.leafValue();
				ptr = root;
			} else if (i == digits.length - 1) { 
				// ptr is not leaf and at last digit
				// the encoded string is invalid
				return null;
			}
		}

		return msg;
	}
	
//	public static void main(String args[]) {
//		char[] chars = {'H','e','l','o'};
//		int[] weights = {20, 25, 30, 35};
//		
////		HuffmanTree myTree = new HuffmanTree(chars, weights);
//		HuffmanTree myTree = null;
//		HuffmanCODEC cryp = new HuffmanCODEC(myTree);
//		
//		System.out.println(cryp.encode("abc"));
//		System.out.println(cryp.encode("Hello"));
//		
//		System.out.println(cryp.decode("000110101"));
//		System.out.println(cryp.decode("0123"));
//	}
}


