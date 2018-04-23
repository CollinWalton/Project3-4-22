package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class SalesAssociate extends LoginAccount implements Serializable {

    private final String accountType = "Sales Associate";

    private ArrayList<Invoice> IAL =  new ArrayList<Invoice>();
    private Warehouse wh;

    public SalesAssociate(String username, String password) { super(username,password); }

    public ArrayList<Invoice> getIAL() { return IAL; }

    public void createInvoice(ArrayList<BikePart> bpAL, Date date, String buyer) {
        Invoice temp = new Invoice(bpAL, date, buyer);
        //if invoice is not already in IAL, add it in
        if (!IAL.contains(temp)) {
            temp.setSeller(this.getUsername());
            IAL.add(temp);
        }
    }

    public String getAccountType() { return accountType; }
    public Warehouse getWH() { return wh; }
    public void setWH(Warehouse w) { this.wh = w; }

}
