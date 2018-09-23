import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SimpleIteration simpleIteration;
        int k; //количество неизвестных
        int t; //точность
        double matrix[][]; //матрица
        double B[]; //свободные члены
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Выберите способ ввода:\n f - ввод из файла, \n c - ввод с клавиатуры.");
            String check = in.nextLine();
            check = check.trim();
            while (!check.equals("f") && !(check.equals("c"))) {
                System.out.println("Повторите ввод");
                check = in.nextLine();
                check = check.trim();
            }
            if (check.equals("f")) {
                System.out.println("Введите путь к файлу");
                String filename = in.nextLine();
                try {
                    Scanner inFile = new Scanner(new File(filename));
                    t = inFile.nextInt();
                    k = inFile.nextInt();
                    matrix = new double[k][k];
                    B = new double[k];
                    System.out.println("Введите матрицу");
                    for (int i = 0; i < k; i++) {
                        for (int j = 0; j < k; j++) {
                            matrix[i][j] = inFile.nextDouble();
                        }
                        B[i] = inFile.nextDouble();
                    }
                    simpleIteration = new SimpleIteration(matrix, k, B, t);
                    check(simpleIteration,t);
                } catch (Exception exc) {
                    System.out.println("Ошибка при чтении с файла");
                }
            } else {
                if (check.equals("c")) {
                    System.out.println("Введите точность");
                    t = in.nextInt();
                    System.out.println("Введите количество неизвестных");
                    k = in.nextInt();
                    matrix = new double[k][k];
                    B = new double[k];
                    System.out.println("Выберите способ ввода:\n r - случайные коэффициенты, \n c - ввод с клавиатуры.");
                    in.nextLine();
                    check = in.nextLine();
                    while (!check.equals("r") && !(check.equals("c"))) {
                        System.out.println("Повторите ввод");
                        check = in.nextLine();
                    }
                    if (check.equals("c")) {
                        System.out.println("Введите матрицу");
                        for (int i = 0; i < k; i++) {
                            for (int j = 0; j < k; j++) {
                                matrix[i][j] = in.nextDouble();
                            }
                            B[i] = in.nextDouble();
                        }
                        simpleIteration = new SimpleIteration(matrix, k, B,t);
                        check(simpleIteration,t );
                    } else {
                        if (check.equals("r")) {
                            for (int i = 0; i < k; i++) {
                                for (int j = 0; j < k; j++) {
                                    Random r = new Random();
                                    if (i == j) {
                                        matrix[i][j] = 40 + 50 * r.nextDouble();
                                    } else {
                                        matrix[i][j] = -10 + 10 * r.nextDouble();
                                    }
                                }
                                Random r = new Random();
                                B[i] = -20 + 20 * r.nextDouble();
                            }
                            simpleIteration = new SimpleIteration(matrix,  k, B,t);
                            check(simpleIteration,t );
                        }
                    }
                }
            }
        } catch (Exception exc) {
            System.out.println("Ошибка при вводе данных. Повторите попытку");
        }
    }
    public static void printResult(SimpleIteration simpleIteration, int t) {
        simpleIteration.method();
        System.out.println("Количество итераций: " + simpleIteration.iter);
        System.out.println("Столбец неизвестных: " );
        for (int i = 0; i < simpleIteration.n; i++) {
            double truncatedDouble = BigDecimal.valueOf(simpleIteration.nextB[i]).setScale(t+2, RoundingMode.HALF_UP).doubleValue();
            System.out.println(truncatedDouble);
        }
        System.out.println("Столбец погрешностей: ");
        for (int i = 0; i < simpleIteration.n; i++) {
            double truncatedDouble = BigDecimal.valueOf(simpleIteration.epsB[i]).setScale(t+2, RoundingMode.HALF_UP).doubleValue();
            System.out.println(truncatedDouble);
        }
    }

    public  static  void check(SimpleIteration simpleIteration, int t) {
        if (!simpleIteration.checkDiagonal()) {
            if (!simpleIteration.changeMatrix()) {
                System.out.println("Матрицу невозможно привести к диагональному виду");
            } else {
                printResult(simpleIteration, t);
            }
        } else {
            printResult(simpleIteration, t);
        }
    }
}
