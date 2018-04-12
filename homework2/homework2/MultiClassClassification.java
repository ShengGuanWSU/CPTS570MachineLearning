package homework2;

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

import libsvm.*;
import homework2.OutputPrecisions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;


public class MultiClassClassification {
	private static final String  trainingFileNameBase = "trainData/";
    private static final String  testFileNameBase = "testData/";
    private static final String NameHead = "ocr_fold";
    private static final String trainNameTail ="_sm_train";
    private static final String testNameTail="_sm_test";
    private static final String resultFileNameBase = "result/";
    private static final String TRAINFILE = trainingFileNameBase +NameHead+ 0 +".txt";
	private static final String VALFILE = trainingFileNameBase +NameHead+ 0 +"_validation.txt";
	private static final String TESTFILE = testFileNameBase +NameHead+ 0 +"_test.txt"; 
    private static final String MODELFILE = resultFileNameBase+ "model_v.txt";
    private static final String OUTFILETRAIN = resultFileNameBase+"out_train_0.txt";
	private static final String OUTFILEVAL = resultFileNameBase+"out_val_0.txt";
    private static final String OUTFILETEST = resultFileNameBase+"out_test_0.txt";
    private static final int numOfC = 9;
	


	
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
		for(int i = 1;i <= size; i++){
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
	
	public static input createTrainSetFromTraining(String trainFile) throws IOException {
		FileProcessor fileProcessor = new FileProcessor(trainFile);
		int size = fileProcessor.getLineNumbers();
		int trainSize = (int) (size * 0.8);
		//System.out.printf("The training datasize is %d \n", trainSize);
		int validationSize = size - trainSize;
		input Data = readData(trainFile,size);
		input trainData =new input(trainSize);
		for(int i = 1; i <=trainSize; i++)
			trainData.y[i] = Data.y[i];
		for(int i = 1; i <= trainSize; i++){
			for(int j = 1; j <= 128; j++){
				trainData.featureValue[i][j] =Data.featureValue[i][j]; 
			}
	}
					
		return trainData;
	}
	
	public static input createValidationSetFromTraining(String trainFile) throws IOException {
		FileProcessor fileProcessor = new FileProcessor(trainFile);
		int size = fileProcessor.getLineNumbers();
		int trainSize = (int) (size * 0.8);
		//System.out.printf("The training datasize is %d \n", trainSize);
		int validationSize = size - trainSize;
		input Data = readData(trainFile,size);
		input validationData =new input(validationSize);
		for(int i = 1; i <=validationSize; i++)
			validationData.y[i] = Data.y[i+trainSize];
		for(int i = 1; i <= validationSize; i++){
			for(int j = 1; j <= 128; j++){
				validationData.featureValue[i][j] =Data.featureValue[i+trainSize][j]; 
			}
	}
					
		return validationData;
	}
	
	public static input createTestSet(String testFile) throws IOException {
		FileProcessor fileProcessor = new FileProcessor(testFile);
		int size = fileProcessor.getLineNumbers();
		//int trainSize = (int) (size * 0.8);
		//System.out.printf("The training datasize is %d \n", trainSize);
		//int validationSize = size - trainSize;
		input Data = readData(testFile,size);
		input testData =new input(size);
		for(int i = 1; i <=size; i++)
			testData.y[i] = Data.y[i];
		for(int i = 1; i <= size; i++){
			for(int j = 1; j <= 128; j++){
				testData.featureValue[i][j] =Data.featureValue[i][j]; 
			}
	}
					
		return testData;
	}
	
	
	public static void writeTrainData(input trainData) throws IOException {
		FileWriter fwTrain=new FileWriter(trainingFileNameBase +NameHead+ 0 +".txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fwTrain); 
        String label="";
	    String features="";
	    String newLine ="";
        for (int i=1;i<=trainData.size;i++){
        	label=String.valueOf(trainData.y[i]);
        	for (int j=1; j<=128;j++)
			        	{
			        		if (trainData.featureValue[i][j]==1)
			        		features+=String.valueOf(j)+":"+"1"+" ";
			        	}
           newLine =label+" "+features+"\n";
           features="";
           bufferedWriter.write(newLine);  
       }
       bufferedWriter.close(); 
       fwTrain.close();       
	}
	
	public static void writeValidationData(input validationData) throws IOException {
        FileWriter fwValidation=new FileWriter(trainingFileNameBase +NameHead+ 0 +"_validation.txt");
        BufferedWriter bufferedWriter2 = new BufferedWriter(fwValidation); 
        String label="";
	    String features="";
	    String newLine ="";
        for (int i=1;i<=validationData.size;i++){
        	label=String.valueOf(validationData.y[i]);
        	for (int j=1; j<=128;j++)
			        	{
			        		if (validationData.featureValue[i][j]==1)
			        		features+=String.valueOf(j)+":"+"1"+" ";
			        	}
           newLine =label+" "+features+"\n";
           features="";
           bufferedWriter2.write(newLine);  
       }
       bufferedWriter2.close(); 
       fwValidation.close();       
	}
	
	public static void writeTestData(input testData) throws IOException {
        FileWriter fwValidation=new FileWriter(testFileNameBase +NameHead+ 0 +"_test.txt");
        BufferedWriter bufferedWriter3 = new BufferedWriter(fwValidation); 
        String label="";
	    String features="";
	    String newLine ="";
        for (int i=1;i<=testData.size;i++){
        	label=String.valueOf(testData.y[i]);
        	for (int j=1; j<=128;j++)
			        	{
			        		if (testData.featureValue[i][j]==1)
			        		features+=String.valueOf(j)+":"+"1"+" ";
			        	}
           newLine =label+" "+features+"\n";
           features="";
           bufferedWriter3.write(newLine);  
       }
       bufferedWriter3.close(); 
       fwValidation.close();       
	}
	
	
	public static void main(String args[]) throws IOException{
			
			String currTrainFileName = trainingFileNameBase +NameHead+ 0+ trainNameTail + ".txt";
			input trainDataReading = createTrainSetFromTraining(currTrainFileName);
			input validationDataReading = createValidationSetFromTraining(currTrainFileName);
			writeTrainData(trainDataReading);
			writeValidationData(validationDataReading);
			String currTestFileName = testFileNameBase + NameHead+ 0 +testNameTail + ".txt";
			input testDataReading = createTestSet(currTestFileName);
			writeTestData(testDataReading);
			
			 
			try
		       {
				 
				 	 double[] testAc = new double[numOfC];
				 	 int[] nSV = new int[numOfC];
				 	
				 	 double[] trainAc = new double[numOfC];
				 	 double[] vaAc = new double[numOfC];
				 	 double[] cValue = new double[numOfC];
					 cValue[0]= 0.0001;
					 ArrayList<OutputPrecisions> outputPrecisions = new ArrayList <OutputPrecisions>();
					 ArrayList<OutputSVs> outputSVs = new ArrayList <OutputSVs>();
					 
					 for (int i=1;i<numOfC ; i++){
							 cValue[i]=cValue[i-1]*10;	
						}
					 for (int i=0;i<numOfC; i++){
						 System.out.println("........SVM Start..........");  
	                     /*
						 String[] trainArg= {"-t","0", "-c", String.valueOf(cValue[i]), "-v ","2",TRAINFILE};// training accuracy
						 trainAc[i]= svm_train.main(trainArg);
						 System.out.println("trainAc="+trainAc[i]);
						 */
						 System.out.printf("Current c value is%s", cValue[i]);
						 String[] nSVArg= {"-t","0", "-c", String.valueOf(cValue[i]), "-q", TRAINFILE,MODELFILE};
						 nSV[i]=(int)svm_train.main(nSVArg);
						 System.out.println("nSV="+nSV[i]);
					    
						 
						 String[] preTrainArg= {TRAINFILE,MODELFILE,OUTFILETRAIN};//validation accuracy		 
						 trainAc[i]= svm_predict.main(preTrainArg);				 
						 System.out.println("trainAc="+trainAc[i]);
						 String[] preValArg= {VALFILE ,MODELFILE,OUTFILEVAL};//validation accuracy		 
						 vaAc[i]= svm_predict.main(preValArg);				 
						 System.out.println("vaAc="+vaAc[i]);
						 
						 
						 String[] preTestArg= {TESTFILE,MODELFILE,OUTFILETEST};				 
						 testAc[i]=svm_predict.main(preTestArg);				 
						 System.out.println("testAc="+testAc[i]);
						 
						 
						 outputPrecisions.add(new OutputPrecisions(cValue[i],trainAc[i],vaAc[i],testAc[i]));
						 
						 outputSVs.add(new OutputSVs(cValue[i],nSV[i]));
					 }
					 
					 String csvFile = resultFileNameBase+"CValue_Accuracy_Problem1.csv";
					 FileWriter writer = new FileWriter(csvFile);
					 CSVUtils.writeLine(writer, Arrays.asList("CValue","TrainAccuracies","ValAccuracies","TestAccuracies"));
					 
					 String csvFile1 = resultFileNameBase+"CValue_Nsv_Problem1.csv";
					 FileWriter writer1 = new FileWriter(csvFile1);
					 CSVUtils.writeLine(writer1, Arrays.asList("CValue","nSV"));
					 for (OutputSVs o : outputSVs){
					        ArrayList<String> list = new ArrayList<>();
					        list.add(String.valueOf(o.getCValue()));
					        //list.add(String.valueOf(o.getMistakes()));
					        list.add(String.valueOf(o.getNSV()));
					        CSVUtils.writeLine(writer1, list);

								//try custom separator and quote.
								//CSVUtils.writeLine(writer, list, '|', '\"');
					        }
					 
					 for (OutputPrecisions o : outputPrecisions){
					        ArrayList<String> list = new ArrayList<>();
					        list.add(String.valueOf(o.getCValues()));
					        //list.add(String.valueOf(o.getMistakes()));
					        list.add(String.valueOf(o.getTrainAccuracies()));
					        list.add(String.valueOf(o.getValAccuracies()));
					        list.add(String.valueOf(o.getTestAccuracies()));
					        CSVUtils.writeLine(writer, list);

								//try custom separator and quote.
								//CSVUtils.writeLine(writer, list, '|', '\"');
					        }
					 
					 writer.flush();
					 writer.close();
			
					 writer1.flush();
					 writer1.close();
		       }
				catch (Exception e)
		        {
		            e.printStackTrace();
		           
		        }	
		          
		}
		
}

