package homework2;

public class OutputPrecisions1 {
	
	 private double trainAccuracy;
	 private double valAccuracy;
	 private double testAccuracy;
	 private double cValue  ;
	 private int degree; 
	 
	 public OutputPrecisions1(){
		 this.trainAccuracy=0.0;
		 this.valAccuracy = 0.0;
		 this.testAccuracy=0.0;
		 this.cValue=0.0;
		 this.degree=0;
	 }
	 
	 public OutputPrecisions1( double cValue, int degree, double trainAccuracy, double valAccuracy, double testAccuracy){
		 this.trainAccuracy=trainAccuracy;
		 this.valAccuracy =valAccuracy;
		 this.testAccuracy = testAccuracy;
		 this.cValue=cValue;
		 this.degree =degree;
	 }
	 
	 public double getTrainAccuracies(){
		 return trainAccuracy;
	 }
	 
	 public double getValAccuracies(){
		 return valAccuracy;
	 }
	 
	 public double getTestAccuracies(){
		 return testAccuracy;
	 }
	 
	 public double getCValues(){
		 return cValue;
	 }
	 
	 public int getDegree(){
		 return degree;
	 }

}
