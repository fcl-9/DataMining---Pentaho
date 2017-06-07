<%@ page import="pack.CSV2Arff, pack.RulesGenerator, pack.Transformations, org.pentaho.di.trans.Trans"  %>
<%
try{
	String base = "C:\\sad\\PBI\\workspace\\Fase 2\\";
	String [] fileNames = {"TASKDATA1","TASKDATA2","TASKDATA3"};

	for(int i = 0; i < fileNames.length; i++) {
		Transformations transformation = new Transformations();
		transformation.runTransformationFromFileSystem(base + fileNames[i] + ".ktr");
		CSV2Arff fileConverted = new CSV2Arff();
		fileConverted.fileConversion(base + fileNames[i]);
		RulesGenerator rules = new RulesGenerator(base + fileNames[i]+".arff", 0.3, 0.9);
		rules.AlgorithmApplier();
	}
}catch ( Exception e ) {

	// something went wrong, just log and return 
	e.printStackTrace();
}

%>