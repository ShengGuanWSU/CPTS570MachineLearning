package kernelizedPerceptron;

import java.util.HashSet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import homework2.CSVUtils;
import homework2.FileOperation;
import homework2.FileProcessor;
import homework2.input;


import homework2.OutputPrecisions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class BinaryClass {
	
	private static final String  trainingFileNameBase = "trainData/";
    private static final String  testFileNameBase = "testData/";
    private static final String NameHead = "ocr_fold";
    private static final String trainNameTail ="_sm_train";
    private static final String testNameTail="_sm_test";
    
    private static final int iterations = 20;
    private static final int degree = 4;
    private static int mistakes =0;
    private static double accuracies= 0.0;
    private static int trainingMistakes=0;
    private static double trainingAccuracies =0.0;
    public static String currTestDataName;
	private static final HashSet<Character> vowels = new HashSet<Character>();
	static{
	    vowels.add('a');
	    vowels.add('e');
	    vowels.add('i');
	    vowels.add('o');
	    vowels.add('u');
	    vowels.add('A');
	    vowels.add('E');
	    vowels.add('I');
	    vowels.add('O');
	    vowels.add('U');
	}
	public static boolean isVowel(Character c){
	    return vowels.contains(c);
	}
	
	
	public static int dotProduct(int[] w, int[] xt, int featureSize){
		int res = 0;
		for(int i = 1; i<= featureSize; i++)
			res += w[i] * xt[i];
		return res;
	}
	
	public static ArrayList<BinaryData> alphaCal(String trainFile) throws IOException{
	
	            FileProcessor fileProcessor = new FileProcessor(trainFile);
	            //System.out.printf("%d lines\n", fileProcessor.getLineNumbers());
	            int size = fileProcessor.getLineNumbers();
	            //int size = 2000;
	            input trainData = readData(trainFile,size);	
	            ArrayList<BinaryData> trainDataList = new ArrayList <BinaryData>();
	            for (int j=1; j<= trainData.size;j++){
	            	trainDataList.add(new BinaryData(trainData.featureValue[j], trainData.y[j]));
	            }
	            int currentIter = 0;
	            double ytt; //yt hat, predict using the current weights
	            double result=0.0;
	            //int degree =3;
	            int mark =1;
	            while ((currentIter< iterations)|| (mark==0)){
	            	
	            	trainingMistakes =0;
	            	System.out.printf("Current Iteration is %s\n", currentIter);
	            	mark = 0;
	            	for (BinaryData data : trainDataList){
	            	    ytt=predictBinary(data, trainDataList, degree);
	            		if((ytt * data.getLabel()) <= 0){
	            			trainingMistakes ++;
	            			//The training mistakes for this iteration this training-testing pair is stored
	            			data.alpha =data.alpha + data.getLabel();
	            			//System.out.print("%sth column in sample%s is training \n", k,sampleFileI);
	            			mark = 1;
	            			}
	            	}
	            	System.out.printf("Training mistakes for Iteration %s is %s\n",currentIter,trainingMistakes);
	            	currentIter++;
	            	
	            }
	            		//System.out.println((double)(trainData.size - trainingMistakes[sampleFileI])/trainData.size);
	        	trainingAccuracies =(double)(trainData.size - trainingMistakes )/trainData.size;
	        	System.out.printf("Training Accuracy is %s\n", trainingAccuracies);
	        	return trainDataList;
	     
	}
	
	public static double test(ArrayList <BinaryData> trainDataList) throws IOException{
	
			currTestDataName = testFileNameBase +NameHead+ 0+ testNameTail + ".txt";
			FileProcessor fileProcessor = new FileProcessor(currTestDataName);
			int size = fileProcessor.getLineNumbers();
			input testData = readData(currTestDataName,size);
			ArrayList<BinaryData> testDataList = new ArrayList <BinaryData>();
	        for (int j=1; j<= testData.size;j++){
	            	testDataList.add(new BinaryData(testData.featureValue[j], testData.y[j]));
	        }
            double ytt; //yt hat, predict using the current weights
            double result=0.0;
            //int degree =3;
            int mark =1;
            mistakes=0;
            for (BinaryData data : testDataList){
            	ytt=predictBinary(data, trainDataList, degree);	
            	if((ytt * data.getLabel()) <= 0){
            			mistakes ++;
        	    }
            }
            System.out.printf("Testing mistakes is %s\n",mistakes);
			//System.out.println((double)(size - mistakes[j])/size);
			accuracies =(double)(size - mistakes)/size;
			System.out.printf("Testing Accuracy is %s\n", accuracies);
        	return accuracies;
			
		}

    public static double predictBinary(BinaryData data, ArrayList<BinaryData> SV ,int degree){
    	double sum =0;
    	for (BinaryData supportVector: SV){
    		double kernel = 0;
    		int kernelTemp = 0;
    		for (int i=0;i<data.getFeatures().length;i++){
    			kernelTemp=kernelTemp+ data.getFeatures()[i]*supportVector.getFeatures()[i];
    		}
    		kernel= Math.pow((kernelTemp+1),degree);
    		sum=sum + supportVector.getAlpha()*kernel;
    	}
    	return sum;
    }
    
	public static input readData(String FileName, int size) throws IOException{
		String strLine="";
		Vector<String> vec = null;
 		input data = new input(size);
		File dataFile = new File(FileName);
		if(!dataFile.exists()){
			System.out.println("data input not exist");
			return null;
		}
		BufferedReader in = FileOperation.openFile(FileName);
		for(int i = 1;i <= size ; i++){
			strLine = in.readLine();
			if(strLine==null){
				break; // skip the null line
			}
			if (strLine.isEmpty()||strLine.equals("\t\t\t")){
				i--;
				continue;
			}
			vec = createVectorFromString(strLine, "\t");
			String yString = vec.elementAt(2);
			
			if(isVowel(yString.charAt(0))){
				data.y[i] = 1;
			}
			else if(!isVowel(yString.charAt(0))){
				data.y[i] = -1;
			}
			String arr =vec.elementAt(1);
			String[] items = arr.replaceAll("im", "").replaceAll("\\s", "").split("");
			
			for (int j = 0; j < items.length; j++) {
			    try {
			        data.featureValue[i][j+1] = Integer.parseInt(items[j]);
			    } catch (NumberFormatException nfe) {
			    	 System.out.printf("The feature transformation is not successful.\n" );			    			
			    };
			}
		}
		
		return data;
	}
	
	public static Vector<String> createVectorFromString(String sContent,
			String sDelimiter) {
		Vector<String> vec = new Vector<String>();
		String[] words = sContent.split(sDelimiter);

		for (int i = 0; i < words.length; i++) {
			vec.addElement(words[i]);
		}
		return vec;
	}
	
	public static void main(String args[]) throws IOException{
		
	    ArrayList<OutputPrecisions> outputPrecisions = new ArrayList <OutputPrecisions>();
		//Arrays.fill(trainingMistakes, 0);
		//Arrays.fill(trainingAccuracies, 0.0);
		String currTrainFileName = trainingFileNameBase +NameHead+ 0+ trainNameTail + ".txt";
		ArrayList <BinaryData> trainModel= alphaCal(currTrainFileName);
	    double testAccuracy= test(trainModel);		
	}
    

}
