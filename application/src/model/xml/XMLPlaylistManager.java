package model.xml;

import model.list.*;
import model.playlist.IPlaylistManager;
import model.playlist.Playlist;
import model.playlist.PlaylistManager;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileWriter;

public class XMLPlaylistManager extends PlaylistManager {

	// CONSTRUCTEUR

	public XMLPlaylistManager() {}

	// COMMANDES

	public void load(File f) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			XplPlaylistHandler handler = new XplPlaylistHandler(getBuilder());
			saxParser.parse(f, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getBuilder().getPlaylist();
	}

	public void save() {
		// On récupère tout les éléments à ajouter
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter xsw = null;
		try{
			xsw = xof.createXMLStreamWriter(new FileWriter("./saves/" + getPlaylist().getName() + ".xml"));
			xsw.writeStartDocument();
			xsw.writeStartElement("list");
			for (IMedia l : getPlaylist().getPlaylist()) {
				xsw.writeStartElement("sublist");
				xsw.writeCData(l.getPath());
				xsw.writeEndElement();
			}
			xsw.writeEndElement();
			xsw.writeEndDocument();
			xsw.flush();
		}
		catch (Exception e) {
			System.err.println("Unable to write the file: " + e.getMessage());
		}
		finally {
			try {
				if (xsw != null) {
					xsw.close();
				}
			}
			catch (Exception e) {
				System.err.println("Unable to close the file: " + e.getMessage());
			}
		}
	}

    public static void main(String argv[]) {
    	File f = new File("/home/barbier/Documents/TP-Master/ArchiLogicielle/Projet/ArchiLogiciel/compilation/playlist.xpl");
    	XMLPlaylistManager loader = new XMLPlaylistManager();

		loader.load(f);
    }
}
