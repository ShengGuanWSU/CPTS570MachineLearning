package homework3;

import java.util.ArrayList;

public class InputList {
  
	 private ArrayList<Input> inputList;

	    public InputList () {

	        inputList = new ArrayList<>();

	    }

	    public void  add(Input tuple) {

	        inputList.add(tuple);

	    }

	    public Input get (int i ) {

	        return inputList.get(i);

	    }

	    public int size () {
	        return inputList.size();
	    }
	    
	    public void setInputList(ArrayList<Input> inputList){
	    	this.inputList = inputList;
	    }
}
