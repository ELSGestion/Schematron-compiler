package eu.els.schematronCompiler.transform;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.thaiopensource.relaxng.util.Driver;

import eu.els.schematronCompiler.SchematronCompilationException;

public class JingXSLFetcher {
	
	private static final String SCHEMATRON_IN_JING_JAR = "com/thaiopensource/validate/schematron/resources/";
	private static final String ERROR_MESSAGE = "Unable to load Jing Schematron implementation ";
	
	

	public static Source getJingXsl(String name) throws SchematronCompilationException{
		String xslPath = SCHEMATRON_IN_JING_JAR + name;
		try {
			ClassLoader cl = Driver.class.getClassLoader();		
			Source xsl = new StreamSource(cl.getResourceAsStream(xslPath));
			xsl.setSystemId(cl.getResource(xslPath).toString());
			
			return xsl;
		} catch (Exception e){
			throw new SchematronCompilationException(ERROR_MESSAGE + xslPath, e);
		}
	}

	

}
