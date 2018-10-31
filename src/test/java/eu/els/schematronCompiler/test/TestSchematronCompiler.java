package eu.els.schematronCompiler.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;

import eu.els.schematronCompiler.SchematronCompilationException;
import eu.els.schematronCompiler.SchematronCompiler;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;

public class TestSchematronCompiler {
	
	private static Processor testSaxonProc = new Processor(false);

	
	@Test
	public void tinySampleIso(){
		XdmNode resultXsl = prepareTestCase("test-ISO.sch");
		
		assertXpathTrue(resultXsl, "//report/@test = '@toto'");
	}
	
	@Test
	public void tinySampleSch15(){
		XdmNode resultXsl = prepareTestCase("test-1.5.sch");
		
		assertXpathTrue(resultXsl, "//failed-assertion/@test = '@toto'");
	}
	
	
	/**
	 * <ul>This method :
	 * 
	 * <li>
	 * 	Runs the schematron compilation process with the specified
	 *  test ressource as input and a temporary file as output.<br>
	 *  It ensures that the processing is running well.
	 *  </li>
	 *  <li>
	 *  Read the output file with Saxon and return the built XdmNode so it can used as context item for an XPath test.</li>  
	 *  
	 * 
	 * @param ressource Name of the test case ressource file
	 * @return A Saxon's XdmNode instance of the result document
	 */
	private XdmNode prepareTestCase(String ressource){
		try {
			SchematronCompiler schCompiler = new SchematronCompiler();
			File input = testRessourceAsFile(ressource);
			File output = tmpOutputFile();
			schCompiler.compile(input, output);
			
			assertTrue(output.exists() && output.length() > 1);
			
			return buildFileWithSaxon(output);
			
		} catch (SchematronCompilationException e) {
			throw wrapException(e);
		}
		
		
	}
	
	
	private File tmpOutputFile(){
		try {
			return File.createTempFile("compiled-schematron", ".xsl");
		} catch (IOException e) {
			throw wrapException(e);
		}
	}
	
	private File testRessourceAsFile(String ressource){
		File f;
		try {
			f = new File(TestSchematronCompiler.class.getClassLoader().getResource(ressource).toURI());
		} catch (URISyntaxException e) {
			throw wrapException(e);
		}
		assertTrue(f.exists());
		return f;
	}
	
	private void assertXpathTrue(XdmNode context, String xpath){
		String exp = "boolean(" + xpath + ")";
		try {
			XdmValue result = testSaxonProc.newXPathCompiler().evaluate(exp, context);
			boolean test = ((XdmAtomicValue) result).getBooleanValue();
			
			assertTrue("Xpath assertion " + xpath + " is not verified !",test);
		} catch (SaxonApiException e) {
			throw wrapException(e);
		}
	}
	
	private XdmNode buildFileWithSaxon(File file){
		try {
			return testSaxonProc.newDocumentBuilder().build(file);
		} catch (SaxonApiException e) {
			throw wrapException(e);
		}
	}
	
	private RuntimeException wrapException(Throwable cause){
		return new RuntimeException(cause);
	}
	
	

}
