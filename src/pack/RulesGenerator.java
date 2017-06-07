package pack;
import java.util.List;

import weka.associations.Apriori;
import weka.associations.AssociationRule;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
 
public class RulesGenerator {
	private DataSource source;
	private double support;
	private double confidence;
	private Instances data = null;
	
	public RulesGenerator(String path , double support, double confidence){
		System.out.println("Generation rules constructor start");
		try {
			source = new DataSource(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: Can't load the file.");
			e.printStackTrace();
		}
		try {
			this.data = source.getDataSet();
			System.out.println("Dataset read");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: Can't read data from the file.");
			e.printStackTrace();
		}
		 // setting class attribute if the data format does not provide this information
		 // For example, the XRFF format saves the class attribute information as well
		if (this.data.classIndex() == -1){
		  this.data.setClassIndex(this.data.numAttributes() - 1);
		}
		this.setSupport(support);
		this.setConfidence(confidence);
		System.out.println("Generation rules constructor finish");
	}

	public List<AssociationRule> AlgorithmApplier(){
		System.out.println("Going to apply Apriori");
		Apriori apriori = new Apriori();
		System.out.println("New Apriori created");
		//Set Support and Significance Level
		apriori.setMinMetric(this.confidence);
		System.out.println("Set confidence");
		apriori.setLowerBoundMinSupport(this.support);
		System.out.println("Set support lower");
		apriori.setUpperBoundMinSupport(1);
		System.out.println("Support defined");
		try {
			System.out.println("Try to produce rules");
			apriori.buildAssociations(this.data); //Generates all Large Item Sets
			System.out.println("Check if we can produce rules");
			if(apriori.canProduceRules()){
				System.out.println("I can produce rules");
				//ArrayList<Object>[] rules = apriori
				System.out.println(apriori);
				return apriori.getAssociationRules().getRules();
				/*for(int i=0; i < rules.length;i++ ){
					System.out.println(rules[i]);
				}*/
				
			}else{
				System.out.println("We can't generate association rules, with the data you've entered.");
			}
		} catch (Exception e) {
			System.out.println("Exception occured");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

}