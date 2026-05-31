import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial{
    double[] coefficients;
    int[] degrees;
    
    public Polynomial(){
        coefficients = new double[1];
        degrees = new int[1];
    }
    public Polynomial(double[] co, int[] degrees) {
    	this.coefficients = co;
    	this.degrees = degrees;
    }
    public Polynomial(File f) {
    	try {
    		Scanner sc = new Scanner(f);
    		String s = sc.next();
    		
    		String[] s_all = s.split("");
    		String[] sl = s.split("[-+]");
    		
    		int len = sl.length;
    		if(s_all[0].equals("-")) {
    			len--;
    		}
    		String[] s_coefficients = new String[len];
    		String[] s_degrees = new String[len];
    		int i = 0;
    		coefficients = new double[len];
    		degrees = new int[len];
   

    		for(int j = 0; j< len; j++) {
    			// taking advantage of the fact that polynomials cannot have - degree nor type double degree
    			if(s_all[i].equals("-")) {
    				s_coefficients[j] = "-";
    				i++;
    				while(!(s_all[i].equals("-")) && !(s_all[i].equals("x")) && !(s_all[i].equals("+"))) {
        				s_coefficients[j] += s_all[i];
        				i++;
    				}
    				if(s_coefficients[j].equals("-") && !s_all[i].equals("x")) {
    					s_coefficients[j] = "-1";
    					i++;
    				}else if(s_coefficients[j].equals("-")){
    					s_coefficients[j] = "-1";
    				}
    			} 
    			else {
    				s_coefficients[j] = "";
    				
    				while(!(s_all[i].equals("-")) && !(s_all[i].equals("x")) && !(s_all[i].equals("+"))) {
        				s_coefficients[j] += s_all[i];
        				i++;
    				}
    				if(s_coefficients[j].equals("") && !s_all[i].equals("x")) {
    					s_coefficients[j] = "1";
    					i++;
    				}else if(s_coefficients[j].equals("")){
    					s_coefficients[j] = "1";
    				}
    			}
    			if(s_all[i].equals("x")) {
    				i++;
    				s_degrees[j] = "";
    				
    				
    				while(i < s_all.length && !(s_all[i].equals("-")) && !(s_all[i].equals("+"))) {
        				s_degrees[j] += s_all[i];
        				i++;
    				}
    				if(s_degrees[j].equals("")) {
    					s_degrees[j] = "1";
    				}
    			}else {
    				s_degrees[j] = "0";
    			}
    			
    			coefficients[j] = Double.parseDouble(s_coefficients[j]);
    			degrees[j] = Integer.parseInt(s_degrees[j]);
    		}

    		
    		
    	}catch(FileNotFoundException e) {
    		System.out.println("File was not found" + e);
    		coefficients = new double[1];
            degrees = new int[1];
    	}
    }
    /*
     * Add a new polynomial to the current polynomial
     * Since there are two arrays: one for the coefficients and one 
     * for the degrees, add the coefficients of same degree and concatenate 
     * the rest
     */
    public Polynomial add(Polynomial poly) {
    	int max_deg = -1;
    	
    	for(int deg: this.degrees) {
    		if(deg > max_deg) {
    			max_deg = deg;
    		}	
    	}
    	for(int deg: poly.degrees) {
    		if(deg > max_deg) {
    			max_deg = deg;
    		}
    	}
    	double[] combined = new double[max_deg+1];
    	for(int i = 0; i< this.coefficients.length; i++) {
    		combined[this.degrees[i]] += this.coefficients[i];
    	}
    	int count = this.degrees.length+poly.degrees.length;
    	for(int i = 0; i< poly.coefficients.length; i++) {
    		// if there is a duplicate make the new lists one smaller
    		if(combined[poly.degrees[i]] != 0) {
    			count--;
    		}
    		combined[poly.degrees[i]] += poly.coefficients[i];
    		// if the coefficients of a degree cancel out make it one smaller
    		if(combined[poly.degrees[i]] == 0) {
    			count--;
    		}
    	}
    	double[] n_co = new double[count];
    	int[] n_deg = new int[count];
    	int j=0;
    	for(int i = 0; i< combined.length; i++) {
    		if(combined[i] != 0) {
    			n_co[j] = combined[i];
    			n_deg[j] = i;
    			j++;
    		}
    	}
    	return new Polynomial(n_co, n_deg);
    	
    }
    
    public double evaluate(double x) {
    	double sum = 0;
    	for(int i = 0; i< coefficients.length; i++) {
    		sum += coefficients[i] * Math.pow(x, degrees[i]);
    	}
    	return sum;
    }
    public boolean hasRoot(double x) {
    	return evaluate(x) == 0;
    }
    public Polynomial clone() {
    	return new Polynomial(this.coefficients, this.degrees);
    }
    public Polynomial multiply(Polynomial poly) {
    	double[] temp_co;
    	int[] temp_deg;
    	Polynomial product = new Polynomial();
    	Polynomial temp_poly;
    	for (int i = 0; i < this.degrees.length; i++) {
    		temp_co = new double[poly.degrees.length];
    		temp_deg = new int[poly.degrees.length];
    		temp_poly = new Polynomial(temp_co, temp_deg);
    		for(int j = 0; j < poly.degrees.length; j++) {
    			temp_co[j] = this.coefficients[i] * poly.coefficients[j];
    			temp_deg[j] = this.degrees[i] + poly.degrees[j];
    		}
    		
    		if(i == 0) {
    			product = temp_poly.clone();
    		}else {
        		product = temp_poly.add(product);
    		}
    	}
    	return product;
    }
    public void saveToFile(String name) {
    	try (FileWriter w = new FileWriter(name)){
    		String s = ""; 
    		for(int i = 0; i< degrees.length; i++) {
    			if(coefficients[i] > 0 && i != 0) {
    				s+= "+";
    			}
    			if(coefficients[i] == (int)coefficients[i] && Math.abs(coefficients[i]) != 1 ) {
    				s += (int)coefficients[i];
    			}else if(Math.abs(coefficients[i]) != 1) {
    				s += coefficients[i];
    			}else if (coefficients[i] != 1){
    				s+= "-";
    			}
    			if(degrees[i] != 0 && degrees[i] != 1) {
    				s += "x" + degrees[i];
    			}
    			else if(degrees[i] == 1) {
    				s+= "x";
    			}else {
    				s+="1";
    			}
    		}
    		w.write(s);
    		w.close();
    	}catch(IOException e) {
    		System.out.println("Error");
    		e.printStackTrace();
    	}
    }
    @Override
    public String toString() {
    	
    	return "Degrees: "+ Arrays.toString(degrees) + "\nCoefficients: "+ Arrays.toString(coefficients);
    }
    
}