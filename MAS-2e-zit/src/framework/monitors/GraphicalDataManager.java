package framework.monitors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * 
 * A datamanager that outputs the XY-chart data to a dynamic graphical chart.
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class GraphicalDataManager extends DataManager {

	private final JFrame frame = new JFrame();

	private final String title;

	public GraphicalDataManager(String title, String xLabel, String yLabel) {
		super(xLabel, yLabel);
		if (title == null) {
			throw new IllegalArgumentException();
		}
		this.title = title;
		initiateGUI();
	}

	private JFreeChart createChart(final String title, final String xAxis, final String yAxis, final XYDataset dataset) {
		return ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, false, true, false);
	}

	public void changeLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	private void initiateGUI() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(getSeries());
		final JFreeChart chart = createChart(title, getXLabel(), getYLabel(), dataset);

		final ChartPanel chartPanel = new ChartPanel(chart);

		final JPanel content = new JPanel(new BorderLayout());
		content.add(chartPanel);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		frame.setContentPane(content);

		frame.setBounds(0, 0, 450, 300);
		frame.setVisible(true);
	}

	//
	// private class Graph extends JPanel implements ComponentListener {
	// private final int offset = 75;
	//
	// private int graphWidth = 600;
	// private int graphHeight = 400;
	// private int totalWidth = graphWidth + 2 * offset;
	// private int totalHeight = graphHeight + 2 * offset;
	//
	// public Graph() {
	// setLayout(null);
	// addComponentListener(this);
	// }
	//
	// private void sizeChanged() {
	// graphWidth = getSize().width - 2 * offset;
	// graphHeight = getSize().height - 2 * offset;
	// graphWidth = (graphWidth < 0) ? 0 : graphWidth;
	// graphHeight = (graphHeight < 0) ? 0 : graphHeight;
	// totalWidth = getSize().width;
	// totalHeight = getSize().height;
	// }
	//
	// @Override
	// protected void paintComponent(Graphics g) {
	// if (!shouldUpdate) {
	// return;
	// }
	// Graphics2D g2d = (Graphics2D) g;
	// clearPrevious(g2d);
	// drawAxis(g2d);
	// drawData(g2d);
	// drawText(g2d);
	// }
	//
	// private void clearPrevious(Graphics2D g2d) {
	// g2d.setColor(Color.white);
	// g2d.fillRect(0, 0, totalWidth, totalHeight);
	// }
	//
	// private void drawData(Graphics2D g2d) {
	// g2d.setColor(Color.blue);
	// for (int i = 0; i < getXValues().size() - 1; i++) {
	// int x1 = scale(getXValues().get(i), getMinX(), getMaxX(), graphWidth);
	// int y1 = scale(getYValues().get(i), getMinY(), getMaxY(), graphHeight);
	// int x2 = scale(getXValues().get(i + 1), getMinX(), getMaxX(),
	// graphWidth);
	// int y2 = scale(getYValues().get(i + 1), getMinY(), getMaxY(),
	// graphHeight);
	// g2d.drawLine(offset + x1, totalHeight - offset - y1, offset + x2,
	// totalHeight - offset - y2);
	// }
	// }
	//
	// private int scale(double value, double minValue, double maxValue, int
	// size) {
	// double percentage = (value - minValue) / (maxValue - minValue);
	// return (int) Math.round(percentage * size);
	// }
	//
	// private void drawAxis(Graphics2D g2d) {
	// g2d.setColor(Color.black);
	// int left = offset;
	// int top = offset;
	// int bottom = totalHeight - left;
	// int right = totalWidth - left;
	// g2d.drawLine(left, top, left, bottom);
	// g2d.drawLine(left, bottom, right, bottom);
	// int nbTicks = 10;
	// for (int i = 0; i <= nbTicks; i++) {
	// int x = left + i * (right - left) / nbTicks;
	// String text = "" + round(getMinX() + i * (double) (getMaxX() - getMinX())
	// / nbTicks);
	// g2d.drawLine(x, bottom - 5, x, bottom + 5);
	// g2d.drawString(text, x - 10, bottom + 20);
	// }
	// for (int i = 0; i <= nbTicks; i++) {
	// int y = top + i * (bottom - top) / nbTicks;
	// String text = "" + round(getMaxY() - i * (double) (getMaxY() - getMinY())
	// / nbTicks);
	// g2d.drawLine(left - 5, y, left + 5, y);
	// g2d.drawString(text, left - 40, y + 5);
	// }
	// }
	//
	// private double round(double d) {
	// return Math.round(d * 100D) / 100D;
	// }
	//
	// private void drawText(Graphics2D g2d) {
	// g2d.setColor(Color.black);
	// int textOffset = g2d.getFontMetrics().getHeight();
	// int titleWidth = g2d.getFontMetrics().stringWidth(title);
	// int xLabelWidth = g2d.getFontMetrics().stringWidth(getXLabel());
	// g2d.drawString(title, (totalWidth - titleWidth) / 2, textOffset);
	// g2d.drawString(getXLabel(), (totalWidth - xLabelWidth) / 2, totalHeight -
	// textOffset);
	// g2d.translate(textOffset, totalHeight / 2);
	// g2d.rotate(-Math.PI / 2);
	// g2d.drawString(getYLabel(), 0, 0);
	// g2d.rotate(Math.PI / 2);
	// g2d.translate(-textOffset, -totalHeight / 2);
	// }
	//
	// @Override
	// public void componentHidden(ComponentEvent e) {
	// }
	//
	// @Override
	// public void componentMoved(ComponentEvent e) {
	// }
	//
	// @Override
	// public void componentResized(ComponentEvent e) {
	// sizeChanged();
	// }
	//
	// @Override
	// public void componentShown(ComponentEvent e) {
	// }
	// }

	public static void main(String[] args) throws InterruptedException {
		GraphicalDataManager gm = new GraphicalDataManager("Title", "xLabel", "yLabel");
		long i = 1;
		Random random = new Random();
		while (true) {
			gm.addValue(i, random.nextInt(100) + 1);
			i++;
			Thread.sleep(1000);
		}
	}

	@Override
	protected void onValueAdded(double xValue, double yValue) {

	}

	@Override
	public void terminate() {
		frame.dispose();
	}

	public void changeSize(int width, int height) {
		if (width < 0 || height < 0) {
			throw new IllegalArgumentException();
		}
		frame.setSize(width, height);
	}

}
