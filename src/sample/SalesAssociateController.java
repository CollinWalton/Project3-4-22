package sample;

//import com.sun.jdi.IntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static sample.Controller.bwh;
import static sample.Controller.currentUser;

public class SalesAssociateController {

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

    public void transfer()  throws IOException {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(filterField.getScene().getWindow());

        Warehouse w1 = null;
        Warehouse w2 = null;
        Scanner in = new Scanner(file);
        boolean alreadyExecuted = false;
        while(in.hasNext()) {
            if (!alreadyExecuted) {
                alreadyExecuted = true;
                String input = in.next();
                String [] sa = input.split(",");
                for (Warehouse w : bwh.getBWH()) {
                    if (sa[0].equalsIgnoreCase(w.getName())) {
                        w1 = w;
                    }
                    if (sa[1].equalsIgnoreCase(w.getName())) {
                        w2 = w;
                    }
                }
            }

            if(w1.equals(currentUser.getWH()) || w2.equals(currentUser.getWH())) {
                String input = in.next();
                String[] sa = input.split(",");
                String name = sa[0];
                int qty = Integer.parseInt(sa[1]);
                for (BikePart b : w1.getbpAL()) {
                    if (b.getPartName().equalsIgnoreCase(name)) {
                        boolean match = false;
                        for (BikePart p : w2.getbpAL()) {
                            if (p.getPartName().equalsIgnoreCase(b.getPartName())) {
                                match = true;
                                p.setQuantity(p.getQuantity() + qty);
                                b.setQuantity(b.getQuantity() - qty);
                            }
                        }
                        if (!match) {
                            BikePart temp = new BikePart(b.getPartName(), b.getNumber(), b.getListPrice(), b.getSalePrice(), b.getOnSale(), qty);
                            w2.addPart(temp);
                            b.setQuantity(b.getQuantity() - qty);
                        }
                    }
                }
            }
        }

        for(Warehouse w : bwh.getBWH()) {
            for(BikePart b : w.getbpAL()) {
                if(!masterData.contains(b)) {
                    masterData.add(b);
                }
            }
        }
        bpTable.refresh();
    }

    public void sellParts() {

        Date date = new Date();
        String customer = "";
        ArrayList<BikePart> bpal = new ArrayList<BikePart>();

        ObservableList<BikePart> parts = bpTable.getSelectionModel().getSelectedItems();

        TextInputDialog textDialog = new TextInputDialog("Warehouse Name");
        textDialog.setTitle("Creating New Sale");
        textDialog.setHeaderText("Please Enter the Name of the Purchasing Customer.");
        textDialog.setContentText("Enter Customer Name Here:");
        Optional<String> textresult = textDialog.showAndWait();
        if (textresult.isPresent()){
            customer = textresult.get();
        }
        for(BikePart b : parts) {

            List<Integer> choices = new ArrayList<>();
            for (int i = 0; i < b.getQuantity(); i++) {
                choices.add(i+1);
            }

            ChoiceDialog<Integer> dialog = new ChoiceDialog<Integer>(1, choices);
            dialog.setTitle("Create Sale");
            dialog.setHeaderText(b.getPartName());
            dialog.setContentText("Choose quantity to sell:");

            Optional<Integer> result = dialog.showAndWait();
            if (result.isPresent()) {

                //creating new bikepart to add to invoice
                System.out.print(b.getQuantity());

                int numSold = dialog.getSelectedItem();

                //adjusting bikepart quantity by amount sold
                b.setQuantity(b.getQuantity() - numSold);

                BikePart temp = new BikePart(b.getPartName(),b.getNumber(),b.getListPrice(),b.getSalePrice(),b.getOnSale(),b.getQuantity());
                temp.setQuantity(numSold);
                bpal.add(temp);


            }
        }

        currentUser.createInvoice(bpal, date, customer);

        for(Warehouse w : bwh.getBWH()) {
            for(BikePart b : w.getbpAL()) {
                if(!masterData.contains(b)) {
                    masterData.add(b);
                }
            }
        }

        bpTable.refresh();

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
