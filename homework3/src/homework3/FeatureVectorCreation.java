package homework3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.TreeSet;

import homework3.CSVUtils;

import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;
import homework3.Vocabulary;
import homework3.Input;
import homework3.InputList;
import homework3.Feature;
import homework3.Features;

public class FeatureVectorCreation {
	private static final String  trainingFile = "trainData/traindata.txt";
	private static final String  trainingLabel = "trainData/trainlabels.txt";
	private static final String  testFile= "testData/testdata.txt";
	private static final String  testLabel= "testData/testlabels.txt";
	private static final String  testFileNameBase = "testData/";
	private static final String  trainFileNameBase = "trainData/";
	

	
	
	
	public static TreeMap readFeatureVector(String fileName){
		    TreeMap usefulVocabulary = new TreeMap();
			File dataFile = new File(fileName);
			if(!dataFile.exists()){
				System.out.println("data input not exist");
				return null;
			}
			BufferedReader br= null;
			try{
				FileReader fr=new FileReader(fileName);
				br=new BufferedReader(fr);
				
				String temp= null;
				int i=0;
				while((temp=br.readLine())!=null){
					String[] content = temp.split("\\s+");
					for (String part : content) {
			            	usefulVocabulary.put(part,i);
			            	i++;
			            	//System.out.println("current key is" +part + ", current value is"+ i);
			        }
				}
			}
			
			catch (Exception e){
				e.printStackTrace();
			}
			
			return usefulVocabulary;
	}
	
	public static InputList createFeature(TreeMap usefulVocabulary,String trainFile,String trainLabel) throws IOException{
		InputList inputList = new InputList();
		File dataFile = new File(trainFile);
		if(!dataFile.exists()){
			//System.out.println("data input not exist");
			return null;
		}
		File labelFile = new File(trainLabel);
		if(!labelFile.exists()){
			//System.out.println("label input not exist");
			return null;
		}
		BufferedReader br= null;
		Scanner lr= null;
		try{
			FileReader fr=new FileReader(trainFile);
			br=new BufferedReader(fr);
			File file=new File(trainLabel);
			lr=new Scanner(file);
			String temp= null;
			int i=0;
			while((temp=br.readLine())!=null ||lr.hasNextLine()){
				String[] content = temp.split("\\s+");
				String content2 =lr.nextLine();
				Input newInput = new Input();
				for (String part : content) {
					if (usefulVocabulary.containsKey(part)){
						int k= (int)usefulVocabulary.get(part);
						newInput.featureValue[k]=1;
						//System.out.println(" the k will be modified is" + k);
					}
				}
				int y =0;
				y= Character.getNumericValue(content2.charAt(0));
				newInput.label=y;
				inputList.add(newInput);
				//for (int j = 0; j < newInput.featureValue.length; j++) {
                  //System.out.print(newInput.featureValue[j]);
				//}
				//System.out.println(i);
				i++;
			}	
		} catch (IOException e) {

			e.printStackTrace();
        }
		br.close();
		lr.close();
	
		
		return inputList;
		
		
	}
	
	
	public static void writeFeature(InputList feature,String file) throws IOException{
		
		FileWriter writer = new FileWriter(file);
		try{
			ArrayList<String> headerList = new ArrayList<>();
			for(int j=1;j<=693;j++){
			    headerList.add("Attribute"+j);	    
			}
			headerList.add("Label");
			CSVUtils.writeLine(writer, headerList);
			for (int i =0; i<feature.size(); i++) {
		        ArrayList<String> list = new ArrayList<>();
		        Input o = feature.get(i);
		        for(int j=1;j<=693;j++){
		        	list.add(String.valueOf(o.getFeature(j)));
		        }
		        //list.add(String.valueOf(o.getMistakes()));
		        list.add(String.valueOf(o.getLabel()));
		        CSVUtils.writeLine(writer, list);
			}
		}catch (IOException e) {

			e.printStackTrace();
        }
		writer.flush();
	    writer.close();
	}
	
	public static Features loadFeatures(TreeMap usefulVocabulary){
		List<Feature> featureList = new ArrayList<Feature>();
		Features features = new Features();
		int ftrIdx = 0;
		String[] fOptions={"1","0"};
		for(ftrIdx=0; ftrIdx<693;ftrIdx++){
			Feature f = new Feature();
			f.setfName(usefulVocabulary.keySet().toArray()[ftrIdx].toString());
			f.setfOptions(fOptions);
			f.setIdx(ftrIdx);
			featureList.add(f);
		}
		
		features.setFeatures(featureList);
		return features;
	}
	
	//public InputList splitTrainingFeatures(){
		
	//}
	
	
	
	public static void main(String[] args) throws IOException {
		TreeMap usefulVocabulary = new TreeMap();
		usefulVocabulary = readFeatureVector(trainFileNameBase+"vocabularyList.txt");
		Features features = loadFeatures(usefulVocabulary);
		InputList trainFeature = new InputList();
		trainFeature=createFeature(usefulVocabulary,trainingFile, trainingLabel);
		String csvFile = testFileNameBase+ "trainingFeature.csv";
		writeFeature(trainFeature,csvFile);
		InputList testFeature = new InputList();
		testFeature=createFeature(usefulVocabulary,testFile, testLabel);
		String csvFile2 = testFileNameBase+ "testFeature.csv";
		writeFeature(trainFeature,csvFile2);
    }

}
