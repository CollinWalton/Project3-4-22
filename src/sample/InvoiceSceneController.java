package sample;

import static sample.Controller.userBase;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import static sample.Controller.userBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

public class InvoiceSceneController {

	public DatePicker startdate;
	public DatePicker enddate;
	public Button submit;
	private Scene officeManagerScene;
	public TextArea invoiceDisplay;
	public ChoiceBox choiceID;
	public Button display;
	ObservableList<String> options = FXCollections.observableArrayList();
	public Button refresh;



	public void setOfficeManagerScene(Scene officeManagerScene) {this.officeManagerScene = officeManagerScene;}

	public void close()
	{
		Stage primaryStage = (Stage) submit.getScene().getWindow();
		primaryStage.setScene(officeManagerScene);
	}


	@FXML
	public void initialize() {
		for(LoginAccount l: userBase.getlogAL())
		{
			if(l.getAccountType().equalsIgnoreCase("Sales Associate"))
			{
				options.add(l.getUsername());
				choiceID.setItems(options);
			}
		}

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

		String user = (String) choiceID.getValue();
		LocalDate localDate1 = startdate.getValue();
		Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
		min = Date.from(instant1);

		LocalDate localDate2 = enddate.getValue();
		Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
		max = Date.from(instant2);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		DecimalFormat df = new DecimalFormat("#.##");
		
		for(Invoice i: ial)
		{
			if(user.equalsIgnoreCase(i.getSeller()) && i.getDate().compareTo(min) > 0 && i.getDate().compareTo(max) < 0)
			{
				invoiceDisplay.appendText((i.getBuyer() + "bought: \n"));
				for(BikePart bp: i.getBpal())
				{
					
					invoiceDisplay.appendText( (bp.toString() + " \n"));
				}
				
				invoiceDisplay.appendText( ("for a total of: " + df.format(i.getTotal()) + "\nsold by: " + i.getSeller() + " on " + dateFormat.format(i.getDate()) + "\n" ));
				
				
			
				
			}
		}


	}

	public void refresh()
	{
		ArrayList<String> usernames = new ArrayList();
		for(LoginAccount l: userBase.getlogAL())
		{
			if(l.getAccountType().equalsIgnoreCase("Sales Associate"))
			{
				if(!(options.contains(l.getUsername())))
				{
					options.add(l.getUsername());
				}
			}
		}
		choiceID.setItems(options);
	}
}
