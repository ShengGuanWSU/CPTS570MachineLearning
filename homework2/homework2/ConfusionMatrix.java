package homework2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ConfusionMatrix {

	private static final String  trainingFileNameBase = "trainData/";
    private static final String  testFileNameBase = "testData/";
    private static final String NameHead = "ocr_fold";
    private static final String trainNameTail ="_sm_train";
    private static final String testNameTail="_sm_test";
    private static final String resultFileNameBase = "result/";
    private static final String TRAINFILE = trainingFileNameBase +NameHead+ 0 +"_train.txt";
	private static final String TESTFILE = testFileNameBase +NameHead+ 0 +"_test.txt"; 
    private static final String MODELFILE = resultFileNameBase+ "model_2.txt";
    private static final String OUTFILETEST = resultFileNameBase+"out_test_1.txt";
    
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
		//int trainSize = (int) (size * 0.8);
		//System.out.printf("The training datasize is %d \n", trainSize);
		//int validationSize = size - trainSize;
		input Data = readData(trainFile,size);
		input trainData =new input(size);
		for(int i = 1; i <=size; i++)
			trainData.y[i] = Data.y[i];
		for(int i = 1; i <= size; i++){
			for(int j = 1; j <= 128; j++){
				trainData.featureValue[i][j] =Data.featureValue[i][j]; 
			}
	}
					
		return trainData;
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
		FileWriter fwTrain=new FileWriter(TRAINFILE);
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
	
	public static void writeTestData(input testData) throws IOException {
        FileWriter fwValidation=new FileWriter(TESTFILE);
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
	
	public static double[] getGroundTruthLabel(input testData) throws IOException {
        double[] label = new double[testData.size+1];
        for (int i=1;i<=testData.size;i++){
        	label[i]=testData.y[i];
       }  
        return label;
	}
	
	public static double[] getLabelsFromFile(String file)
	{	
		
		 double labels[] = new double[47536];
		 try
	        {
				 FileReader fr=new FileReader(file);
				 BufferedReader bufferedReader = new BufferedReader(fr);  
				 int lineCount=1;
				 String line = ""; 
				 String labelStr="";
				 while ((line = bufferedReader.readLine()) != null) { 
			        		labels[lineCount]=Double.parseDouble(line);
			        		lineCount++;
			        } 	
		        
		        bufferedReader.close(); 
		        fr.close(); 
		        return labels;
	        }
			 catch (Exception e)
	        {
	            e.printStackTrace();
	            return labels;
	        }	
	}
	
	public static void createConfusionMatrix(double[] groundTruth, double[] predict)
	{	
		 Map<Integer, OutputConfusion> map = new HashMap<>();
		 int mapIndex =1;
		 for (int i=1;i<=26;i++){
	        	for(int j=1;j<=26;j++){
	        		OutputConfusion	outputConfusion = new OutputConfusion(i-1,j-1);
	        		map.put(mapIndex, outputConfusion);
	        		mapIndex++;
	        	}
	     }
		 /*
		 for(int i=1; i<=26*26;i++){
			 System.out.printf("ground truth is %s"+"predict label is %s" +" count is %s \n", map.get(i).getActualLabel(), map.get(i).getPredictLabel(),
					 map.get(i).getCount());		 
		 }
		 */
		
		for(int i=1;i<=47535;i++){
			int index = (int) (groundTruth[i]*26 +predict[i]+1); 
			map.get(index).addCount(1);
			//System.out.printf("ground truth is %s"+"predict label is %s" +" count is %s \n", map.get(index).getActualLabel(), map.get(index).getPredictLabel(),
					 //map.get(index).getCount());
		}
		
		System.out.println("........Confusion Matrix.........."); 
		for(int i=1; i<=26*26;i++){
			 System.out.printf("ground truth is %s"+"predict label is %s" +" count is %s \n", map.get(i).getActualLabel(), map.get(i).getPredictLabel(),
					 map.get(i).getCount());		 
		 }
		
		

	}
	

	
	public static void main(String args[]) throws IOException{
		
		String currTrainFileName = trainingFileNameBase +NameHead+ 0+ trainNameTail + ".txt";
		input trainDataReading = createTrainSetFromTraining(currTrainFileName);
		writeTrainData(trainDataReading);
		String currTestFileName = testFileNameBase + NameHead+ 0 +testNameTail + ".txt";
		input testDataReading = createTestSet(currTestFileName);
		writeTestData(testDataReading);
		double[] groundTestLabel=getGroundTruthLabel(testDataReading);
		
		
		 
		try
	       {
			 
			 	 double testAc = 0.0;
				 double cValue= 0.1;
				 System.out.println("........SVM Start.........."); 
				 
				 String[] nSVArg= {"-t","0","-c", String.valueOf(cValue),"-q", TRAINFILE,MODELFILE};
				 svm_train.main(nSVArg);
			 
				 String[] preTestArg= {TESTFILE,MODELFILE,OUTFILETEST};				 
				 testAc=svm_predict.main(preTestArg);				 
				 System.out.println("testAc="+testAc);
				 
				 double[] preTestLabel = getLabelsFromFile(OUTFILETEST);
				 createConfusionMatrix(groundTestLabel,preTestLabel);
				 
				 
				 
	       }catch (Exception e){
	            e.printStackTrace();
	           
	        }	
	}
}
