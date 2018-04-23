package sample;

import javafx.beans.property.*;

import java.io.Serializable;
import java.util.Optional;

/**
 * The BikePart Class, containing the field members and methods necessary to create and store BikePart data.
 */
public class BikePart implements Serializable {

    private String partName;
    private String partNumber;
    private double listPrice;
    private double salePrice;
    private boolean onSale;
    private int quantity;
    private String whName;

    /**
     * Creates a new BikePart object.
     * @param name The name of the BikePart
     * @param number The part number of the BikePart
     * @param list The list price of the BikePart
     * @param sale The on sale price of the BikePart
     * @param onSale The status of the BikePart (whether it is on sale or not)
     * @param qty The quantity of the BikePart
     */
    public BikePart(String name, String number, double list, double sale, boolean onSale, int qty) {
        this.partName = name;
        this.partNumber = number;
        this.listPrice = list;
        this.salePrice = sale;
        this.onSale = onSale;
        this.quantity = qty;
    }

    //getters

    public String getNumber() { return partNumber; }

    public double getTruePrice() { if(onSale) { return salePrice; } else { return listPrice; } }

    public String getPartName() { return partName; }

    public int getQuantity() { return quantity; }

    public boolean getOnSale() { return onSale; }

    public double getSalePrice() { return salePrice; }

    public double getListPrice() { return listPrice; }

    //setters

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void setListPrice(double price) { this.listPrice = price; }

    public void setSalePrice(double price) { this.salePrice = price; }

    public void setOnSale(boolean onSale) { this.onSale = onSale; }

    public void setWhName (String n) { this.whName = n; }

    //property wrappers to assist with TableView

    public StringProperty nameProperty() { StringProperty nameProp = new SimpleStringProperty(this.partName); return nameProp;}

    public StringProperty numProperty() { StringProperty numProp = new SimpleStringProperty(this.partNumber); return numProp;}

    public DoubleProperty listProperty() { DoubleProperty listProp = new SimpleDoubleProperty(this.listPrice); return listProp; }

    public DoubleProperty saleProperty() { DoubleProperty saleProp = new SimpleDoubleProperty(this.salePrice); return saleProp; }

    public BooleanProperty onSaleProperty() { BooleanProperty boolProp = new SimpleBooleanProperty(this.onSale); return boolProp; }

    public IntegerProperty qtyProperty() { IntegerProperty qtyProp = new SimpleIntegerProperty(this.quantity); return qtyProp; }

    public StringProperty whNameProperty() { StringProperty whProp = new SimpleStringProperty(this.whName); return whProp; }

    //override toString() method so that writing the files in I/O works

    @Override
    public String toString() { return partName + "," + partNumber + "," + listPrice + "," + salePrice + "," + onSale + "," + quantity; }

}

