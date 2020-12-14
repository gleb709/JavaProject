package sample;

import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalTime;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class DataBaseHandler extends Configs implements Serializable {
    Connection dbConnection;


    public Connection getDbConnection() throws
            ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName + "?verifyServerCertificate=false&useSSL=false&requireSSL=false&useLegacyDatetimeCode=false&amp&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public ResultSet searchAll(Product product){
        ResultSet resSet = null;

        String select = "SELECT * FROM into" + Const.PRODUCT_TABLE;
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);
            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void removeProduct(Product product){
        String  delete = "DELETE FROM into" + Const.PRODUCT_TABLE + " WHERE " + Const.PRODUCT_ID + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(delete);

            app.setString(1, product.getProductID());

            app.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeTariff(Tariff tariff){
        String  delete = "DELETE FROM intotarifflist" + " WHERE tariffID" + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(delete);

            app.setString(1, tariff.getTariffID());

            app.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet openOrder(Order order){
        ResultSet resSet = null;
        String select = "SELECT * FROM into" + order.getLogin()+order.getId();
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet allOrders(Order order){
        ResultSet resSet = null;
        String select = "SELECT * FROM into" + Const.ORDERS;
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }


    public ResultSet search(String string) throws SQLException {
        ResultSet resSet = null;

        System.out.println(string);

        String select = "SELECT * FROM into" + Const.PRODUCT_TABLE + " WHERE " +
                Const.PRODUCT_TYPE + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            app.setString(1, string);

            resSet = app.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void deleteOrder(Order obj) throws SQLException, ClassNotFoundException {
        ResultSet resSet = null;
        String select = "SELECT * FROM into" + Const.ORDERS_TABLE + " WHERE " +
                Const.ORDER_ID + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            app.setString(1, obj.getId());

            resSet = app.executeQuery();

        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        User order = new User();
        while (resSet.next()) {
            order.setFirstName(resSet.getString("idorders"));
            order.setLastName(resSet.getString("login"));
        }

        String  delete = "DELETE FROM into" + Const.ORDERS_TABLE + " WHERE " + Const.ORDER_ID + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(delete);

            app.setString(1, obj.getId());

            app.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet addProduct(OrderInfo product){
        ResultSet resSet = null;
        String select = "SELECT * FROM into" + Const.PRODUCT_TABLE + " WHERE " +
                Const.PRODUCT_ID + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            app.setString(1, product.getProductID());

            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;

    }

    public ResultSet userOrders(Order obj){
        ResultSet resSet = null;
        String select = "SELECT * FROM into" + Const.ORDERS + " WHERE " +
                Const.USERS_LOGIN + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            app.setString(1, obj.getCost());

            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void addOrder(ArrayList<OrderInfo> order, String userName) throws SQLException, ClassNotFoundException {
       String insert = "INSERT INTO" + Const.ORDERS_TABLE + "(" + Const.USERS_LOGIN + "," +
                Const.ORDER_COST + "," + Const.ORDER_DATE + "," + Const.ORDER_TIME + ")" + "VALUES(?,?,?,?)";

        int orderCost = 0;
        for (int i = 0; i < order.size(); i++){
            orderCost += parseInt(order.get(i).getPrice());
        }

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        try {
            PreparedStatement app = getDbConnection().prepareStatement(insert);
            app.setString(1, userName);
            app.setString(2, Integer.toString(orderCost));
            app.setString(3, date.toString());
            app.setString(4, Integer.toString(time.getMinute()));

            app.executeUpdate();

            System.out.println("Order created");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ResultSet res2 = null;
        String select = "SELECT * FROM " + "into" + Const.ORDERS_TABLE + " WHERE " +
                Const.ORDER_TIME + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            app.setString(1,Integer.toString(time.getMinute()));

            res2 = app.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        res2.next();
        String SQL = "CREATE TABLE "+ "into"+ userName + res2.getString("idorders") + "(" + " productName VARCHAR(50), " +
                " productID VARCHAR (50), " + " productCost VARCHAR (50), " + " productCount VARCHAR (50), " +
                " productPrice VARCHAR (50), " + " productInfo VARCHAR (50), " + " PRIMARY KEY (productName))";

        try {
            PreparedStatement app = getDbConnection().prepareStatement(SQL);

            app.executeUpdate(SQL);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Order added");
         insert = "INSERT INTO" + userName + res2.getString("idorders") + "(" + Const.PRODUCT_NAME + "," +
                Const.PRODUCT_ID + "," + Const.PRODUCT_COST + "," +
                Const.PRODUCT_COUNT + "," + Const.PRODUCT_PRICE + "," + Const.PRODUCT_INFO + ")" + "VALUES(?,?,?,?,?,?)";


        try {
            PreparedStatement app = getDbConnection().prepareStatement(insert);
            for ( int i = 0; i < order.size(); i++) {
                app.setString(1, order.get(i).getProductName());
                app.setString(2, order.get(i).getProductID());
                app.setString(3, order.get(i).getProductCost());
                app.setString(4, order.get(i).getCount());
                app.setString(5, order.get(i).getPrice());
                app.setString(6, order.get(i).getProductInfo());

                app.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Order inserted");

    }

    public void signUpUser(User user) {

        String insert = "INSERT INTO" + Const.USER_TABLE + "(" + Const.USERS_FIRSTNAME + "," +
                Const.USERS_LASTNAME + "," + Const.USERS_LOGIN + "," + Const.USERS_PASSWORD +","+ Const.USERS_MONEY+")" + "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement app = getDbConnection().prepareStatement(insert);

            app.setString(1, user.firstName);
            app.setString(2, user.lastName);
            app.setString(3, user.userLogin);
            app.setString(4, user.userPassword);
            app.setString(5, "0");

            app.executeUpdate();
            System.out.println("Пользователь зарегистрирован");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ResultSet money(User user) throws SQLException, ClassNotFoundException {

        ResultSet resSet = null;

        String select = "SELECT * FROM " + "into" + Const.USER_TABLE + " WHERE " +
                Const.USERS_LOGIN + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            app.setString(1, user.getUserLogin());
            System.out.println(user.getUserLogin());

            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void removeMoney(User user, ResultSet res) throws SQLException {
        ResultSet resSet = null;
        int money = 0;
        money += parseInt(res.getString("money"));
        money -= parseInt(user.getUserMoney());
        user.setUserMoney(Integer.toString(money));
        String select1 = "UPDATE " + "into" + Const.USER_TABLE + " SET " + "money " + "=?" + " WHERE "
                + Const.USERS_LOGIN + " =?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select1);
            app.setString(1,user.getUserMoney());
            app.setString(2,user.getUserLogin());
            app.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTariffInfo(User user) throws SQLException {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + "intousers" + " WHERE " +
                Const.USERS_LOGIN + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);
            app.setString(1,user.getUserLogin());
            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        select = "SELECT * FROM " + "intotarifflist WHERE tariffID" + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);
            resSet.next();
            app.setString(1, resSet.getString("tariffID"));
            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet allTariffs(Tariff tariff){
        ResultSet resSet = null;

        String select = "SELECT * FROM intotarifflist";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);
            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public void newProduct(Product product){
        String insert = "INSERT INTO" + "products" + "(" + "productName"+ "," +
                "productType" + "," + "productPrice" + "," + "productInfo" + ")" + "VALUES(?,?,?,?)";

        try {
            PreparedStatement app = getDbConnection().prepareStatement(insert);
            app.setString(1, product.getProductName());
            app.setString(2, product.getProductType());
            app.setString(3, product.getProductCost());
            app.setString(4, product.getProductInfo());

            app.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addTariff(Tariff tariff){
        String insert = "INSERT INTO" + "tarifflist" + "(" + "tariffName"+ "," +
                "tariffSale" + "," + "tariffPrice" + "," + "tariffInfo" + ")" + "VALUES(?,?,?,?)";

        try {
            PreparedStatement app = getDbConnection().prepareStatement(insert);
            app.setString(1, tariff.getTariffName());
            app.setString(2, tariff.getTariffSale());
            app.setString(3, tariff.getTariffPrice());
            app.setString(4, tariff.getTariffInfo());

            app.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void deleteUser(User user){
        String  delete = "DELETE FROM into" + Const.USER_TABLE + " WHERE " + Const.USERS_LOGIN + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(delete);

            app.setString(1, user.getUserLogin());

            app.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addMoney(User user, ResultSet res) throws SQLException {
        ResultSet resSet = null;
        int money = 0;
        money += parseInt(res.getString("money"));
        money += parseInt(user.getUserMoney());
        user.setUserMoney(String.valueOf (money));
        String select1 = "UPDATE " + "into" + Const.USER_TABLE + " SET " + "money " + "=?" + " WHERE "
                + Const.USERS_LOGIN + " =?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select1);
            app.setString(1,user.getUserMoney());
            app.setString(2,user.getUserLogin());
            app.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getOrder(Order order) throws SQLException {
        ResultSet resSet = null;
        System.out.println(order.getId());
        String select = "SELECT * FROM " + "into" + Const.ORDERS_TABLE + " WHERE " +
                Const.ORDER_ID + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            app.setString(1, order.getId());

            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        resSet.next();
        System.out.println(resSet.getString("login") + resSet.getString("idorders"));
        String name = resSet.getString("login")+
                resSet.getString("idorders");
        ResultSet res1 = null;
        select = "SELECT * FROM " + "into"  + name;
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            res1 = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res1;
    }

    public void changePassword(User user){
        ResultSet resSet = null;
        String select1 = "UPDATE " + "into" + Const.USER_TABLE + " SET " + "password " + "=?" + " WHERE "
                + Const.USERS_LOGIN + " =?";
        try {
            System.out.println(user.getUserPassword());
            PreparedStatement app = getDbConnection().prepareStatement(select1);
            app.setString(1,user.getUserPassword());
            app.setString(2,user.getUserLogin());
            app.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changeTariff(User user){
        ResultSet resSet = null;
        String select1 = "UPDATE " + "into" + Const.USER_TABLE + " SET " + "tariffID " + "=?" + " WHERE "
                + Const.USERS_LOGIN + " =?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select1);
            app.setString(1,user.getUserTariff());
            app.setString(2,user.getUserLogin());
            System.out.println(user.getUserLogin() + user.getUserTariff());
            app.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet allUsers(User user){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + "into"+ Const.USER_TABLE;
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);
            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet checkUser(User user) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + "into"+ Const.USER_TABLE + " WHERE " +
                Const.USERS_LOGIN + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            app.setString(1, user.getUserLogin());

            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet signCheak(User user) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + "into"+ Const.USER_TABLE + " WHERE " +
                Const.USERS_LOGIN + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            app.setString(1, user.getUserLogin());
            System.out.println(user.getUserLogin());

            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet signInUser(User user) {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + "into"+ Const.USER_TABLE + " WHERE " +
                Const.USERS_LOGIN + "=? AND " + Const.USERS_PASSWORD + "=?";
        try {
            PreparedStatement app = getDbConnection().prepareStatement(select);

            app.setString(1, user.getUserLogin());
            app.setString(2, user.getUserPassword());
            System.out.println(user.getUserLogin());

            resSet = app.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }
}