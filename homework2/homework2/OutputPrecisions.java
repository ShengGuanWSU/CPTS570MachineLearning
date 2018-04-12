package homework2;

public class OutputPrecisions {
	

	 private double trainAccuracy;
	 private double valAccuracy;
	 private double testAccuracy;
	 private double cValue  ;
	 
	 public OutputPrecisions(){
		 this.trainAccuracy=0.0;
		 this.valAccuracy = 0.0;
		 this.testAccuracy=0.0;
		 this.cValue=0.0;
	 }
	 
	 public OutputPrecisions( double cValue, double trainAccuracy, double valAccuracy, double testAccuracy){
		 this.trainAccuracy=trainAccuracy;
		 this.valAccuracy =valAccuracy;
		 this.testAccuracy = testAccuracy;
		 this.cValue=cValue;
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

}
