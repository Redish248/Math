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
        Font fontPlain = new Font("Courier",Font.PLAIN,17);
        JLabel bigLabel = new JLabel("Метод прямоугольников");
        bigLabel.setFont(new Font("Courier",Font.BOLD,25));
        ButtonGroup group = new ButtonGroup();
        JRadioButton first = new JRadioButton("x^3+10x^2+20", false);
        first.setFont(fontPlain);
        first.setBackground(new Color(208,255,200));
        group.add(first);
        JRadioButton second = new JRadioButton("sqrt(x)+x^2+2", true);
        second.setFont(fontPlain);
        second.setBackground(new Color(208,255,200));
        group.add(second);
        JRadioButton third = new JRadioButton("(8x+20)/(2x^2+4x+3)", true);
        third.setFont(fontPlain);
        third.setBackground(new Color(208,255,200));
        group.add(third);
        JRadioButton fourth = new JRadioButton("1/7 * sqrt(6x^2+4x+10)", true);
        fourth.setFont(fontPlain);
        fourth.setBackground(new Color(208,255,200));
        group.add(fourth);
        JPanel integrals = new JPanel();
        JPanel out = new JPanel();
        JButton submit = new JButton("Вычислить");


        /*Вставка для ввода данных */
        integrals.setBackground(new Color(208,255,200));
        JLabel chooseIntegral = new JLabel("Выберите функцию:");
        chooseIntegral.setFont(font);
        JLabel chooseHigh = new JLabel("Верхний предел интегрирования:");
        chooseHigh.setFont(font);
        JLabel chooseLow = new JLabel("Нижний предел интегрирования:");
        chooseLow.setFont(font);
        JLabel chooseAccuracy = new JLabel("Введите точность:");
        chooseAccuracy.setFont(font);
        JLabel chooseMethod = new JLabel("Выберите способ разбиения:");
        chooseMethod.setFont(font);
        integrals.setLayout(new GridLayout(11,1));
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
        Font font2 = new Font("Courier",Font.BOLD,25);
        out.setBackground(new Color(208,255,200));
        out.setLayout(new GridLayout(10,1));
        JLabel outAnswer = new JLabel("Ответ:");
        outAnswer.setFont(font2);
        JLabel outCount = new JLabel("Количество разбирений:");
        outCount.setFont(font2);
        JLabel outPogr = new JLabel("Полученная погрешность:");
        outPogr.setFont(font2);
        JLabel logAnswer = new JLabel();
        logAnswer.setFont(new Font("Courier",Font.PLAIN,20));
        JLabel logCount = new JLabel();
        logCount.setFont(new Font("Courier",Font.PLAIN,20));
        JLabel logPogr = new JLabel();
        logPogr.setFont(new Font("Courier",Font.PLAIN,20));
        ButtonGroup group1 = new ButtonGroup();
        JRadioButton middle = new JRadioButton("Средние прямоугольники");
        middle.setFont(fontPlain);
        middle.setSelected(true);
        middle.setBackground(new Color(208,255,200));
        JRadioButton left = new JRadioButton("Левые прямоугольники");
        left.setFont(fontPlain);
        left.setBackground(new Color(208,255,200));
        JRadioButton right = new JRadioButton("Правые прямоугольники");
        right.setFont(fontPlain);
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
                int numberOfMethod = 3;
                if (left.isSelected()) {
                    numberOfMethod = 1;
                }
                if (middle.isSelected()) {
                    numberOfMethod = 2;
                }

                double low = (double)setLow.getValue();
                double high = (double)setHigh.getValue();
                int a = 1;
                if (low > high) {
                    double t = low;
                    low = high;
                    high = t;
                    a = -1;
                }
                Function function = new Function(numberOfFunction,numberOfMethod,low,
                        high,Math.pow(10,(-1)*Integer.parseInt(setAccuracy.getValue().toString())),a);
                double one = BigDecimal.valueOf(function.getResult()).setScale((int)setAccuracy.getValue()+2, RoundingMode.HALF_UP).doubleValue();
                logAnswer.setText(Double.toString(one));
                logCount.setText(Integer.toString(function.getN()));
                double two = BigDecimal.valueOf(function.error).setScale((int)setAccuracy.getValue()+3, RoundingMode.HALF_UP).doubleValue();
                logPogr.setText(Double.toString(two));
                out.updateUI();
            }

        });



        frame.add(bigLabel);
        frame.add(integrals);
        frame.add(out);
        frame.add(submit);
        bigLabel.setBounds(5,5,600,30);
        integrals.setBounds(10,50,280,330);
        out.setBounds(400,50,350,350);
        submit.setBounds(180,400,120,30);

        frame.getContentPane().setBackground(new Color(208,255,200));
        frame.setSize(800,480);
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
