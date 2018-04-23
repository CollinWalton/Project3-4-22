package sample;

import sample.OfficeManager;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    public TextField userField;
    public TextField passField;
    private Scene sysAdminScene;

    public static UserBase userBase = new UserBase();
    public static BigHouse bwh =  new BigHouse();
    public static LoginAccount currentUser = null;
    public static LoginAccount sysadmin = new SystemAdmin("sysadmin", "12345");
    public static ArrayList<Invoice> invoices = new ArrayList<Invoice>();

    //SCENES
    private Scene warehouseManagerScene;
    private Scene salesAssociate;
    private Scene officeManagerScene;

    //SETS SCENES FOR SCENE SWITCHING FROM LOGIN PAGE
    public void setSysAdminScene(Scene scene) { sysAdminScene = scene; }
    public void setSalesAssociate(Scene salesAssociate) { this.salesAssociate = salesAssociate; }
    public void setWarehouseManagerScene(Scene warehouseManagerScene) { this.warehouseManagerScene = warehouseManagerScene; }

    public void login(ActionEvent actionEvent) {

        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        String username = userField.getText();
        String pass = passField.getText();

        if (userBase.getUserMap().containsKey(username) && userBase.getUserMap().get(username).getPassword().equals(pass)) {
            currentUser = userBase.getUserMap().get(username);
            if(userBase.getUserMap().get(username) instanceof SystemAdmin) {
                primaryStage.setScene(sysAdminScene);
            }
            if(userBase.getUserMap().get(username) instanceof WarehouseManager) {
                primaryStage.setScene(warehouseManagerScene);
            }
            if(userBase.getUserMap().get(username) instanceof SalesAssociate) {
                primaryStage.setScene(salesAssociate);
            }
            if(userBase.getUserMap().get(username) instanceof OfficeManager) {
                primaryStage.setScene(officeManagerScene);
            }
        }
        userField.clear();
        passField.clear();
    }

   @FXML
   public void initialize() {

        if(userBase.getUserMap().isEmpty()) {

            String filename = "time.ser";
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = new FileInputStream(filename);
                in = new ObjectInputStream(fis);
                userBase = (UserBase) in.readObject();
                in.close();
            } catch (Exception ex) {
                userBase.addUser(sysadmin);
            }
        }

       System.out.println("Loading user data.");

       //reads warehouses.txt, converts to warehouse objects, then fills them using the warehouse name.txt file if there is one.
       File file = new File("warehouses.txt");
       Scanner in = null;
       try {
           in = new Scanner(file);
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }

       Warehouse temp;

       while (in.hasNext()) {
           String s = in.nextLine();
           String [] sa = s.split(",");
           for (String st : sa) {
               temp = new Warehouse(st);
               bwh.addWH(temp);
           }
       }

       for (Warehouse w : bwh.getBWH()) {
           try {
               w.updateWH(new File(w.getName()+".txt"));
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

    public static void onClose() throws IOException {

        FileWriter output = new FileWriter("warehouses.txt");
        BufferedWriter buffOut = new BufferedWriter(output);
        for(Warehouse w : bwh.getBWH()) {
            w.write();
            buffOut.write(w.getName() + ",");
        }
        buffOut.close();
    }


    public void setOfficeManagerScene(Scene officeManagerScene) {
        this.officeManagerScene = officeManagerScene;
    }
}
