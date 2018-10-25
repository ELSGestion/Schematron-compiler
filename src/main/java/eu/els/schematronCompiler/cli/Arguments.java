package eu.els.schematronCompiler.cli;

import java.io.File;

public class Arguments {
	
	private File input;
	private File output;
	
	public Arguments(String[] args) {
		if(args.length < 1 || args.length > 2){
			throw new IllegalArgumentException();
		}
		
		input = new File(args[0]);
		if( ! input.exists()){
			throw new IllegalArgumentException("File not found : " + input.getAbsolutePath());
		}
		
		if(args.length == 2 && !(args[1].isEmpty())){
			output = new File(args[1]);
			output.getParentFile().mkdirs();
		} else {
			output = new File(input.getParentFile(),input.getName() + ".compiled.xsl");
		}
		
		
	}

	public File getInput() {
		return input;
	}

	public File getOutput() {
		return output;
	}
	
	
}
