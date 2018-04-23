package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;

import static sample.Controller.bwh;
import static sample.Controller.userBase;

public class OfficeManagerController {
    public TableView <BikePart> bpTable;
    public TableColumn <BikePart, String> whCol;
    public TableColumn <BikePart, String> nameCol;
    public TableColumn <BikePart, String> numCol;
    public TableColumn <BikePart, Double> listCol;
    public TableColumn <BikePart, Double> saleCol;
    public TableColumn <BikePart, Integer> qtyCol;
    public TableColumn <BikePart, Boolean> onSaleCol;

    ObservableList<BikePart> masterData = FXCollections.observableArrayList();;
    public TextField filterField;
    public MenuBar menu;
    private Scene loginScene;
    private Scene editAccountScene;
    private Scene invoiceScene;
	public MenuItem invoiceItem;

    public void setLoginScene(Scene loginScene) { this.loginScene = loginScene; }
    public void setEditAccountScene(Scene editAccountScene) { this.editAccountScene = editAccountScene; }
    public void setInvoiceScene(Scene invoiceScene) {this.invoiceScene = invoiceScene;}

    
    
    public void invoice()
    {
    	Stage primaryStage = (Stage) (menu).getScene().getWindow();
		primaryStage.setScene(invoiceScene);
    }
    public void displayInvoices() {


        Date min;
        Date max;
        Date d;

         ArrayList<Invoice> ial = new ArrayList<Invoice>();

         for(LoginAccount l : userBase.getlogAL()) {
             if(l instanceof SalesAssociate) {
                 ial.addAll(((SalesAssociate) l).getIAL());
             }
         }


         ArrayList<Invoice> display = new ArrayList<Invoice>();

        // for(Invoice i : ial) {
         //   d = i.getDate();
          //(d.after(min) && d.before(max)) {
             //   display.add(i);
          //  }
        // }

         //for (Invoice i : display) {
            //out.write(i);
         //}

    }

    @FXML
    public void initialize() {

        for(Warehouse w: bwh.getBWH()) {
            for (BikePart b : w.getbpAL()) {
                masterData.add(b);
            }
        }

        //START UP TABLEVIEW, WRAP IN Filtered and Sorted TABLE, THEN INITIALIZE
        whCol.setCellValueFactory(cellData -> cellData.getValue().whNameProperty());
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        numCol.setCellValueFactory(cellData -> cellData.getValue().numProperty());
        listCol.setCellValueFactory(cellData -> cellData.getValue().listProperty().asObject());
        saleCol.setCellValueFactory(cellDara -> cellDara.getValue().saleProperty().asObject());
        qtyCol.setCellValueFactory(cellData -> cellData.getValue().qtyProperty().asObject());
        onSaleCol.setCellValueFactory(cellData -> cellData.getValue().onSaleProperty());

        FilteredList<BikePart> filteredData = new FilteredList<>(masterData, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(bp -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(bp.getPartName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<BikePart> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(bpTable.comparatorProperty());

        bpTable.setItems(sortedData);

        bpTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

    public void close() {
        Stage primaryStage = (Stage)(menu).getScene().getWindow();
        primaryStage.setScene(loginScene);
    }

    public void openEditAccount() {
        Stage primaryStage = (Stage) (menu).getScene().getWindow();
        primaryStage.setScene(editAccountScene);
    }



}
