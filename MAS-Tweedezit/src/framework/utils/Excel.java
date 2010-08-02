package framework.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 
 * Interface to Excel files
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class Excel {

	public static void export(String filename, ColumnData... data) {
		File file = new File(filename + ".xls");
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("sheet1", 0);
			addData(sheet, data);
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	private static void addData(WritableSheet sheet, ColumnData... data) throws RowsExceededException, WriteException {
		addColumnHeaders(sheet, data);
		addRows(sheet, data);
	}

	private static void addRows(WritableSheet sheet, ColumnData... data) throws RowsExceededException, WriteException {
		int dataSize = getDataSize(data);
		int row = 1;
		Object[] dataStrings = new Object[data.length];
		for (int index = 0; index < dataSize; index++) {
			for (int i = 0; i < data.length; i++) {
				dataStrings[i] = data[i].getData(index);
			}
			addCell(sheet, row, dataStrings);
			row++;
		}
	}

	private static void addColumnHeaders(WritableSheet sheet, ColumnData... data) throws RowsExceededException, WriteException {
		Object[] headers = new Object[data.length];
		for (int i = 0; i < data.length; i++) {
			headers[i] = data[i].getColumnName();
		}
		addCell(sheet, 0, headers);
	}

	private static int getDataSize(ColumnData... data) {
		int dataSize = 0;
		for (ColumnData columnData : data) {
			if (columnData.getDataSize() > dataSize) {
				dataSize = columnData.getDataSize();
			}
		}
		return dataSize;
	}

	private static void addCell(WritableSheet sheet, int row, Object... data) throws RowsExceededException, WriteException {
		for (int i = 0; i < data.length; i++) {
			if (isNumeric(data[i])) {
				sheet.addCell(new jxl.write.Number(i, row, (Double) data[i]));
			} else {
				sheet.addCell(new Label(i, row, data[i].toString()));
			}
		}
	}

	private static boolean isNumeric(Object object) {
		return Number.class.isAssignableFrom(object.getClass());
	}

	public static class ColumnData {
		private final String columnName;
		private final List<?> data;

		public ColumnData(String columnName, List<?> data) {
			if (columnName == null || data == null) {
				throw new IllegalArgumentException();
			}
			this.columnName = columnName;
			this.data = data;
		}

		public String getColumnName() {
			return columnName;
		}

		public int getDataSize() {
			return data.size();
		}

		public Object getData(int index) {
			if (index >= data.size()) {
				return "";
			}
			return data.get(index);
		}
	}
}
