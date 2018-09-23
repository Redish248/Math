public class SimpleIteration {

    private double matrix[][]; //матрица
    private double B[]; //свободные члены
    int n; //количество неизвестных
    int k; //точность
    int iter = 0; //количество итераций

    SimpleIteration(double[][] mas, int k, double[] last, int t) {
        this.matrix = mas;
        this.n = k;
        this.B = last;
        this.k = t;
    }

    /**
     * Проверка, является ли матрица диагональной.
     * @return вернёт true, если матрица соответствует диагональному виду.
     */
    public boolean checkDiagonal() {
        boolean checkDiag = true;
        for (int i = 0; i < n; i++) {
            checkDiag = checkString(i);
            if (!checkDiag) {
                break;
            }
        }
        return checkDiag;
    }

    /**
     * Вспомогательный метод, проверяющий одну строку.
     * @param strNumber номер строки
     * @return вернёт true, если строка правильная.
     */
    public boolean checkString(int strNumber) {
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Math.abs(matrix[strNumber][i]);
        }
        sum -= Math.abs(matrix[strNumber][strNumber]);
        if (sum < Math.abs(matrix[strNumber][strNumber])) {
            return  true;
        } else {
            return false;
        }
    }

    /**
     * Преобразование матрицы к диагональному виду.
     * @return вернёт true, если матрицу удалось преобразовать.
     */
    public boolean changeMatrix() {
        for (int i = 0; i < n; i++) {
            double[] a = matrix[i];
            int element = -1;
            double max = -1;
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += Math.abs(a[j]);
                if (a[j] > max) {
                    element = j;
                    max = a[j];
                }
            }
            sum -= Math.abs(max);
            if (sum < Math.abs(max)) {
                double m[] = matrix[i];
                matrix[i] = matrix[element];
                matrix[element] = m;
                double t = B[i];
                B[i] = B[element];
                B[element] = t;
            } else {
                return false;
            }
        }
        return checkDiagonal();
    }

    double prevB[]; //старые погрешности
    double nextB[]; //новые погрешности
    double B2[]; //новые свободные члены; после преобразования матрицы к нужному виду
    double matrixV[][]; //новая матрица (приведённая к виду, удобному для выполнения итераций)
    double epsB[] = new double[n]; //для проверки точности

    /**
     * Метод простых итераций.
     */
    public void method() {
        prevB = new double[n];
        nextB = new double[n];
        B2 = new double[n];
        matrixV = new double[n][n];
        epsB = new double[n];
        //приведение матрицы к виду, нужному для начала итераций
        for (int i = 0; i < n; i++) {
            int a = 1;
            if (matrix[i][i] > 0) {
                a = -1;
            }
            for (int j = 0; j < n; j++) {
                if (j == i) {
                    matrixV[i][j] = 0;
                } else {
                    matrixV[i][j] = matrix[i][j] / matrix[i][i]*a;
                }
            }
        }

        //заполнение массива со свободными членами из новой матрицы
        for (int i = 0; i < n; i++) {
            prevB[i] = B[i] / matrix[i][i];
        }

        //рассчёт погрешностей, итерации
        boolean action = true;
        while (action) {
            //при первой итерации столбец погрешностей равен стобцу свободных членов
            if (iter == 0) {
                for (int j = 0; j < n; j++) {
                    B2[j] = prevB[j];
                }
            }
            //вычисления новых погрешностей
            for (int i = 0; i < n; i++) {
                nextB[i] = B2[i];
                for (int j = 0; j < n; j++) {
                    nextB[i] += prevB[j] * matrixV[i][j];
                }
            }

            //вычисления разницы новых и старых погрешностей
            double max = Math.abs(nextB[1] - prevB[1]);
            for (int i = 0; i < n; i++) {
                epsB[i] = Math.abs(nextB[i] - prevB[i]);
                if (max < (Math.abs(nextB[i] - prevB[i]))) {
                    max = Math.abs(nextB[i] - prevB[i]);
                }
            }

            //проверка точности
            if (max < Math.pow(10,-k)) {
                action = false;
            }

            for (int j = 0; j < n; j++) {
                prevB[j] = nextB[j];
            }
            iter++;

        }

    }


}
