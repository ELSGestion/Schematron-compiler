package eu.els.schematronCompiler.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.junit.Test;

import eu.els.schematronCompiler.SaxonHelper;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmEmptySequence;
import net.sf.saxon.s9api.XdmValue;

public class TestSaxonParameters {
	
	@Test
	public void testParameterSet() {
		FakeXsltTransformer fakeTransformer = new FakeXsltTransformer();
		
		Map<QName, Object> params = new HashMap<>();
		params.put(new QName("toto", "int"), 12);
		params.put(new QName("toto", "double"), 2.4);
		params.put(new QName("toto", "boolean"), true);
		params.put(new QName("toto", "string"),"hello");
		params.put(new QName("toto", "null"), null);
		
		SaxonHelper.setParameters(params, fakeTransformer);
		
		// Validation
		verifyParams(fakeTransformer);
	}

	private void verifyParams(FakeXsltTransformer fakeTransformer) {
		Map<net.sf.saxon.s9api.QName, XdmValue> receivedParams = fakeTransformer.getReceivedParameters();
		
		assertTrue(((XdmAtomicValue) receivedParams.get(new net.sf.saxon.s9api.QName("toto", "int")))
				.getTypeName().equals(net.sf.saxon.s9api.QName.XS_INT));
		
		assertTrue(((XdmAtomicValue) receivedParams.get(new net.sf.saxon.s9api.QName("toto", "double")))
				.getTypeName().equals(net.sf.saxon.s9api.QName.XS_DOUBLE));
		
		assertTrue(((XdmAtomicValue) receivedParams.get(new net.sf.saxon.s9api.QName("toto", "boolean")))
				.getTypeName().equals(net.sf.saxon.s9api.QName.XS_BOOLEAN));
		
		assertTrue(((XdmAtomicValue) receivedParams.get(new net.sf.saxon.s9api.QName("toto", "string")))
				.getTypeName().equals(net.sf.saxon.s9api.QName.XS_STRING));
		
		assertTrue(((XdmAtomicValue) receivedParams.get(new net.sf.saxon.s9api.QName("toto", "string")))
				.getStringValue().equals("hello"));
		
		assertTrue( receivedParams.get(new net.sf.saxon.s9api.QName("toto", "null")) instanceof XdmEmptySequence);
	}
	
	

}
