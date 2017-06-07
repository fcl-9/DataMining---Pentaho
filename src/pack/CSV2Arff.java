package pack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class CSV2Arff {
	  /**
	   * takes 2 arguments:
	   * - CSV input file
	   * - ARFF output file
	   */
	  public void fileConversion(String filename) throws Exception {
	    // load CSV
		  System.out.println("Starting CSV Conversion");
	    CSVLoader loader = new CSVLoader();
	    loader.setSource(new File(filename + ".csv"));
	    Instances data = loader.getDataSet();
	    
	    // Create list to hold nominal values "first", "second", "third" 
	    System.out.println("Creating Nominal Values");
	    List<String> my_nominal_values = new ArrayList<String>(3); 
	    my_nominal_values.add("y"); 
	    my_nominal_values.add("n"); 
	    
	    ArrayList<Attribute> headValue = new ArrayList<Attribute>();    
	    for(int i = 0; i < data.numInstances(); i++){
	    	String row = data.instance(i).toString();	
	    	String [] splitedString = row.split("\\# ");
	    	for(int k = 0; k < splitedString.length - 1; k++){
	    		if(k == 0){
	    			splitedString[k] = splitedString[k].substring(splitedString[k].lastIndexOf("\'") + 1);
	    		}
	    		if(!headValue.contains(new Attribute(splitedString[k].replaceAll("\'",""),my_nominal_values))){ //Add all items to header array
	    			headValue.add(new Attribute(splitedString[k].replaceAll("\'",""),my_nominal_values));
	    		}
	    	}
	    }
	    
	    //Create the new dataset
	    System.out.println("Creating Dataset");
	    Instances newDataset = new Instances("Dataset", headValue, data.numInstances());
	    
	    for(int l = 0; l < data.numInstances(); l++){
	    	Instance inst = new DenseInstance(headValue.size()); 
	    	
	    	String row = data.instance(l).toString();	
	    	String [] splitedString = row.split("\\# ");
	    	
	    	for(int n = 0; n < splitedString.length; n++){
	    		for(int m = 0; m < headValue.size(); m++){
	    			System.out.print(splitedString[n] + " = ");
	    			System.out.print(headValue.get(m).name() + " ");
	    			System.out.print((splitedString[n].replaceAll("\'","")).equals(headValue.get(m).name()));
	    			System.out.println("");
	    			if((splitedString[n].replaceAll("\'","")).equals(headValue.get(m).name())){
	    				inst.setValue(headValue.get(m), "y"); 
	    			}else{
	    				//inst.setValue(headValue.get(m), "?"); 
	    			}
	    		}
	    	}
		    newDataset.add(inst);
	    }

	    // save ARFF
	    System.out.println("Saving ARFF");
	    ArffSaver saver = new ArffSaver();
	    saver.setFile(new File(filename + ".arff"));
	    saver.setInstances(newDataset);
	    //saver.setDestination(new File(args[1]));
	    saver.writeBatch();
	  }
}
