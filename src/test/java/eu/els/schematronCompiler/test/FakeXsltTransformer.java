package eu.els.schematronCompiler.test;

import java.util.HashMap;
import java.util.Map;

import eu.els.schematronCompiler.SaxonHelper;
import net.sf.saxon.expr.instruct.GlobalParameterSet;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.s9api.XsltTransformer;

public class FakeXsltTransformer extends XsltTransformer {
	
	private Map<QName, XdmValue> receivedParameters = new HashMap<>();

	public FakeXsltTransformer() {
		super(SaxonHelper.getInstance().getProcessor(), null, new GlobalParameterSet());
	}
	
	@Override
	public void setParameter(QName name, XdmValue value) {
		this.receivedParameters.put(name, value);
	}

	public Map<QName, XdmValue> getReceivedParameters() {
		return receivedParameters;
	}


	
	
	
}
