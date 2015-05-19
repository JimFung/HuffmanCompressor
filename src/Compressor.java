import java.util.HashMap;
import java.util.PriorityQueue;

public class Compressor {

	private HashMap<Byte, Integer> freq;
	private HashMap<Byte, String> encodings;
	private PriorityQueue<HuffNode> prefixTree;

	public Compressor() {
		freq = new HashMap<Byte, Integer>();
		prefixTree = new PriorityQueue<HuffNode>(11, new HuffTupleComparator());
		encodings = new HashMap<Byte, String>();
	}

	/**
	 * Compresses the inputed text using huffman's compression algorithm
	 * 
	 * @param text
	 *            the string that is to be compressed.
	 * @return The compressed string.
	 */
	public String compress(String text) {

		// compression logic
		getFreq(text.getBytes());
		buildTree();
		generateEncodings(prefixTree.peek(), "");

		// generating coded string
		String resp = "";
		for (byte b : text.getBytes()) {
			resp += encodings.get(b);
		}

		return resp;
	}

	/**
	 * Obtain the frequency of each character. This is done in order to create
	 * the prefix tree. Uses the character as a key in a hashmap that holds the
	 * number of time it appears as the value.
	 * 
	 * @param bytes
	 *            the characters from the string as an array of bytes
	 */
	private void getFreq(byte[] bytes) {
		for (byte key : bytes) {
			if (freq.containsKey(key)) {
				freq.put(key, freq.get(key) + 1);
			} else {
				freq.put(key, 1);
			}
		}
	}

	/**
	 * Creates the prefix tree. Starts by creating a forest of nodes, then uses
	 * a min heap to organize them. pulls the top 2 items off the heap and
	 * creates a new node, with a new frequency. Node is added back into min
	 * heap tree is complete when there is only 1 node left in the heap
	 */
	private void buildTree() {
		for (byte key : freq.keySet()) {
			prefixTree.add(new HuffNode(key, freq.get(key)));
		}
		while (prefixTree.size() > 1) {
			HuffNode temp = new HuffNode();
			temp.addLeft(prefixTree.poll());
			temp.addRight(prefixTree.poll());
			prefixTree.add(temp);
		}

	}

	/**
	 * Recursively traverses the prefix tree to generate the encoding for the
	 * character. encoding is only added to the encoding hash if the node is a
	 * leaf. Tail recursive, encoding is generated as the method progresses.
	 * 
	 * @param node
	 *            current node
	 * @param code
	 *            current encoding
	 */
	private void generateEncodings(HuffNode node, String code) {
		if (node.getLeft() == null && node.getRight() == null) {
			encodings.put(node.getKey(), code);
		}

		// traversing to the left
		if (!(node.getLeft() == null)) {
			// generateEncodings(node.getLeft(), (byte) (code << 1));
			generateEncodings(node.getLeft(), code + "0");
		}

		// traversing to the right
		if (!(node.getRight() == null)) {
			// generateEncodings(node.getRight(), (byte) (code << 1 | 1));
			generateEncodings(node.getRight(), code + "1");
		}

	}

	// HELPER FUNCTIONS

	protected void printTree(HuffNode node) {
		System.out.println(node.toString());

		if (!(node.getLeft() == null)) {
			printTree(node.getLeft());
		}
		if (!(node.getRight() == null)) {
			printTree(node.getRight());
		}

	}

	protected void printFreqHash() {
		for (Byte key : freq.keySet()) {
			System.out.println(key + ": " + freq.get(key));
		}
	}

	protected String printEncodingHash() {
		String result = "";
		for (Byte b : encodings.keySet()) {
			result += b + ":" + encodings.get(b) + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	/**
	 * Counts the total number of characters in the string. The prefix tree
	 * should be built until the top most node's value is equal to the number of
	 * characters in the string.
	 * 
	 * @return total number of characters in the string
	 */
	private int getTotal() {
		int total = 0;
		for (byte key : freq.keySet()) {
			total += freq.get(key);
		}
		return total;
	}
}
