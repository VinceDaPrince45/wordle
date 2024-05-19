package application;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class AlertHandler {

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void showInstructionsAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Instructions");
        alert.setHeaderText("WORDLE");
        alert.setContentText("Instructions:\n\n" +
                "1. Try to guess the 5-letter word within 6 attempts\n" +
                "2. Your guesses can only be valid 5 letters words\n" +
                "3. The color of the tiles will change depending on how close your guess was to the word\n");
        ButtonType closeButton = new ButtonType("OK");
        alert.getButtonTypes().setAll(closeButton);
        alert.showAndWait();
    }
    
    public void showStats(String end, int streak, int maxStreak, ArrayList<Integer> gamelogs) {
        // Count the occurrences of each guess
        HashMap<Integer, Integer> frequency = new HashMap<>();
        for (int guess : gamelogs) {
        	if (frequency.containsKey(guess)) {
        		frequency.put(guess,frequency.get(guess) + 1);
        	} else {
        		frequency.put(guess, 1);	
        	}
        }

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setMinorTickVisible(false);
        
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Guesses Distribution");
        xAxis.setLabel("Guesses");
        yAxis.setLabel("Frequency"); 
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Guess Frequency"); 

        float winPercentage = 0;
        int losses = 0; 
        for (int guess = 1; guess <= 7; guess++) {
        	String label = "";
        	if (guess == 7) {
        		label = "Unguessed";
        	} else {
        		label = String.valueOf(guess);
        	}
        	int count = 0;
        	if (frequency.containsKey(guess)) {
        		count = frequency.get(guess);
        		if (guess == 7) {
        			losses = count;
        		}
        	}
            series.getData().add(new XYChart.Data<>(label, count));
        }
        winPercentage = (float)(gamelogs.size() - losses)/(gamelogs.size()) * 100;

        // Add the series to the chart
        barChart.getData().add(series);
         
        Pane pane = new Pane();
        pane.getChildren().add(barChart);
        
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	Image image;
    	if (end.equals("W")) {
    		alert.setTitle("CONGRATS");
    		image = new Image(getClass().getResource("thumbsup.png").toExternalForm());
    	} else {
    		alert.setTitle("NICE TRY");
    		image = new Image(getClass().getResource("thumbsdown.png").toExternalForm());
    	}
    	ImageView imageView = new ImageView(image);
    	imageView.setFitWidth(50); // Set the width of the image view
        imageView.setFitHeight(50); // Set the height of the image view
    	alert.setGraphic(imageView);
    	alert.setHeaderText("Statistics:");
    	alert.setContentText(
    			"Games Played: " + gamelogs.size() + "\n" +
    			"Streak: " + streak + "\n" + 
    			"Max Streak: " + maxStreak + "\n" +
    			"Win Percentage: " + String.valueOf(winPercentage) + "\n"
    			);
    	
        alert.getDialogPane().setExpandableContent(pane);
        alert.getDialogPane().setExpanded(true);
        
    	alert.showAndWait();
    }
}
