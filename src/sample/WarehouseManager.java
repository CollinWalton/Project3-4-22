package sample;

import java.io.Serializable;

public class WarehouseManager extends LoginAccount implements Serializable {

    private final String accountType = "Warehouse Manager";

    private BigHouse bwh;

    public void setBwh(BigHouse bwh) { this.bwh = bwh; }

    public WarehouseManager(String user, String pass) { super(user,pass); bwh = null; }

    public String getAccountType() { return accountType; }

}