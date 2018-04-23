package sample;

import java.io.Serializable;

public class SystemAdmin extends LoginAccount implements Serializable {

    private final String accountType = "System Admin";

    public SystemAdmin(String username, String password) {
        super(username, password);
    }

    public String getAccountType() { return accountType; };

}
