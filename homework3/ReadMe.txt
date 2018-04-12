The language for homework is JAVA, and the IDE is Eclipse, the execution environment is JavaSE-1.8
To facilitate you run the code, I include the .classpath and .project file in the zip file. If you open the project in Eclipse environment, then you should be able to directly import this project.
The dependency jar file is weka.jar I also included in the zip file.
In Eclipse, you should import this jar file into your project by 
Right click project name homework3 -> Build Path -> Configure Build Path ->Libraries ->Add External JARs ->Import this weka.jar file. The weka.jar is used for NaiveBayes.java and Logistic.java

The training data should be allocated in the path ../homework3/trainData/
The testing data should be allocated in the path ../homework3/testData/
Please keep the data file naming rule.
Also to run NaiveBayes.java and Logistic.java, I have already converted fortune.arff and fortunetest.arff from the csv files I generated from the java program offline. This part is not the focus of this project.

For decisionTree program, you should allocate -- DecisionTree.java 
This program will printOut the selectedFeatureIndex or outputLabel as the leaf node, with the corresponding maximumInfoGain.
For NaiveBayes, you should allocate -- NaiveBayes.java
This program will printOut the training and testing accuracy correspondingly.
For Logistic, you should allocate -- Logistic.java
This program will printOut the training and testing accuracy correspondingly.

Most importantly, feel free to send me an email in anytime sguan@eecs.wsu.edu if you encounter a programming execution issue. Thanks in advance.

