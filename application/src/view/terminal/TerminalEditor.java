package view.terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import facade.editor.IEditorModel;
import facade.editor.StdEditorModel;
import facade.player.IPlayerModel;

import javax.swing.*;

public class TerminalEditor {

	private final String createList = "create";
	private final String loadList = "load";
	private final String saveList = "save";
	private final String showInfos = "infos";
	private final String enterList = "enter";
	private final String ascendList = "ascend";
	private final String importFile = "import";
	private final String importFiles = "importFiles";
	private final String importList = "importXPL";
	private final String help = "help";

	private IEditorModel model;

	// CONSTRUCTEURS
	public TerminalEditor() {
		createModel();
        createController();
	}

	private void createModel() {
        model = new StdEditorModel();
    }

	private void createController() {

		System.out.println("Pour une aide : " + help);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
        	String line = reader.readLine();
			//while(line != null && model.isFinish()) {
        	while(line != null) {
        		switch (line.split(" ")[0]) {
					case createList:
						model.create(line.split(" ")[1]);
						break;

					case loadList:
						File f = new File(line.split(" ")[1]);
						model.load(f);;
						break;

					case saveList:
						model.save();
						break;

					case showInfos:
						model.getInfos();
						break;

					case enterList:
						model.enterList(Integer.parseInt(line.split(" ")[1]));
						break;

					case ascendList:
						model.ascendList();
						break;

					case importFile:
						model.addFile(line.split(" ")[1]);
						break;

					case importFiles:
						model.addFilesFromFolder(line.split(" ")[1]);
						break;

					case importList:
						model.addList(line.split(" ")[1]);
						break;

					case help:
						System.out.println(createList + " : .");
						System.out.println(loadList + " : .");
						System.out.println(saveList + " : .");
						System.out.println(showInfos + " : .");
						System.out.println(enterList + " :  .");
						System.out.println(ascendList + " : .");
						System.out.println(importFile + " : .");
						System.out.println(importFiles + " : .");
						System.out.println(importList + " : .");
						break;

					default:
						System.out.println(line + " : commande introuvable.");
						System.out.println("Pour une aide : " + help);
						break;
				}

				line = reader.readLine();
			}
        }
		catch (IOException e) {
				// Problème lors de la lecture
				e.printStackTrace();
		}
        finally {
			try {
				reader.close();
			} catch (IOException e) {
				// Problème lors de la fermeture
				e.printStackTrace();
			}
		}

	}

	/*private void refresh() {
		 if (model.canGetChange()) {
			 changeInfo.setText("Cet appareil rend la monnaie");
		 } else {
			 changeInfo.setText("Cet appareil ne rend pas la monnaie");
	     }
	     creditInfo.setText("Vous disposez d'un crédit de "
	    		 + model.getCreditAmount() + " cents");
	     if (model.getLastDrink() == null) {
	    	 drinkOutput.setText("");
	     } else {
	    	 drinkOutput.setText(model.getLastDrink().toString());
	     }
	     changeOutput.setText("" + model.getChangeAmount());
	     for (final JButton b : buttonToDrinkRelation.keySet()) {
	    	 if (model.getDrinkNb(buttonToDrinkRelation.get(b)) == 0) {
	    		 b.setEnabled(false);
	    	 } else {
	    		 b.setEnabled(true);
	    	 }
     	}
	}*/


	// POINT D'ENTREE
	public static void main(String[] args) {

		if(args.length != 1){
			new AssertionError("TerminalPlayer main() : Argument manquant !");
		}
		//File f = new File();

		new TerminalEditor();
	}


}
