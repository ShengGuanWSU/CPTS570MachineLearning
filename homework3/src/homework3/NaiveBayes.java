package homework3;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;




public class NaiveBayes {

	 


	   public static void main(String[] args) {


	      Instances trainIns = null;

	      Instances testIns = null;

	      Classifier cfs1 = null;

	      Classifier cfs2 = null;



	     

	      try{
	    	  
	    	  File file= new File("trainData/fortune.arff");

	          ArffLoader loader = new ArffLoader();

	          loader.setFile(file);

	          trainIns = loader.getDataSet();
	          
	          file = new File("testData/fortunetest.arff");

	          loader.setFile(file);

	          testIns = loader.getDataSet();
	          
	          trainIns.setClassIndex(trainIns.numAttributes()-1);

	          testIns.setClassIndex(testIns.numAttributes()-1);
	          
	          cfs1 = (Classifier)Class.forName("weka.classifiers.bayes.NaiveBayes").newInstance();
              cfs2 = (Classifier)Class.forName("weka.classifiers.bayes.NaiveBayes").newInstance();
	          //cfsArray[0] = cfs1;
	     
	          
	          Instance testInst;
	          
	          cfs1.buildClassifier(trainIns);
	          cfs2.buildClassifier(trainIns);
	          
	          Evaluation eval1 = new Evaluation(trainIns);
	          Evaluation eval2 = new Evaluation(trainIns);
	          
	          
	          //System.out.println( "The accuracy of training is£º" + (1- eval.errorRate()));
	          
	          eval1.evaluateModel(cfs1, testIns);
	          eval2.evaluateModel(cfs1, trainIns);
	          
	          //Evaluation testingEvaluation = new Evaluation(testIns);

	        		  
	          System.out.println( "The accuracy of training is£º" + (1- eval2.errorRate()));	  
	          System.out.println( "The accuracy of testing is£º" + (1- eval1.errorRate()));

	      }catch(Exception e){

	          e.printStackTrace();

	      }
	   }
}
