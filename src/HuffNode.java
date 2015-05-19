public class HuffNode {

	private HuffNode left;
	private HuffNode right;
	private byte key;
	private int total;

	public HuffNode() {
		this.left = null;
		this.right = null;
		this.key = 0;
		this.total = 0;
	}

	public HuffNode(byte key, Integer freq) {
		this.key = key;
		this.total = freq;
		this.left = null;
		this.right = null;
	}

	public byte getKey() {
		return key;
	}

	public int getFreq() {
		return total;
	}

	public void addLeft(HuffNode left) {
		this.left = left;
		total += left.getFreq();
	}

	public void addRight(HuffNode right) {
		this.right = right;
		total += right.getFreq();
	}

	public HuffNode getLeft() {
		return left;
	}

	public HuffNode getRight() {
		return right;
	}

	public String toString() {
		return "key: " + key + " Freq: " + total;
	}
}
