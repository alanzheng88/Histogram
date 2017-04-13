package histogramui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import histogram.Histogram;


/**
 * Decorate the Histogram class with observable functionality for 
 * notification when the histogram's data changes.
 */
public class ObservableHistogram extends Histogram{
	private List<ChangeListener> listeners = new ArrayList<ChangeListener>();

	public ObservableHistogram(int[] data, int numBars) {
		super(data, numBars);
	}
	
	@Override
	public void setData(int[] data) {
		super.setData(data);
		notifyListeners();
	}
	
	@Override
	public void setNumberBars(int numBars) {
		super.setNumberBars(numBars);
		notifyListeners();
	}
	
	
	// Observer Functions
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}
	
	private void notifyListeners() {
		if(listeners == null) {
			return;
		}
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}
}
