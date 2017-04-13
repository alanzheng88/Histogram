package histogramui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * UI panel to change the number of bars the histogram has.
 * Also contains a label showing what value it's currently set to for the histogram.
 */
public class ChangeBarCountPanel extends JPanel {
	private static final int TEXT_FIELD_WIDTH = 10;
	private static final long serialVersionUID = 1L;

	private ObservableHistogram histogram;
	private JTextField textField;

	public ChangeBarCountPanel(ObservableHistogram histogram) {
		this.histogram = histogram;

		setLayout(new BorderLayout());
		
		add(makeNumBarsLabel(), BorderLayout.WEST);
		add(makeNumBarsTextBox(), BorderLayout.CENTER);
		add(makeSetNumBarsButton(), BorderLayout.EAST);
		
		add(makeCurrentNumBarsLabel(), BorderLayout.SOUTH);
		
		// Cause this panel not to stretch vertically (but horizontally OK)
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
	}
	
	private Component makeNumBarsLabel() {
		return new JLabel("# Bars: ");
	}
	private Component makeNumBarsTextBox() {
		textField = new JTextField(TEXT_FIELD_WIDTH);
		textField.setText("" + HistogramDisplayer.DEFAULT_NUMBER_BARS);
		textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));
		return textField;
	}
	private JButton makeSetNumBarsButton() {
		JButton btn = new JButton("Set");
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				changeNumberBars(textField.getText());
			}
		});
		return btn;
	}
	private void changeNumberBars(String numBarsStr) {
		try {
			int numBars = Integer.parseInt(numBarsStr);
			histogram.setNumberBars(numBars);
		} catch (NumberFormatException exception) {
			JOptionPane.showMessageDialog(
					this, 
					"Error: Must enter an integer number of bars.");
		}
	}
	
	private Component makeCurrentNumBarsLabel() {
		final JLabel displayCurrentBarCount = new JLabel("# Bar is set at: ");
		/*
		 * STUDENTS MUST ADD THE FOLLOWING:
		 */		
		histogram.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				updateCurrentNumBarsLabel(displayCurrentBarCount);				
			}

		});
		updateCurrentNumBarsLabel(displayCurrentBarCount);
		return displayCurrentBarCount;
	}
	private void updateCurrentNumBarsLabel(final JLabel label) {
		String text = "# Bar is set at: " + histogram.getNumberBars();
		label.setText(text);
	}
}