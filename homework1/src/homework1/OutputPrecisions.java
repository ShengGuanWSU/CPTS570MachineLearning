package homework1;

public class OutputPrecisions {
	

	 private double precisions;
	 private int iterations ;
	 
	 public OutputPrecisions(){
		 this.precisions=0.0;
		 this.iterations=0;
	 }
	 
	 public OutputPrecisions(double precisions, int iterations){
		 this.precisions=precisions;
		 this.iterations=iterations;
	 }
	 
	 public void setPrecisions(double precisions){
		 this.precisions=precisions;
	 }
	 public double getPrecisions(){
		 return precisions;
	 }
	 public int getIterations(){
		 return iterations;
	 }

}
