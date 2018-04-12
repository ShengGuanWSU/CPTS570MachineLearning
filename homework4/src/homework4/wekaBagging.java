package homework4;
import java.io.File;
import java.io.IOException;

import weka.classifiers.Evaluation;
import weka.core.converters.ArffLoader;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.REPTree;
import weka.core.Instances;


public class wekaBagging {
	private static final String  trainingFile = "trainData/training.arff";
	private static final String  validationFile = "trainData/validation.arff";
	private static final String  testingFile ="testData/test.arff";
	
	public static Instances preProcess(String filePath) throws IOException{
		File file = new File(filePath);
		ArffLoader arLoader = new ArffLoader();
		arLoader.setFile(file);
		Instances instance= arLoader.getDataSet();
		return instance;
	}
	
	public static Bagging BaggingClassifier(int depths, int treeNum)
	{
		Bagging bagging = new Bagging();
		try {
			
			REPTree repTree=new REPTree();
			String[] options = {"-L",""+depths};			
			repTree.setOptions(options);
			String[] options2 ={"-P",""+treeNum};
			bagging.setOptions(options2);
			bagging.setClassifier(repTree);
			
			return bagging;
		} catch (Exception e) {
			e.printStackTrace();
			return bagging;
		}
	}
	
	public static void main(final String[] args) throws Exception {
        
		//Use an array to store all accuracy values
		double[][] trainAccu = new double[6][5];
		double[][] testAccu = new double[6][5];
		int[] depths={1,2,3,5,10};
		int[] treeNumbers={10,20,40,60,80,100};
		double tempTrainAccuracy=0.00;
		double tempTestAccuracy=0.00;
		try {
			// Preprocess train data file
			Instances trainInstance = preProcess(trainingFile);
			trainInstance.setClassIndex(trainInstance.numAttributes()-1);
			
			// Preprocess validation data file
			
			Instances validationInstance = preProcess(validationFile);
			validationInstance.setClassIndex(validationInstance.numAttributes()-1);
				
			// Preprocess test data file
			
			Instances testInstance = preProcess(testingFile);
			testInstance.setClassIndex(testInstance.numAttributes()-1);
			
			for (int i=0;i<6; i++)
			{
				for (int j=0;j<5; j++)
				{	
					trainAccu[i][j]=0.0;
					testAccu[i][j]=0.0;
					Bagging bagging = BaggingClassifier(depths[j], treeNumbers[i]);				
					bagging.buildClassifier(trainInstance);
					// create training accuracy
					Evaluation trainEvaluation=new Evaluation(trainInstance);
					trainEvaluation.evaluateModel(bagging, trainInstance);
					tempTrainAccuracy=1-trainEvaluation.errorRate();
					trainAccu[i][j]=tempTrainAccuracy;
					System.out.println("Number of trees is"+treeNumbers[i]+"   Depth is"+depths[j]+" Training Accuracy is"+ trainAccu[i][j]);	
						
	
					// create testing accuracy	
					Evaluation testEvaluation=new Evaluation(testInstance);
					testEvaluation.evaluateModel(bagging, testInstance);
					tempTestAccuracy=1-testEvaluation.errorRate();
					testAccu[i][j]=tempTestAccuracy;
					System.out.println("Number of trees is"+treeNumbers[i]+"   Depth is"+depths[j]+" Testing Accuracy="+ testAccu[i][j]);
				}
			}
		}	
		catch (Exception e)
		{
		e.printStackTrace();
		}		 
	}
}