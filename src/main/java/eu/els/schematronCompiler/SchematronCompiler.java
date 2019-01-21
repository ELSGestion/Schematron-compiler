package eu.els.schematronCompiler;

import java.io.File;

import javax.xml.transform.Source;

import eu.els.schematronCompiler.transform.TransformPipe;
import net.sf.saxon.s9api.Destination;
import net.sf.saxon.s9api.SaxonApiException;

/**
 * 
 * API entry-point
 * 
 * 
 * @author ext-jetevenard
 *
 */
public class SchematronCompiler {
	
	private String[] catalogs;
	
	public SchematronCompiler() {
		
	}
	
	public SchematronCompiler(String... catalogs) {
		this.catalogs = catalogs;
	}
	
	public void compile(File input, File output) throws SchematronCompilationException  {
			
		Source inputAsSource;
		try {
			
			inputAsSource = SaxonHelper.getInstance().getDocumentBuilder().build(input).asSource();		
			Destination outputDestination = SaxonHelper.getInstance().getProcessor().newSerializer(output);
			
			compile(inputAsSource, outputDestination);	
			
		} catch (SaxonApiException e) {
			throw new SchematronCompilationException(e);
		}
		
	}
	
	public void compile(Source input, Destination output) throws SchematronCompilationException {

		TransformPipe pipe;
		if (this.hasCatalogs()) {
			pipe = TransformPipe.newInstance(input, output, this.catalogs);
		} else {
			pipe = TransformPipe.newInstance(input, output);
		}
		pipe.transform();

	}
	
	private boolean hasCatalogs(){
		return this.catalogs != null && this.catalogs.length > 0;
	}

	public String[] getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(String[] catalogs) {
		this.catalogs = catalogs;
	}
	
	

}
