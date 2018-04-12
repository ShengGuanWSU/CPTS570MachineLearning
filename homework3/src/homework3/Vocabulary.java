package homework3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;






public class Vocabulary {
	private static final String  trainingFileNameBase = "trainData/";
	private static final String  testFileNameBase = "testData/";
	private static HashSet<String> stopList = new HashSet<String>();
	private static HashSet<String> usefulVocabulary = new HashSet<String>();
	
	public static TreeSet<String>buildVocabulary(String fileName){
		
		ArrayList<String>vocab = new ArrayList<String>();
		TreeSet<String> vocabularyTreeSet = new TreeSet<String>();
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
			while((temp=br.readLine())!=null){
				String[] content = temp.split("\\s+");
				for (String part : content) {
		            if(!isStopList(part)){
		            	vocab.add(part);
		            	//System.out.println(part);
		            }
		        }
			}
		}
		
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				br.close();
				//System.out.println("Without Considering duplication, the total size is "+ vocab.size());
				for (int i=0; i<vocab.size(); i++){
					usefulVocabulary.add(vocab.get(i));
				}
				//System.out.println("With considering duplication, the total size is "+ usefulVocabulary.size());
				
				
				
				vocabularyTreeSet.addAll(usefulVocabulary);
				//System.out.println(vocabularyTreeSet.size());
				//System.out.println(vocabularyTreeSet);
		        
				/*
				System.out.print("Vocabulary in Sorted Order:");
		        for (int i = 0; i < vocab.size(); i++){
		            System.out.println(i + "," + vocab.get(i));
		        }
		        */
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return vocabularyTreeSet;
	}
	
	public static HashSet<String>buildStopList(String fileName) {
		BufferedReader br= null;
		try{
			FileReader fr=new FileReader(fileName);
			br=new BufferedReader(fr);
			String temp= null;
			stopList = new HashSet<>();
			while((temp=br.readLine())!=null){
				String[] content = temp.split("\\s+");
				for (String part : content) {
		            //System.out.println(part);
		            stopList.add(part);
		        }
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				br.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}	
		}
		return stopList;
	}
	
	public static boolean isStopList(String c){
	    return stopList.contains(c);
	}
	
	public static void writeVocabularyList(TreeSet<String> vocabularyTreeSet) throws IOException{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(trainingFileNameBase+ "vocabularyList.txt"));
			for (String str:vocabularyTreeSet){
				writer.write(str);
				writer.newLine();
			}
			writer.close();
		}catch (FileNotFoundException e){
	        System.out.println("File not found.");
	    }
	}
	
	
	public static void main(String[] args) throws IOException {
		TreeSet<String> vocabularyList=new TreeSet<String> ();
        stopList=buildStopList(trainingFileNameBase+ "stoplist.txt");
        vocabularyList= buildVocabulary( trainingFileNameBase+ "traindata.txt");
        writeVocabularyList(vocabularyList);
        
    }
    
	
	

}
