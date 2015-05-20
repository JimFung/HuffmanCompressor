import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Export {

	private static int start;
	private static int end;
	private static String text;
	private static String path;
	private static String encodings;
	private static DataOutputStream output;

	public Export(String text, String encodings, String path)
			throws FileNotFoundException {
		this.text = text;
		this.encodings = encodings;
		this.path = path;

		this.start = 0;
		if (text.length() <= 7) {
			this.end = text.length();
		} else {
			this.end = 7;
		}
	}

	public void export() {
		// TODO: write prefix tree to file.
		// String[] codes = encodings.split(",");
		// for (String s : codes) {
		// String[] keyValue = s.split(":");
		// System.out.println(Integer.toBinaryString(Integer
		// .parseInt(keyValue[0])));
		// }

		try {
			output = new DataOutputStream(new FileOutputStream(path
					+ "//compressed.txt"));

			while (end < text.length()) {
				byte temp = Byte.parseByte(text.substring(start, end), 2);
				output.writeByte(temp);
				if (end + 7 <= text.length()) {
					start = end;
					end += 7;
				} else {
					start = end;
					end = text.length();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
