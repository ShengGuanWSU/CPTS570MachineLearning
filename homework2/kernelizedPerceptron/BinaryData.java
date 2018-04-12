package kernelizedPerceptron;

public class BinaryData {
	
	 private int[] features;
	 public int alpha;
	 private int label;
	 
	 public BinaryData(){
		 this.features= new int[128];
		 this.alpha=0;
		 this.label=0;
	 }
	 
	 public BinaryData(int[]features,int label){
		 this.features=features;
		 this.alpha=0;
		 this.label=label;
	 }
	 
	 public void setAlpha(int alpha){
		 this.alpha=alpha;
	 }
	 public int[] getFeatures(){
		 return features;
	 }
	 public int getAlpha(){
		 return alpha;
	 }
	 public int getLabel(){
		 return label;
	 }


}
