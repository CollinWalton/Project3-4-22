package sample;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * The BigHouse Class. Designed to contain and manipulate an ArrayList of Warehouse objects.
 */
public class BigHouse implements Serializable {

    private ArrayList<Warehouse> bwh = new ArrayList<>();

    /**
     * Checks to see if the Warehouse name is a duplicate and if it is not the Warehouse is added to the ArrayList bwh.
     * @param w The Warehouse to be added to the internal ArrayList of Warehouses.
     */
    public void addWH (Warehouse w) { if(!bwh.contains(w)) { bwh.add(w); }}

    //getter
    public ArrayList<Warehouse> getBWH (){ return bwh; }

    /**
     * Sells a part from a specific warehouse using partnumber.
     * @param number The part number that you would like to sell.
     * @param w The warehouse from which you would like to sell the part.
     */

    public String sellPart(String number, Warehouse w) {

        boolean match = false;
        String p = null;
        String t = null;
        for (BikePart bp : w.getbpAL()) {
            if (bp.getNumber().equals(number) && bp.getQuantity() > 0) {
                match = true;
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                p = bp.getPartName() + " - " + bp.getTruePrice() + " - ";

                if (bp.getOnSale()) {
                    t = "ON SALE" + " - " + "Sold at " + dateFormat.format(date) + ".";
                } else if (!bp.getOnSale()) {
                    t = "NOT ON SALE" + " - " + "Sold at " + dateFormat.format(date) + ".";
                }

                bp.setQuantity(bp.getQuantity() - 1);
            }
        }
        if (!match) {
            t = "ERROR: PART NOT FOUND/OUT OF STOCK.";
            return t;
        }
        String c = p + t;
        return c;
    }

    /**
     * This method takes a filename, reads it, and uses the information to transfer parts from one warehouse to another
     * warehouse.
     * @param file The file used to initiate transfer between 2 warehouses.
     * @throws IOException Catches file not found exception for filename if file by that name does not exist.
     */
    public void transferParts(File file) throws IOException {

        Warehouse w1 = null;
        Warehouse w2 = null;
        Scanner in = new Scanner(file);
        boolean alreadyExecuted = false;
        while(in.hasNext()) {
            if (!alreadyExecuted) {
                alreadyExecuted = true;
                String input = in.next();
                String [] sa = input.split(",");
                for (Warehouse w : bwh) {
                    if (sa[0].equalsIgnoreCase(w.getName())) {
                        w1 = w;
                    }
                    if (sa[1].equalsIgnoreCase(w.getName())) {
                        w2 = w;
                    }
                }
            }
            String input = in.next();
            String [] sa = input.split(",");
            String name = sa[0];
            int qty = Integer.parseInt(sa[1]);
            for (BikePart b : w1.getbpAL()) {
                if (b.getPartName().equalsIgnoreCase(name)) {
                    boolean match = false;
                    for (BikePart p : w2.getbpAL()) {
                        if(p.getPartName().equalsIgnoreCase(b.getPartName())) {
                            match = true;
                            p.setQuantity(p.getQuantity()+qty);
                            b.setQuantity(b.getQuantity()-qty);
                        }
                    }
                    if(!match) {
                        BikePart temp = new BikePart(b.getPartName(),b.getNumber(),b.getListPrice(),b.getSalePrice(),b.getOnSale(),qty);
                        w2.addPart(temp);
                        b.setQuantity(b.getQuantity() - qty);
                    }
                }
            }
        }
    }

    public void transferSalesAssociateParts(File file) throws IOException {

        Warehouse w1 = null;
        Warehouse w2 = null;
        Scanner in = new Scanner(file);
        boolean alreadyExecuted = false;
        while(in.hasNext()) {
            if (!alreadyExecuted) {
                alreadyExecuted = true;
                String input = in.next();
                String [] sa = input.split(",");
                for (Warehouse w : bwh) {
                    if (sa[0].equalsIgnoreCase(w.getName())) {
                        w1 = w;
                    }
                    if (sa[1].equalsIgnoreCase(w.getName())) {
                        w2 = w;
                    }
                }
            }
            String input = in.next();
            String [] sa = input.split(",");
            String name = sa[0];
            int qty = Integer.parseInt(sa[1]);
            for (BikePart b : w1.getbpAL()) {
                if (b.getPartName().equalsIgnoreCase(name)) {
                    boolean match = false;
                    for (BikePart p : w2.getbpAL()) {
                        if(p.getPartName().equalsIgnoreCase(b.getPartName())) {
                            match = true;
                            p.setQuantity(p.getQuantity()+qty);
                            b.setQuantity(b.getQuantity()-qty);
                        }
                    }
                    if(!match) {
                        BikePart temp = new BikePart(b.getPartName(),b.getNumber(),b.getListPrice(),b.getSalePrice(),b.getOnSale(),qty);
                        w2.addPart(temp);
                        b.setQuantity(b.getQuantity() - qty);
                    }
                }
            }
        }
    }
}

