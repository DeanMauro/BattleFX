package wekafolder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.Attribute;

public class wekaML {
	
	BufferedReader fileRead;
	Instances fileData;
	Instances[] trainingSplits;
	Instances[] testingSplits;
	
	public Instances myInstances;
	public J48 myTree;
	

	public BufferedReader readData(String filename) {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(filename));
			fileRead = reader;
			//fileData = new Instances(reader);
			//fileData.setClassIndex(fileData.numAttributes() - 1);
		} catch (Exception ex) {
			System.err.println("Error finding file: " + filename + "\n " + ex.toString());
		}
 
		return reader;
	}
	
	public Instances newInstances(String filename) {
		BufferedReader reader = null;
		Instances data=null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			fileRead = reader;
			data = new Instances(reader);
			data.setClassIndex(fileData.numAttributes() - 1);
		} catch (Exception ex) {
			System.err.println("Error finding file: " + filename);
		}
 
		return data;
		
	}
	
	public static Evaluation classify(Classifier model, Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
 
		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);
 
		
		return evaluation;
	}
	
	public static double calculateAccuracy(FastVector predictions) {
		double correct = 0;
 
		for (int i = 0; i < predictions.size(); i++) {
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
 
		return 100 * correct / predictions.size();
	}
	
	public Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
 
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
 
		trainingSplits = split[0];
		testingSplits = split[1];
		return split;
	}
	
	public void predict() {
		J48 dTree = new J48();
		FastVector predictions = new FastVector();
		
		for (int i=0; i < trainingSplits.length; i++) {
			try {
				Evaluation validation = classify(dTree,trainingSplits[i], testingSplits[i]);
				predictions.appendElements(validation.predictions());
				double accuracy = calculateAccuracy(predictions);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public double[] newInstancePrediction(String[] features) {
		Classifier model = (Classifier) new J48();
		try {
			model.buildClassifier(fileData);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Instances newInstances = newInstances("feats.txt");

		Instance newInst = new Instance(35);
		newInst.setValue(newInstances.attribute(0), 1.0);
		
		for (int i=0; i<35; i++) {
			if (i == 1 || i == 32 || i == 33) {
				newInst.setValue(newInstances.attribute(i), Double.parseDouble(features[i]));
			}
			else {
				newInst.setValue(newInstances.attribute(i), features[i]);
			}
		}
		newInstances.add(newInst);
		
		newInst.setDataset(fileData);
		
		double[] predictions = null;
		try {
			predictions = model.distributionForInstance(newInst);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return predictions;
	}
	
	public J48 makeTree(String fileName) throws Exception {
		BufferedReader reader = readData(fileName);
		
		Instances data = new Instances(reader);
		data.setClassIndex(data.numAttributes() - 1);
		
		Evaluation eval = new Evaluation(data);
		J48 tree = new J48();
		String[] options = new String[1];
		options[0] = "-U";
		tree.setOptions(options);
		tree.buildClassifier(data);
		eval.crossValidateModel(tree, data, 10, new Random(1));
		
		myTree = tree;
		myInstances = data;
		
		return tree;
	}
	
	
	public wekaML() {
		
	}
	public wekaML(String fileName) {
	}
	
	public static void main (String[] args) throws Exception {		
		wekaML ml = new wekaML();
		BufferedReader reader = ml.readData("Log.txt");
		
		Instances data = new Instances(reader);
		data.setClassIndex(data.numAttributes() - 1);
		
		Evaluation eval = new Evaluation(data);
		J48 tree = new J48();
		String[] options = new String[1];
		options[0] = "-U";
		tree.setOptions(options);
		tree.buildClassifier(data);
		eval.crossValidateModel(tree, data, 10, new Random(1));
		
		System.out.println(tree.toString());
		/*Instances[] split = ml.crossValidationSplit(data, 10);
		
		Instances trainingSplit = split[0];
		Instances testingSplit = split[1];
		
		Classifier[] models = {
				new J48()
		};
		
		for (int j=0; j<models.length; j++) {
			FastVector predictions = new FastVector();
			
			for (int i = 0; i< trainingSplit.length; i++) {
				Evaluation validation = classify(models[j], trainingSplit[i], testingSplit[i]);
				
				predictions.appendElements(validation.predictions());
				
				System.out.println(models[j].toString());
			}
			
			double accuracy = calculateAccuracy(predictions);
			
			System.out.println("Accuracy of " + models[j].getClass().getSimpleName() + ": " + String.format("%.2f%%", accuracy) + "\n---------------------");
		}
		*/
	}
}
