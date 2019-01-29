package eu.els.schematronCompiler;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmEmptySequence;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

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

	public static void setParameters(Map<QName, Object> parameters, XsltTransformer step) {
		for(Entry<QName, Object> param : parameters.entrySet()) {
			net.sf.saxon.s9api.QName saxonQname = new net.sf.saxon.s9api.QName(param.getKey());
			
			XdmValue value = xdmValueFromObject(param.getValue());
			
			step.setParameter(saxonQname, value);
		}
		
	}

	private static XdmValue xdmValueFromObject(Object object) {
		XdmValue value;
		if(object == null) {
			value = XdmEmptySequence.getInstance();
		} else if (object instanceof Boolean) {
			value = new XdmAtomicValue((Boolean) object);
		} else if(object instanceof Integer){
			value = new XdmAtomicValue((Integer) object);
		} else if(object instanceof Double){
			value = new XdmAtomicValue((Double) object);
		} else {
			value = new XdmAtomicValue(object.toString());
		}
		
		return value;
	}
	
	

}
