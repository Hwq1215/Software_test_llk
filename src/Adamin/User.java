package Adamin;

import java.io.Serializable;

public class User implements Serializable{
    public String account;
    public String password;

    public User(){
        this.account = null;
        this.password = null;
    }

    public User(String account,String password){
        this.account = account;
        this.password = password;
    }

}
