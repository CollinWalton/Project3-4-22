package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static sample.Controller.bwh;

public class WarehouseManagerController {

    public MenuBar menu;
    private Scene loginScene;
    private Scene editAccountScene;


    public TextField filterField;

    @FXML
    private TableView<BikePart> bpTable;
    @FXML
    private TableColumn<BikePart, String> whCol;
    @FXML
    private TableColumn<BikePart, String> nameCol;
    @FXML
    private TableColumn<BikePart, String> numCol;
    @FXML
    private TableColumn<BikePart, Double> listCol;
    @FXML
    private TableColumn<BikePart, Double> saleCol;
    @FXML
    private TableColumn<BikePart, Boolean> onSaleCol;
    @FXML
    private TableColumn<BikePart, Integer> qtyCol;

    private ObservableList<BikePart> masterData = FXCollections.observableArrayList();

    public void setLoginScene(Scene loginScene) { this.loginScene = loginScene; }
    public void setEditAccountScene(Scene editAccountScene) { this.editAccountScene = editAccountScene; }

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

                if(bp.getPartName().toLowerCase().contains(lowerCaseFilter) || bp.getNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<BikePart> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(bpTable.comparatorProperty());

        bpTable.setItems(sortedData);
    }

    public void updateInventory() throws IOException {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(filterField.getScene().getWindow());

        bwh.getBWH().get(0).updateWH(file);
        for( BikePart b : bwh.getBWH().get(0).getbpAL()) {
            if(!masterData.contains(b)) {
                masterData.add(b);
            }
        }

        bpTable.refresh();

    }

    public void openEditAccount() {
        Stage primaryStage = (Stage) (menu).getScene().getWindow();
        primaryStage.setScene(editAccountScene);
    }

    public void close() {
        Stage primaryStage = (Stage)(menu).getScene().getWindow();
        primaryStage.setScene(loginScene);
    }

}
