package sample;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    protected String firstName;
    protected String lastName;
    protected String userLogin;
    protected String userPassword;
    protected  String userMoney = "0";
    protected String userTariff;

    public User(String firstName, String lastName, String userLogin, String userPassword, String userMoney, String userTariff) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userMoney = userMoney;
        this.userTariff = userTariff;
    }

    public String getUserTariff() {
        return userTariff;
    }

    public void setUserTariff(String userTariff) {
        this.userTariff = userTariff;
    }

    public String getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(String userMoney) {
        this.userMoney = userMoney;
    }

    public User(String firstName, String lastName, String userLogin, String userPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }

    public User() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
