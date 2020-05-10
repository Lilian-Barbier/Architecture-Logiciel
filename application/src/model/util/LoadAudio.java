package model.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.list.Audio;
import model.list.IMedia;

public class LoadAudio implements LoadFiles {

	@Override
	public IMedia loadFile(String path) {
        if (path == null) {
            throw new AssertionError("Paramètre invalide LoadAudio addFile");
        }
        
		Audio a = new Audio(path);
        BufferedReader br = null;
        
        
        /*
         * Ici Le choix de lire les données devrait dépendre du type d'extension
         * Switch selon le type ?
         */
        
        try {
        	br = new BufferedReader(new FileReader(path));
            a.setDuration(Integer.parseInt(br.readLine()));
            a.setName(br.readLine());
            a.setArtist(br.readLine());
        } catch(FileNotFoundException exc) {
            System.out.println("Chemin de fichier introuvable");
        } catch (NumberFormatException e) {
            System.out.println("Erreur ParseInt");
		} catch (IOException e) {
            System.out.println("Erreur IOException");
		} finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return a;
	}
}
