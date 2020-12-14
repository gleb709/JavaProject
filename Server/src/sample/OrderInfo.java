package sample;

import java.io.Serializable;

public class OrderInfo implements Serializable {
    private String count;
    private String productName;
    private String productCost;
    private String productID;
    private String productInfo;
    private String price;

   public String getPrice() {
     return price;
   }

    public OrderInfo(String count, String productName, String productCost, String productID, String productInfo, String price) {
        this.count = count;
        this.productName = productName;
        this.productCost = productCost;
        this.productID = productID;
        this.productInfo = productInfo;
        this.price = price;
    }

    public void setPrice(String price) {
      this.price = price;
    }

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

    public OrderInfo() {

    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
