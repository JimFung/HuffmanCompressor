import java.util.Comparator;

public class HuffTupleComparator implements Comparator<HuffNode> {

	@Override
	public int compare(HuffNode node1, HuffNode node2) {

		if (node1.getFreq() == node2.getFreq()) {
			return 0;
		}

		return node1.getFreq() < node2.getFreq() ? -1 : 1;
	}

}
