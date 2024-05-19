package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Random;
import java.util.Scanner;
import javafx.fxml.FXML;

public class WordOperations {    
    public String chooseWord() {
    	// need to choose random number from 1 to 2315
    	Random rand = new Random(); 
    	int randomInt = rand.nextInt(2314) + 1;
    	Scanner infile = null;
    	try {
    		infile = new Scanner(new FileReader("src/application/wordle-list.txt"));
    	}
		catch (FileNotFoundException e)  
		{
			System.out.println("File not found"); 
			e.printStackTrace(); // prints error(s)
			System.exit(0); // Exits entire program 
		}
    	String line = "";
    	for (int i = 0; i < randomInt; i++) {
    		line = infile.nextLine();	
    	}
    	System.out.println(line);
    	return line;
    }
    
    public void updateLabels(ArrayList<ArrayList<String>> userAttempts, ArrayList<Label> labels) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
    	for (int i = 0; i < userAttempts.size(); i++) {
            for (int j = 0; j < userAttempts.get(i).size(); j++) {
                String labelText = String.valueOf(userAttempts.get(i).get(j));
                int index = i*5 + j;
                // Assuming labels are named labelXY where X is the row number and Y is the column number
                Label label = labels.get(index);
				if (label != null) {
					label.setText(labelText);	
				}
            }
        }
    }
     
    public void resetLabels(ArrayList<ArrayList<String>> userAttempts, ArrayList<Label> labels, ArrayList<Button> buttons, ArrayList<Button> allButtons) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
    	for (int i = 0; i < userAttempts.size(); i++) {
            for (int j = 0; j < userAttempts.get(i).size(); j++) {
                int index = i*5 + j;
                // Assuming labels are named labelXY where X is the row number and Y is the column number
                Label label = labels.get(index);
                userAttempts.get(i).set(j, "");
				if (label != null) {
					label.setText("");	
					label.setStyle("-fx-border-width: 2; -fx-border-style: solid; -fx-border-color: #787c7f;");
				}
            }
        }
    	
    	for (Button button : buttons) {
    		button.setStyle("-fx-background-color: #787c7f;");
    	}
    	
    	for (Button button : allButtons) {
    		button.setStyle("-fx-background-color: #787c7f;");
    	}
    }
}
