package model.xml;

import java.io.FileWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import model.playlist.IPlaylist;

public class XMLPlaylistSaver {

	public static void save(IPlaylist iPlaylist) {
		// On récupère tous les éléments à ajouter
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter xsw = null;
		try {
			xsw = xof.createXMLStreamWriter(new FileWriter("./saves/" + iPlaylist.getName() + ".xml"));
			xsw.writeStartDocument();
			xsw.writeStartElement("list");
			/*for (IMedia l : getPlaylist().getPlaylist()) {
				xsw.writeStartElement("sublist");
				xsw.writeCData(l.getPath());
				xsw.writeEndElement();
			}*/
			xsw.writeEndElement();
			xsw.writeEndDocument();
			xsw.flush();
		} catch (Exception e) {
			System.err.println("Unable to write the file: " + e.getMessage());
		} finally {
			try {
				if (xsw != null) {
					xsw.close();
				}
			} catch (Exception e) {
				System.err.println("Unable to close the file: " + e.getMessage());
			}
		}
	}

}
