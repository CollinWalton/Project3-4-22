package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public abstract class LoginAccount implements Serializable {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String accountType;

	public LoginAccount(String username, String password) {
		this.username = username;
		this.password = password;
	}

	//setters
	public void setUsername(String username) {
		this.username = username;
	}
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
	public void setPassword(String password) {
		this.password = password;
	}
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
    public String getEmail() {
        return email;
    }
	public String getAccountType() { return accountType; }

	//property wrappers
    public StringProperty userNameProperty () { SimpleStringProperty property = new SimpleStringProperty(this.username); return property; }
    public StringProperty passwordProperty () { SimpleStringProperty property = new SimpleStringProperty(this.password); return property; }
    public StringProperty fNameProperty () { SimpleStringProperty property = new SimpleStringProperty(this.firstName); return property; }
    public StringProperty lNameProperty () { SimpleStringProperty property = new SimpleStringProperty(this.lastName); return property; }
    public StringProperty emailProperty () { SimpleStringProperty property = new SimpleStringProperty(this.email); return property; }
    public StringProperty typeProperty () { SimpleStringProperty property = new SimpleStringProperty(this.accountType); return property; }

    //overridden equals method for comparison
	public boolean equals(LoginAccount x) {
		if(this.username.equalsIgnoreCase(x.getUsername())) {
			return true;
		}
		else {
			return false;
		}
	}

	//trash
    public void setBwh(BigHouse bwh) { }
    public Warehouse getWH() { return null; }
    public void setWH(Warehouse wh) {}
    public void createInvoice(ArrayList<BikePart> bpal, Date date, String customer) {}
}
