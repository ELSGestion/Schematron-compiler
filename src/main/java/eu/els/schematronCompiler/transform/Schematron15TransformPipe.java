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

public class Schematron15TransformPipe extends TransformPipe {
	
	private static final String SCHEMATRON_XSLT_NAME = "schematron.xsl";
	private static XsltExecutable xslt;

	Schematron15TransformPipe(Map<QName, Object> parameters, Source schematron, Destination output) throws SchematronCompilationException {
		super(parameters, schematron,output);
		if(xslt == null) xslt = CompileXSL(SCHEMATRON_XSLT_NAME);
		
	}

	@Override
	public void transform() throws SchematronCompilationException {
		XsltTransformer step = xslt.load();
		try {
			step.setSource(getFirstStepSource());
			step.setDestination(getLastStepDestination());
			step.setURIResolver(this.resolver);
			
			SaxonHelper.setParameters(getParameters(),step);
			
			step.transform();
		} catch (SaxonApiException e) {
			throw new SchematronCompilationException(e);
		}

	}

}
