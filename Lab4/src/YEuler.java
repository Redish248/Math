public class YEuler {
    int e;
    double x0;
    double y0;
    double end;
    double x[];
    double y[];
    double coefficients[];
    int numberOfFunction;

    public YEuler(double x0, double y0, double end, int e, int numberOfFunction) {
        this.e = e;
        this.x0 = x0;
        this.y0 = y0;
        this.end = end;
        this.numberOfFunction = numberOfFunction;
    }

    public double function1(double x, double y) {
        return x*x-2*y;
    }

    public double function2(double x, double y) {
        return x+y;
    }

    public double function3(double x, double y) {
        return Math.pow(Math.E,x)-2*y;
    }

    public void method() {
        double h = 0.01;
        double lastY = y0;
        int n = (int)Math.round((end - x0)/h);
        boolean check = true;
        while (check) {
            x = new double[n];
            y = new double[n];
            x[0] = x0;
            y[0] = y0;
            for (int i = 1; i < n; i++) {
                x[i] = x[i - 1] + h;
                switch (numberOfFunction) {
                    case 1: {
                        y[i] = y[i - 1] + h * function1(x[i - 1] + h / 2, y[i - 1] + (h / 2) * function1(x[i - 1], y[i - 1]));
                        break;
                    }
                    case 2: {
                        y[i] = y[i - 1] + h * function2(x[i - 1] + h / 2, y[i - 1] + (h / 2) * function2(x[i - 1], y[i - 1]));
                        break;
                    }
                    default:  {
                        y[i] = y[i - 1] + h * function3(x[i - 1] + h / 2, y[i - 1] + (h / 2) * function3(x[i - 1], y[i - 1]));
                        break;
                    }
                }
            }

            if (Math.abs(lastY - y[n - 1]) > Math.pow(10,e*(-1))) {
                h = h/2;
                lastY = y[n-1];
                n = (int)Math.round((end - x0)/h);
            } else {
                check = false;
            }
        }
        Approximation approximation = new Approximation(x,y,n,4);
        coefficients = approximation.mnk(x,y,n,4);
    }

}
