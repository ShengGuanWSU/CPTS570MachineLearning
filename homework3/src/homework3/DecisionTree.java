package homework3;

import java.io.IOException;
import  java.util.*;

import homework3.Feature;
import homework3.Input;
import homework3.FeatureVectorCreation;

public class DecisionTree {
	
	private static final String  trainingFile = "trainData/traindata.txt";
	private static final String  trainingLabel = "trainData/trainlabels.txt";
	private static final String  testFile= "testData/testdata.txt";
	private static final String  testLabel= "testData/testlabels.txt";
	private static final String  testFileNameBase = "testData/";
	private static final String  trainFileNameBase = "trainData/";
	public static int[] outputLabels = {1,0};
	
	
	
	public static double  entropyCalculator(Integer positiveCt, Integer negativeCt){
		
		double positive =0.0+ positiveCt;
		double negative =0.0+ negativeCt;
		
		
		double temp1 = positive/(positive +negative);
		double temp2 = 1- temp1;
		//P(x) proportion of the number of elements in class + to the number of elements in set S
		double entropyPos = (temp1!=0)? temp1*(Math.log(temp1)/Math.log(2))*(-1.0):0;
		double entropyNeg = (temp2!=0)? temp2*(Math.log(temp2)/Math.log(2))*(-1.0):0;
		double entropy = entropyPos + entropyNeg;
		
	    return entropy;
	}
	
	public static double getOutputEntropyOverFeature (int totOutputCt, int featurePosCt, int featureNegCt){
		
		int totFeatureCt = featurePosCt + featureNegCt;
		double featureOption1Prob = 1.0* totFeatureCt/totOutputCt;
		
		if(featureNegCt ==0 ||featurePosCt ==0){
			return 0;
		}
		
		double ftrOption1OutputLabel1Prob = 1.0*featurePosCt/totFeatureCt;
		double ftrOption1OutputLabel0Prob = 1.0*featureNegCt/totFeatureCt;
		double ftrOption1Entropy = ((-1.0)* ftrOption1OutputLabel1Prob *(Math.log(ftrOption1OutputLabel1Prob)/Math.log(2))-
				(ftrOption1OutputLabel0Prob *(Math.log(ftrOption1OutputLabel0Prob)/Math.log(2))));
		
		return ftrOption1Entropy * featureOption1Prob;
		
	}
	
	
	public TreeNode buildDecisionTree(Features features,  InputList trainExamples){
		
		if(trainExamples.size() ==0 || features.getFeatures().size() ==0) {
			return null;
		}
		
		Map<Integer, Integer> outputLabelCt = new HashMap <Integer, Integer>();
		outputLabelCt = getOutputLabelDistribution(trainExamples);
		int posCt = outputLabelCt.get(outputLabels[0]);
		int negCt = outputLabelCt.get(outputLabels[1]);
		String debug = "{ " + posCt + "(+)" + " / "
				+ negCt + "(-)" + " }";
		
		int outputLabel = outputLabels[1];
		if(posCt > negCt){
			outputLabel = outputLabels[0];
		}
		
		if(posCt ==0){
			TreeNode node = new TreeNode();
			node.setOutputLabel(outputLabel);
			node.setLog(outputLabel+"#ONLYNEG#"+debug);
			System.out.println(outputLabel);
			return node;
		}
		
		if(negCt ==0){
			TreeNode node = new TreeNode();
			node.setOutputLabel(outputLabel);
			node.setLog(outputLabel+"#ONLYPOS#"+debug);
			System.out.println(outputLabel);
			return node;
		}
		
		//System.out.printf("Current training Example size is %s,\n", trainExamples.size());
		Feature bestFeature = getBestFeature(features.getFeatures(),trainExamples,outputLabelCt);
	
		System.out.println(bestFeature.getIdx());
		Features remainingFtrs = getRemainingFtrs(features.getFeatures(),bestFeature);
		
		TreeNode root = new TreeNode();
		root.setLog("#Normal#"+debug);
		root.setFeature(bestFeature);
		
		System.out.println("traversing left");
		InputList filteredExOnBestFtr = getFilteredExamplesOnBestFtr(trainExamples, bestFeature, bestFeature.getfOptions()[0]);
		root.setLeftNode(buildDecisionTree(remainingFtrs, filteredExOnBestFtr));
		
		System.out.println("traversing right");
		filteredExOnBestFtr = getFilteredExamplesOnBestFtr(trainExamples, bestFeature, bestFeature.getfOptions()[1]);
		root.setRightNode(buildDecisionTree(remainingFtrs, filteredExOnBestFtr));
		
		return root;
	}
	
