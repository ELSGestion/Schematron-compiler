package eu.els.schematronCompiler.transform;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class SchematronNSFetcher implements ContentHandler {
	
	private boolean firstElementPassed = false;
	private String rootNamespace;

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		// nope.

	}

	@Override
	public void endDocument() throws SAXException {
		// nope.

	}

	@Override
	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		// nope.

	}

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		// nope.

	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
		// nope.

	}

	@Override
	public void processingInstruction(String arg0, String arg1) throws SAXException {
		// nope.

	}

	@Override
	public void setDocumentLocator(Locator arg0) {
		// nope.

	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {
		// nope.

	}

	@Override
	public void startDocument() throws SAXException {
		// nope.

	}

	@Override
	public void startElement(String nsUri, String arg1, String arg2, Attributes arg3) throws SAXException {
		if(!firstElementPassed){
			this.rootNamespace = nsUri;
			this.firstElementPassed = true;
		}

	}

	@Override
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
		// nope.

	}

	public String getRootNamespace() {
		if(!firstElementPassed){
			throw new IllegalStateException("Root element not yet read");
		}
		return rootNamespace;
	}


}
