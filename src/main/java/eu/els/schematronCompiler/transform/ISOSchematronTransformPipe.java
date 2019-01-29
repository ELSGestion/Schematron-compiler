package eu.els.schematronCompiler.transform;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;

import eu.els.schematronCompiler.SaxonHelper;
import eu.els.schematronCompiler.SchematronCompilationException;
import net.sf.saxon.s9api.Destination;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

public class ISOSchematronTransformPipe extends TransformPipe {
	
	private static final String ISO_DSDL_XSLT_NAME = "iso-dsdl-include.xsl";
	private static final String ISO_SCHEMATRON_XSLT_NAME = "iso-schematron.xsl";
	
	private static XsltExecutable dsdlIncludeXSLT;
	private static XsltExecutable isoSchematronXSLT;

	ISOSchematronTransformPipe(Map<QName, Object> parameters, Source schematron, Destination output) throws SchematronCompilationException {
		super(parameters, schematron,output);
		
		if(dsdlIncludeXSLT == null) dsdlIncludeXSLT = CompileXSL(ISO_DSDL_XSLT_NAME);
		if(isoSchematronXSLT == null) isoSchematronXSLT = CompileXSL(ISO_SCHEMATRON_XSLT_NAME);
	}
	
	

	@Override
	public void transform() throws SchematronCompilationException {
		XsltTransformer step1 = dsdlIncludeXSLT.load();
		XsltTransformer step2 = isoSchematronXSLT.load();
		
		try {
			step1.setURIResolver(this.resolver);
			step2.setURIResolver(this.resolver); // Probably not usefull for step2, but who knows...
			
			SaxonHelper.setParameters(getParameters(), step1);
			SaxonHelper.setParameters(getParameters(), step2);
			
			step1.setSource(getFirstStepSource());
			step1.setDestination(step2);
			step2.setDestination(getLastStepDestination());
			
			step1.transform();
		} catch (SaxonApiException e) {
			throw new SchematronCompilationException(e);
		}

	}

}
