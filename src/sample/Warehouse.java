package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Warehouse Class. Designed to hold and manipulate an ArrayList of BikeParts.
 */
public class Warehouse implements Serializable {

    private ArrayList<BikePart> bpAL = new ArrayList<>();
    private String name;

    /**
     * Creates a Warehouse Object.
     * @param name The name of the Warehouse.
     */
    public Warehouse(String name) {
        this.name = name;
    }

    //getter

    public ArrayList<BikePart> getbpAL() { return bpAL; }

    public String getName() { return name; }

    /**
     * Uses file to update warehouse inventory.
     * @param file The file that contains the inventory update.
     */
    public void updateWH (File file) throws IOException {

        Scanner inputFile = null;
        inputFile = new Scanner(file);

        while (inputFile.hasNext()) {

            String input = inputFile.next();

            String[] sa = input.split(",");

            String partName = sa[0];
            String partNumber = sa[1];
            double price = Double.parseDouble(sa[2]);
            double salesPrice = Double.parseDouble(sa[3]);
            boolean onSale = Boolean.parseBoolean(sa[4]);
            int quantity = Integer.parseInt(sa[5]);

            BikePart tempPart = new BikePart(partName, partNumber, price, salesPrice, onSale, quantity);
            boolean match = false;
            for (BikePart b : this.bpAL) {
                if (b.getPartName().equals(tempPart.getPartName())) {
                    b.setListPrice(price);
                    b.setQuantity(b.getQuantity() + quantity);
                    b.setSalePrice(tempPart.getSalePrice());
                    b.setOnSale(tempPart.getOnSale());
                    match = true;
                }
            }

            if (!match) {
                this.addPart(tempPart);
            }
        }
    }

    /**
     * Allows a single designated BikePart to be added to the Warehouse.
     * @param bp The BikePart that will be added/updated.
     */
    public void addPart(BikePart bp) {

        boolean match = false;
        for (BikePart b : bpAL) {
            if(b.getPartName().equalsIgnoreCase(bp.getPartName())) { match = true;
                b.setListPrice(bp.getListPrice());
                b.setSalePrice(bp.getSalePrice());
                b.setOnSale(bp.getOnSale());
                b.setQuantity(b.getQuantity() + bp.getQuantity());
            }
        }
        if (!match) { bp.setWhName(this.name); bpAL.add(bp); }
    }

    /**
     * Writes current inventory of BikeParts to a .txt file with the same name of the Warehouse.
     * @throws IOException
     */
    public void write() throws IOException {

        FileWriter output = new FileWriter(this.name + ".txt");
        BufferedWriter buffOut = new BufferedWriter(output);
        for(BikePart b : this.bpAL) {
            buffOut.write(String.valueOf(b));
            buffOut.newLine();
        }
        buffOut.close();
    }

    public BikePart getBikePart(String partNumber)
    {
        int temp = 0;
        for(int x = 0; x < bpAL.size(); x++)
        {
            if(bpAL.get(x).getNumber().equalsIgnoreCase(partNumber))
            {
                temp = x;
            }
        }
        if(temp >= 0)
        {
            return bpAL.get(temp);
        }
        else
        {
            return null;
        }
    }

}