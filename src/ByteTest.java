public class ByteTest {
	public static void main(String args[]) {
		byte zero = 0;
		System.out.println(zero);

		// changing the last digit to a 1
		zero = (byte) (zero | 1);
		System.out.println(zero);

		// appending a zero to the end
		zero = (byte) (zero << 1);
		System.out.println(zero);

		// changing the last digit 1 to the end
		zero = (byte) (zero << 1 | 1);
		System.out.println(zero);

		// appending a zero to the end
		zero = (byte) (zero << 1);
		System.out.println(zero);

	}
}
