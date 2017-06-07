package pack;
import java.util.List;
import java.util.Scanner;

import org.pentaho.di.trans.Trans;

import weka.associations.AssociationRule;

public class Main {
	static CSV2Arff fileConverted;
	static RulesGenerator rules;
	static Transformations transformation;
	
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		/*String inputedVal = "0"; 
		while(true){
				System.out.println("########## MENU ##########");
				System.out.println("1 - File Conversion");
				System.out.println("2 - Rule Generation");
				System.out.println("3 - Ktr Execution");
				System.out.println("4 - Exit");
				System.out.println("########## ---- ##########");
				inputedVal = scan.next();
			 
			 if(inputedVal.equals("1")){
				 fileConversion();
			 }
			 else if(inputedVal.equals("2")){
				 ruleGenMenu();
			 }else if(inputedVal.equals("3")){
				 tranformationExec();
			 }else if(inputedVal.equals("4")){
					System.out.println("Exiting...");
					break;
			 }
		 }
		scan.close();*/
		String base = "C:\\sad\\PBI\\workspace\\Fase 2\\";
		String [] fileNames = {"TASKDATA1"};//,"TASKDATA2","TASKDATA3"};

		for(int i = 0; i < fileNames.length; i++) {
			Transformations transformation = new Transformations();
			transformation.runTransformationFromFileSystem(base + fileNames[i] + ".ktr");
			CSV2Arff fileConverted = new CSV2Arff();
			fileConverted.fileConversion(base + fileNames[i]);
			System.out.println("Going to generate rules");
			RulesGenerator rulesGen = new RulesGenerator(base + fileNames[i]+".arff", 0.3, 0.9);
			List<AssociationRule> rules = rulesGen.AlgorithmApplier();
			for (AssociationRule rule : rules) {
				System.out.println("People that buys " + rule.getPremise() + " also buys " + rule.getConsequence() + " with a confidence of " + rule.getNamedMetricValue("Confidence")*100 + "%.");
			}
		}
	 }
	/*private static void fileConversion(){
		System.out.println("########## File Conversion ##########");
		fileConverted = new CSV2Arff();
	    String[] args1s = new String[2];
	    System.out.println("Insert name of your input CSV");
	    String inputCSV =scan.next();
	    args1s[0] = inputCSV;
	    System.out.println("Insert name of your output ARFF");
	    String outputARFF = scan.next();
	    args1s[1] = outputARFF ;
		try {
			fileConverted.fileConversion(args1s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	private static void ruleGenMenu(){
		System.out.println("########## Rule Generation ##########");
		System.out.println("Insert path");
		String path = scan.next();
		System.out.println("Insert support value");
		String support = scan.next();
		System.out.println("Insert significance value");
		String significance = scan.next();
		rules = new RulesGenerator(path, Double.parseDouble(support), Double.parseDouble(significance));
		rules.AlgorithmApplier();
	}
	
	private static Trans tranformationExec(){
		System.out.println("########## Kettle Tranformation ##########");
		System.out.println("Insert Filename");
		String _filename = scan.next();
		transformation = new Transformations();
		return transformation.runTransformationFromFileSystem(_filename);
	}
}
