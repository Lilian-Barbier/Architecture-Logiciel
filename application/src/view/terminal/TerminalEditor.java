package view.terminal;

import facade.editor.IEditorModel;
import facade.editor.StdEditorModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalEditor {

	private static final String CREATE_LIST = "create";
	private static final String LOAD_LIST = "load";
	private static final String SAVE_LIST = "save";
	private static final String SHOW_INFOS = "infos";
	private static final String ENTER_LIST = "enter";
	private static final String ASCEND_LIST = "ascend";
	private static final String IMPORT_FILE = "import";
	private static final String IMPORT_FILES = "importFiles";
	private static final String IMPORT_LIST = "importXPL";
	private static final String HELP = "help";
	private static final String QUIT = "quit";

	private IEditorModel model;

	// CONSTRUCTEURS

	public TerminalEditor() {
		createModel();
        createController();
	}

	/**
	 * Instancie le model
	 */
	private void createModel() {
        model = new StdEditorModel();
    }

	/**
	 * Création des Controller
	 */
	private void createController() {
		System.out.println("Pour une aide : " + HELP);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
        	String line = reader.readLine();
        	while (line != null) {
        		switch (line.split(" ")[0]) {
					case CREATE_LIST:
						if (line.split(" ").length < 2) {
							System.out.println("Veuillez indiquer un nom pour la playslit !");
							break;
						}
						model.create(line.split(" ")[1]);
						break;
					case LOAD_LIST:
						if (line.split(" ").length < 2) {
							System.out.println("Veuillez indiquer le chemin d'une playlist à charger !");
							break;
						}
						File f = new File(line.split(" ")[1]);
						model.load(f);
						break;
					case SAVE_LIST:
						model.save();
						break;
					case SHOW_INFOS:
						System.out.println(model.getInfos());
						break;
					case ENTER_LIST:
						if (line.split(" ").length < 2) {
							System.out.println("Indice incorrecte");
							break;
						}
						try {
							model.enterList(Integer.parseInt(line.split(" ")[1]));
						} catch (NumberFormatException e) {
							System.out.println("Indice incorrecte");
						}
						break;
					case ASCEND_LIST:
						model.ascendList();
						break;
					case IMPORT_FILE:
						int index = line.indexOf(" ");
						model.addFile(line.substring(index + 1));
						break;
					case IMPORT_FILES:
						model.addFilesFromFolder(line.split(" ")[1]);
						break;
					case IMPORT_LIST:
						model.addList(line.split(" ")[1]);
						break;
					case HELP:
						System.out.println(CREATE_LIST + " : créé une nouvelle playlist de nom passé en paramètre");
						System.out.println(LOAD_LIST + " : charge la playliste de chemin passé en paramètre");
						System.out.println(SAVE_LIST + " : enregistre la playlist en cours d'édition");
						System.out.println(SHOW_INFOS + " : affiche les informations sur la playlist");
						System.out.println(ENTER_LIST + " :  rentre dans la sous-liste d'indice passé en paramètre");
						System.out.println(ASCEND_LIST + " : remonte dans la sous-liste parente");
						System.out.println(IMPORT_FILE + " : ajoute le fichier passé en paramètre");
						System.out.println(IMPORT_FILES + " : ajoute les fichiers passés en paramètre");
						System.out.println(IMPORT_LIST + " : ajoute la playlist passée en paramètre");
						System.out.println(QUIT + " : arrête le programme");
						break;
					case QUIT:
						System.exit(0);
					default:
						System.out.println(line + " : commande introuvable.");
						System.out.println("Pour une aide : " + HELP);
						break;
				}
				line = reader.readLine();
			}
        } catch (IOException e) {
				// Problème lors de la lecture
				System.out.println("Erreur lors de la lecture d'un fichier, le programme doit se terminer.");
				System.exit(-1);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// Problème lors de la fermeture
				System.out.println("Erreur lors de la fermeture d'un fichier, le programme doit se terminer.");
				System.exit(-1);
			}
		}
	}

	// POINT D'ENTREE
	public static void main(String[] args) {
		new TerminalEditor();
	}
}
