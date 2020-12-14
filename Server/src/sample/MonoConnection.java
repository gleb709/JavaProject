package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class MonoConnection implements Runnable {

    private static Socket clientDialog;
    private ObservableList<OrderInfo> order = FXCollections.observableArrayList();

    private ArrayList<User> list = null;
    private ArrayList<Product> products = null;
    private ArrayList<Tariff> tariffs = null;
    private ArrayList<Order> orders = null;
    private ArrayList<OrderInfo> ordersInfo = null;

    public MonoConnection(Socket client) {

        MonoConnection.clientDialog = client;
        products = new ArrayList<Product>();
        list = new ArrayList<User>();
        orders = new ArrayList<Order>();
        ordersInfo = new ArrayList<OrderInfo>();
        tariffs = new ArrayList<Tariff>();
    }
    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientDialog.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");
            System.out.println("DataOutputStream  created");
            Object obj = in.readObject();
            System.out.println("Get object " + obj);
            String str = in.readUTF();
            System.out.println(("Get info about object " + str));

            DataBaseHandler dbHander = new DataBaseHandler();
            ResultSet res = null;
            User user1 = new User();
            user1.setFirstName("123");

            switch (str) {
                case Const.SIGN_IN: {
                    res = dbHander.signInUser((User) obj);
                    if (!res.next())
                        list.add(user1);
                    out.writeObject(list);
                    break;
                }
                case Const.DELETE_USER:{
                    dbHander.deleteUser((User)obj);
                    list.add(user1);
                    out.writeObject(list);
                }
                case Const.USERS:{
                    res = dbHander.allUsers((User) obj);
                    while(res.next()){
                        list.add(new User(res.getString("firstName"), res.getString("lastName"), res.getString("login"), res.getString("password")));
                    }
                    out.writeObject(list);
                }
                case Const.CHECK: {
                    res = dbHander.checkUser((User) obj);
                    if (res.next())
                            list.add(user1);
                    out.writeObject(list);
                    break;
                }
                case Const.SIGN_UP: {
                    res = dbHander.signCheak((User) obj);
                    if (!res.next())
                        dbHander.signUpUser((User) obj);
                    else {
                        list.add(user1);
                    }
                    out.writeObject(list);
                    break;
                }
                case Const.SEARCH_ANIMALS: {
                    res = dbHander.search(str);
                    while (res.next())
                        products.add(new Product(res.getString("productName"), res.getString("productPrice"),
                                res.getString("productID"), res.getString("productInfo")));
                    System.out.println(products.size());
                    out.writeObject(products);
                    break;
                }
                case Const.SEARCH_FOOD: {
                    res = dbHander.search(str);
                    while (res.next())
                        products.add(new Product(res.getString("productName"), res.getString("productPrice"),
                                res.getString("productID"), res.getString("productInfo")));
                    out.writeObject(products);
                    break;
                }
                case "tariffInfo": {
                    res = dbHander.getTariffInfo((User) obj);
                    Tariff tariff = new Tariff();
                    if(res.next()) {
                        tariff.setTariffID(res.getString("tariffID"));
                        tariff.setTariffName(res.getString("tariffName"));
                        tariff.setTariffPrice(res.getString("tariffPrice"));
                        tariff.setTariffInfo(res.getString("tariffInfo"));
                    }
                    else System.out.println("Error");
                    out.writeObject(tariff);
                    break;
                }

                case "discountList":{
                    res = dbHander.allTariffs((Tariff)obj);
                    while (res.next()){
                        tariffs.add(new Tariff(
                                res.getString("tariffName"),
                                res.getString("tariffID"),
                                res.getString("tariffInfo"),
                                res.getString("tariffPrice"),
                                res.getString("tariffSale")));
                    }
                    out.writeObject(tariffs);
                }

                case Const.SEARCH_TRASH: {
                    res = dbHander.search(str);
                    while (res.next())
                        products.add(new Product(res.getString("productName"), res.getString("productPrice"),
                                res.getString("productID"), res.getString("productInfo")));
                    System.out.println(products.size());
                    out.writeObject(products);
                    break;
                }

                case "userInfo":{
                    res = dbHander.money((User)obj);
                    res.next();
                    User user = new User();
                    user.setUserMoney(res.getString("money"));
                    user.setFirstName(res.getString("firstName"));
                    user.setLastName(res.getString("lastName"));
                    user.setUserPassword(res.getString("password"));
                    res = dbHander.getTariffInfo((User)obj);
                    res.next();
                    user.setUserTariff(res.getString("tariffName"));
                    out.writeObject(user);
                    break;
                }

                case Const.MONEY_CHECK:{
                    res = dbHander.money((User)obj);
                    res.next();
                    User user = new User();
                    user.setUserMoney(res.getString("money"));
                    user.setFirstName(res.getString("firstName"));
                    user.setLastName(res.getString("lastName"));
                    user.setUserPassword(res.getString("password"));
                    res = dbHander.getTariffInfo((User)obj);
                    res.next();
                    user.setUserTariff(res.getString("tariffName"));
                    out.writeObject(user);
                    break;
                }
                case Const.REMOVE_MONEY:{
                    res = dbHander.money((User)obj);
                    res.next();
                    dbHander.removeMoney((User)obj, res);
                    out.writeObject(user1);
                    break;
                }
                case Const.MONEY_ADD:{
                    res = dbHander.money((User)obj);
                    ResultSet res1 = null;
                    res.next();
                    dbHander.addMoney((User)obj, res);
                    res1 = dbHander.money((User)obj);
                    res1.next();
                    user1.setUserMoney(String.valueOf(parseInt(res1.getString("money"))));
                    ;
                    out.writeObject(user1);
                    break;
                }
                case Const.ADD_PRODUCT:{
                    res = dbHander.addProduct((OrderInfo) obj);
                    OrderInfo prod = new OrderInfo();
                    if(res.next() == true) {
                        prod.setProductName(res.getString("productName"));
                        prod.setProductCost(res.getString("productPrice"));
                        prod.setProductInfo(res.getString("productInfo"));
                        prod.setProductID(res.getString("productID"));
                    }
                    out.writeObject(prod);
                    break;
                }

                case Const.USER_ORDERS:{
                    res = dbHander.userOrders((Order)obj);
                    while (res.next())
                        orders.add(new Order(res.getString("idorders"),res.getString("orderCost"), res.getString("orderDate"), res.getString("orderTime")));
                    out.writeObject(orders);
                    break;
                }
                case Const.DELETE_ORDER:{
                    dbHander.deleteOrder((Order)obj);
                    list.add(user1);
                    out.writeObject(list);
                    break;
                }
                case Const.REMOVE_PRODUCT:{
                    dbHander.removeProduct((Product)obj);
                    list.add(user1);
                    out.writeObject(list);
                    break;
                }

                case "removeTariff": {
                    dbHander.removeTariff((Tariff) obj);
                    list.add(user1);
                    out.writeObject(list);
                    break;
                }

                case Const.CHANGE_PASSWORD:{
                    dbHander.changePassword((User)obj);
                    list.add(user1);
                    out.writeObject(list);
                    break;
                }

                case "changeTariff":{
                    dbHander.changeTariff((User)obj);
                    out.writeObject(obj);
                    break;
                }

                case Const.ALL_PRODUCTS:{
                    res = dbHander.searchAll((Product)obj);
                    while (res.next()){
                        products.add(new Product(res.getString("productName"), res.getString("productPrice"),
                                res.getString("productID"), res.getString("productInfo"), res.getString("productType")));
                    }
                    out.writeObject(products);
                }

                case "tariffList":{
                    res = dbHander.allTariffs((Tariff)obj);
                    while (res.next()){
                        tariffs.add(new Tariff(
                                res.getString("tariffName"),
                                res.getString("tariffID"),
                                res.getString("tariffInfo"),
                                res.getString("tariffPrice"),
                                res.getString("tariffSale")));
                    }
                    out.writeObject(tariffs);
                }
                case Const.NEW_PRODUCT:{
                    dbHander.newProduct((Product)obj);
                    list.add(user1);
                    out.writeObject(list);
                    break;
                }

                case "addTariff":{
                    dbHander.addTariff((Tariff)obj);
                    list.add(user1);
                    out.writeObject(list);
                    break;
                }

                case Const.ALL_ORDERS:{
                    res = dbHander.allOrders((Order)obj);
                    while (res.next()){
                        orders.add(new Order(res.getString("orderCost"),res.getString("login"),
                                res.getString("orderDate"), res.getString("orderTime"), res.getString("idorders")));
                    }
                    out.writeObject(orders);
                    break;
                }
                case Const.OPEN_ORDER:{
                    res = dbHander.openOrder((Order)obj);
                    while (res.next()){
                        ordersInfo.add(new OrderInfo(res.getString("productCount"),res.getString("productName"),
                                res.getString("productCost"), res.getString("productID"), res.getString("productInfo"),
                                res.getString("productPrice")));
                    }
                    out.writeObject(ordersInfo);
                    break;
                }
                case Const.GET_ORDER:{
                    res = dbHander.getOrder((Order)obj);
                    while (res.next()){
                        ordersInfo.add(new OrderInfo(res.getString("productCount"),res.getString("productName"),
                                res.getString("productCost"), res.getString("productID"), res.getString("productInfo"),
                                res.getString("productPrice")));

                    }
                    out.writeObject(ordersInfo);
                    break;

                }default:{
                    dbHander.addOrder((ArrayList<OrderInfo>)obj, str);
                    list.add(user1);
                    out.writeObject(list);
                }

            }

                System.out.println("Server try writing to channel");
                System.out.println("Server Wrote message to clientDialog.");
                out.flush();
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");
            in.close();
            out.close();

            // потом закрываем сокет общения с клиентом в нити моносервера
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void getResults(ResultSet result, String string) throws SQLException {
        if(string == "user"){
            while (result.next()){

            }
        }
    }
}