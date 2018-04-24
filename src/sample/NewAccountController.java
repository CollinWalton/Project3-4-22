package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

import static sample.Controller.bwh;
import static sample.Controller.userBase;


public class NewAccountController {

    public TextField userField;
    public ChoiceBox choiceID;
    public TextField passField;
    private Scene sysAdminScene;

    ObservableList<String> options = FXCollections.observableArrayList("Warehouse Manager","Office Manager", "Sales Associate", "System Administrator");

    public void setSysAdminScene(Scene sysAdminScene) {
        this.sysAdminScene = sysAdminScene;
    }

    //opens sysadmin page
    public void OpenSysAdminScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) (userField).getScene().getWindow();
        primaryStage.setScene(sysAdminScene);
    }

    @FXML
    public void initialize() {
        choiceID.setItems(options);
    }


    public void createAccount(ActionEvent event) {

        String type = (String) choiceID.getValue();
        String user = userField.getText();
        String pass = passField.getText();
        LoginAccount temp = null;
        switch (type) {

            case "Warehouse Manager":
                temp = new WarehouseManager(user,pass);
                temp.setBwh(bwh);
                break;
            case "Office Manager":
                 temp = new OfficeManager(user,pass);
                break;
            case "Sales Associate":
                temp = new SalesAssociate(user,pass);

                boolean match = false;
                TextInputDialog dialog = new TextInputDialog("Warehouse Name");
                dialog.setTitle("Create a New Sales Associate");
                dialog.setHeaderText("Please enter a warehouse name to link to the associate.");
                dialog.setContentText("Enter Warehouse Name Here:");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    for(Warehouse w : bwh.getBWH()) {
                        if(w.getName().equalsIgnoreCase(String.valueOf(result))){
                            temp.setWH(w);
                            match = true;
                        }
                    }
                    if (!match) {
                        Warehouse wtemp = new Warehouse(String.valueOf(result));
                        bwh.addWH(wtemp);
                        temp.setWH(wtemp);
                    }
                }
                break;
            case "System Administrator":
                temp = new SystemAdmin(user,pass);
                break;

        }

        userBase.addUser(temp);
        Stage primaryStage = (Stage) (userField).getScene().getWindow();
        primaryStage.setScene(sysAdminScene);
    }

}
