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
            Scene scene = new Scene(root,750,300);
            scene.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
            primaryStage.setScene(scene);
            root.setId("pane");
            primaryStage.setTitle("Лабораторная работа №3");

            GridPane panel = new GridPane();
            Button submit = new Button("Построить");
            final Spinner<Integer> setX = new Spinner<Integer>();
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 10, 5);
            setX.setValueFactory(valueFactory);
            final ToggleGroup group = new ToggleGroup();
            RadioButton first = new RadioButton("sin(x)");
            first.setToggleGroup(group);
            first.setSelected(true);
            RadioButton second = new RadioButton("3x^2+5x+6");
            second.setToggleGroup(group);
            second.setSelected(false);
            RadioButton third = new RadioButton("e^x+2");
            third.setToggleGroup(group);
            third.setSelected(false);
            Label label = new Label("Количество разбиений:");
            label.setFont(new Font("Arial",30));
            Label label2 = new Label("Выберите функцию:");
            label2.setFont(new Font("Arial",30));
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
            panel.add(label2,1,3);
            panel.add(first,1,4);
            panel.add(second,1,5);
            panel.add(third,1,6);
            panel.add(submit,1,7);
            panel.add(parabola1,2,1);
            panel.add(par1Text,2,2);
            panel.add(parabola2,2,3);
            panel.add(par2Text,2,4);



            submit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    int size = setX.getValue();
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

                   createGraph(approximation.coefficients1,approximation.coefficients2, x, size+1, numberOfFunction);
                    double a1 = BigDecimal.valueOf(approximation.coefficients1[0]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    double b1 = BigDecimal.valueOf(approximation.coefficients1[1]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    double c1 = BigDecimal.valueOf(approximation.coefficients1[2]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    double a2 = BigDecimal.valueOf(approximation.coefficients2[0]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    double b2 = BigDecimal.valueOf(approximation.coefficients2[1]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    double c2 = BigDecimal.valueOf(approximation.coefficients2[2]).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    par1Text.setText(a1 + "x^2 + " + b1 + "x + " + c1);
                    par2Text.setText(a2 + "x^2 + " + b2 + "x + " + c2);
                }
            });

            root.setLeft(panel);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    void createGraph(double koef1[], double koef2[], double[] x0, int size, int numberOfFunction) {
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
        series3.setName(a1+"*x^2 + "+b1 + "*x + " + c1);
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas2 = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas3 = FXCollections.observableArrayList();
        if (numberOfFunction == 1) {
            for (int i = 0; i < size; i ++) {
                datas.add(new XYChart.Data(x0[i], Math.sin(x0[i])));
            }
        } else {
            if (numberOfFunction == 2) {
                for (int i = 0; i < size; i ++) {
                    datas.add(new XYChart.Data(x0[i], 3*Math.pow(x0[i],2)+5*x0[i]+6));
                }
            } else {
                for (int i = 0; i < size; i ++) {
                    datas.add(new XYChart.Data(x0[i], Math.pow(Math.E,x0[i]) + 2));
                }
            }
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

