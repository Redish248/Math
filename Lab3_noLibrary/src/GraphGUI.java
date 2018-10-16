import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class GraphGUI extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root,1000,500);
            scene.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
            primaryStage.setScene(scene);
            root.setId("pane");
            primaryStage.setTitle("Лабораторная работа №3");

            GridPane panel = new GridPane();
            Button submit = new Button("Построить");
            final Spinner<Integer> setX = new Spinner<Integer>();
            final Spinner<Integer> setPolinom = new Spinner<Integer>();
            Label label = new Label("Количество точек:");
            label.setFont(new Font("Arial",30));
            Label labelX = new Label("Введите Х через пробел:");
            labelX.setFont(new Font("Arial",30));
            TextArea textAreaX = new TextArea();
            Label labelY = new Label("введите Y через пробел:");
            labelY.setFont(new Font("Arial",30));
            TextArea textAreaY = new TextArea();
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 3);
            setX.setValueFactory(valueFactory);
            SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 10, 3);
            setPolinom.setValueFactory(valueFactory2);
            Label labelE = new Label("Степень полинома:");
            labelE.setFont(new Font("Arial",30));
            Label parabola1 = new Label("Первая парабола:");
            parabola1.setFont(new Font("Arial",30));
            Label par1Text = new Label();
            par1Text.setFont(new Font("Courier New",20));
            Label parabola2 = new Label("Вторая парабола:");
            parabola2.setFont(new Font("Arial",30));
            Label par2Text = new Label();
            par2Text.setFont(new Font("Courier New",20));

            panel.setHgap(20);
            panel.setVgap(10);
            panel.setPadding(new Insets(10, 10, 10, 10));

            panel.add(label,1,1);
            panel.add(setX,1,2);
            panel.add(labelX,1,3);
            panel.add(textAreaX,1,4);
            panel.add(labelY,1,5);
            panel.add(textAreaY,1,6);
            panel.add(labelE,1,7);
            panel.add(new Label("3"),1,8);
            panel.add(submit,1,9);
            panel.add(parabola1,2,1);
            panel.add(par1Text,2,2);
            panel.add(parabola2,2,3);
            panel.add(par2Text,2,4);



            submit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    int size = setX.getValue();


                    String strX = textAreaX.getText().trim();
                    String strY = textAreaY.getText().trim();
                    char[] masX = strX.toCharArray();
                    char[] masY = strY.toCharArray();
                    double x[] = new double[size];
                    double y[] = new double[size];
                    int m = 0;
                    int n = 0;
                    while (m < masX.length) {
                        String s = "";
                        if (!(String.valueOf(masX[m]).equals(" "))) {
                            while ((m != masX.length) &&(!(String.valueOf(masX[m]).equals(" ")))) {
                                s = s.concat(String.valueOf(masX[m]));
                                m++;
                            }
                            x[n] = Integer.parseInt(s);
                            n++;
                        }
                        m++;
                    }
                    m = 0;
                    n = 0;
                    while (m < masY.length) {
                        String s = "";
                        if (!(String.valueOf(masY[m]).equals(" "))) {
                            while (!(String.valueOf(masY[m]).equals(" "))) {
                                s = s.concat(String.valueOf(masY[m]));
                                m++;
                            }
                            y[n] = Integer.parseInt(s);
                            n++;
                        }
                        m++;
                    }

                    Approximation approximation = new Approximation(x, y,size+1,1);
                    approximation.mnk();

                   createGraph(approximation.coefficients1,approximation.coefficients2, x,y, size+1);
                    double a1 = BigDecimal.valueOf(approximation.coefficients1[0]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    double b1 = BigDecimal.valueOf(approximation.coefficients1[1]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    double c1 = BigDecimal.valueOf(approximation.coefficients1[2]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    double a2 = BigDecimal.valueOf(approximation.coefficients2[0]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    double b2 = BigDecimal.valueOf(approximation.coefficients2[1]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    double c2 = BigDecimal.valueOf(approximation.coefficients2[2]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    String resK1 = "";
                    for (int i = 0; i < size; i++) {
                        resK1 = resK1.concat(approximation.coefficients1[i]+"; ");
                    }
                    String resK2 = "";
                    for (int i = 0; i < size; i++) {
                        resK2 = resK2.concat(approximation.coefficients1[i]+"; ");
                    }
                    par1Text.setText(resK1);
                    par2Text.setText(resK2);
                }
            });

            root.setLeft(panel);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    void createGraph(double koef1[], double koef2[], double[] x0, double[] y0, int size) {
        double a1 = BigDecimal.valueOf(koef1[0]).setScale(3, RoundingMode.HALF_UP).doubleValue();
        double b1 = BigDecimal.valueOf(koef1[1]).setScale(3, RoundingMode.HALF_UP).doubleValue();
        double c1 = BigDecimal.valueOf(koef1[2]).setScale(3, RoundingMode.HALF_UP).doubleValue();
        double a2 = BigDecimal.valueOf(koef2[0]).setScale(3, RoundingMode.HALF_UP).doubleValue();
        double b2 = BigDecimal.valueOf(koef2[1]).setScale(3, RoundingMode.HALF_UP).doubleValue();
        double c2 = BigDecimal.valueOf(koef2[2]).setScale(3, RoundingMode.HALF_UP).doubleValue();

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> numberLineChart = new LineChart<Number, Number>(x,y);
        numberLineChart.setTitle("МНК");
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        series1.setName("sin(x)");
        series2.setName(a1+"*x^2 + "+b1 + "*x + " + c1);
        series3.setName(a2+"*x^2 + "+b2 + "*x + " + c2);
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas2 = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas3 = FXCollections.observableArrayList();

            for (int i = 0; i < size; i ++) {
                datas.add(new XYChart.Data(x0[i], y0[i]));
            }


        for(double i = 0; i <= 4.7; i+=0.1){
            datas2.add(new XYChart.Data(i, koef1[0]*Math.pow(i,2)+koef1[1]*i+koef1[2]));
        }

        for(double i = 0; i <= 4.7; i+=0.1){
            datas3.add(new XYChart.Data(i, koef2[0]*Math.pow(i,2)+koef2[1]*i+koef2[2]));
        }


        series1.setData(datas);
        series2.setData(datas2);
        series3.setData(datas3);

        Scene newScene = new Scene(numberLineChart, 600,600);
        newScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        numberLineChart.getData().add(series1);
        numberLineChart.getData().add(series2);
        numberLineChart.getData().add(series3);


        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Графики");
        newWindow.setScene(newScene);

        // Set position of second window, related to primary window.
        newWindow.setX(newWindow.getX() + 200);
        newWindow.setY(newWindow.getY() + 100);

        newWindow.show();
    }
}

