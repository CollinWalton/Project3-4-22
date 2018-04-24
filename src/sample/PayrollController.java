package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static sample.Controller.userBase;

public class PayrollController {


    public ChoiceBox choiceID;
    public DatePicker startdate;
    public DatePicker enddate;
    public TextArea payrollBox;
    ObservableList<String> options = FXCollections.observableArrayList();
    private Scene officeManager;

    public void refresh() {

        ArrayList<String> usernames = new ArrayList();

        for(LoginAccount l: userBase.getlogAL())
        {
            if(l.getAccountType().equalsIgnoreCase("Sales Associate"))
            {
                if(!(options.contains(l.getUsername())))
                {
                    options.add(l.getUsername());
                }
            }
        }
        choiceID.setItems(options);
    }

    public void createPayroll() {

        ArrayList<LoginAccount> sales = new ArrayList<>();
        Date min;
        Date max;

        LocalDate localDate1 = startdate.getValue();
        Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
        min = Date.from(instant1);

        LocalDate localDate2 = enddate.getValue();
        Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
        max = Date.from(instant2);

        for(LoginAccount l : userBase.getlogAL()) {
            if (l instanceof SalesAssociate) {
                if(choiceID.getValue().equals(l)) {
                    int total = 0;
                    for (Invoice i : l.getIAL()) {
                        if (i.getDate().compareTo(min) > 0 && i.getDate().compareTo(max) < 0) {
                            total += i.getTotal();
                        }
                    }
                    payrollBox.appendText("Sales Commission for " + l.getUsername() + ":\n");
                    payrollBox.appendText(String.valueOf(total*.15));
                }
            }
        }
    }

    @FXML
    public void initialize() {
        for(LoginAccount l: userBase.getlogAL())
        {
            if(l.getAccountType().equalsIgnoreCase("Sales Associate"))
            {
                options.add(l.getUsername());
                choiceID.setItems(options);
            }
        }

    }


    public void setOfficeManager(Scene officeManager) {
        this.officeManager = officeManager;
    }

    public void close() {
        Stage primaryStage = (Stage) choiceID.getScene().getWindow();
        primaryStage.setScene(officeManager);
    }
}
