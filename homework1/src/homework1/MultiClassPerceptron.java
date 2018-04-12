package homework1;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Vector;

import homework1.input;
import homework1.FileProcessor;
import homework1.OutputMistakes;
import homework1.CSVUtils;

public class MultiClassPerceptron {

	private static final String  trainingFileNameBase = "trainData/";
    private static final String  testFileNameBase = "testData/";
    private static final String NameHead = "ocr_fold";
    private static final String trainNameTail ="_sm_train";
    private static final String testNameTail="_sm_test";
    private static final int iterations = 20;
    //private static final int iterations = 1;
    private static final int[][] w= new int[10][128*26+1];
    private static final int[] mistakes = new int[10];
    private static final double[] accuracies = new double[10];
    private static final int[] trainingMistakes= new int[10];
    private static final double[] trainingAccuracies = new double[10];
	public static String currTestDataName;
	
	public static int sign(int[] w, int[] xt, int featureSize){
		int res = 0;
		for(int i = 1; i<= featureSize; i++)
			res += w[i] * xt[i];
		return res;
	}
	
	public static void weightCal(String trainFile, int sampleFileI) throws IOException{
        //System.out.println("current sampleFile is " + sampleFileI);
        //System.out.println(Arrays.toString(w[sampleFileI]));
        FileProcessor fileProcessor = new FileProcessor(trainFile);
        //System.out.printf("%d lines\n", fileProcessor.getLineNumbers());
        int size = fileProcessor.getLineNumbers();
        //int size = 2000;
        input trainData = readData(trainFile,size);		
        int ytt; //yt hat, predict using the current weights
        	for(int j = 1; j <= trainData.size; j++){
        		//System.out.printf("%d is Size\n", trainData.size);
        		ytt = argMax(w[sampleFileI], trainData.featureValue[j]);
        		//ytt = sign(w[sampleFileI], trainData.featureValue[j]);
        		//System.out.printf("The ground truth for this instance is %s", trainData.y[j]);
        		//if((ytt * trainData.y[j]) <= 0)
        		if(ytt != trainData.y[j]){
        			//for(int k = 1; k <= 128; k++){
        				//w[sampleFileI][k] = w[sampleFileI][k] + trainData.y[j] * trainData.featureValue[j][k];
        			trainingMistakes[sampleFileI] ++;
        			UpdateWeightMultiClassPerceptron(w[sampleFileI], ytt, trainData.y[j], trainData.featureValue[j]);
        				//System.out.printf("%sth column in sample%s is training \n", k,sampleFileI);
        			//}
        		}
        	}
        	trainingAccuracies[sampleFileI] =(double)(trainData.size - trainingMistakes[sampleFileI])/trainData.size;
        	//System.out.println(Arrays.toString(w[sampleFileI]));
	}
	
	public static void UpdateWeightMultiClassPerceptron(int[] w, int ytt, int yGround, int[] data) {

        int la = yGround;
        int predict = ytt;

        int[] FXY = new int[128 * 26+1];
        int[] FXYtt = new int[128 * 26+1];

        for(int i = 1; i <=128; i++) {
            FXY[(128 * la) + i] =  data[i];
            FXYtt[(128 * predict) + i] = data[i];
        }

        for (int i = 1; i <=128*26; i++) {
            w[i] = w[i] + FXY[i] - FXYtt[i];
        }

    }
	
