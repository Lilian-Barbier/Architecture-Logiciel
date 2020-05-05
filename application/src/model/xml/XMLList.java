package model.xml;

import model.list.IList;
import model.list.List;
import model.list.ListBuilder;
import model.list.ListBuilderStd;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class XMLList {

    // ATTRIBUTS

    private IList list;
    private ListBuilder builder;

    // CONSTRUCTEUR

    public XMLList() {
        list = new List();
        builder = new ListBuilderStd();
    }

    public XMLList(int duration, String name) {
        list = new List(duration, name);
        builder = new ListBuilderStd();
    }

    // METHODES

    public IList getList() {
        return list;
    }
    public ListBuilder getBuilder() {
        return builder;
    }

    
    // COMMANDES
    public void load(File f) {
        
    	try {
        	SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            XplPlaylistHandler handler = new XplPlaylistHandler(builder);
            saxParser.parse(f, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	this.builder.getPlaylist();
    }

    
    
    
    
    
    public void save() {
        // On récupère tout les éléments à ajouter
        java.util.List<IList> fullList = new ArrayList<IList>();

        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        XMLStreamWriter xsw = null;
        try{
            xsw = xof.createXMLStreamWriter(new FileWriter(getList().getName() + ".xml"));
            xsw.writeStartDocument();
            xsw.writeStartElement("list");
            for (IList l : fullList) {
                xsw.writeStartElement("sublist");
                xsw.writeAttribute("path", l.getName());
                xsw.writeEndElement();
            }
            xsw.writeEndElement();
            xsw.writeEndDocument();
            xsw.flush();
        }
        catch (Exception e){
            System.err.println("Unable to write the file: " + e.getMessage());
        }
        finally{
            try{
                if (xsw != null){
                    xsw.close();
                }
            }
            catch (Exception e){
                System.err.println("Unable to close the file: " + e.getMessage());
            }
        }
    }
    
    
    
    public static void main(String argv[]) {
    	 
    	File f = new File("/home/barbier/Documents/TP-Master/ArchiLogicielle/Projet/ArchiLogiciel/compilation/playlist.xpl");
    	XMLList loader = new XMLList();
    	
		loader.load(f);
		

    }
    
    
}
