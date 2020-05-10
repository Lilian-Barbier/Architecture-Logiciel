package model.xml;

import java.io.FileWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import model.list.Audio;
import model.list.IMedia;
import model.list.SubList;
import model.list.Video;
import model.playlist.IPlaylist;

public class XMLPlaylistSaver {

	public static void save(IPlaylist iPlaylist) {
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		XMLStreamWriter xsw = null;
		try {
			String urlCourante = XMLPlaylistSaver.class.getProtectionDomain().getCodeSource().getLocation().getFile();
			urlCourante = urlCourante.substring(0, urlCourante.lastIndexOf("ArchiLogiciel"));
			int index = iPlaylist.getPlaylist().getName().lastIndexOf(".");
			if (index != -1) {
				xsw = xof.createXMLStreamWriter(new FileWriter(urlCourante + "saves/"
						+ iPlaylist.getPlaylist().getName().substring(0, index) + ".xpl"));
			} else {
				xsw = xof.createXMLStreamWriter(new FileWriter(urlCourante + "saves/"
						+ iPlaylist.getPlaylist().getName() + ".xpl"));
			}
			xsw.writeStartDocument();
			xsw.writeStartElement("playlist");
			SubList media = (SubList) iPlaylist.getPlaylist();
			saveDeep(xsw, media);
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

	/**
	 * Permet de parcourir en profondeur les sous-listes
	 * @param xsw le XMLStreamWriter entrain d'être utilisé dans la méthode save
	 * @param sublist la sous-liste à parcourir
	 * @throws XMLStreamException exception levée en cas d'erreur d'écriture
	 */
	private static void saveDeep(XMLStreamWriter xsw, SubList sublist) throws XMLStreamException {
		for (IMedia l : sublist.getContains()) {
			if (l instanceof Audio) {
				xsw.writeStartElement("audio");
				xsw.writeCData(l.getPath());
			}
			if (l instanceof Video) {
				xsw.writeStartElement("video");
				xsw.writeCData(l.getPath());
			}
			if (l instanceof SubList) {
				xsw.writeStartElement("sublist");
				xsw.writeAttribute("title", l.getName());
				saveDeep(xsw, (SubList) l);
			}
			xsw.writeEndElement();
		}
	}
}
