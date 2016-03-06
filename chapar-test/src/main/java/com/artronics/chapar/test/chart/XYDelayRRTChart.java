package com.artronics.chapar.test.chart;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;
import java.util.List;

public class XYDelayRRTChart extends ApplicationFrame{
    private final static Logger log = Logger.getLogger(XYDelayRRTChart.class);
    private List<Long> rtt;

    public XYDelayRRTChart(String title,List<Long> rtt) {
        super(title);
        setRtt(rtt);

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "RTT",
                "Packet" ,
                "RTT" ,
                createDataset() ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }
    private XYDataset createDataset( )
    {
        assert rtt!=null;

        final XYSeries data = new XYSeries("RTT");
        for (int i = 0; i < rtt.size(); i++) {
            data.add(i,rtt.get(i));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries(data);
        return dataset;
    }

    public void setRtt(List<Long> rtt) {
        this.rtt = rtt;
    }
}
