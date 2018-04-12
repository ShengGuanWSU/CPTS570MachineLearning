The language for homework is JAVA, and the IDE is Eclipse, the execution environment is JavaSE-1.8
To facilitate you run the code, I include the .classpath and .project file in the zip file. If you open the project in Eclipse environment, then you should be able to directly import this project and run.
The training data should be allocated in the path ../homework1/trainData/
The testing data should be allocated in the path ../homework1/testData/
Please keep the data file naming rule as the original since I use NameHead and NameTail to hardcode all the names. 
For example, all the training data should be:
ocr_fold#i_sm_train.txt

<1>
To get the learning curve shown in 5.1.a, you should look for Perceptron.java, PA.java and run it.
You can manually change the iteration number in the Class definition part by changing the value:
private static final int iterations = ?
In the console, it will print out the training iteration number and the corresponding mistakes, training accuracy by default. You need to collect the results and draw the curve using excel or other tools.

<2>
To get the accuracy curve comparison in 5.1.b , you should look for Perceptron.java, PA.java and run it.
In this time, by default the program will save the results into the path:
../homework1/testData/
There will be two files called "Plain_Test_Accuracy.csv" and "PA_Test_Accuracy.csv".
If the name doesn't match, please check Line 245 String csvFile = testFileNameBase+ "Plain_Test_Accuracy.csv"; in Perceptron.java and Line 255  String csvFile = testFileNameBase+"PA_Test_Accuracy.csv";in PA.java to see whether the name have been modified. 
And the definition of testFileNameBase is also located at the head of each class.
The accuracy of training result you should get from <1>'s collection.

<3>
To get the accuracy curve comparison in 5.1.c , you should look for Perceptron.java, AvePerceptron.java and run it.
In this time, by default the program will save the results into the path:
../homework1/testData/
There will be two files called "Plain_Test_Accuracy.csv" and "AvePercep_Test_Accuracy.csv".
If the name doesn't match, please check Line 245 String csvFile = testFileNameBase+ "Plain_Test_Accuracy.csv"; in Perceptron.java and Line 258 String csvFile = testFileNameBase+ "AvePercep_Test_Accuracy.csv"; in AvePerceptron.java to see whether the name have been modified. 
And the definition of testFileNameBase is also located at the head of each class.
The accuracy of training result you should get one part from <1>'s collection, and the other part in the console result. In the console, it will print out the training iteration number and the corresponding mistakes, training accuracy by default.

<4>
In Perceptron.java and PA.java, you can manually set the training sample size in WeightCal function.
To change Line 64 in Perceptron.java int size = fileProcessor.getLineNumbers(), you can manually set int size =100. and so on.
Similarly to change Line 77 in PA.java int size = fileProcessor.getLineNumbers(), you can manually set int size =100, and so on. 
Then for each size in ../homework1/testData/ you will get a corresponding csv result, just collect the value with iteration=20 and draw the picture.

<5>,<6>,<7>,<8> are pretty similar step like the above. You just repeat the steps in MultiClassPerceptron.java, MultiClassPA.java, MultiClassAvePerceptron.java

<9>Most importantly, feel free to send me an email in anytime sguan@eecs.wsu.edu if you encounter a programming execution issue. Thanks in advance.

