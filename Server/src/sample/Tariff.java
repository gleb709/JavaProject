package sample;

import java.io.Serializable;

public class Tariff implements Serializable {
    private String tariffName;
    private String tariffID;
    private String tariffInfo;
    private String tariffPrice;
    private String tariffSale;

    public String getTariffSale() {
        return tariffSale;
    }

    public void setTariffSale(String tariffSale) {
        this.tariffSale = tariffSale;
    }

    public Tariff(String tariffName, String tariffID, String tariffInfo, String tariffPrice, String tariffSale) {
        this.tariffName = tariffName;
        this.tariffID = tariffID;
        this.tariffInfo = tariffInfo;
        this.tariffPrice = tariffPrice;
        this.tariffSale = tariffSale;
    }

    public Tariff(){}

    public Tariff(String tariffName, String tariffID, String tariffInfo, String tariffPrice) {
        this.tariffName = tariffName;
        this.tariffID = tariffID;
        this.tariffInfo = tariffInfo;
        this.tariffPrice = tariffPrice;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public String getTariffID() {
        return tariffID;
    }

    public void setTariffID(String tariffID) {
        this.tariffID = tariffID;
    }

    public String getTariffInfo() {
        return tariffInfo;
    }

    public void setTariffInfo(String tariffInfo) {
        this.tariffInfo = tariffInfo;
    }

    public String getTariffPrice() {
        return tariffPrice;
    }

    public void setTariffPrice(String tariffPrice) {
        this.tariffPrice = tariffPrice;
    }
}