	public void printDecisionTree(TreeNode root, int level) {
		System.out.println(root);
	}
	
	
	public  InputList getFilteredExamplesOnBestFtr(InputList examples,
			Feature bestFeature, String ftrValue) {
		InputList ins = new InputList();
		ArrayList<Input> newInstances = new ArrayList<Input>();

		for(int i=0; i<examples.size(); i++) {
			double[] ftrValues = examples.get(i).getFeatures();

			if(ftrValues[bestFeature.getIdx()]==Double.parseDouble(ftrValue)) {
				newInstances.add(examples.get(i));
			}

		}
		ins.setInputList(newInstances);
		return ins;
	}

	
	
	public static Map<Integer, Integer> getOutputLabelDistribution(InputList trainFeature) {

		Map<Integer, Integer> outputLabelCt = new HashMap<Integer, Integer>();
		outputLabelCt.put(outputLabels[0], 0);
		outputLabelCt.put(outputLabels[1], 0);

		for(int i=0; i<trainFeature.size(); i++){
			if(trainFeature.get(i).getLabel() == 1 ) {
				//System.out.printf("feature value is %s \n", trainFeature.get(i).getLabel());
				//System.out.printf("positive label i is %s\n", i);
				int ct = outputLabelCt.get(outputLabels[0])+1;
				outputLabelCt.put(outputLabels[0], ct);
			} else {
				//System.out.printf("nagative label i is %s\n", i);
				int ct = outputLabelCt.get(outputLabels[1])+1;
				outputLabelCt.put(outputLabels[1], ct);
			}
		}
		
		return outputLabelCt;
	}
	
	public static Feature getBestFeature(List<Feature>features, InputList trainExamples,Map<Integer, Integer> outputLabelCt) {
		
		double outputLabelEntropy = entropyCalculator(outputLabelCt.get(outputLabels[0]), outputLabelCt.get(outputLabels[1]));
		int totOutputCt = outputLabelCt.get(outputLabels[0])+outputLabelCt.get(outputLabels[1]);

		double maxInfoGain = 0;
		Feature bestFeature = null;
		
		for(Feature currentFeature: features){
			Map<Integer, Integer> featureOption1ValueDist = getFeatureValueDistribution(currentFeature,  
				currentFeature.getfOptions()[0], currentFeature.getIdx(), trainExamples, outputLabels);
			
			double featureOption1Entropy = getOutputEntropyOverFeature(totOutputCt, featureOption1ValueDist.get(outputLabels[0]),
					featureOption1ValueDist.get(outputLabels[1]));
			
			Map<Integer, Integer> featureOption2ValueDist = getFeatureValueDistribution(currentFeature,  
					currentFeature.getfOptions()[1], currentFeature.getIdx(), trainExamples, outputLabels);
			
			double featureOption2Entropy = getOutputEntropyOverFeature(totOutputCt, featureOption2ValueDist.get(outputLabels[0]),
					featureOption2ValueDist.get(outputLabels[1]));
			
			double featureToOutputEntropy = featureOption1Entropy + featureOption2Entropy;
			
			double infoGain = outputLabelEntropy-featureToOutputEntropy;
			
			if(infoGain > maxInfoGain) {
				maxInfoGain = infoGain;
				bestFeature = currentFeature;
			}
		}
		if(maxInfoGain==0.0){
			bestFeature=features.get(0);
		}
		System.out.printf("maximumInfoGain is %s\n", maxInfoGain);
		return bestFeature;
	}
	
