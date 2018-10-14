import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphGUI {

    JFrame frame;
    GraphGUI() {
        frame = new JFrame("Лабораторная работа № 3");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null );

        JPanel panel = new JPanel();
        JButton submit = new JButton("Построить");
        JSpinner setX = new JSpinner(new SpinnerNumberModel(5,4,10,1));
        ButtonGroup group = new ButtonGroup();
        JRadioButton first = new JRadioButton("sin(x)", false);
        first.setFont(new Font("Courier New", Font.BOLD, 15));
        //first.setBackground(new Color(208,255,200));
        group.add(first);
        JRadioButton second = new JRadioButton("3x^2+5x+6", false);
        second.setFont(new Font("Courier New", Font.BOLD, 15));
        //second.setBackground(new Color(208,255,200));
        group.add(second);
        JRadioButton third = new JRadioButton("e^x+2", false);
        third.setFont(new Font("Courier New", Font.BOLD, 15));
        //third.setBackground(new Color(208,255,200));

        panel.add(new Label("Введите количество разбиений"));
        panel.add(setX);
        panel.add(new Label("Выберите функцию"));
        group.add(third);
        panel.add(first);
        panel.add(second);
        panel.add(third);
        panel.add(submit);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel chartPanel = new JPanel();
                int size = (int)setX.getValue();
                int numberOfFunction;
                if (first.isSelected()) {
                    numberOfFunction =1;
                } else {
                    if (second.isSelected()) {
                        numberOfFunction = 2;
                    } else {
                        numberOfFunction = 3;
                    }
                }
                double x[] = new double[size+1];
                double step = 3*Math.PI/(2*size);
                double k =0;
                for (int i = 0; i <= size; i++) {
                    x[i] = k;
                    k +=step;
                }
                Approximation approximation = new Approximation(x,numberOfFunction,size+1);
                approximation.mnk();
                chartPanel = createGraph(approximation.coefficients1,approximation.coefficients2, x, size+1, numberOfFunction);
                frame.getContentPane().add(chartPanel);
                chartPanel.setBounds(210,0,770,550);
            }
        });

        frame.add(panel);
        panel.setBounds(0,0,200,500);

        frame.setSize(1000,600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GraphGUI();
        });
    }

    JPanel createGraph(double koef1[], double koef2[], double[] x, int size, int numberOfFunction) {
        double low = x[0];
        double high = x[size-1];
        XYSeries series1;

        switch (numberOfFunction) {
            case 1: {
                series1 = new XYSeries("sin(x)");
                for (double i = low; i <= high; i += 0.1) {
                    series1.add(i, Math.sin(i));
                }
                break;
            }
            case 2: {
                series1 =  new XYSeries("3x^2+5x+6");
                for (double i = low; i <= high; i += 0.1) {
                    series1.add(i, 3*Math.pow(i,2)+5*i+6);
                }
                break;
            }
            default: {
                series1 =  new XYSeries("e^x+2");
                for (double i = low; i <= high; i += 0.1) {
                    series1.add(i, Math.pow(Math.E,i)+2);
                }
                break;
            }
        }

        XYSeries series2 = new XYSeries(koef1[0]+"*x^2+"+koef1[1] + "*x+" + koef1[2]);
        for(double i = low; i <= high; i+=0.1){
            series2.add(i, koef1[0]*Math.pow(i,2)+koef1[1]*i+koef1[2]);
        }

        XYSeries series3 = new XYSeries(koef2[0]+"*x^2+"+koef2[1] + "*x+" + koef2[2]);
        for(double i = x[0]; i <= x[size-1]; i+=0.1){
            series3.add(i, koef2[0]*Math.pow(i,2)+koef2[1]*i+koef2[2]);
        }

        XYSeriesCollection dataset1 = new XYSeriesCollection();
        XYSeriesCollection dataset2 = new XYSeriesCollection();
        XYSeriesCollection dataset3 = new XYSeriesCollection();
        dataset1.addSeries(series1);
        dataset2.addSeries(series2);
        dataset3.addSeries(series3);

        XYPlot plot = new XYPlot();
        plot.setDataset(0, dataset1);
        plot.setDataset(1, dataset2);
        plot.setDataset(2, dataset3);

        plot.setRenderer(0, new XYSplineRenderer());//use default fill paint for first series
        XYSplineRenderer splinerenderer = new XYSplineRenderer();
        splinerenderer.setSeriesFillPaint(0, Color.BLUE);
        XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
        splinerenderer2.setSeriesFillPaint(1, Color.GREEN);
        plot.setRenderer(1, splinerenderer);
        plot.setRenderer(2, splinerenderer2);
        plot.setRangeAxis(0, new NumberAxis("Y"));
        plot.setRangeAxis(1, new NumberAxis(koef1[0]+"*x"+koef1[1]));
        plot.setRangeAxis(2, new NumberAxis(koef2[0]+"*x"+koef2[1]));
        plot.setDomainAxis(new NumberAxis("X"));

        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 1);
        plot.mapDatasetToRangeAxis(2, 2);

        JFreeChart chart = new JFreeChart("MNK", new Font("Courier New", Font.BOLD, 20), plot, true);
        chart.setBackgroundPaint(Color.WHITE);
        JPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }
}
