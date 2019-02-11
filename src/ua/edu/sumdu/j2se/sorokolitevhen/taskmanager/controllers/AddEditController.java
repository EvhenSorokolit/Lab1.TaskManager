package ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model.Task;


import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;


public class AddEditController {

    @FXML
    public VBox timeVbox;
    @FXML
    public VBox startVbox;
    @FXML
    public VBox endVbox;

    private static SimpleDateFormat convert = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static Format mm = new SimpleDateFormat("mm");
    private static Format hh = new SimpleDateFormat("HH");
    private static final Logger log = Logger.getLogger(AddEditController.class);

    @FXML
    private TextField name;
    @FXML
    private DatePicker time;
    @FXML
    private DatePicker start;
    @FXML
    private DatePicker end;
    @FXML
    private Spinner timeH;
    @FXML
    private Spinner startH;
    @FXML
    private Spinner endH;
    @FXML
    private Spinner timeM;
    @FXML
    private Spinner startM;
    @FXML
    private Spinner endM;
    @FXML
    private TextField interval;
    @FXML
    private CheckBox active;
    @FXML
    public CheckBox repit;

    private Task task;
    private int i = 13;


    @FXML
    private void initialize() {
        repit.setSelected(true);
        showHide();
    }

    private int spinnerConvert(Spinner spinner) {
        return Integer.parseInt(spinner.getEditor().getText());


    }

    public Task getTask() {
        return task;
    }


    public void setTask(Task task) {
        try {


            this.task = task;
            name.setText(task.getTitle());
            start.setValue(task.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            end.setValue(task.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            active.setSelected(task.isActive());
            startM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, Integer.parseInt(mm.format(task.getTime()))));
            startH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, Integer.parseInt(hh.format(task.getTime()))));
            endM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, Integer.parseInt(mm.format(task.getTime()))));
            endH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, Integer.parseInt(hh.format(task.getTime()))));
            interval.setText(Integer.toString(task.getRepeatInterval()));
            time.setValue(task.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            active.setSelected(task.isActive());
            timeM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, Integer.parseInt(mm.format(task.getTime()))));
            timeH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, Integer.parseInt(hh.format(task.getTime()))));


        } catch (Exception e) {
            StackTraceElement[] s = e.getStackTrace();
            for (StackTraceElement t : s) {
                log.info("vozmojen nulllpoiner i  problemi s parrisovkoi " + t);
            }
        }


    }


    private static Date dateConvert(DatePicker date, int hour, int minutes) {
        Date tmp = null;
        try {


            tmp = convert.parse(date.getValue() + " " + hour + ":" + minutes);
        } catch (ParseException e) {
            StackTraceElement[] s = e.getStackTrace();
            for (StackTraceElement t : s) {
                log.info("Unparseable date values " + t);
            }
        }
        return tmp;
    }


    public void btnAdd(ActionEvent actionEvent) {
        try {

            if (repit.isSelected()) {
                task = new Task(name.getText(), dateConvert(start, spinnerConvert(startH), spinnerConvert(startM)), dateConvert(end, spinnerConvert(endH), spinnerConvert(endM)), Integer.parseInt(interval.getText()));
                task.setActive(active.isSelected());
                System.out.println(task.toString());
                Close(actionEvent);
            } else {

                task = new Task(name.getText(), dateConvert(time, spinnerConvert(timeH), spinnerConvert(timeM)));
                task.setActive(active.isSelected());
                System.out.println(task.toString());
                Close(actionEvent);

            }
        } catch (Exception e) {

            StackTraceElement[] s = e.getStackTrace();
            for (StackTraceElement t : s) {
                log.info("Unparseable date values or task Date  is null " + t);
            }
        }
    }


    public void disableChar(KeyEvent keyEvent) {


        char c = keyEvent.getCharacter().charAt(0);
        if (!Character.isDigit(c)) {
            keyEvent.consume();
        }
    }

    public void nums(KeyEvent keyEvent) {

        disableChar(keyEvent);
    }

    public void Close(ActionEvent actionEvent) {

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();


    }


    public void changName(ActionEvent actionEvent) {
        if (active.isSelected())
            active.setText("Diactivate");
        else active.setText("Activate");
    }

    private void showHide() {
        if (repit.isSelected()) {
            timeVbox.setVisible(false);
            startVbox.setVisible(true);
            endVbox.setVisible(true);
        } else {
            timeVbox.setVisible(true);
            startVbox.setVisible(false);
            endVbox.setVisible(false);
        }
    }

    public void repited(ActionEvent actionEvent) {
        showHide();

    }
}
