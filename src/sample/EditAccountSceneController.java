package sample;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static sample.Controller.currentUser;
import static sample.SysAdminController.selectedUser;

public class EditAccountSceneController {

    public TextField nameField;
    public TextField lastNameField;
    public PasswordField passField;
    public PasswordField confirmField;
    public TextField emailField;
    public TextField phoneField;

    private Scene sysAdmin;
    private Scene salesAssociateScene;
    private Scene warehouseManagerScene;
    private Scene officeManagerScene;

    public void setSalesAssociateScene(Scene salesAssociateScene) { this.salesAssociateScene = salesAssociateScene; }
    public void setSysAdmin(Scene sysAdmin) {
        this.sysAdmin = sysAdmin;
    }
    public void setWarehouseManagerScene(Scene scene) {
        this.warehouseManagerScene = warehouseManagerScene;
    }
    public void setOfficeManagerScene(Scene officeManagerScene) { this.officeManagerScene = officeManagerScene; }

    //Opens to the correct page by checking currentUser value
    public void openLast() {

        Stage primaryStage = (Stage) (nameField).getScene().getWindow();
        String type = currentUser.getAccountType();
        switch (type) {
            case "System Admin":
                primaryStage.setScene(sysAdmin);
                break;
            case "Sales Associate":
                primaryStage.setScene(salesAssociateScene);
                break;
            case "Office Manager":
                primaryStage.setScene(officeManagerScene);
                break;
            case "Warehouse Manager":
                primaryStage.setScene(warehouseManagerScene);
                break;
        }
        nameField.clear();
        lastNameField.clear();
        passField.clear();
        confirmField.clear();
        emailField.clear();

    }

    //changes account info based on whether text fields are empty
    public void changeAccount() {

        if(!nameField.getText().isEmpty()) {
            currentUser.setFirstName(nameField.getText());
        }
        if(!lastNameField.getText().isEmpty()) {
            currentUser.setLastName(lastNameField.getText());
        }
        if(!emailField.getText().isEmpty()) {
            currentUser.setEmail(emailField.getText());
        }
        openLast();
    }

    public void changePassword() {
        if(!passField.getText().isEmpty() && !confirmField.getText().isEmpty()) {
            if(passField.getText().compareTo(confirmField.getText()) == 0) {
                currentUser.setPassword(passField.getText());
                openLast();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops!");
                alert.setHeaderText(null);
                alert.setContentText("The passwords you entered do not match. Please confirm your password, or select 'cancel' below.");

                alert.showAndWait();
            }
        }
    }
}
