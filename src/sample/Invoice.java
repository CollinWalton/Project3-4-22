package sample;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Invoice implements Serializable {

    private ArrayList<BikePart> bpal = new ArrayList<BikePart>();
    private double total;
    Date date;
    String seller;
    String buyer;

    public Invoice(ArrayList<BikePart> bpal, Date d, String buyer) {
        this.bpal = bpal;
        this.date = d;
        calcTotal();
    }

    //setters
    public void setSeller(String seller) { this.seller = seller; }
    public void setTotal(double total) {
        this.total = total;
    }

    //getters
    public double getTotal() { return total; }
    public String getSeller() {return seller;}
    public String getBuyer() {return buyer;}

    /**
     * Calculates total of all BikeParts sold in this Invoice, and sets Total to that value using setTotal().
     */
    public void calcTotal() {
        double temp = 0;
        for(BikePart b : bpal) {
            temp += b.getTruePrice();
        }
        total = temp;
        this.setTotal(temp);
    }

    public ArrayList<BikePart> getBpal() {
        return bpal;
    }


    public Date getDate() {
        return date;
    }
    
   // public String toString()
    //{
    	//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	//String invoice = new String();
    	//invoice.concat(buyer + " bought \n");
    	//for(BikePart bp: bpal)
    	//{
    		//invoice.concat(bp + "\n");
    	//}
    	//invoice.concat("at " + dateFormat.format(date)+ "\n");
    	//invoice.concat("Sold by " + seller);
    	//System.out.println(invoice);
    	//return invoice;

    //}
}
