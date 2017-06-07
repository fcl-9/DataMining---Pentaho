<%@ page import="java.util.List, pack.CSV2Arff, pack.RulesGenerator, pack.Transformations, org.pentaho.di.trans.Trans, weka.associations.AssociationRule"  %>
<%
try{
	String base = "C:\\sad\\PBI\\workspace\\Fase 2\\";
	String [] fileNames = {"TASKDATA1","TASKDATA2","TASKDATA3"};
	double support;
	double confidence;

	for(int i = 0; i < fileNames.length; i++) {
		if (i == 0){
			out.println("<h1>During 2005 in France were found these rules:</h1>");
			support = 0.07;
			confidence = 0.9;
		}
		else if(i == 1){
			out.println("<h1>During 2004 and 2005 in the territory EMEA were found these rules:</h1>");
			support = 0.2;
			confidence = 0.5;
		}
		else {
			out.println("<h1>Since the begining of our activities in USA were found these rules:</h1>");
			support = 0.2;
			confidence = 0.5;
		}
		Transformations transformation = new Transformations();
		transformation.runTransformationFromFileSystem(base + fileNames[i] + ".ktr");
		CSV2Arff fileConverted = new CSV2Arff();
		fileConverted.fileConversion(base + fileNames[i]);
		System.out.println("Going to generate rules");
		RulesGenerator rulesGen = new RulesGenerator(base + fileNames[i]+".arff", support, confidence);
		List<AssociationRule> rules = rulesGen.AlgorithmApplier();
		for (AssociationRule rule : rules) {
			out.println("<p>People that buys " + rule.getPremise() + " also buys " + rule.getConsequence() + " with a confidence of " + rule.getNamedMetricValue("Confidence")*100 + "%.</p>");
		}
	}
}catch ( Exception e ) {

	// something went wrong, just log and return 
	e.printStackTrace();
}

%>