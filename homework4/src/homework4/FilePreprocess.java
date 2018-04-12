package homework4;

public class FilePreprocess {

	
	public static double[] readFile(String fileName, int TotalDataPoints)        
	{   
	    double[] dataPointsInFile = new double[TotalDataPoints];
	    
	    TextFileInput fileInput = new TextFileInput(fileName);
		String line = fileInput.readLine(); 
	    int count=0;
		while(line!=null)
		{
			dataPointsInFile[count] =  Double.parseDouble(line);
			 count++;      
			 line=fileInput.readLine();
		}
		
		return dataPointsInFile;        
  	}
	
	public static int CountTotalDataPoints(String fileName)        
    {   
		    int CountLine=0;
		    TextFileInput fileInput = new TextFileInput(fileName);
		    while(fileInput.readLine()!=null)
			{	
				CountLine++;
			}
		    
			return CountLine;                                         
	 }
	
	
}
