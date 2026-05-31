import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,3,2,5};
        int [] d1 = {0,1,2,3};
        Polynomial p1 = new Polynomial(c1, d1);
        double [] c2 = {1,-2,7.6,4,-9};
        int [] d2 = {0,2,5,100,3};
        Polynomial p2 = new Polynomial(c2, d2);
        Polynomial s = p2.add(p1);
        for(int i = 0; i<s.degrees.length; i++) {
        	System.out.print(s.degrees[i] + " ");
        	System.out.print(s.coefficients[i]+" ");
        	System.out.println();
        }
        
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        Polynomial t = p1.multiply(p2);
        for(int i = 0; i<t.degrees.length; i++) {
        	System.out.print(t.degrees[i] + " ");
        	System.out.print(t.coefficients[i]+" ");
        	System.out.println();
        }
    	File f = new File("PolyTest.txt");
    	
    	Polynomial p3 = new Polynomial(f);
    	System.out.println(p3);
    	
        p3.saveToFile("PolyTest.txt");
        
    }
}
