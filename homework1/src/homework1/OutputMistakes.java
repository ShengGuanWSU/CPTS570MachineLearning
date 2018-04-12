package homework1;

public class OutputMistakes {
	
	 private int mistakes;
	 private int iterations ;
	 
	 public OutputMistakes(){
		 this.mistakes=0;
		 this.iterations=0;
	 }
	 
	 public OutputMistakes(int mistakes, int iterations){
		 this.mistakes=mistakes;
		 this.iterations=iterations;
	 }
	 
	 public void setMistakes(int mistakes){
		 this.mistakes=mistakes;
	 }
	 public int getMistakes(){
		 return mistakes;
	 }
	 public int getIterations(){
		 return iterations;
	 }

}
