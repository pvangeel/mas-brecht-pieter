package dashboard.graph;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class AvgPickupGraph extends ApplicationFrame {

	private static final long serialVersionUID = 3957044664715063809L;

    private XYSeries series;


//	private static Logger logger = Logger.getLogger(AvgPickupGraph.class);

    public AvgPickupGraph(final String title, final String xAxis, final String yAxis, final String legend) {
        super(title);

        this.series = new XYSeries("First");
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        
        final JFreeChart chart = createChart(title, xAxis, yAxis, dataset);

        final ChartPanel chartPanel = new ChartPanel(chart);

        final JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(content);
        
        this.pack();
        this.setVisible(true);

    }

    private JFreeChart createChart(final String title, final String xAxis, final String yAxis, final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
            title, 
            xAxis, 
            yAxis,
            dataset,
            PlotOrientation.VERTICAL,
            true, 
            true, 
            false
        );
        

        return chart;
    }

	public void addValue(double x, double y) {
		series.add(x, y);
	}
    
	public String toString() {
		return "AvgPickupGraph";
	}

	public void terminate() {
		
	}

}