package com.koumpis.pomodoro;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.text.DateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class PomodoroController {
    @FXML
    private Label timerText;
    @FXML
    private TextField timeSetter;
    private static Timer timer;
    private int seconds;
    private int displayedSeconds;
    private Timeline timeline;

    public void setTimeline(Timeline timeline) {
        this.timeline= timeline;
    }
    public Timeline getTimeline() {
        return timeline;
    }
    @FXML
    protected void start() {
        timer= new Timer();
        seconds= Integer.parseInt(timeSetter.getText()) * 60000;
        displayedSeconds= Integer.parseInt(timeSetter.getText()) * 60;
        TimerTask task= new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    Alert al= new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Pomodoro Dialog");
                    al.setHeaderText("REST");
                    al.show();
                });
                timeSetter.setText("Done");
            }
        };
        timer.schedule(task, seconds);
        Timeline timeline= new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if(displayedSeconds > 0) {
                timerText.setText("" + displayedSeconds--);
            }
            else {
                timerText.setText("Finish");
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        setTimeline(timeline);
    }
    @FXML
    protected void cancel() {
        timer.cancel();
        timer.purge();
        timeline= getTimeline();
        timeline.stop();
        Alert al= new Alert(Alert.AlertType.WARNING);
        al.setTitle("Pomodoro Dialog");
        al.setHeaderText("You have cancelled the timer");
        al.show();
    }
}