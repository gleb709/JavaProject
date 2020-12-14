package sample;

import java.io.Serializable;

public class Product implements Serializable {
    private String productName;
    private String productCost;
    private String productID;
    private String productInfo;
    private String productType;

    public Product(String productName, String productCost, String productID, String productInfo, String productType) {
        this.productName = productName;
        this.productCost = productCost;
        this.productID = productID;
        this.productInfo = productInfo;
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Product(String productName, String productCost, String productID, String productInfo) {
        this.productName = productName;
        this.productCost = productCost;
        this.productID = productID;
        this.productInfo = productInfo;
    }

    public Product(){}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getProductCost() {
        return productCost;
    }

    public void setProductCost(String productCost) {
        this.productCost = productCost;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }
}
