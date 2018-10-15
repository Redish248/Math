import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class GraphGUI {

    JFrame frame;
    GraphGUI(Approximation approximation) {
        frame = new JFrame("Лабораторная работа № 3");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null );

        JPanel panel = new JPanel();

        JLabel parabola1 = new JLabel("Первые коэффициенты:");
        parabola1.setFont(new Font("Courier New", Font.BOLD, 15));
        JLabel par1Text = new JLabel();
        JLabel parabola2 = new JLabel("Вторые коэффициенты");
        parabola2.setFont(new Font("Courier New", Font.BOLD, 15));
        JLabel par2Text = new JLabel();

        panel.setLayout(new GridLayout(4,1));
        panel.setBackground(new Color(208,255,200));
        panel.add(parabola1);
        panel.add(par1Text);
        panel.add(parabola2);
        panel.add(par2Text);

        JPanel chartPanel = new JPanel();

        approximation.mnk();
        chartPanel = createGraph(approximation);
        frame.getContentPane().add(chartPanel);
        chartPanel.setBounds(210,0,770,550);

        String par1 = "";
        String par2 = "";
        for (int i = approximation.k; i >= 0 ; i--) {
            par1 = par1.concat( BigDecimal.valueOf(approximation.coefficients1[i]).setScale(2, RoundingMode.HALF_UP).doubleValue() + "; ");
        }

        for (int i = approximation.k; i >= 0 ; i--) {
            par2 = par2.concat( BigDecimal.valueOf(approximation.coefficients2[i]).setScale(2, RoundingMode.HALF_UP).doubleValue() + "; ");
        }
        par1Text.setText(par1);
        par2Text.setText(par2);
        panel.updateUI();


        frame.add(panel);
        panel.setBounds(0,0,200,200);

        frame.getContentPane().setBackground(new Color(208,255,200));
        frame.setSize(1000,600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Введите количество точек:");
                int n = in.nextInt();
                System.out.println("Введите координаты х:");
                double x[] = new double[n];
                for (int i = 0; i < n; i++) {
                    x[i] = in.nextDouble();
                }
                System.out.println("Введите координаты y:");
                double y[] = new double[n];
                for (int i = 0; i < n; i++) {
                    y[i] = in.nextDouble();
                }
                System.out.println("Введите степень полинома:");
                int k = in.nextInt();
                Approximation approximation = new Approximation(x, y, n, k);
                new GraphGUI(approximation);
            } catch (Exception exc) {
                System.out.println("Повторите ввод");
            }
        });
    }

    JPanel createGraph(Approximation approximation) {

        XYSeries series1;

                series1 = new XYSeries("points");
                for (int i = 0; i < approximation.n; i ++) {
                    series1.add(approximation.x[i], approximation.y[i]);
                }


        XYSeries series2 = new XYSeries("function1");
        for(double i = 0; i <= 10; i+=0.1){
            double y = 0;
            for (int j = 0; j <= approximation.k; j++) {
                y += approximation.coefficients1[j]*Math.pow(i,j);
            }
            series2.add(i, y);
        }

        XYSeries series3 = new XYSeries("function 2");
        for(double i = 0; i <= 10; i+=0.1){
            double y = 0;
            for (int j = 0; j <= approximation.k; j++) {
                y += approximation.coefficients2[j]*Math.pow(i, j);
            }
            series3.add(i, y);
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

        XYSplineRenderer splinerenderer0 = new XYSplineRenderer();
        splinerenderer0.setSeriesLinesVisible(0,false);

        plot.setRenderer(0, splinerenderer0);//use default fill paint for first series
        XYSplineRenderer splinerenderer = new XYSplineRenderer();
        splinerenderer.setSeriesFillPaint(0, Color.BLUE);
        XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
        splinerenderer2.setSeriesFillPaint(1, Color.GREEN);
        plot.setRenderer(1, splinerenderer);
        plot.setRenderer(2, splinerenderer2);
        NumberAxis numberAxis = new NumberAxis();
        plot.setRangeAxis(0, numberAxis);
        plot.setRangeAxis(1,numberAxis);
        plot.setRangeAxis(2, numberAxis);
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
