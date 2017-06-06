import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
 
public class RulesGenerator {
	private DataSource source;
	private double support;
	private double confidence;
	private Instances data = null;
	
	public RulesGenerator(String path , double support, double confidence){
		try {
			source = new DataSource(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: Can't load the file.");
			e.printStackTrace();
		}
		try {
			this.data = source.getDataSet();
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
	}

	public void AlgorithmApplier(){
		Apriori apriori = new Apriori();
		//Set Support and Significance Level
		apriori.setMinMetric(this.confidence);
		apriori.setLowerBoundMinSupport(this.support);
		apriori.setUpperBoundMinSupport(1);
		try {
			apriori.buildAssociations(this.data); //Generates all Large Item Sets
			if(apriori.canProduceRules()){
				//ArrayList<Object>[] rules = apriori
				System.out.println(apriori);
				/*for(int i=0; i < rules.length;i++ ){
					System.out.println(rules[i]);
				}*/
				
			}else{
				System.out.println("We can't generate association rules, with the data you've entered.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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