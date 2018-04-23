package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.Optional;

import static sample.Controller.sysadmin;
import static sample.Controller.userBase;

public class SysAdminController {

    public TableColumn <LoginAccount, String> usernameCol;
    public TableColumn <LoginAccount, String>  passCol;
    public TableColumn <LoginAccount, String>  fnameCol;
    public TableColumn <LoginAccount, String>  lnameCol;
    public TableColumn <LoginAccount, String>  emailCol;
    public TableView <LoginAccount> userTable;

    public static LoginAccount selectedUser = null;

    public TextField filterField;
    public MenuItem newAccount;
    public MenuItem close;
    public MenuBar menu;

    private ObservableList<LoginAccount> masterData = FXCollections.observableArrayList();

    private Scene loginScene;
    private Scene createAccountScene;
    private Scene editAccountScene;

    public void setLoginScene(Scene scene) {
        loginScene = scene;
    }

    public void setCreateAccountScene(Scene createAccountScene) {
        this.createAccountScene = createAccountScene;
    }

    public void setEditAccountScene(Scene editAccountScene) {
        this.editAccountScene = editAccountScene;
    }

    //goes to Login Screen
    public void openLoginScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)(menu).getScene().getWindow();
        primaryStage.setScene(loginScene);
    }
    //goes to Create Account Screen
    public void OpenCreateAccountScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) (menu).getScene().getWindow();
        primaryStage.setScene(createAccountScene);
    }

    //goes to Edit Account Scene
    public void openEditAccount() {
        Stage primaryStage = (Stage) (menu).getScene().getWindow();
        primaryStage.setScene(editAccountScene);
    }

    public void refreshUsers (ActionEvent event) {

        for(LoginAccount x : userBase.getlogAL()) {
            if (!masterData.contains(x)) {
                masterData.add(x);
            }
        }

        userTable.refresh();

    }

    public void deleteUser(ActionEvent event) {

        LoginAccount temp = userTable.getSelectionModel().getSelectedItem();
        if(!temp.equals(sysadmin)) {
            this.masterData.remove(temp);
            userBase.deleteUser(temp);
            userTable.refresh();
        }
    }

    public void resetPasswords() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ATTENTION");
        alert.setHeaderText("PLEASE READ THE TEXT BELOW.");
        alert.setContentText("By selecting 'OK', you are choosing to reset all user passwords. " +
                "All passwords will be reset to the default setting. Is this okay?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            String defaultPassword = "";
            for(LoginAccount la : userBase.getlogAL()) {
                String[] sa = la.getUsername().split("");
                defaultPassword += sa[0]+sa[1]+la.getLastName();
                if(!la.equals(sysadmin)) {
                    la.setPassword(defaultPassword);
                }
            }
        } else {
        }
    }


    @FXML
    public void initialize() {

        for(LoginAccount x : userBase.getlogAL()) {
            masterData.add(x);
        }
        usernameCol.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        passCol.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        fnameCol.setCellValueFactory(cellData -> cellData.getValue().fNameProperty());
        lnameCol.setCellValueFactory(cellData -> cellData.getValue().lNameProperty());
        emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        FilteredList<LoginAccount> filteredData = new FilteredList<>(masterData, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(bp -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(bp.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<LoginAccount> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(userTable.comparatorProperty());

        userTable.setItems(sortedData);

    }

}
