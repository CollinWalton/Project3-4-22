package sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserBase implements Serializable {

    private Map<String, LoginAccount> userMap = new HashMap<>();
    private ArrayList<LoginAccount>logAL = new ArrayList<>();

    public UserBase() {
    }

    public Map<String, LoginAccount> getUserMap() {
        return userMap;
    }

    public void addUser(LoginAccount x) {
        if(!userMap.containsValue(x)){
            userMap.put(x.getUsername(), x);
            logAL.add(x);
        }
    }

    public void deleteUser(LoginAccount x) {
        userMap.remove(x.getUsername());
        logAL.remove(x);
    }

    public ArrayList<LoginAccount> getlogAL() {
        return logAL;
    }
}
