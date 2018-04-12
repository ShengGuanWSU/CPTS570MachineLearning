package homework3;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Vector;



public class Input {
	public int label;		 //label
	public double[] featureValue;
	
	
	public Input(){
		label=0;
		featureValue = new double[694];
	}
	
	public int getLabel(){
		return label;
	}
	
	public double getFeature(int i){
		return featureValue[i];
	}
	
	public double[] getFeatures(){
		return featureValue;
	}
	
}
