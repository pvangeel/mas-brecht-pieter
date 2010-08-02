package framework.monitors;

import framework.utils.Excel;
import framework.utils.Excel.ColumnData;

/**
 * 
 * A datamanager that outputs the XY-chart data to an excell file
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class ExcelDataManager extends DataManager {
	
	private final String filename;

	public ExcelDataManager(String filename, String xLabel, String yLabel) {
		super(xLabel, yLabel);
		if (filename == null) {
			throw new IllegalArgumentException();
		}
		this.filename = filename;
	}

	@Override
	protected void onValueAdded(double xValue, double yValue) {
	}

	@Override
	public void terminate() {
		ColumnData xData = new ColumnData(getXLabel(), getXValues());
		ColumnData yData = new ColumnData(getYLabel(), getYValues());
		Excel.export(filename, xData, yData);
	}

}
