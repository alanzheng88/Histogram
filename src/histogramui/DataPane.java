package histogramui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * UI Panel showing current data used to create histogram, plus allows user to 
 * change and randomize the data.
 */
public class DataPane extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int NUM_TEXT_LINES = 1;
	private static final int NUM_TEXT_COLS = 30;

	// For randomizing the data
	private static final int MAX_NUM_VALUES = 200;
	private static final int MAX_MAX_VALUE = 100;

	private ObservableHistogram histogram;
	private int[] data = HistogramDisplayer.INITIAL_HISTOGRAM_DATA;
	private JTextArea dataDisplay;

	public DataPane(ObservableHistogram histogram) {
		this.histogram = histogram;
		
		// Use a BorderLayout so that the text field expands when stretched.
		setLayout(new BorderLayout());
		add(makeDataLabel(), BorderLayout.NORTH);
		add(makeDataDisplayBox(), BorderLayout.CENTER);
		add(makeRowOfButtons(), BorderLayout.SOUTH);

		updateDataDisplay();
		registerForHistogramUpdates();
	}

	private Component makeDataLabel() {
		return new JLabel("Histogram Data:");
	}
	private Component makeDataDisplayBox() {
		dataDisplay = new JTextArea(NUM_TEXT_LINES, NUM_TEXT_COLS);
		dataDisplay.setLineWrap(true);
		return dataDisplay;
	}
	private Component makeRowOfButtons() {
		JPanel buttonRow = new JPanel();
		buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.LINE_AXIS));
		
		// Add glue to push buttons to the right.
		buttonRow.add(Box.createHorizontalGlue());
		buttonRow.add(makeSetDataButton());
		buttonRow.add(makeRandomizeDataButton());
		
		return buttonRow;
	}
	private Component makeSetDataButton() {
		JButton btn = new JButton("Set Data");
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				setDataFromInput(dataDisplay.getText());
			}
		});
		return btn;
	}
	private JButton makeRandomizeDataButton() {
		JButton btn = new JButton("Randomize Data");

		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				randomizeHistogramData(histogram);		
			}
		});
		return btn;
	}
	

	/*
	 * STUDENTS MUST ADD THE FOLLOWING:
	 */
	private void registerForHistogramUpdates() {
		histogram.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				updateDataDisplay();
			}
		});
	}

	private void updateDataDisplay() {
		// Sort the values just so it's easier for user to read the UI.
		int[] sortable = Arrays.copyOf(data, data.length);
		Arrays.sort(sortable);
		String displayString = "";
		for (int value : sortable) {
			displayString += value + ", ";
		}
		
		dataDisplay.setText(displayString);
	}						

	private void setDataFromInput(String newDataString) {
		try {
			data = makeIntArrayFromCSVString(newDataString); 
			histogram.setData(data);
			updateDataDisplay();
		} catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(this, "Data format error. Must be comma separated integers.");
		}
	}
	
	private int[] makeIntArrayFromCSVString(String text) throws NumberFormatException{
		ArrayList<Integer> values = new ArrayList<Integer>();
		String[] split = text.split(",");			
		for (String str : split) {
			if (str.trim().length() > 0) {
				values.add(Integer.parseInt(str.trim()));
			}
		}
		
		int[] returnArray = new int[values.size()];
		for (int i = 0; i < values.size(); i++) {
			returnArray[i] = values.get(i).intValue();
		}
		return returnArray;		
	}

	private void randomizeHistogramData(final ObservableHistogram histogram) {
		Random rand = new Random();
		int numValues = rand.nextInt(MAX_NUM_VALUES + 1);   // 0 OK
		int maxValue = rand.nextInt(MAX_MAX_VALUE) + 1;     // 0 not OK
		data = new int[numValues];
		for (int i = 0; i < numValues; i++) {
			data[i] = rand.nextInt(maxValue);
		}
		histogram.setData(data);
		updateDataDisplay();
	}
}
