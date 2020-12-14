package sample;

import java.io.Serializable;

public class Order  implements Serializable {
    private String cost;
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Order(String cost, String login, String date, String time, String id) {
        this.cost = cost;
        this.login = login;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    private String date;
    private String time;
    private String id;

    public String getTime() {
        return time;
    }

    public Order(String id, String cost, String date, String time) {
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
