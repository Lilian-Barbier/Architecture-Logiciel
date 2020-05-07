package model.xml;

import model.list.IListBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XPLPlaylistHandler extends DefaultHandler {

	/**
	 * Le builder permettant de fabriquer une Playlist
	 */
	private final IListBuilder builder;

	/**
	 * L'indicateur de profondeur de la playlist
	 */
	int sublist;

	/**
	 * Drapeau indiquant si on est dans une balise Audio ou non
	 */
	boolean audio;

	/**
	 * Drapeau indiquant si on est dans une balise Video ou non
	 */
	boolean video;

	public XPLPlaylistHandler(IListBuilder builder) {
		if (builder == null) {
			throw new AssertionError("Paramètre invalide XPLPlaylistHandler constructeur");
		}
		this.builder = builder;
		sublist = 0;
		audio = false;
		video = false;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		System.out.println("Start Element :" + qName);
		if (qName.equalsIgnoreCase("sublist")) {
			sublist++;
			builder.startSublist();
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

	public void endElement(String uri, String localName, String qName) {
		if (qName.equalsIgnoreCase("sublist")) {
			sublist--;
			builder.stopSublist();
			System.out.println("End Element : sublist");
		}
	}

	public void characters(char[] data, int start, int length) {
		builder.addPath(new String(data, start, length));
		if (audio) {
			audio = false;
			builder.stopAudio();
			System.out.println("End Element : audio");
		} else if (video) {
			video = false;
			builder.stopVideo();
			System.out.println("End Element : video");
		}
	}
}
