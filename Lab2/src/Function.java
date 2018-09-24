public class Function {
    int numberOfFunction;
    double highLimit;
    double lowLimit;
    int numberOfMethod;
    double result;
    double eps;
    int n;
    double error;
    double step;
    int a = 1;

    Function(int numberOfFunction, int numberOfMethod, double lowLimit, double highLimit, double eps, int a) {
        this.numberOfFunction = numberOfFunction;
        this.numberOfMethod = numberOfMethod;
        this.highLimit = highLimit;
        this.lowLimit = lowLimit;
        this.eps = eps;
        this.a = a;
    }

    public double rectangleMethod(double delta) {
        result = 0;
        for (double i = lowLimit; i < highLimit; i+=delta) {
            double x;
            if (numberOfMethod == 1) {
                x = i;
            } else {
                if (numberOfMethod == 2) {
                    x = i+delta/2;
                } else {
                    x = i+delta;
                }
            }
            if (numberOfFunction == 1) {
                result += delta * (Math.pow(x, 3) + 10 * Math.pow(x, 2) + 20);
            }
            if (numberOfFunction == 2) {
                result += delta * (Math.pow(x,0.5)+Math.pow(x, 2)+2);
            }
            if (numberOfFunction == 3) {
                result += delta * ((8 * x + 20) / (2 * Math.pow(x, 2) + 4 * x + 3));
            }
            if (numberOfFunction == 4) {
                result += delta * (Math.sqrt(6*Math.pow(x,2)+4*x+10))/7;
            }
        }
        result = result*a;
        return  result;
    }

    public int getN() {
        n = (int)Math.ceil((highLimit - lowLimit)/step);
        return  n;
    }

    public double getResult() {
        step = Math.pow(eps,0.25);
        double int1 = rectangleMethod(step);
        double int2 = rectangleMethod(step*2);
        error = Math.abs(int1 - int2)/3;
        while (error > eps){
            step = step/2;
            int1 = rectangleMethod(step);
            int2 = rectangleMethod(step*2);
            error = Math.abs(int1 - int2)/3;
        }
        result = int1;
        n = getN();
        return result;
    }

}

