package eu.els.schematronCompiler;

import java.io.File;

import eu.els.schematronCompiler.transform.TransformPipe;
import eu.els.schematronCompiler.transform.TransformPipeFactory;

/**
 * 
 * Compiles a Schematron file to an XSLT using Schematron's SKELETON implementation
 * 
 * 
 * @author ext-jetevenard
 *
 */
public class SchematronCompiler {
	
	
	
	private static SchematronCompiler instance;


	private TransformPipeFactory pipeFactory;
	
	public static SchematronCompiler getInstance() throws SchematronCompilationException {
		if(instance == null){
			instance = new SchematronCompiler();
		}
		return instance;
		// TODO cette classe n'a plus à être un singleton
		// TODO Cette classe à t elle encore un raison d'être ?
	}
	

	public void compile(File input, File output) throws SchematronCompilationException  {
			
		TransformPipe pipe = TransformPipe.getInstance(input, output);
		
		pipe.transform();	
		
	}

	
	

}
