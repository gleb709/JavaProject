package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OrderInfoController {

    private ObservableList<Order> orders = FXCollections.observableArrayList();
    private ArrayList<String> list = new ArrayList<String>();
    private String userName;
    private int userKey;

    public int getUserKey() {
        return userKey;
    }

    public void setUserKey(int userKey) {
        this.userKey = userKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button stepBackButton;

    @FXML
    private Button openOrderButton;

    @FXML
    private TextField orderButton;

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, String > orderID;

    @FXML
    private TableColumn<Order, String > orderPrice;

    @FXML
    private TableColumn<Order, String > dateButton;

    @FXML
    private Button textButton;

    @FXML
    private TextArea tButton;

    @FXML
    void initialize() throws InterruptedException {
        orderID.setCellValueFactory(new PropertyValueFactory<Order, String>("id"));
        orderPrice.setCellValueFactory(new PropertyValueFactory<Order, String>("cost"));
        dateButton.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));

        table();

        openOrderButton.setOnAction(actionEvent -> {
            String id = orderButton.getText().trim();
            int flag = 0;
            int flag1 = 0;
            try {
                Integer.parseInt(id);
            } catch(Exception e) {
                flag = 1;
            }
            if (flag == 0) {
                setUserId(id);
                for ( int i = 0; i < orders.size(); i++){
                    if(id.equals(orders.get(i).getId()))
                        flag1 = 1;
                }
                if(flag1 == 1) {
                    Order order = new Order();
                    Connection connect = new Connection();
                    Object obj = new Object();

                    order.setId(id);

                    try {
                        obj = connect.Connect(order, "getOrder");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ArrayList<OrderInfo> product1 = (ArrayList<OrderInfo>) obj;
                    System.out.println(product1.size());
                    Iterator<OrderInfo> iterator = product1.listIterator();
                    File file = new File(id + getUserName() + ".txt");
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        FileWriter f1 = new FileWriter(file);
                        int count = 0;
                        f1.write("_______________________________________________________________________________\n");
                        f1.write("|                   Отчет о покупке товаров ZooWorld                          |\n");
                        f1.write("|_____________________________________________________________________________|\n");
                        f1.write("|         Наименование         | Цена ед.| Кол-во |   Цена  |  Производитель  |\n");
                        while (iterator.hasNext()) {
                            f1.write("|------------------------------|---------|--------|---------|-----------------|\n");
                            f1.write("|" + corrector("productName", product1.get(count).getProductName()) + "|"
                                    + corrector("productCost", product1.get(count).getProductCost()) + "|" + corrector("count", product1.get(count).getCount()) + "|"
                                    + corrector("price", product1.get(count).getPrice()) + "|" + corrector("productInfo", product1.get(count).getProductInfo()) + "|\n");
                            count++;
                            iterator.next();
                        }

                        System.out.println("Отчет создан");
                        setUserKey(1);
                        f1.write("|______________________________|_________|________|_________|_________________|\n");
                        f1.flush();
                        f1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Shake button = new Shake(openOrderButton);
                    button.playAnim();
                }
            }else{
                Shake button = new Shake(openOrderButton);
                button.playAnim();
            }
        });

       stepBackButton.setOnAction(actionEvent -> {
            Stage stage1 = (Stage) stepBackButton.getScene().getWindow();
            stage1.close();
        });

        textButton.setOnAction(actionEvent -> {
            String id = orderButton.getText().trim();
            if(getUserKey() == 1) {
                TextController textController = new TextController();
                FXMLLoader loader = new FXMLLoader();
                textController.setUserName(getUserId() + getUserName());
                loader.setLocation(getClass().getResource("/sample/Text.fxml"));
                loader.setController(textController);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }else{
                Shake button = new Shake(textButton);
                button.playAnim();
            }
        });
    }

    public String corrector(String str1, String str2){
        int key = 0;
        switch (str1){
            case "productName":{
                key = 29;
                break;
            }
            case "productCost":{
                key = 8;
                break;
            }
            case "count":{
                key = 7;
                break;
            }
            case "price":{
                key = 8;
                break;
            }
            case "productInfo":{
                key = 16;
                break;
            }
        }
        String strNew= new String();
        for(int i = 0; i <= key-str2.length(); i++){
            strNew += " ";
        }
        strNew+=str2;
        return strNew;
    }

    public void table() throws InterruptedException {

        list.clear();

        Connection connect = new Connection();
        Order order = new Order();

        order.setCost(getUserName());

        Object obj = connect.Connect(order, "userOrders");

        ArrayList<Order> product1 = (ArrayList<Order>) obj;
        Iterator<Order> iterator = product1.listIterator();
        int count = 0;
        while(iterator.hasNext()){
            list.add(product1.get(count).getId());
            orders.add(new Order(product1.get(count).getId(),
                    product1.get(count).getCost(),product1.get(count).getDate(),
                    product1.get(count).getTime()));
            iterator.next();
            count++;
        }
        ordersTable.setItems(orders);

    }
}