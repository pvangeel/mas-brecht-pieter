package framework.monitors;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

/**
 * A manager for holding XY-chart data
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public abstract class DataManager {

	private final XYSeries series = new XYSeries("");

	private final String xLabel, yLabel;

	public DataManager(String xLabel, String yLabel) {
		if (xLabel == null || yLabel == null) {
			throw new IllegalArgumentException();
		}
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}

	public void addValue(double xValue, double yValue) {
		series.add(xValue, yValue);
		onValueAdded(xValue, yValue);
	}
	
	protected XYSeries getSeries() {
		return series;
	}

	public double getMinX() {
		return series.getMinX();
	}

	public double getMinY() {
		return series.getMinY();
	}

	public double getMaxX() {
		return series.getMaxX();
	}

	public double getMaxY() {
		return series.getMaxY();
	}

	public String getXLabel() {
		return xLabel;
	}

	public String getYLabel() {
		return yLabel;
	}

	protected abstract void onValueAdded(double xValue, double yValue);

	@SuppressWarnings("unchecked")
	public List<Double> getXValues() {
		List<Double> xValues = new ArrayList<Double>();
		for (XYDataItem item : (List<XYDataItem>) series.getItems()) {
			xValues.add(item.getXValue());
		}
		return xValues;
	}

	@SuppressWarnings("unchecked")
	public List<Double> getYValues() {
		List<Double> yValues = new ArrayList<Double>();
		for (XYDataItem item : (List<XYDataItem>) series.getItems()) {
			yValues.add(item.getYValue());
		}
		return yValues;
	}

	public abstract void terminate();

}
