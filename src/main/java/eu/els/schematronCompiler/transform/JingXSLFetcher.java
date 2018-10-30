package eu.els.schematronCompiler.transform;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.thaiopensource.relaxng.util.Driver;

public class JingXSLFetcher {
	
	private static final String SCHEMATRON_IN_JING_JAR = "com/thaiopensource/validate/schematron/resources/";
	
	

	public static Source getJingXsl(String name){
		String xslPath = SCHEMATRON_IN_JING_JAR + name;
		ClassLoader cl = Driver.class.getClassLoader();
		
		Source xsl = new StreamSource(cl.getResourceAsStream(xslPath));
		xsl.setSystemId(cl.getResource(xslPath).toString());
		
		return xsl;
	}

	

}
