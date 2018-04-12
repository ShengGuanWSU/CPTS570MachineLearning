package homework4;
import homework4.GMixtureModel;


public class GMixtureModelMain {
	private static GMixtureModel GaussianMixtureModel;
    public static void main(String[] args) {            
		
		 GaussianMixtureModel = new GMixtureModel();             
		 if(GaussianMixtureModel.GetNumDistribution()==5){
			 GaussianMixtureModel.GaussianMixtureModel(1); 
		 }
		 else {
			 GaussianMixtureModel.GaussianMixtureModel(0); 
		 }
		 GaussianMixtureModel.OutputTerminationResult();                
		 
	}  

}   
