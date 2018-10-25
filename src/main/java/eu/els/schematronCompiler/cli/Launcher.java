package eu.els.schematronCompiler.cli;


import java.io.File;

import javax.xml.transform.Source;

import eu.els.schematronCompiler.SchematronCompilationException;
import eu.els.schematronCompiler.SchematronCompiler;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmNode;

public class Launcher {
	
	public static void main(String[] args) throws SchematronCompilationException  {
		
		try {
			Arguments arguments = new Arguments(args);		
			
			log(arguments);
			SchematronCompiler.getInstance().compile(arguments.getInput(),arguments.getOutput());
		} catch (IllegalArgumentException e) {
			usage(e);
		}		

	}
	
	
	private static void usage(Exception e){
		System.out.println(e.getMessage() + '\n');
		usage();
	}
	
	private static void usage(){
		System.out.println("\nUSAGE\n=====\n");
		
		System.out.println("schematron_path [compiled_xslt_path]");
		System.out.println("If not provided, the compiled XSLT will be {schematron}.compiled.xslt, is the same dir.");
		
		System.out.println("----\nYou can also directly integrate " + SchematronCompiler.class.getName() + " in your Java application.");
	}
	
	private static void log(Arguments arguments){
		System.out.println("Compiling : " + arguments.getInput().getAbsolutePath() );
		System.out.println("to : " + arguments.getOutput().getAbsolutePath() );
		System.out.println("----" );
	}
	


}
