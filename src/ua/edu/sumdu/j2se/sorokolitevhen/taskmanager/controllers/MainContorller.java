package ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.controllers;


import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model.ArrayTaskList;
import ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model.Task;
import ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model.TaskIO;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;


public class MainContorller {

    private static final Logger log = Logger.getLogger(MainContorller.class);
    @FXML
    public Button btnRemove;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnEdit;
    @FXML
    private ListView<Task> taskListView;

    private static ObservableList<Task> observList = FXCollections.observableArrayList();
    private ArrayTaskList startArrayList = new ArrayTaskList();
    private ArrayTaskList exittArrayList = new ArrayTaskList();
    private Parent fxmlEdit;
    private Parent fxmlCalendar;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private FXMLLoader fxmlLoader2 = new FXMLLoader();
    private AddEditController addEditController;
    private CalendarController calendController;
    private Stage addEditStage;
    private Stage calendarStage;


    public static ArrayTaskList getList() {
        ArrayTaskList alist = new ArrayTaskList();
        for (Task tmp : observList) {
            alist.add(tmp);
        }
        return alist;
    }

    File saveFile = new File("Save.txt");

    // Inialize method
    @FXML
    private void initialize() {

        try {

            fxmlLoader.setLocation(getClass().getResource("/ua/edu/sumdu/j2se/sorokolitevhen/taskmanager/view/AddEditMenu.fxml"));
            fxmlEdit = fxmlLoader.load();
            addEditController = fxmlLoader.getController();

            fxmlLoader2 = new FXMLLoader(getClass().getResource("/ua/edu/sumdu/j2se/sorokolitevhen/taskmanager/view/Calendar.fxml"));
            fxmlCalendar = fxmlLoader2.load();
            calendController = fxmlLoader2.getController();


        } catch (IOException e) {
            StackTraceElement[] s = e.getStackTrace();
            for(StackTraceElement t : s) {
                log.info("IO problems " + t);
            }
        }

        // show Stage metods
    }

    private void showAddEditStage(Window parrent) {
        if (addEditStage == null) {
            addEditStage = new Stage();
            addEditStage.setTitle("AddEdit");
            addEditStage.setMinHeight(175);
            addEditStage.setMinWidth(450);
            addEditStage.setResizable(false);
            addEditStage.setScene(new Scene(fxmlEdit));
            addEditStage.initModality(Modality.WINDOW_MODAL);
            addEditStage.initOwner(parrent);
        }
        addEditStage.showAndWait();
    }

    private void showCalendar(Window parrent) {
        if (addEditStage == null) {
            calendarStage = new Stage();
            calendarStage.setTitle("Calendar");
            calendarStage.setMinHeight(175);
            calendarStage.setMinWidth(450);
            calendarStage.setResizable(false);
            calendarStage.setScene(new Scene(fxmlCalendar));
            calendarStage.initModality(Modality.WINDOW_MODAL);
            calendarStage.initOwner(parrent);
        }
        calendarStage.showAndWait();
    }

    public static void checkFile(File file) throws IOException {


        if (!file.exists()) {
            file.createNewFile();
        }


    }

    private void startLoadTasks() throws IOException {

        checkFile(saveFile);
        try {
            TaskIO.readBinary(startArrayList, saveFile);
        } catch (IOException e) {
            StackTraceElement[] s = e.getStackTrace();
            for(StackTraceElement t : s) {
                log.info("IO problems " + t);
            }
        }

        for (Task tmp : startArrayList) {

            observList.add(tmp);
        }
    }

    public void buttonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            switch (clickedButton.getId()) {
                case "btnRemove":
                    observList.remove((Task) taskListView.getSelectionModel().getSelectedItem());
                    break;
                case "btnAdd":
                    Task clear = new Task(new String(), new Date(), new Date(), 1);
                    addEditController.setTask(clear);
                    showAddEditStage(parentWindow);
                    observList.add(addEditController.getTask());

                    break;
                case "btnEdit":
                    addEditController.setTask((Task) taskListView.getSelectionModel().getSelectedItem());
                    showAddEditStage(parentWindow);
                    if (taskListView.getSelectionModel().getSelectedItem().equals(addEditController.getTask())) break;
                    else observList.remove(taskListView.getSelectionModel().getSelectedItem());
                    observList.add(addEditController.getTask());
                    break;

            }
        } catch (NullPointerException e) {
            StackTraceElement[] s = e.getStackTrace();
            for(StackTraceElement t : s) {
                log.info("NullPointer problems " + t);
            }
    }
    }

    public void btnExit(ActionEvent actionEvent) throws IOException {

        for (Task add : observList) {
            exittArrayList.add(add);
        }
        try {
            TaskIO.writeBinary(exittArrayList, saveFile);
        } catch (IOException e) {
            StackTraceElement[] s = e.getStackTrace();
            for(StackTraceElement t : s) {
                log.info("IO problems " + t);
            }
        }

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void btnCalendar(ActionEvent actionEvent) {

        Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
        showCalendar(parentWindow);
    }

    public void btnLoad(ActionEvent actionEvent) throws IOException {
        startLoadTasks();
        taskListView.setItems(observList);

    }
}
