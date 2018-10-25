package eu.els.schematronCompiler;

public enum SchematronStep {
	STEP1("iso_dsdl_include.xsl"),
	STEP2("iso_abstract_expand.xsl"),
	STEP3("iso_svrl_for_xslt2.xsl");
	
	private String stepPath;
	private static final String PATH = "schematron-code/";
	
	SchematronStep(String step){
		this.stepPath = PATH + step;
	}

	public String getStepPath() {
		return stepPath;
	}
	

}