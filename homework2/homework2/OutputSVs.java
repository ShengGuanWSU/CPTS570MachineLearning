package homework2;

public class OutputSVs {
	
	 private double cValue;
	 private int nSV;
	 
	 public OutputSVs(){
		 this.cValue=0.0;
		 this.nSV=0;
	 }
	 
	 public OutputSVs(double cValue, int nSV){
		 this.cValue=cValue;
		 this.nSV=nSV;
	 }
	 
	
	 public double getCValue(){
		 return cValue;
	 }
	 public int getNSV(){
		 return nSV;
	 }

}
