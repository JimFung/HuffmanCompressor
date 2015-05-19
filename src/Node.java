public class Node {

	private Node left;
	private Node right;
	private HuffNode character;
	private int value;

	public Node() {
		this.left = null;
		this.right = null;
		value = 0;
	}

	public Node(HuffNode tuple) {
		this.left = null;
		this.right = null;
		value = tuple.getFreq();
	}

	public void setLeft(Node node) {
		this.left = node;
		value = node.getValue();
	}

	public void setRight(Node node) {
		this.right = node;
		value = node.getValue();
	}

	public HuffNode getChar() {
		return this.character;
	}

	public void setValue() {
		value = left.getValue() + right.getValue();
	}

	public int getValue() {
		return value;
	}
}