	public static int argMax(int[] multiWeight, int data[]) {
	        double maxPrediction = Integer.MIN_VALUE;
	        int preClass = 0;
	        for (int k = 0; k < 26; k++) {
	            int[] currMultiX = new int[128 * 26+1];
	            for (int i = 1; i <=128; i++) {
	                currMultiX[k*128 + i] = data[i];
	            }
	            int currWX = sign(multiWeight,currMultiX,128*26);
	            if (currWX > maxPrediction) {
	                maxPrediction = currWX;
	                preClass = k;
	            }
	        }

            //System.out.printf("Predict for this training instance with label %s", preClass);
	        return preClass;
	    }
	
	
	public static double test() throws IOException{
		//System.out.println("current sampleFile is " + sampleFileI);
		//currTestDataName = "test1.txt";
		Arrays.fill(mistakes, 0);
		Arrays.fill(accuracies, 0.0);
		for (int j=0;j<10;j++){
			//System.out.println("current testingFile is " + j);
			//currTestDataName = testFileNameBase +NameHead+ j+ testNameTail + ".txt";
			currTestDataName = testFileNameBase + NameHead + j+ testNameTail + ".txt";
			FileProcessor fileProcessor = new FileProcessor(currTestDataName);
			int size = fileProcessor.getLineNumbers();
			//System.out.printf("%d fileProcessor reads lines\n", fileProcessor.getLineNumbers());
			input testData = readData(currTestDataName,size);
			//System.out.printf("%d ReadData reads lines\n", testData.size);
				
			for(int i = 1; i <= testData.size; i++){
				//if((testData.y[i] * sign(w[j],testData.featureValue[i],128)) <= 0)
				if(argMax(w[j], testData.featureValue[i])!=testData.y[i])
					mistakes[j] ++;
			}
			//System.out.println((double)(size - mistakes[j])/size);
			accuracies[j] =(double)(size - mistakes[j])/size;
		}
		//System.out.println(Arrays.toString(mistakes));
		int sum=0;
		for (int d : mistakes) sum += d;
		int aveMistakes = sum / mistakes.length;
		//System.out.printf("The average mistake in this testing iteration is %s\n",aveMistakes);
		//System.out.println(Arrays.toString(accuracies));
		double sumAccu=0.0;
		for (double d : accuracies) sumAccu += d;
		double aveAccu =sumAccu / accuracies.length;
		//System.out.printf("The average precision in this testing iteration is %s\n",aveAccu);
		return aveAccu;
	}
	
	
	/*
	public static int test() throws IOException{
		//System.out.println("current sampleFile is " + sampleFileI);
		//currTestDataName = "test1.txt";
		Arrays.fill(mistakes, 0);
		Arrays.fill(accuracies, 0.0);
		for (int j=0;j<10;j++){
			System.out.println("current testingFile is " + j);
			currTestDataName = testFileNameBase +NameHead+ j+ testNameTail + ".txt";
			//currTestDataName = trainingFileNameBase + NameHead + j+ trainNameTail + ".txt";
			FileProcessor fileProcessor = new FileProcessor(currTestDataName);
			int size = fileProcessor.getLineNumbers();
			//System.out.printf("%d fileProcessor reads lines\n", fileProcessor.getLineNumbers());
			input testData = readData(currTestDataName,size);
			//System.out.printf("%d ReadData reads lines\n", testData.size);
				
			for(int i = 1; i <= testData.size; i++){
				if((testData.y[i] * sign(w[j],testData.featureValue[i],128)) <= 0)
					mistakes[j] ++;
			}
			System.out.println((double)(size - mistakes[j])/size);
			accuracies[j] =(double)(size - mistakes[j])/size;
		}
		System.out.println(Arrays.toString(mistakes));
		int sum=0;
		for (int d : mistakes) sum += d;
		int aveMistakes = sum / mistakes.length;
		System.out.printf("The average mistake in this iteration is %s\n",aveMistakes);
		System.out.println(Arrays.toString(accuracies));
		double sumAccu=0.0;
		for (double d : accuracies) sumAccu += d;
		double aveAccu =sumAccu / accuracies.length;
		System.out.printf("The average precision in this iteration is %s\n",aveAccu);
		return aveMistakes;
	}
	*/
	
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
			
			data.y[i] = (int)(yString.charAt(0) - 97);//we have 26 labels, starting from 0
			
			//if(isVowel(yString.charAt(0))){
				//data.y[i] = 1;
			//}
			//else if(!isVowel(yString.charAt(0))){
				//data.y[i] = -1;
			//}
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
		
	    for (int i=0; i<10;i++)
	    	for(int u = 0; u <= 128*26; u++ )
	    		w[i][u] = 0;
	    //ArrayList<OutputMistakes> outputMistakes = new ArrayList <OutputMistakes>();
	    ArrayList<OutputPrecisions> outputPrecisions = new ArrayList <OutputPrecisions>();
	
		for (int iteration = 1; iteration <= iterations; iteration++){
			Arrays.fill(trainingMistakes, 0);
			Arrays.fill(trainingAccuracies, 0.0);
			for (int i = 0; i < 10; i++) {
	    		String currTrainFileName = trainingFileNameBase +NameHead+ i+ trainNameTail + ".txt";
	    		//System.out.println("Loading Set ..." + i);
	    		weightCal(currTrainFileName,i);
	    		
	    	}
	    	//int aveMistakes=test();
			int sum=0;
			for (int d : trainingMistakes) sum += d;
			int aveMistakes = sum / trainingMistakes.length;
			System.out.printf("The average training mistake in iteration %s is %s\n",iteration, aveMistakes);
			double sumAccu=0.0;
			for (double d : trainingAccuracies) sumAccu += d;
			double aveAccu =sumAccu /trainingAccuracies.length;
			System.out.printf("The average training accuracy in iteration %s is %s\n",iteration,aveAccu);
	    	double avePrecisions = test();
	    	//outputMistakes.add(new OutputMistakes(aveMistakes,iteration));
	    	outputPrecisions.add(new OutputPrecisions(avePrecisions, iteration));
	    }
		//String csvFile = testFileNameBase+ "Plain_Iteration_AverageMistakes.csv";
		//String csvFile = testFileNameBase+ "Plain_Iteration_AveragePrecisions.csv";
		String csvFile = testFileNameBase+ "Plain_Test_MultiClass_Accuracy.csv";
		//String csvFile = testFileNameBase+ "Plain_Test_MultiClass_Accuracy.csv";
	    FileWriter writer = new FileWriter(csvFile);
		//CSVUtils.writeLine(writer, Arrays.asList("Iterations", "AverageMistakes"));
		CSVUtils.writeLine(writer, Arrays.asList("Iterations", "AveragePrecisions"));
		
	    //for (OutputMistakes o : outputMistakes) {
        for (OutputPrecisions o: outputPrecisions) {
	        ArrayList<String> list = new ArrayList<>();
	        list.add(String.valueOf(o.getIterations()));
	        //list.add(String.valueOf(o.getMistakes()));
	        list.add(String.valueOf(o.getPrecisions()));
	        CSVUtils.writeLine(writer, list);

				//try custom separator and quote.
				//CSVUtils.writeLine(writer, list, '|', '\"');
	        }

	    writer.flush();
	    writer.close();
	}
	
}


