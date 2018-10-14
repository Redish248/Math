public class Approximation {

    double[] x;
    double y[];
    int numberOfFunction;
    int n;
    double[] coefficients1,coefficients2 ;

    Approximation(double[] x, int numberOfFunction, int n) {
        this.x = x;
        this.numberOfFunction = numberOfFunction;
        this.n = n;
    }

    /**
     * Высчитывает коэффициенты y
     * @param x0  точки х из заданной функции
     * @param n  количество точек
     * @return  полученные коэффициенты Y
     */
    double[] createY(double x0[], int n) {
        double[] y0 = new double[n];
        switch (numberOfFunction) {
            case 1: {
                for (int i = 0; i < n; i++) {
                    y0[i] = Math.sin(x0[i]);
                }
                break;
            }
            case 2: {
                for (int i = 0; i < n; i++) {
                    y0[i] = 3*Math.pow(x0[i],2) + 5*x0[i] + 6;
                }
                break;
            }
            default: {
                for (int i = 0; i < n; i++) {
                    y0[i] = Math.pow(Math.E,x0[i]) + 2;
                }
                break;
            }
        }
        return y0;
    }

    /**
     * Функция для расчёта коэффициентов паработы по методу наименьших квадратов
     * @param x абсциссы исходных точек
     * @param y ординаты исходных точек
     * @param n количество точек
     * @return массив с полученными коэффицентами
     */
    double[] countCoefficients(double x[], double y[], int n) {
        double transMaitix[][] = new double[3][3];
        double result[] = new double[3];  //результат
        double matrix[][] = new double[3][3]; //матрица, из которой будет получен ответ
        double free[] = new double[3]; //матрица свободных членов
        //получение матрицы
        for (int i = 0; i < n; i++) {
            matrix[0][0] += Math.pow(x[i],4);
            matrix[0][1] += Math.pow(x[i],3);
            matrix[1][0] += Math.pow(x[i],3);
            matrix[2][0] += Math.pow(x[i],2);
            matrix[0][2] += Math.pow(x[i],2);
            matrix[1][1] += Math.pow(x[i],2);
            matrix[1][2] += x[i];
            matrix[2][1] += x[i];
            free[0] += Math.pow(x[i],2)*y[i];
            free[1] += x[i]*y[i];
            free[2] += y[i];
        }
        matrix[2][2] = n;
        //расчёт определителя
        double det = matrix[0][0]*matrix[1][1]*matrix[2][2];
        det += matrix[1][0]*matrix[2][1]*matrix[0][2];
        det += matrix[0][1]*matrix[1][2]*matrix[2][0];
        det -= matrix[0][2]*matrix[1][1]*matrix[2][0];
        det -= matrix[0][1]*matrix[1][0]*matrix[2][2];
        det -= matrix[0][0]*matrix[2][1]*matrix[1][2];
        //получение обратной матрицы
        transMaitix[0][0] = Math.pow(-1,0)*(matrix[1][1]*matrix[2][2]-matrix[1][2]*matrix[2][1]);
        transMaitix[0][1] = Math.pow(-1,1)*(matrix[1][0]*matrix[2][2]-matrix[2][0]*matrix[1][2]);
        transMaitix[0][2] = Math.pow(-1,2)*(matrix[1][0]*matrix[2][1]-matrix[2][0]*matrix[1][1]);
        transMaitix[1][0] = Math.pow(-1,1)*(matrix[0][1]*matrix[2][2]-matrix[2][1]*matrix[0][2]);
        transMaitix[1][1] = Math.pow(-1,2)*(matrix[0][0]*matrix[2][2]-matrix[2][0]*matrix[0][2]);
        transMaitix[1][2] = Math.pow(-1,3)*(matrix[0][0]*matrix[2][1]-matrix[2][0]*matrix[0][1]);
        transMaitix[2][0] = Math.pow(-1,2)*(matrix[0][1]*matrix[1][2]-matrix[1][1]*matrix[0][2]);
        transMaitix[2][1] = Math.pow(-1,3)*(matrix[0][0]*matrix[1][2]-matrix[1][0]*matrix[0][2]);
        transMaitix[2][2] = Math.pow(-1,4)*(matrix[0][0]*matrix[1][1]-matrix[1][0]*matrix[0][1]);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                transMaitix[i][j] = transMaitix[i][j] / det;
            }
        }
        //расчёт матрицы коэффицентоа
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i] += transMaitix[i][j]*free[j];
            }
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
    double[] deletePoint(double koef[],double x[], double y[]) {
        double max = -1;
        int index = -1;
        double x0[] = new double[n-1];
        double y0[] = new double[n-1];
        for (int i = 0; i < n; i++) {
            if (y[i] - (koef[0] * Math.pow(x[i], 2) + koef[1] * x[i] + koef[2]) > max) {
                max = Math.abs(Math.sin(x[i]) - (koef[0] * Math.pow(x[i], 2) + koef[1] * x[i] + koef[2]));
                index = i;
            }
        }
        int i = 0;
        int k = 0;
        while (i < n-1) {
            if (i != index) {
                x0[k] = x[i];
                y0[k] = y[i];
                k++;
            }
            i++;
        }
        return countCoefficients(x0,y0,n-1);
    }

    void mnk(){
        this.y = this.createY(this.x, this.n);
        this.coefficients1 = countCoefficients(x,y,n);
        this.coefficients2 = deletePoint(coefficients1,x,y);
    }
}
