package homework2;

public class OutputSVs1 {
	
	 private double cValue;
	 private int nSV;
	 private int degree; 
	 
	 public OutputSVs1(){
		 this.cValue=0.0;
		 this.nSV=0;
	 }
	 
	 public OutputSVs1(double cValue, int degree, int nSV){
		 this.cValue=cValue;
		 this.degree=degree;
		 this.nSV=nSV;
	 }
	 
	
	 public double getCValue(){
		 return cValue;
	 }
	 
	 public int getNSV(){
		 return nSV;
	 }
	 
	 public int getDegree(){
		 return degree;
	 }

}
