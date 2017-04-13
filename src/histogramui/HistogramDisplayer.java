package histogramui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import histogram.Histogram;
import histogram.HistogramIcon;

/**
 * GUI application to support testing a HistogramIcon. Supports displaying
 * histogram image, displaying data that generated the histogram, and 
 * runtime changes to the histogram (data and number of bars).
 */
public class HistogramDisplayer {
	private static final int ICON_WIDTH = 300;
	private static final int ICON_HEIGHT = 400;
	private static final int LAYOUT_GAP = 10;
	public static final int DEFAULT_NUMBER_BARS = 5;
	
	public static final int[] INITIAL_HISTOGRAM_DATA = new int[] {5, 6, 7, 8, 14};
	
	public HistogramDisplayer() {
		ObservableHistogram histogram = new ObservableHistogram(INITIAL_HISTOGRAM_DATA, DEFAULT_NUMBER_BARS);

		JFrame frame = new JFrame();
		frame.setTitle("Histogram");
		
		// Use BorderLayout to allow ControlPanel to stretch (CENTER),
		// while icon does not (EAST).
		frame.setLayout(new BorderLayout(LAYOUT_GAP, LAYOUT_GAP));
		frame.add(makeControlPanel(histogram), BorderLayout.CENTER);
		frame.add(makeHistogramLabel(histogram), BorderLayout.EAST);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private JLabel makeHistogramLabel(ObservableHistogram histogram) {
		final JLabel label = new JLabel(new HistogramIcon(histogram, ICON_HEIGHT, ICON_WIDTH));
		
		/*
		 * STUDENTS MUST ADD THE FOLLOWING:
		 */
		histogram.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				label.repaint();
			}
		});
		return label;
	}

	private JPanel makeControlPanel(ObservableHistogram histogram) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		panel.add(new DataPane(histogram));
		panel.add(makeItemCountLabel(histogram));
		panel.add(new ChangeBarCountPanel(histogram));
		
		return panel;
	}
	

	private Component makeItemCountLabel(final ObservableHistogram histogram) {
		final JLabel label = new JLabel();

		/*
		 * STUDENTS MUST ADD THE FOLLOWING:
		 */
		histogram.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				updateItemCount(histogram, label);
			}
		});
		updateItemCount(histogram, label);

		// Put the label in its own panel to better control the stretching.
		// Set panel's max width to very large to allow stretch;
		// Set panel's max height to 0 to prevent stretching (will still have room for contents)
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(label, BorderLayout.NORTH);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
		return panel;
	}
	
	private void updateItemCount(ObservableHistogram histogram, JLabel label) {
		int itemCount = 0;
		Iterator<Histogram.Bar> itr = histogram.iterator();
		while(itr.hasNext()) {
			itemCount += itr.next().getCount();
		}
		String text = "# Items in Histogram: " + itemCount
				+ ", Max Value: " + histogram.getMaxBarCount();
		label.setText(text);
	}
	
	public static void main(String[] args) {
		new HistogramDisplayer();
	}
}
