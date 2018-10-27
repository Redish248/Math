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
import javafx.stage.Stage;


public class GraphGUI extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root,300,500);
            scene.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
            primaryStage.setScene(scene);
            root.setId("pane");
            primaryStage.setTitle("ЛР №4");

            Label function = new Label("Выберите функцию:");
            final ToggleGroup group = new ToggleGroup();
            RadioButton first = new RadioButton("x*x-2*y");
            first.setToggleGroup(group);
            first.setSelected(true);
            RadioButton second = new RadioButton("x+y");
            second.setToggleGroup(group);
            RadioButton third = new RadioButton("e^x-2*y");
            third.setToggleGroup(group);


            GridPane panel = new GridPane();
            Label startValue = new Label("Начальные условия:");
            Label y1 = new Label("y(");
            TextField textX = new TextField();
            TextField textY = new TextField();
            Label y2 = new Label(")=");
            Label end = new Label("Конец отрезка:");
            TextField textEnd = new TextField();
            Label accuracy = new Label("Введите точность:");
            final Spinner<Integer> setAccuracy = new Spinner<Integer>();
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 2);
            setAccuracy.setValueFactory(valueFactory);
            Button submit = new Button("Построить");

            panel.setHgap(20);
            panel.setVgap(10);
            panel.setPadding(new Insets(10, 10, 10, 10));

            GridPane gridPane = new GridPane();
            gridPane.setHgap(20);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.add(y1,1,1);
            textX.setPrefWidth(50);
            textY.setPrefWidth(50);
            textX.setPrefHeight(1);
            textY.setPrefHeight(1);
            gridPane.add(textX,2,1);
            gridPane.add(textY,4,1);
            gridPane.add(y2,3,1);

            GridPane gridPane2 = new GridPane();
            gridPane2.setVgap(10);
            gridPane2.setPadding(new Insets(10, 10, 10, 10));;
            gridPane2.add(function,1,1);
            gridPane2.add(first,1,2);
            gridPane2.add(second,1,3);
            gridPane2.add(third,1,4);

            panel.add(gridPane2,1,1);
            panel.add(startValue, 1,2);
            panel.add(gridPane,1,3);
            panel.add(end,1,4);
            textEnd.setMaxSize(185,5);
            panel.add(textEnd,1,5);
            panel.add(accuracy,1,6);
            panel.add(setAccuracy,1,7);
            panel.add(submit,1,8);


            submit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        double x0 = Double.parseDouble(textX.getText().trim());
                        double y0 = Double.parseDouble(textY.getText().trim());
                        double end = Double.parseDouble(textEnd.getText().trim());
                        int e = setAccuracy.getValue();
                        int number;
                        if (first.isSelected()) {
                            number = 1;
                        } else {
                            if(second.isSelected()) {
                                number = 2;
                            } else {
                                number = 3;
                            }
                        }
                        YEuler yEuler = new YEuler(x0, y0, end, e,number);
                        yEuler.method();
                        createGraph(yEuler);
                    } catch (Exception exc) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Ошибка ввода данных");
                        alert.setHeaderText("Поля ввода не должны оставяться пустыми,\nони должны содержать только цифры.\nДля разделителя используйте точку.");
                        alert.setContentText("Проверьте введённые данные!");

                        alert.showAndWait();
                    }
                }
            });

            root.setLeft(panel);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    void createGraph(YEuler yEuler) {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> numberLineChart = new LineChart<Number, Number>(x,y);
        numberLineChart.setTitle("Усовершенствованный метод Эйлера");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("function");
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();

        for(double i = yEuler.x0; i <= yEuler.end; i+=0.1){
            double yA = 0;
            for (int j = 0; j <= 4; j++) {
                yA += yEuler.coefficients[j]*Math.pow(i,j);
            }
            datas.add(new XYChart.Data(i,yA));
        }


        series1.setData(datas);

        Scene newScene = new Scene(numberLineChart, 600,600);
        newScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        numberLineChart.getData().add(series1);

        Stage newWindow = new Stage();
        newWindow.setTitle("Графики");
        newWindow.setScene(newScene);

        newWindow.setX(newWindow.getX() + 200);
        newWindow.setY(newWindow.getY() + 100);

        newWindow.show();
    }
    }

