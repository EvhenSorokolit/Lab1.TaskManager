package ua.edu.sumdu.j2se.sorokolitevhen.taskmanager;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Main extends Application {
    private static final Logger log = Logger.getLogger(Main.class);
    @Override
    public void start(Stage primaryStage) {
        try {


            Parent root = FXMLLoader.load(getClass().getResource("/ua/edu/sumdu/j2se/sorokolitevhen/taskmanager/view/mainMenu.fxml"));


            primaryStage.setTitle("Task Manager");
            primaryStage.setScene(new Scene(root, 600, 500));
        } catch (IOException e) {
            StackTraceElement[] s = e.getStackTrace();
            for (StackTraceElement t : s) {
                log.info("IO problems " + t);
            }
        }
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(300);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                event.consume();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
