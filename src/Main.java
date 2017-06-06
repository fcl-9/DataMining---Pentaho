import java.util.Scanner;

import org.pentaho.di.trans.Trans;

public class Main {
	static CSV2Arff fileConverted;
	static RulesGenerator rules;
	static Tranformations transformation;
	
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		String inputedVal = "0"; 
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
		scan.close();
	 }
	private static void fileConversion(){
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
	}
	
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
		transformation = new Tranformations();
		return transformation.runTransformationFromFileSystem(_filename);
	}
}
