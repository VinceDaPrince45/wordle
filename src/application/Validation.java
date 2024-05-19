package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Validation {
    @SuppressWarnings("resource")
	public boolean isValidWord(int row, ArrayList<ArrayList<String>> userAttempts) throws FileNotFoundException {
    	String word = "";
    	for (int i = 0; i < 5; i++) {
    		word += userAttempts.get(row).get(i);
    	}
    	try {
    	    File file = new File("src/application/valid-wordle-words.txt");
    	    Scanner scanner = new Scanner(file); 

    	    while (scanner.hasNextLine()) {
    	        String line = scanner.nextLine();
    	        if (line.equals(word.toLowerCase())) {
    	            scanner.close();
    	            return true;
    	        }
    	    } 

    	    scanner.close();
    	} catch (FileNotFoundException e) {
    	    System.err.println("File not found: " + e.getMessage());
    	}

    	return false;

    }  
    
    public boolean checkAnswer(ArrayList<Button> buttons, ArrayList<ArrayList<String>> userAttempts, int currAttempt, String targetWord,ArrayList<Label> labels) {
    	int correctLetters = 0;
    	for (int i = 0; i < 5; i++) {
    		String currChar = userAttempts.get(currAttempt).get(i).toLowerCase();
    		
            String labelText = String.valueOf(userAttempts.get(currAttempt).get(i));
            // Assuming labels are named labelXY where X is the row number and Y is the column number
            int index = 5*currAttempt + i;
            Label label = labels.get(index);
			
    		// includes and is at same position
    		if (targetWord.indexOf(currChar) != -1 && String.valueOf(targetWord.charAt(i)).equals(currChar)) {
    			buttons.get(i).setStyle("-fx-background-color: #6ca965;");
    			label.setStyle("-fx-background-color: #6ca965; -fx-border-width: 1; -fx-border-style: solid;");
    			correctLetters++;
    		}
    		// includes
    		else if (targetWord.indexOf(currChar) != -1) {
    			buttons.get(i).setStyle("-fx-background-color: #c8b653;");
    			label.setStyle("-fx-background-color: #c8b653; -fx-border-width: 1; -fx-border-style: solid;");
    		}
    		// doesnt include
    		else {
    			buttons.get(i).setStyle("-fx-background-color:  #282828;");
    			label.setStyle("-fx-background-color: #787c7f; -fx-border-width: 1; -fx-border-style: solid;");
    		}
    	}
    	buttons.clear();
    	if (correctLetters == 5) {
    		return true;
    	} else {
    		return false;
    	}
    }
}
