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

public class Perceptron {
	private static final String  trainingFileNameBase = "trainData/";
    private static final String  testFileNameBase = "testData/";
    private static final String NameHead = "ocr_fold";
    private static final String trainNameTail ="_sm_train";
    private static final String testNameTail="_sm_test";
    //private static final int iterations = 20;
    private static final int iterations = 20;
    private static final int[][] w= new int[10][129];
    //w vector
    private static final int[] mistakes = new int[10];
    // an array separately stores each training-testing pair testing mistakes
    private static final double[] accuracies = new double[10];
    // an array separately stores each training-testing pair testing accuracies
    private static final int[] trainingMistakes= new int[10];
    // an array separately stores each training-testing pair training mistakes
    private static final double[] trainingAccuracies = new double[10];
    // an array separately stores each training-testing pair training accuracies
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
	// A hashset to determine the binary label output
	public static boolean isVowel(Character c){
	    return vowels.contains(c);
	}
	// To determine whether this labels in the hashset or not
	
	public static int sign(int[] w, int[] xt, int featureSize){
		int res = 0;
		for(int i = 1; i<= featureSize; i++)
			res += w[i] * xt[i];
		return res;
	}
	// just a w[] * x[] cross-product function
		
	public static void weightCal(String trainFile, int sampleFileI) throws IOException{
	// To calculate the weight update of the plain Perceptron algorithm 	
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
	            		ytt = sign(w[sampleFileI], trainData.featureValue[j],128);
	            		if((ytt * trainData.y[j]) <= 0){
	            			trainingMistakes[sampleFileI] ++;
	            			//The training mistakes for this iteration this training-testing pair is stored 
	            			for(int k = 1; k <= 128; k++){
	            				w[sampleFileI][k] = w[sampleFileI][k] + trainData.y[j] * trainData.featureValue[j][k];
	            				//System.out.printf("%sth column in sample%s is training \n", k,sampleFileI);
	            			}
	            		}
	            }
	            		//System.out.println((double)(trainData.size - trainingMistakes[sampleFileI])/trainData.size);
	        	trainingAccuracies[sampleFileI] =(double)(trainData.size - trainingMistakes[sampleFileI])/trainData.size;
	           //The training accuracies for this iteration this training-testing pair is stored 	
	        //System.out.println(Arrays.toString(w[sampleFileI]));
	}

	/*
	public static int test() throws IOException{
		//System.out.println("current sampleFile is " + sampleFileI);
		//currTestDataName = "test1.txt";
		Arrays.fill(mistakes, 0);
		Arrays.fill(accuracies, 0.0);
		for (int j=0;j<10;j++){
			//System.out.println("current testingFile is " + j);
			currTestDataName = testFileNameBase +NameHead+ j+ testNameTail + ".txt";
			FileProcessor fileProcessor = new FileProcessor(currTestDataName);
			int size = fileProcessor.getLineNumbers();
			//System.out.printf("%d fileProcessor reads lines\n", fileProcessor.getLineNumbers());
			input testData = readData(currTestDataName,size);
			//System.out.printf("%d ReadData reads lines\n", testData.size);
				
			for(int i = 1; i <= testData.size; i++){
				if((testData.y[i] * sign(w[j],testData.featureValue[i],128)) <= 0)
					mistakes[j] ++;
			}
			//System.out.println((double)(size - mistakes[j])/size);
			accuracies[j] =(double)(size - mistakes[j])/size;
		}
		//System.out.println(Arrays.toString(mistakes));
		int sum=0;
		for (int d : mistakes) sum += d;
		int aveMistakes = sum / mistakes.length;
		//System.out.printf("The average mistake in this iteration is %s\n",aveMistakes);
		//System.out.println(Arrays.toString(accuracies));
		double sumAccu=0.0;
		for (double d : accuracies) sumAccu += d;
		double aveAccu =sumAccu / accuracies.length;
		//System.out.printf("The average precision in this iteration is %s\n",aveAccu);
		return aveMistakes;
	}
	*/
	
	//In the testing process, the training mistakes and accuracies print out in the console
	//The testing accuracy in each iteration will be output in a csv file
	public static double test() throws IOException{
		//System.out.println("current sampleFile is " + sampleFileI);
		//currTestDataName = "test1.txt";
		Arrays.fill(mistakes, 0);
		Arrays.fill(accuracies, 0.0);
		for (int j=0;j<10;j++){
			//System.out.println("current testingFile is " + j);
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
			//System.out.println((double)(size - mistakes[j])/size);
			accuracies[j] =(double)(size - mistakes[j])/size;
		}
		System.out.println(Arrays.toString(mistakes));
		int sum=0;
		for (int d : mistakes) sum += d;
		int aveMistakes = sum / mistakes.length;
		//System.out.printf("The average mistake in this iteration is %s\n",aveMistakes);
		//System.out.println(Arrays.toString(accuracies));
		double sumAccu=0.0;
		for (double d : accuracies) sumAccu += d;
		double aveAccu =sumAccu / accuracies.length;
		//System.out.printf("The average precision in this iteration is %s\n",aveAccu);
		return aveAccu;
	}
	
	//Training, testing file read and pre-processing
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
  
    //Each iteration, we will calculate the testing average accuracy and write into the csv
	//Each iteration, we will calculate the training average accuracy and mistakes and output in the console
	public static void main(String args[]) throws IOException{
	
	    for (int i=0; i<10;i++)
	    	for(int u = 0; u <= 128; u++ )
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
		//String csvFile = testFileNameBase+ "Plain_Train_Iteration_AveragePrecisions.csv";
		String csvFile = testFileNameBase+ "Plain_Test_Accuracy.csv";
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