	public static Map<Integer, Integer> getFeatureValueDistribution(Feature feature, String featureOptionValue, int ftrIdx, InputList trainExamples, int[] outputLabels){
		Map<Integer, Integer> outputLabelCt = new HashMap<Integer, Integer>();
		outputLabelCt.put(outputLabels[0], 0);
		outputLabelCt.put(outputLabels[1], 0);
		
		for(int i=0; i<trainExamples.size(); i++){
			double[] featuresArr = trainExamples.get(i).featureValue;
			double featureValue = featuresArr[ftrIdx];
			if(Double.parseDouble(featureOptionValue)==featureValue){
				if (trainExamples.get(i).getLabel() == outputLabels[0]){
					int ct = outputLabelCt.get(outputLabels[0])+1;
					outputLabelCt.put(outputLabels[0], ct);
				} else{
					int ct = outputLabelCt.get(outputLabels[1])+1;
					outputLabelCt.put(outputLabels[1], ct);
				}
			}
		}
		
		return outputLabelCt;
	}
	
	public static Features getRemainingFtrs(List<Feature> features,
			Feature bestFeature) {
		List<Feature> remFtrs = new ArrayList<Feature>();

		for(Feature f: features) {
			if(!f.getfName().equalsIgnoreCase(bestFeature.getfName())) {
				remFtrs.add(f);
			}
		}
		Features fs = new Features();
		fs.setFeatures(remFtrs);
		return fs;
	}
	
	public InputList testDecisionTree(TreeNode root, InputList testData){
		
		ArrayList<Input> failedExamples = new ArrayList<Input>();
		for(int i=0; i<testData.size(); i++){
			int currentOutputLabel = testData.get(i).getLabel();
			int dtOutputLabel = validateExample(testData.get(i), root);
			
			if(dtOutputLabel!=currentOutputLabel){
				failedExamples.add(testData.get(i));
			}
		}
		int successfulExamples = testData.size()-failedExamples.size();
		double accuracy = (1.0*successfulExamples)/testData.size();
		System.out.println("accuracy achieved is "+accuracy*100+"%");
		InputList is = new InputList();
		is.setInputList(failedExamples);
		return is;
	}
	
	public int validateExample(Input example, TreeNode root){
		int dtOutputLabel=0;
		while (root!=null){
			double[] exFtrValues = example.getFeatures();
			Feature dtFtr = root.getFeature();
			
			if(dtFtr != null){
				String[] dtFtrValues = dtFtr.getfOptions();
				double exFtrValue = exFtrValues[dtFtr.getIdx()]; 
				
				if(exFtrValue==Double.parseDouble(dtFtrValues[0])) {
					root = root.getLeftNode();
				} else {
					root = root.getRightNode();
				}
				
				if(root != null) {
					dtOutputLabel = root.getOutputLabel();
				}
			} else {
				root = root.getLeftNode();
			}
		}
		return dtOutputLabel;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		TreeMap usefulVocabulary = new TreeMap();
		usefulVocabulary = FeatureVectorCreation.readFeatureVector(trainFileNameBase+"vocabularyList.txt");
		Features features = FeatureVectorCreation.loadFeatures(usefulVocabulary);
		InputList trainFeature = new InputList();
		trainFeature=FeatureVectorCreation.createFeature(usefulVocabulary,trainingFile, trainingLabel);
		//Map<Integer, Integer> TrainInstanceLabelDis = new HashMap<Integer, Integer>();
		//TrainInstanceLabelDis = getOutputLabelDistribution(trainFeature);
		
		//String csvFile = testFileNameBase+ "trainingFeature.csv";
		//FeatureVectorCreation.writeFeature(trainFeature,csvFile);
		InputList testFeature = new InputList();
		testFeature=FeatureVectorCreation.createFeature(usefulVocabulary,testFile, testLabel);
		//String csvFile2 = testFileNameBase+ "testFeature.csv";
		//FeatureVectorCreation.writeFeature(trainFeature,csvFile2);
		DecisionTree dt = new DecisionTree();
		TreeNode root = dt.buildDecisionTree(features, trainFeature);
		//dt.printDecisionTree(root, 0);
		dt.testDecisionTree(root, testFeature);
		
    }
	
		
		
	

}
