The language for homework is JAVA, and the IDE is Eclipse, the execution environment is JavaSE-1.8
To facilitate you run the code, I include the .classpath and .project file in the zip file. If you open the project in Eclipse environment, then you should be able to directly import this project.
The dependency jar file is libsvm.jar I also included in the zip file.
In Eclipse, you should import this jar file into your project by 
Right click project name homework2 -> Build Path -> Configure Build Path ->Libraries ->Add External JARs ->Import this libsvm.jar file.
Also importantly, I modified the svm_train.java and svm_predict.java a little bit to facilitate the output printing, you must include them in the package of homework2.
The training data should be allocated in the path ../homework2/trainData/
The testing data should be allocated in the path ../homework2/testData/
Please keep the data file naming rule as the original since I use NameHead and NameTail to hardcode all the names. 
For example, the training data should be:
ocr_fold_0_sm_train.txt
The result for Problem 9 will be allocated in the path ../homework2/result/

For Problem 9, we should locate the package homework2 first:
<1>
To get the curve shown in 9.a, you should look for MultiClassClassification.java and run it.
In the console, it will print out current cValue and corresponding nSV, training accuracy, validation accuracy, testing accuracy.
by default. The result will also be saved in ../homework2/result/CValue_Nsv_Problem1.csv and 
../homework2/result/CValue_Accuracy_Problem1.csv.
You need to collect the results and draw the curve using excel or other tools.

<2>
To get the confusion Matrix in 9.b , you should look for ConfusionMatrix.java and run it.
In this time, by default the program will print the confusion matrix into the console and also the 
testing accuracy of C 0.1 we get from 9.a and based on the combined set of training and validation examples. You need to collect the results and draw the confusion matrix using excel or other tools.


<3>
To get the accuracy curve comparison in 9.c , you should look for KernelWithDegree.java and run it.
In this time, by default the program will save the results into the path:
../homework2/result/CVale_Nsv_Problem3.csv and ../homework2/result/CValue-Accuracy_Problem3.csv
In the console, it will print out the current degree value and the corresponding training accuracy, validation accuracy, testing accuracy, nSV by default. You need to collect the results and draw the curve using excel or other tools.

For Problem 10, we should locate the package kernelizedPerceptron
<4>
To get the mistakes curve in 10.a, you should look for BinaryClass.java,
You can change the degree at the very beginning of the code.
private static final int degree = 2/3/4
The console by default will print current iteration and corresponding mistakes.
In the very end, it will print out the the training and testing accuracy.
<5>
To get the mistakes curve in 10.b, you should look for MultiClass.java,
You can change the degree at the very beginning of the code.
private static final int degree = 2/3/4
The console by default will print current iteration and corresponding mistakes.
In the very end, it will print out the the training and testing accuracy.

<6>Most importantly, feel free to send me an email in anytime sguan@eecs.wsu.edu if you encounter a programming execution issue. Thanks in advance.

