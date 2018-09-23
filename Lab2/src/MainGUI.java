import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainGUI {

    MainGUI() {
        JFrame frame = new JFrame("Лабораторная работа № 2");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);

        Font font = new Font("Courier",Font.BOLD,17);
        JLabel bigLabel = new JLabel("Метод прямоугольников");
        bigLabel.setFont(new Font("Courier",Font.BOLD,35));
        ButtonGroup group = new ButtonGroup();
        JRadioButton first = new JRadioButton("x^3+10x^2+20", false);
        first.setFont(font);
        first.setBackground(new Color(208,255,200));
        group.add(first);
        JRadioButton second = new JRadioButton("ln(x)+x^2+2", true);
        second.setFont(font);
        second.setBackground(new Color(208,255,200));
        group.add(second);
        JRadioButton third = new JRadioButton("(8x+20)/(2x^2+4x+3)", true);
        third.setFont(font);
        third.setBackground(new Color(208,255,200));
        group.add(third);
        JRadioButton fourth = new JRadioButton("1/7 * sqrt(6x^2+4x+10)", true);
        fourth.setFont(font);
        fourth.setBackground(new Color(208,255,200));
        group.add(fourth);
        JPanel integrals = new JPanel();
        JPanel out = new JPanel();
        JButton submit = new JButton("Вычислить");


        /*Вставка для ввода данных */
        integrals.setBackground(new Color(208,255,200));
        JLabel chooseIntegral = new JLabel("Выберите функцию:");
        chooseIntegral.setFont(font);
        JLabel chooseHigh = new JLabel("Введите верхний предел интегрирования:");
        chooseHigh.setFont(font);
        JLabel chooseLow = new JLabel("Введите нижний предет интегрирования:");
        chooseLow.setFont(font);
        JLabel chooseAccuracy = new JLabel("Введите точность:");
        chooseAccuracy.setFont(font);
        JLabel chooseMethod = new JLabel("Выберите способ разбиения:");
        chooseMethod.setFont(font);
        integrals.setLayout(new GridLayout(13,1));
        integrals.add(chooseIntegral);
        integrals.add(first);
        integrals.add(second);
        integrals.add(third);
        integrals.add(fourth);
        integrals.add(chooseHigh);
        JSpinner setHigh = new JSpinner(new SpinnerNumberModel(0,-10000,1000,0.01));
        integrals.add(setHigh);
        integrals.add(chooseLow);
        JSpinner setLow = new JSpinner(new SpinnerNumberModel(0,-10000,1000,0.01));
        integrals.add(setLow);
        integrals.add(chooseAccuracy);
        JSpinner setAccuracy = new JSpinner(new SpinnerNumberModel(0,0,10,1));
        integrals.add(setAccuracy);

        /*Вывод данных */
        Font font2 = new Font("Courier",Font.BOLD,30);
        out.setBackground(new Color(208,255,200));
        out.setLayout(new GridLayout(10,1));
        JLabel outAnswer = new JLabel("Ответ:");
        outAnswer.setFont(font2);
        JLabel outCount = new JLabel("Количество разбирений:");
        outCount.setFont(font2);
        JLabel outPogr = new JLabel("Полученная погрешность:");
        outPogr.setFont(font2);
        JLabel logAnswer = new JLabel("0");
        JLabel logCount = new JLabel("0");
        JLabel logPogr = new JLabel("0");
        ButtonGroup group1 = new ButtonGroup();
        JRadioButton middle = new JRadioButton("Средние прямоугольники");
        middle.setFont(font);
        middle.setSelected(true);
        middle.setBackground(new Color(208,255,200));
        JRadioButton left = new JRadioButton("Левые прямоугольники");
        left.setFont(font);
        left.setBackground(new Color(208,255,200));
        JRadioButton right = new JRadioButton("Правые прямоугольники");
        right.setFont(font);
        right.setBackground(new Color(208,255,200));
        group1.add(middle);
        group1.add(right);
        group1.add(left);
        out.add(chooseMethod);
        out.add(middle);
        out.add(right);
        out.add(left);
        out.add(outAnswer);
        out.add(logAnswer);
        out.add(outCount);
        out.add(logCount);
        out.add(outPogr);
        out.add(logPogr);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberOfFunction;
                if (first.isSelected()) {
                    numberOfFunction = 1;
                } else {
                    if (second.isSelected()) {
                        numberOfFunction = 2;
                    } else {
                        if (third.isSelected()) {
                            numberOfFunction = 3;
                        } else {
                            numberOfFunction = 4;
                        }
                    }
                }
                int numberOfMethod;
                if (left.isSelected()) {
                    numberOfMethod = 1;
                } else {
                    if (middle.isSelected()) {
                        numberOfMethod = 2;
                    } else {
                        numberOfMethod = 3;
                    }
                }

                Function function = new Function(numberOfFunction,numberOfMethod,(double)setLow.getValue(),
                        (double)setHigh.getValue(),Math.pow(10,(-1)*Integer.parseInt(setAccuracy.getValue().toString())));
                double one = BigDecimal.valueOf(function.getResult()).setScale((int)setAccuracy.getValue()+2, RoundingMode.HALF_UP).doubleValue();
                logAnswer.setText(Double.toString(one));
                logCount.setText(Integer.toString(function.getN()));
                double two = BigDecimal.valueOf(function.error).setScale((int)setAccuracy.getValue()+2, RoundingMode.HALF_UP).doubleValue();
                logPogr.setText(Double.toString(two));
                out.updateUI();
            }

        });



        frame.add(bigLabel);
        frame.add(integrals);
        frame.add(out);
        frame.add(submit);
        bigLabel.setBounds(5,5,600,40);
        integrals.setBounds(10,50,400,550);
        out.setBounds(450,50,400,500);
        submit.setBounds(800,610,120,30);

        frame.getContentPane().setBackground(new Color(208,255,200));
        frame.setSize(1000,700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainGUI();
        });
    }
}
