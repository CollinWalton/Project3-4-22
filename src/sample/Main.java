package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static sample.Controller.userBase;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Login Window
        FXMLLoader loginPaneLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent firstPane = loginPaneLoader.load();
        Scene loginScene = new Scene(firstPane, 300, 275);

        //Sys Admin Window
        FXMLLoader SysAdminLoader = new FXMLLoader(getClass().getResource("SysAdmin.fxml"));
        Parent sysAdminPane = SysAdminLoader.load();
        Scene sysAdminScene = new Scene(sysAdminPane, 416, 400);

        //Sys Admin New Account Window
        FXMLLoader CreateAccountLoader = new FXMLLoader(getClass().getResource("New Account.fxml"));
        Parent createAccountPane = CreateAccountLoader.load();
        Scene createAccountScene = new Scene(createAccountPane, 341, 149);

        //Office Manager Scene
        FXMLLoader officeManagerLoader = new FXMLLoader(getClass().getResource("OfficeManagerScene.fxml"));
        Parent officeManagerPane = officeManagerLoader.load();
        Scene officeManagerScene = new Scene(officeManagerPane, 600, 400);

        //Warehouse Manager Scene
        FXMLLoader WarehouseManagerLoader = new FXMLLoader(getClass().getResource("WarehouseManager.fxml"));
        Parent WarehouseManagerPane = WarehouseManagerLoader.load();
        Scene warehouseManagerScene = new Scene(WarehouseManagerPane, 653, 450 );

        //Sales Associate Scene
        FXMLLoader SalesAssociateLoader = new FXMLLoader(getClass().getResource("SalesAssociateScene.fxml"));
        Parent salesAssociatePane = SalesAssociateLoader.load();
        Scene salesAssociateScene = new Scene(salesAssociatePane, 600, 400);

        //Edit Account Scene
        FXMLLoader editAccountLoader = new FXMLLoader((getClass().getResource("EditAccountScene.fxml")));
        Parent editAccountPane = editAccountLoader.load();
        Scene editAccountScene = new Scene(editAccountPane, 220, 443);


        //LOGIN PAGE INJECTIONS
        Controller loginPaneController = (Controller) loginPaneLoader.getController();
        // system admin
        loginPaneController.setSysAdminScene(sysAdminScene);
        //warehouse manager
        loginPaneController.setWarehouseManagerScene(warehouseManagerScene);
        //sales associate
        loginPaneController.setSalesAssociate(salesAssociateScene);
        //office manager
        loginPaneController.setOfficeManagerScene(officeManagerScene);

        //SYS ADMIN INJECTIONS
        SysAdminController sysAdminPaneController = (SysAdminController) SysAdminLoader.getController();
        // injecting Login Scene into the controller of the System Admin scene
        sysAdminPaneController.setLoginScene(loginScene);
        // injecting New Account Scene into the controller of the System Admin Scene
        sysAdminPaneController.setCreateAccountScene(createAccountScene);
        //injecting Edit Account Scene into Sys Admin Scene
        sysAdminPaneController.setEditAccountScene(editAccountScene);

        //NEW ACCOUNT INJECTION
        NewAccountController newAccountPaneController = (NewAccountController) CreateAccountLoader.getController();
        // injecting Sys Admin into the controller of the New Account scene
        newAccountPaneController.setSysAdminScene(sysAdminScene);

        //EDIT ACCOUNT PAGE INJECTIONS
        EditAccountSceneController editAccountSceneController = (EditAccountSceneController) editAccountLoader.getController();
        // injecting Sys Admin Scene into Edit Account Scene
        editAccountSceneController.setSysAdmin(sysAdminScene);
        //sales associate
        editAccountSceneController.setSalesAssociateScene(salesAssociateScene);
        //warehouse manager
        editAccountSceneController.setWarehouseManagerScene(warehouseManagerScene);
        //office manager
        editAccountSceneController.setOfficeManagerScene(officeManagerScene);

        //OFFICE MANAGER INJECTIONS
        OfficeManagerController officeManagerController = (OfficeManagerController) officeManagerLoader.getController();
        //login page
        officeManagerController.setLoginScene(loginScene);
        //edit account page
        officeManagerController.setEditAccountScene(editAccountScene);


        //WAREHOUSE MANAGER INJECTIONS
        WarehouseManagerController warehouseManagerController = (WarehouseManagerController) WarehouseManagerLoader.getController();
        //injecting login into warehouse manager scene
        warehouseManagerController.setLoginScene(loginScene);
        //edit account
        warehouseManagerController.setEditAccountScene(editAccountScene);

        //SALES ASSOCIATE INJECTIONS
        SalesAssociateController salesAssociateController = (SalesAssociateController) SalesAssociateLoader.getController();
        //injecting Login Page into Sales Associate
        salesAssociateController.setLoginScene(loginScene);
        //edit account scene
        salesAssociateController.setEditAccountScene(editAccountScene);

        //INVOICE SCENE INJECTIONS


        primaryStage.setTitle("Bike Part Distributorship Manager");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {

            System.out.println("Stage is closing");

            String filename = "time.ser";

            // save the object to file
            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                fos = new FileOutputStream(filename);
                out = new ObjectOutputStream(fos);
                out.writeObject(userBase);

                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                Controller.onClose();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }


    public static void main(String[] args) {

        launch(args);
    }
}
