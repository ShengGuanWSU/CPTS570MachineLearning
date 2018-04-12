package kernelizedPerceptron;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import kernelizedPerceptron.FileOperation;


public class input {
	public int y[];		//label
	public int size;	//instance  size
	public int featureValue[][];
	
	
	public input(int size){
		this.size = size;
		y = new int[size+1];
		for(int i = 0; i <= size; i++)
			y[i] = 0; //initialize all the labels for instances
		featureValue = new int[size+1][129];
		for(int i = 1; i <= size; i++)
			for(int j = 1; j <= 128; j++){
				featureValue[i][j] = 0; //initialize all the features for instances
			}
	}
	
	
	
}
