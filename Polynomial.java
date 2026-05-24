

public class Polynomial{
    double[] coefficients;
    
    public Polynomial(){
        coefficients = new double[1];
    }
    public Polynomial(double[] co) {
    	coefficients = new double[co.length];
    	for(int i = 0; i < co.length; i++) {
    		coefficients[i] = co[i];
    	}
    }
    public Polynomial add(Polynomial poly) {
    	double[] c = new double[Math.max(coefficients.length, poly.coefficients.length)];
    	for(int i = 0; i < coefficients.length; i++) {
    		c[i] = this.coefficients[i];
    	}
    	for(int i = 0; i < poly.coefficients.length; i++) {
    		c[i] += poly.coefficients[i];
    	}
    	Polynomial p = new Polynomial(c);
    	return p;
    }
    public double evaluate(double x) {
    	double sum = 0;
    	for(int i = 0; i< coefficients.length; i++) {
    		sum += coefficients[i] * Math.pow(x, i);
    	}
    	return sum;
    }
    public boolean hasRoot(double x) {
    	return evaluate(x) == 0;
    }
}