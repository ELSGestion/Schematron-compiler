package eu.els.schematronCompiler;

import java.io.File;

import eu.els.schematronCompiler.transform.TransformPipe;
import eu.els.schematronCompiler.transform.TransformPipeFactory;

/**
 * 
 * API entry-point
 * 
 * 
 * @author ext-jetevenard
 *
 */
public class SchematronCompiler {
	
	public void compile(File input, File output) throws SchematronCompilationException  {
			
		TransformPipe pipe = TransformPipe.getInstance(input, output);	
		pipe.transform();	
		
	}

}
