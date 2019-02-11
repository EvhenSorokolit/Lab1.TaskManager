package ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model.ArrayTaskList;
import javafx.collections.ObservableList;
import ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model.Task;
import ua.edu.sumdu.j2se.sorokolitevhen.taskmanager.model.Tasks;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class CalendarController {
    private final ObservableList<Task> calendarList = FXCollections.observableArrayList();
    @FXML
    public DatePicker start;
    @FXML
    public DatePicker end;
    @FXML

    public TableView result;
    @FXML
    public TableColumn<?,?> dateColum;
    @FXML
    public TableColumn<?,?> taskColum;
    @FXML
    private Date from;
    private Date to;

    private static SimpleDateFormat formatMePlz = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Date convert(DatePicker tmp) throws ParseException {
        return formatMePlz.parse(tmp.getValue() + " 00:00");
    }
    private void Initialize(){
        start.setValue(LocalDate.now());
        end.setValue(start.getValue().plusDays(1));
        dateColum.setCellValueFactory(new PropertyValueFactory<>("time"));
        taskColum.setCellValueFactory(new PropertyValueFactory<>("title"));


    }

    ArrayTaskList full = new ArrayTaskList();

    public void  setArray(Date from, Date to){
        SortedMap<Date, Set<Task>> calendar = Tasks.calendar(MainContorller.getList(), from, to);
        System.out.println(calendar.size());
        for (int i = 0; i < calendar.values().size(); i++) {



            for (Map.Entry<Date, Set<Task>> entry : calendar.entrySet()) {
                for (Object object : entry.getValue().toArray()) {

                    calendarList.add((Task) object);

                    // full.add((Task) object);

                }
            }
        }
    }

    public void btnSearch(ActionEvent actionEvent) throws ParseException {
        System.out.println(MainContorller.getList().size());
        from = convert(start);
        to = convert(end);




    }

    public void btnMenu(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();



    }
}
