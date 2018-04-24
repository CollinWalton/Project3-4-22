package sample;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

public class SalesAssociateTest extends TestCase {

    ArrayList<BikePart>bpal = new ArrayList<>();

    Date date = new Date();

    SalesAssociate s = new SalesAssociate("j", "g");

    Warehouse w = new Warehouse("x");

    public void testGetIAL() {

       s.createInvoice(bpal, date, "customer");

       s.getIAL();

    }

    public void testCreateInvoice() {

        s.createInvoice(bpal, date, "customer");

    }

    public void testGetAccountType() {

        s.getAccountType();
    }

    public void testGetWH() {
        s.getWH();
    }

    public void testSetWH() {
        s.setWH(w);
    }
}