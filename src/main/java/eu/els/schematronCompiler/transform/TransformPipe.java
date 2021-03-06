package eu.els.schematronCompiler.transform;

import java.io.IOException;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlresolver.Resolver;

import eu.els.schematronCompiler.SaxonHelper;
import eu.els.schematronCompiler.SchematronCompilationException;
import net.sf.saxon.s9api.Destination;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XsltExecutable;

public abstract class TransformPipe {
	
	
	private Source firstStepSource;
	private Destination lastStepDestination;
	
	protected Resolver resolver;
	private Map<QName, Object> parameters;
	
	public static final String SCH_15_NS = "http://www.ascc.net/xml/schematron";
	public static final String SCH_ISO_NS = "http://purl.oclc.org/dsdl/schematron";
	private static final String CATALOGS_SYSTEM_PROPERTY = "xml.catalog.files";
	
	
	
	public static TransformPipe newInstance(Map<QName, Object> parameters, Source schematron, Destination output, String[] catalogs) throws SchematronCompilationException {
		
		setCatalogsSystemProperty(catalogs);
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		try {	
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();	
			SchematronNSFetcher schematronNSContentHandler = new SchematronNSFetcher();	
			reader.setContentHandler(schematronNSContentHandler);	
			reader.parse(new InputSource(schematron.getSystemId()));
			
			switch (schematronNSContentHandler.getRootNamespace()) {
			case SCH_15_NS:
				return new Schematron15TransformPipe(parameters, schematron,output);
			case SCH_ISO_NS:
				return new ISOSchematronTransformPipe(parameters, schematron,output);
			default:
				throw new SchematronCompilationException("Not a schematron document : " + schematron.getSystemId());
			}
	
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new SchematronCompilationException("Error parsing schematron document : " + schematron.getSystemId(), e);
		}
		
		
	}
	
	

	public static TransformPipe newInstance(Map<QName, Object> parameters, Source schematron, Destination output) throws SchematronCompilationException {
		return newInstance(parameters, schematron, output, null);
	}
	
	protected static XsltExecutable CompileXSL(String xslName) throws SchematronCompilationException{
		try {
			return SaxonHelper.getInstance().compile(JingXSLFetcher.getJingXsl(xslName));
		} catch (SaxonApiException e) {
			throw new SchematronCompilationException(e);
		}
	}
	
	
	protected TransformPipe(Map<QName, Object> parameters, Source schematron, Destination output) {
	
		this.resolver = new Resolver();

		this.parameters = parameters;
		this.firstStepSource = schematron;		
		this.lastStepDestination = output;
			
		
	}
	
	private static void setCatalogsSystemProperty(String[] catalogs) {
		if(catalogs == null) return;
		
		StringBuilder semicolonDelimitedList = new StringBuilder();
		for (String catalog : catalogs) {
			semicolonDelimitedList.append(catalog).append(';');
		}
		semicolonDelimitedList.setLength(semicolonDelimitedList.length() - 1);
		
		System.setProperty(CATALOGS_SYSTEM_PROPERTY, semicolonDelimitedList.toString());
	}
	
	
	public abstract void transform() throws SchematronCompilationException;

	public Source getFirstStepSource() {
		return firstStepSource;
	}

	public void setFirstStepSource(Source firstStepSource) {
		this.firstStepSource = firstStepSource;
	}

	public Destination getLastStepDestination() {
		return lastStepDestination;
	}

	public void setLastStepDestination(Destination lastStepDestination) {
		this.lastStepDestination = lastStepDestination;
	}

	public Map<QName, Object> getParameters() {
		return parameters;
	}

	

	


	
	
	
	
	

}
