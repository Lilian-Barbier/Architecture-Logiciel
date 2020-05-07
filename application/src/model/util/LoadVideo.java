package model.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.list.IMedia;
import model.list.Video;

@SuppressWarnings("unused")
public class LoadVideo implements LoadFiles {

	@Override
	public IMedia loadFile(String path) {
        if (path == null) {
            throw new AssertionError("Paramètre invalide LoadVideo addFile");
        }
        
		Video v = new Video(path);
        BufferedReader br = null;
        try {
        	br = new BufferedReader(new FileReader(path));
            v.setDuration(Integer.parseInt(br.readLine()));
            v.setName(br.readLine());
            v.setResolution(br.readLine());
        } catch(FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture");
            throw new AssertionError("Erreur d'ouverture");
        } catch (NumberFormatException e) {
            System.out.println("Erreur ParseInt");
            throw new AssertionError("Erreur ParseInt");
		} catch (IOException e) {
            System.out.println("Erreur IOException");
            throw new AssertionError("Erreur IOException");
		} finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return v;
	}
}
