package eu.els.schematronCompiler;

import java.io.File;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

/**
 * 
 * Compiles a Schematron file to an XSLT using Schematron's SKELETON implementation
 * 
 * 
 * @author ext-jetevenard
 *
 */
public class SchematronCompiler {
	
	// TODO Skeleton parameters ? (autres que allow-foreign
	// TODO étoffer la JavaDoc
	// TODO POM, release et central
	
	
	private static SchematronCompiler instance;
	
	private final static QName allowForeign = new QName("allow-foreign");
	private final static XdmAtomicValue trueAsString = new XdmAtomicValue("true");

	private Processor saxonProcessor;
	private XsltExecutable step1;
	private XsltExecutable step2;
	private XsltExecutable step3;
	private DocumentBuilder documentBuilder;
	private XsltCompiler  compiler;
	
	public static SchematronCompiler getInstance() throws SchematronCompilationException {
		if(instance == null){
			instance = new SchematronCompiler();
		}
		return instance;
	}
	
	private SchematronCompiler() throws SchematronCompilationException{
		saxonProcessor  = new Processor(false);
		compiler = saxonProcessor.newXsltCompiler();
		documentBuilder = saxonProcessor.newDocumentBuilder();
		
		try {
			
			step1 = compiler.compile(getStepAsSource(SchematronStep.STEP1));
			step2 = compiler.compile(getStepAsSource(SchematronStep.STEP2));
			step3 = compiler.compile(getStepAsSource(SchematronStep.STEP3));
			
		} catch (SaxonApiException e) {
			throw new SchematronCompilationException(e);
		}
		
		
	}
	
	

	public void compile(File input, File output) throws SchematronCompilationException  {
		XsltTransformer transformerStep1 = step1.load();
		XsltTransformer transformerStep2 = step2.load();
		XsltTransformer transformerStep3 = step3.load();
		
		Serializer outputSerializer = saxonProcessor.newSerializer();
		outputSerializer.setOutputFile(output);
		
		try {
			
			transformerStep1.setSource(documentBuilder.build(input).asSource());
			transformerStep1.setDestination(transformerStep2);
			transformerStep2.setDestination(transformerStep3);
			transformerStep3.setDestination(outputSerializer);
			transformerStep3.setParameter(allowForeign, trueAsString);
			
			transformerStep1.transform();
			
		} catch (SaxonApiException e) {
			throw new SchematronCompilationException(e);
		}
		
		
		
	}

	private Source getStepAsSource(SchematronStep step) {
		ClassLoader cl = SchematronCompilationException.class.getClassLoader();
		
		InputStream stream = cl.getResourceAsStream(step.getStepPath());
		Source xsltSource = new StreamSource(stream);
		xsltSource.setSystemId(cl.getResource(step.getStepPath()).toString());
		
		return xsltSource;
	}
	

}
