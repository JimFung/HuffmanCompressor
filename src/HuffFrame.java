import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HuffFrame extends JFrame {

	private static JButton input;
	private static JButton action;
	private static JTextArea status;
	private static JScrollPane statusScroll;
	private static JPanel buttonPanel;
	private static Compressor compressor;
	private static Export export;
	private static BufferedReader inStream;
	private static JFileChooser fileChooser;
	private static String text;

	public HuffFrame() {
		this.text = "";
		setSize(550, 200);
		setResizable(false);
		setTitle("Huffman Compressor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		initHud();
		setVisible(true);
	}

	private void initHud() {
		// set up status area
		status = new JTextArea(
				"Welcome! Import a .txt file or a file compressed with this program to get started.\n");
		status.setEditable(false);
		status.setLineWrap(true);

		statusScroll = new JScrollPane();
		statusScroll.setBorder(BorderFactory.createTitledBorder("Status"));
		statusScroll.setViewportView(status);

		// set up the buttons
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		input = new JButton("Import");
		input.addActionListener(new ImportListener(this));
		buttonPanel.add(input);

		action = new JButton("MAGIC TIME!");
		action.addActionListener(new CompressListener());
		buttonPanel.add(action);

		// set up file picker
		fileChooser = new JFileChooser(System.getProperty("user.dir"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("txt files",
				"txt"));

		add(statusScroll);
		add(buttonPanel);
	}

	private class ImportListener implements ActionListener {

		private HuffFrame frame;

		public ImportListener(HuffFrame huffFrame) {
			this.frame = huffFrame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				status.append("Importing File\n");
				try {
					inStream = new BufferedReader(new FileReader(
							fileChooser.getSelectedFile()));
					System.out.println(fileChooser.getSelectedFile().getPath());
					System.out.println(fileChooser.getSelectedFile()
							.getParent());
					while (true) {
						final String line = inStream.readLine();
						if (line == null) {
							break;
						}
						text += line;
					}
				} catch (FileNotFoundException e1) {
					status.append("File can not be found.\n");
				} catch (IOException e1) {
					status.append("There was an issue reading your file. Try again.\n");
				} finally {
					try {
						inStream.close();
					} catch (IOException e1) {
						status.append("File stream was not properly closed.\n");
					}
				}
				status.append("File Imported\n");
			}

		}
	}

	private class CompressListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			status.append("Compression Started\n");
			try {
				compressor = new Compressor();
				String result = compressor.compress(text);
				String encodings = compressor.printEncodingHash();
				System.out.println("Character Encodings: " + encodings);
				System.out.println("Compressed String: " + result);

				export = new Export(result, encodings, fileChooser
						.getSelectedFile().getParent());
				export.export();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			status.append("Compression Complete\n");
		}
	}
}
