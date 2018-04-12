package homework1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileProcessor {
    // Mark these field as private so the object won't get tainted from outside
    private String fileName;
    private File file;

    /**
     * Instantiates an object from the FileProcessor class
     * 
     * @param fileName
     */
    public FileProcessor(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }
    
   
    /*
    public int getLineNumbers() {

        Scanner fileScanner = null;

        try {
            fileScanner = new Scanner(this.file);
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s could not be found.\n",
                    this.file.getName());
        }

        int lines = 0;

        while (fileScanner.hasNextLine()) {
            lines++;
            // Go to next line in file
            fileScanner.nextLine();
        }

        fileScanner.close();

        return lines;
    }
    */
    public int getLineNumbers() throws IOException {

    	BufferedReader reader = new BufferedReader(new FileReader(this.file));
    	int lines = 0;
    	String line;
    	while ((line = reader.readLine()) != null){
    		if(!"".equals(line.trim())){
    			lines++;
    		}
    	}
    	return lines;
    }
}
