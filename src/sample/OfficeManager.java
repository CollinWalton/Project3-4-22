package sample;

import sample.LoginAccount;
import java.io.Serializable;

public class OfficeManager extends LoginAccount implements Serializable {

    private final String accountType = "Office Manager";

    public OfficeManager(String user, String pass) { super(user, pass); }

    public String getAccountType() { return accountType; }
}
