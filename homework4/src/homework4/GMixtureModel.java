package homework4;
import homework4.FilePreprocess;

public class GMixtureModel {
	
	private double[] Threshold = {0.0001,0.001};
	private double[] Alpha_k, Mu_k, Variance_k;
	
	private static int numDistributions =4;
	private String fileName = "trainData/em_data.txt";
    private int NumOfDataPoints = FilePreprocess.CountTotalDataPoints(fileName);
	private double[] inputDataset;
	
	
	
	private int NumOfEMIterations = 0;
	private double temp, TotalLoglikelihood = 0;
	
	public int GetNumDistribution(){
		return numDistributions;
	}
	
	public GMixtureModel(){                                      
		inputDataset = FilePreprocess.readFile(fileName, NumOfDataPoints);
		Alpha_k  = new double[numDistributions];        
		Mu_k  = new double[numDistributions];
		Variance_k = new double[numDistributions];
    }
	
	public void GaussianMixtureModel(int choice){
		
		InitialiGuess(choice);	        
		double 	ChangeInLogLikelihood = 1.0;
		
		while(ChangeInLogLikelihood > Threshold[choice])
		{
			   double LogLikelihood=0, ProbOfXn=0;
				
				// Calculate the Total LogLikelihood...
				for(int i=0; i< inputDataset.length; i++){
					ProbOfXn = 0;
					for(int j=0; j<numDistributions; j++){            
						ProbOfXn = ProbOfXn + Alpha_k[j]*GaunssianXnProb(inputDataset[i], Mu_k[j], Variance_k[j]);  		
					}	
					LogLikelihood = LogLikelihood + log2(ProbOfXn);
				}
				
				// E- Step   Calculating a point x_i belongs to class j
				
		        double[][] EvaluateaArray = new double[inputDataset.length][numDistributions];          // n data points and k component mixture
				for(int i=0; i< inputDataset.length; i++){
					ProbOfXn =0;
					for(int j=0; j<numDistributions; j++){
						EvaluateaArray[i][j] = Alpha_k[j]*GaunssianXnProb(inputDataset[i], Mu_k[j], Variance_k[j]);
						ProbOfXn = ProbOfXn + EvaluateaArray[i][j]; 
					}
					for(int k=0; k<numDistributions; k++){
					EvaluateaArray[i][k] = EvaluateaArray[i][k]/ProbOfXn;
					}
				}
							
				// M-Step     Re-estimating Parameters...
				
				double[] N_k = new double[numDistributions];
	            
				for(int k=0; k<numDistributions; k++){             // Calculating sigma(j=1 to m)P(y=i|x(j))
					for(int n=0; n< inputDataset.length; n++){
						N_k[k] = N_k[k] + EvaluateaArray[n][k];
					}
				}
				
				// Calculating new Mu_k's
				for(int k=0; k<numDistributions; k++){
					Mu_k[k] = 0 ;
					for(int n=0; n< inputDataset.length; n++){
						Mu_k[k] = Mu_k[k] + EvaluateaArray[n][k]*inputDataset[n];
					}
					Mu_k[k] = Mu_k[k]/N_k[k] ;
				}
				
				// Calculating new Variance_k's                 // 
				for(int k=0; k<numDistributions; k++){
					Variance_k[k] = 0;
					for(int n=0; n< inputDataset.length; n++){
						Variance_k[k] = Variance_k[k] + EvaluateaArray[n][k]*Math.pow(inputDataset[n]-Mu_k[k],2);
					}
				   	Variance_k[k] = Variance_k[k]/N_k[k];
				}
				
				// Calculating new Alpha_k's
				for(int k=0; k<numDistributions; k++){
					Alpha_k[k] = N_k[k]/inputDataset.length;
				}
				
	            double NewLogLikelihood=0, ProbOfX=0;
				
				// New Total LogLikelihood...
				for(int i=0; i< inputDataset.length; i++){
					ProbOfX = 0;
					for(int j=0; j<numDistributions; j++){            
						ProbOfX = ProbOfX + Alpha_k[j]*GaunssianXnProb(inputDataset[i], Mu_k[j], Variance_k[j]);  		
					}	
					NewLogLikelihood = NewLogLikelihood + log2(ProbOfX);
				}
				
				// ChangeInTotalLogLikelihood
				ChangeInLogLikelihood = NewLogLikelihood - LogLikelihood;
				TotalLoglikelihood = NewLogLikelihood;
				
				System.out.println("Total LogLikelihood: "+TotalLoglikelihood);
				System.out.println("Change in LogLikelihood: "+ChangeInLogLikelihood);
				System.out.println("EM Iteration #: "+NumOfEMIterations++);
				
		}	
		
   } 
	
	public double GaunssianXnProb(double x_n, double Mu_k , double Variance_k ){     // return N(x_n|...)
		
		double Prob = 0;
		Prob = Math.pow(2*3.14159265*Variance_k ,-0.5)*Math.exp(-(Math.pow(x_n-Mu_k, 2))/(2*Variance_k)); 
	    return Prob;
	}
	
	
	public void OutputTerminationResult(){
		
		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
		System.out.println("Final Result is as below:");
		System.out.println("\nTotalLogLikelihood: "+TotalLoglikelihood);
		System.out.println("Number of EM Iterations: "+ NumOfEMIterations+"\n");
		for(int j=0; j<numDistributions; j++){
		   System.out.println("Distribution"+ j+"\n"+" Alpha_k: "+Alpha_k[j] +"\nMu_k: "+ Mu_k[j] +"\nVariance_k: "+ Variance_k[j]+"\n");	
		}
	}
	
	public static double log2(double num)
	{ if(num==0)
		return 0;
	  else 
	    return (Math.log(num)/Math.log(2));
	}
	
   public void InitialiGuess(int choice){
	 	
	if (numDistributions ==3){
	    Alpha_k[0] = 0.33;                              
		Alpha_k[1] = 0.33;                              
		Alpha_k[2] = 0.34;                              
		
		Mu_k[0] = 2;                               
		Mu_k[1] = 5;                               
		Mu_k[2] = 9;                                
		
		Variance_k[0] = 5;                             
		Variance_k[2] = 3;
		
		if(choice == 0){
		  Variance_k[1] =  3;  
		  temp = Variance_k[1]; 
		}
		else
		  Variance_k[1] = temp;	
	}
   if (numDistributions ==4){
	    Alpha_k[0] = 0.25;                              
		Alpha_k[1] = 0.25;                              
		Alpha_k[2] = 0.25;  
		Alpha_k[3] = 0.25;
		
		Mu_k[0] = 2;                               
		Mu_k[1] = 4;                               
		Mu_k[2] = 6; 
		Mu_k[3] = 8;
		
		Variance_k[0] = 5;
		Variance_k[1] = 3;
		Variance_k[2] = 3;
		Variance_k[3] = 3;
   }
   if (numDistributions ==5){
	    Alpha_k[0] = 0.2;                              
		Alpha_k[1] = 0.2;                              
		Alpha_k[2] = 0.2;  
		Alpha_k[3] = 0.2;
		Alpha_k[4] = 0.2;
		
		Mu_k[0] = 2;                               
		Mu_k[1] = 4;                               
		Mu_k[2] = 6; 
		Mu_k[3] = 8;
		Mu_k[4] = 10;
		
		Variance_k[0] = 5;
		Variance_k[1] = 3;
		Variance_k[2] = 3;
		Variance_k[3] = 3;
		Variance_k[4] = 3;
  }
   
		
   }
	
	
}   
