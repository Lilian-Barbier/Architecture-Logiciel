package model.xml;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements org.xml.sax.ErrorHandler {

	// ATTRIBUTS
	private boolean errorOccured = false;

	
	// METHODES
	public boolean hasError() {
		return errorOccured;
	}
	
	// COMMANDES
	@Override
	public void warning(SAXParseException exception) throws SAXException {
        System.out.println(exception.getMessage());
		errorOccured = true;
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
        System.out.println(exception.getMessage());
		errorOccured = true;		
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println(exception.getMessage());
		errorOccured = true;		
	}
}
