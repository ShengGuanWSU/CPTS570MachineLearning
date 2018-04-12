package homework2;

public class OutputConfusion {
	
	 private double actualLabel;
	 private double predictLabel;
	 private int count;
	 
	 public OutputConfusion(double actualLabel, double predictLabel){
			this.actualLabel=actualLabel; 
			this.predictLabel=predictLabel;
			this.count = 0;
	 }
	 

	 public double getActualLabel(){
		 return actualLabel;
	 }
	 public double getPredictLabel(){
		 return predictLabel;
	 }
	 public int getCount(){
		 return count;
	 }
	 
	 public int addCount( int c){
		 count= count+c;
		 return count;
	 }

}
