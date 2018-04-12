The language for homework is JAVA, and the IDE is Eclipse, the execution environment is JavaSE-1.8
To facilitate you run the code, I include the .classpath and .project file in the zip file. If you open the project in Eclipse environment, then you should be able to directly import this project.
The dependency jar file is weka.jar I also included in the zip file.
In Eclipse, you should import this jar file into your project by 
Right click project name homework3 -> Build Path -> Configure Build Path ->Libraries ->Add External JARs ->Import this weka.jar file. The weka.jar is used for NaiveBayes.java and Logistic.java

The training data should be allocated in the path ../homework4/trainData/
The testing data should be allocated in the path ../homework4/testData/
Please keep the data file naming rule.
In this project, I create training.arff, validation.arff and em_data.txt in  ../homework4/trainData/
                          test.arff in ../homework4/testData/
						  
For Expectation Maximization (EM) algorithm program, you should locate -- GMixtureModelMain.java 
In GMixtureModel.java, you can set the different values of k by changing the value:
private static int numDistributions =4;
This program will printOut the distribution model parameters you get for each value of k.

For Bagging, you should locate -- wekaBagging.java
This program will printOut numberOfTrees, depth, the training and testing accuracy correspondingly.

For Boosting, you should locate -- Adaboost.java
This program will printOut iterations, depth, the training and testing accuracy correspondingly.

Most importantly, feel free to send me an email in anytime sguan@eecs.wsu.edu if you encounter a programming execution issue. Thanks in advance.

