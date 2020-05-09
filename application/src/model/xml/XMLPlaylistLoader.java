package model.xml;

import model.list.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class XMLPlaylistLoader {

	// COMMANDES
	public static void load(File f, IListBuilder builder) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			XPLPlaylistHandler handler = new XPLPlaylistHandler(builder);
			saxParser.parse(f, handler);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
