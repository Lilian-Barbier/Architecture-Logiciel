package model.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.list.ListBuilder;

public class XplPlaylistHandler extends DefaultHandler {

	private ListBuilder builder;
	
	private String currentElement = null;
	
	int sublist = 0;
	boolean audio = false;
	boolean video = false;
	
	
	/**
	 * Récupération du nom du noeud
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		System.out.println("Start Element :" + qName);
		
		if (qName.equalsIgnoreCase("sublist")) {
			sublist++;
			builder.startSublist();
			
			if(attributes==null) {
				//TODO : Exception
			}
			String title = attributes.getValue("title");
			System.out.println(" attribute :" + title);
			
			builder.addName(title);
		}
		
		if (qName.equalsIgnoreCase("audio")) {
			audio = true;
			builder.startAudio();
		}
		
		if (qName.equalsIgnoreCase("video")) {
			video = true;
			builder.startVideo();
		}
		
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		//System.out.println("End Element :" + qName);
		
		if (qName.equalsIgnoreCase("sublist")) {
			sublist--;
			builder.stopSublist();
			System.out.println("End Element : sublist");
		}
	}
	
	/**
	 * permet de récupérer la valeur d'un nœud (path pour audio et video)
	 */
	public void characters(char[] data, int start, int length){   
		
		builder.addPath(new String(data, start, length));
		
		if(audio) {
			audio = false;
			builder.stopAudio();
			System.out.println("End Element : audio");
		}
		
		else if(video) {
			video = false;
			builder.stopVideo();
			System.out.println("End Element : video");
		}
		

		
	}
	
	
	public XplPlaylistHandler(ListBuilder builder) {
		this.builder = builder; 
	}
		
}
