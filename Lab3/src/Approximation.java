public class Approximation {

    double[] x;
    double y[];
    int n;
    double[] coefficients1,coefficients2 ;
    int k;

    Approximation(double[] x, double y[], int n, int k) {
        this.x = x;
        this.y = y;
        this.n = n;
        this.k = k;
    }


    /**
     * Функция для расчёта коэффициентов паработы по методу наименьших квадратов
     * @param x абсциссы исходных точек
     * @param y ординаты исходных точек
     * @param n количество точек
     * @param k степень полинома
     * @return массив с полученными коэффицентами
     */
    double[] countCoefficients(double x[], double y[], int n, int k) {
        double result[] = new double[k+1];  //результат
        double matrix[][] = new double[k+1][k+1];
        double free[] = new double[k+1];

        // заполняем матрицу для поиска коэффициентов
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= k; j++)
            {
                matrix[i][ j] = 0;
                for (int l = 0; l < n; l++)
                    matrix[i][ j] += Math.pow(x[l], i + j);
            }
        }
        // заполняем матрицу свободых членов
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j < n; j++)
                free[i] += Math.pow(x[j], i) * y[j];
        }

        double temp = 0;
        for (int i = 0; i <= k; i++) {
            if (matrix[i][i] == 0){
                for (int j = 0; j <= k; j++) {
                    if (j == i) continue;
                    if (matrix[j][i] !=0 && matrix[i][j] !=0){
                        for (int l = 0; l <= k; l++) {
                            temp = matrix[j][ l];
                            matrix[j][ l] = matrix[i][ l];
                            matrix[i][ l] = temp;
                        }
                        temp = free[j];
                        free[j] = free[i];
                        free[i] = temp;
                        break;
                    }
                }
            }
        }

        for (int i = 0; i <= k; i++) {
            for (int j = i + 1; j <= k; j++) {
                double M = matrix[j][ i] / matrix[i][ i];
                for (int m = i; m <= k; m++) {
                    matrix[j][m] -= M * matrix[i][ m];
                }
                free[j] -= M * free[i];
            }
        }


        for (int i = k ; i >= 0; i--) {
            double sumkoef = 0;
            for (int j = i; j <= k; j++)
            {
                sumkoef += matrix[i][ j] *result[j];
            }
            result[i] = (free[i] - sumkoef) / matrix[i][ i];
        }


        return result;
    }

    /**
     * Функция получает коэффициенты параболы, удалив наиболее удалённую точку
     * @param koef
     * @param x абсциссы точек
     * @param y ординаты точек
     * @return новая матрица коэффициентов
     */
    double[] deletePoint(double koef[], double x[], double y[]) {
        double max = -1;
        int index = -1;
        double x0[] = new double[n-1];
        double y0[] = new double[n-1];
        for (int i = 0; i < n; i++) {
            double yI = 0;
            for (int j = 0; j <= k; j++) {
                yI += koef[j]*Math.pow(x[i], j);
            }
            if (Math.abs(y[i] - yI) > max) {
                max = Math.abs(y[i] - (yI));
                index = i;
            }
        }
        int i = 0;
        int e = 0;
        while (i <= n-1) {
            if (i != index) {
                x0[e] = x[i];
                y0[e] = y[i];
                e++;
            }
            i++;
        }
        return countCoefficients(x0,y0,n-1,k);
    }

    void mnk(){
        this.coefficients1 = countCoefficients(x,y,n,k);
        this.coefficients2 = deletePoint(coefficients1,x,y);
    }
}
