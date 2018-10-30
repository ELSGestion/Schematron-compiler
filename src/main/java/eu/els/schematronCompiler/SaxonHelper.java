package eu.els.schematronCompiler;

import javax.xml.transform.Source;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;

public class SaxonHelper {
	
	private static SaxonHelper instance;
	
	private Processor saxonProcessor;
	private DocumentBuilder documentBuilder;
	private XsltCompiler  compiler;
	
	private SaxonHelper() {
		saxonProcessor  = new Processor(false);
		compiler = saxonProcessor.newXsltCompiler();
		documentBuilder = saxonProcessor.newDocumentBuilder();
	}
	
	public static SaxonHelper getInstance(){
		if(instance == null) instance = new SaxonHelper();
		return instance;
	}

	public Processor getProcessor() {
		return saxonProcessor;
	}

	public DocumentBuilder getDocumentBuilder() {
		return documentBuilder;
	}

	public XsltCompiler getXsltCompiler() {
		return compiler;
	}
	
	public XsltExecutable compile(Source xsl) throws SaxonApiException{
		return compiler.compile(xsl);
	}
	
	

}
