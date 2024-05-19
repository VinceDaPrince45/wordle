package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.ArrayList; 
import java.util.Queue;

import java.io.*;
import java.util.*;

public class SampleController {
//	labels
    public Label label00, label01, label02, label03, label04,
    label10, label11, label12, label13, label14,
    label20, label21, label22, label23, label24,
    label30, label31, label32, label33, label34,
    label40, label41, label42, label43, label44, 
    label50, label51, label52, label53, label54;

	
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private ArrayList<Button> allButtons = new ArrayList<Button>();
    private ArrayList<Label> labels = new ArrayList<Label>();
    private ArrayList<Integer> gamelogs = new ArrayList<>();
    
    private ArrayList<ArrayList<String>> userAttempts = new ArrayList<>(); // list of lists using Collections as shadow structure	
    
     
    private int numRows = 6;
    private int numCols = 5;
    
    private int currAttempt = 0;
    private int numOfCurrLetters = 0;
    private String targetWord = "";
    
    private int streak = 0;
    private int maxStreak = 0;
    private boolean guessed = false; 
    
    private AlertHandler alertHandler = new AlertHandler();
    private WordOperations wordOperations = new WordOperations();
    private Validation validation = new Validation();
   
    public void initialize() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException // think of as the Controller's constructor
    {
        // Initialize the 2D ArrayList with empty ArrayLists
        for (int i = 0; i < numRows; i++) {
            userAttempts.add(new ArrayList<>());
            for (int j = 0; j < numCols; j++) {
            	userAttempts.get(i).add("");
            }
        }
        Collections.addAll(labels,
        		label00, label01, label02, label03, label04,
        	    label10, label11, label12, label13, label14,
        	    label20, label21, label22, label23, label24,
        	    label30, label31, label32, label33, label34,
        	    label40, label41, label42, label43, label44,
        	    label50, label51, label52, label53, label54
        		);
        // choose random word
        targetWord = wordOperations.chooseWord();
        alertHandler.showInstructionsAlert();
    }
    
    public void reset() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        // delete userAttempts 
        wordOperations.resetLabels(userAttempts,labels,buttons,allButtons);
    	guessed = false;
    	currAttempt = 0;
    	numOfCurrLetters = 0;
        targetWord = wordOperations.chooseWord();
        allButtons.clear();
        buttons.clear();
    }
    
    public void press(ActionEvent e) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        if (e.getSource() instanceof Button && !guessed) {
            Button clickedButton = (Button) e.getSource();
            if (clickedButton.getText().equals("DELETE") && numOfCurrLetters > 0) {
            	numOfCurrLetters--;
                userAttempts.get(currAttempt).set(numOfCurrLetters,"");
                buttons.remove(buttons.size()-1);
                allButtons.remove(allButtons.size()-1);
                wordOperations.updateLabels(userAttempts,labels);
            } 
            if (clickedButton.getText().equals("ENTER") && numOfCurrLetters == 5) {
            	try {
					if (validation.isValidWord(currAttempt,userAttempts)) {
						if (validation.checkAnswer(buttons,userAttempts,currAttempt,targetWord,labels)) {
							// show winning screen
							streak++;
							if (streak >= maxStreak) {
								maxStreak = streak;
							} 
							gamelogs.add(currAttempt+1);
							alertHandler.showStats("W",streak,maxStreak,gamelogs);
							guessed = true;
						} else if (currAttempt+1 == 6) {
							// show losing screen;
							streak = 0; 
							gamelogs.add(currAttempt+2);
							alertHandler.showStats("L",streak,maxStreak,gamelogs);
							guessed = true;
						}
		            	currAttempt++;
		            	numOfCurrLetters = 0;
					} else {
						alertHandler.showAlert(Alert.AlertType.ERROR, "Not in word List", "Word is not valid. Please try again.");
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            } else if (clickedButton.getText().equals("ENTER") && numOfCurrLetters != 5){
            	alertHandler.showAlert(Alert.AlertType.ERROR, "Not enough words", "You need 5 letters");
            }
            if (numOfCurrLetters < 5) {
//              userAttempts.get(currAttempt).set(numOfCurrLetters, clickedButton.getText());
                // need to check if its alphabet
                if (clickedButton.getText().equals("DELETE") || clickedButton.getText().equals("ENTER")) {
                	return;
                }
                userAttempts.get(currAttempt).set(numOfCurrLetters,clickedButton.getText());
                numOfCurrLetters++;
                buttons.add(clickedButton);
                allButtons.add(clickedButton);
                wordOperations.updateLabels(userAttempts,labels);
            }
        } else {
        	alertHandler.showAlert(Alert.AlertType.ERROR, "Game Finished", "The game is already finished. You cannot add more letters");
        }
    } 
}
