package kernelizedPerceptron;

public class MultiData {
	
	 private int[] features;
	 public int[] alpha;
	 private int label;
	 
	 public MultiData(){
		 this.features= new int[128];
		 this.alpha=new int[26];
		 this.label=0;
	 }
	 
	 public MultiData(int[]features,int label){
		 this.features=features;
		 this.alpha=new int[26];
		 this.label=label;
	 }
	 
	 public void setAlpha(int[] alpha){
		 this.alpha=alpha;
	 }
	 public int[] getFeatures(){
		 return features;
	 }
	 public int[] getAlpha(){
		 return alpha;
	 }
	 public int getLabel(){
		 return label;
	 }

}
