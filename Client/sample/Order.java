package sample;

import java.io.Serializable;

public class Order  implements Serializable {
    public Order(String id, String cost, String date, String time) {
        this.id = id;
        this.cost = cost;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Order(String id, String login, String cost, String date, String time) {
        this.id = id;
        this.login = login;
        this.cost = cost;
        this.date = date;
        this.time = time;
    }

    private String cost;
    private String date;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    Order(){
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
